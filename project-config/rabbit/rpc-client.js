var amqp = require('amqplib');
var config = require('../config.json');

var submitter;
var receivers = {};
var timeouts = {};

amqp.connect(config.rabbit.url)
.then(function(conn)
{
    process.on('exit', conn.close.bind(conn));
    process.once('SIGINT', conn.close.bind(conn));
    console.log('sigint bound');
    return conn.createChannel();
})
.then(function(ch)
{
    ch.assertQueue('', {exclusive: true, durable: false, autoDelete: true})
    .then(function(q)
    {
        ch.assertExchange(config.rabbit.rpc.ex, 'fanout', {durable: false, autoDelete: true})
        .then(function(ex)
        {
            submitter = function(msg, corr)
            {
                ch.publish(ex.exchange, '', Buffer.from(msg, 'UTF-8'), {correlationId: corr, replyTo: q.queue});
            };
        });

        ch.consume(q.queue, function(msg)
        {
            var corr = msg.properties.correlationId;
            if (timeouts.hasOwnProperty(corr))
            {
                clearTimeout(timeouts[corr]);
                delete timeouts[corr];
            }
            if (receivers.hasOwnProperty(corr))
            {
                receivers[corr](msg.content.toString('UTF-8'));
                delete receivers[corr];
            }
            ch.ack(msg);
        },{noAck: false});
    });
}).catch(console.warn);

function request(msg, callback, err)
{
    var corr = Math.random().toString().substr(2);
    if (typeof callback === 'function')
    {
        receivers[corr] = callback;
        if (typeof err === 'function')
        {
            timeouts[corr] = setTimeout(function(){
                delete timeouts[corr];
                err();
            }, config.rabbit.rpc.timeout);
        }
    }
    if (typeof submitter === 'function')
    {
        submitter(msg, corr);
    }
    else if (typeof err === 'function')
    {
        err();
    }
}

module.exports = exports = request;
