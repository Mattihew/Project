var noble = require('noble');
var amqp = require('amqplib/callback_api');
var config = require('./config.json');

var channel;
amqp.connect(config.rabbit.url, function(err, conn)
{
    return conn.createChannel(function (err, ch)
    {
        ch.assertExchange(config.rabbit.exchange, 'fanout', {durable: false});
        ch.publish(config.rabbit.exchange, '', Buffer.from('hello world', 'UTF-8'));
        channel = ch;
    });
});

noble.on('stateChange', function(state)
{
    if (state === 'poweredOn')
    {
        noble.startScanning(config.serviceUUID, true);
    }
    else
    {
        noble.stopScanning();
    }
});

noble.on('discover', function(perf)
{
    //console.log('id: ' + perf.id);
    console.log("device: " + perf.advertisement.localName);
    //console.log('uuid: '+ perf.advertisement.serviceUuids);
    console.log('RSSI: ' + perf.rssi);
    console.log('dist: ' + toDist(perf.rssi));
    console.log('TX: ' + perf.advertisement.txPowerLevel);
    console.log('------------------------');
    if (typeof channel !== 'undefined')
    {
        var data = {id: config.id, device: perf.advertisement.localName, rssi: perf.rssi, time: Date.now()};
        channel.publish(config.rabbit.exchange, '', Buffer.from(JSON.stringify(data), 'UTF-8'));
    }
});

function toDist(rssi)
{
    var txPower = -59;
    if (rssi === 0)
    {
        return -1;
    }
    var ratio = rssi/txPower;
    if (ratio < 1)
    {
        return Math.pow(ratio, 10);
    }
    else
    {
        return (0.89976) * Math.pow(ratio, 7.7095) + 0.111;
    }
}
