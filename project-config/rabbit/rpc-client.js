var amqp = require('amqplib');
var config = require('../config.json');

var submitter;
var receivers = {};

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
        ch.assertExchange(config.rabbit.ex, 'fanout', {durable: false, autoDelete: true})
        .then(function(ex)
        {
            submitter = function(msg, corr)
            {
                ch.publish(ex.exchange, '', Buffer.from(msg, 'UTF-8'), {correlationId: corr.toString(), replyTo: q.queue});
            };
        });

        ch.consume(q.queue, function(msg)
        {
            if (receivers.hasOwnProperty(msg.properties.correlationId))
            {
                receivers[msg.properties.correlationId](msg.content.toString('UTF-8'));
                delete receivers[msg.properties.correlationId];
            }
            ch.ack(msg);
        },{noAck: false});
    });
});

function request(msg, callback, corr)
{
    if (typeof callback === 'function')
    {
        corr = corr || Math.random();
        receivers[corr] = callback;
    }
    submitter(msg, corr);
}

module.exports = request;
