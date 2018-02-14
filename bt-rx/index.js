var noble = require('noble');
var amqp = require('amqplib/callback_api');
var config = require('./config.json');

var channel;
amqp.connect(config.rabbit.url, function(err, conn)
{
    return conn.createChannel(function (err, ch)
    {
        channel = ch;
        channel.assertExchange(config.rabbit.exchange, 'fanout', {durable: false});
        channel.publish(config.rabbit.exchange, '', Buffer.from('hello world', 'UTF-8'));
    });
});

noble.on('stateChange', function(state)
{
    if (state === 'poweredOn')
    {
        noble.startScanning([config.serviceUUID], true);
    }
    else
    {
        noble.stopScanning();
    }
});

noble.on('discover', function(perf)
{
    console.log('id: ' + perf.id);
    console.log("name: " + perf.advertisement.localName);
    console.log('uuid: '+ perf.advertisement.serviceUuids);
    console.log('RSSI: ' + perf.rssi);
    console.log('TX: ' + perf.advertisement.txPowerLevel);
    console.log('address: ' + perf.address);
    console.log('------------------------');
    if (typeof channel !== 'undefined')
    {
        var data = {id: config.id, device: perf.id, rssi: perf.rssi};
        channel.publish(config.rabbit.exchange, '', Buffer.from(JSON.stringify(data), 'UTF-8'));
    }
});
