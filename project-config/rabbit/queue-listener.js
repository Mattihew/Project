var amqp = require('amqplib');
var config = require('../config');

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
    return ch.assertQueue('', {exclusive:true, durable:false, autoDelete:true})
    .then(function(q)
    {
        return ch.assertExchange(config.rabbit.listen.ex, 'fanout', {durable: false})
        .then(function(ex)
        {
            return ch.bindQueue(q.queue, ex.exchange, '');
        })
        .then(function()
        {
            return ch.consume(q.queue, function(msg)
            {
                console.info(JSON.parse(msg.content.toString('UTF-8')).rssi);
                //TODO
                ch.ack(msg);
            },{noAck:false});
        }).catch(console.warn);

    });
}).catch(console.warn);

module.exports = exports;
