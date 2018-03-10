var noble = require('noble');
var amqp = require('amqplib');
var nodeCleanup = require('node-cleanup');
var config = require('./config.json');

var channel;
amqp.connect(config.rabbit.url)
.then(function(conn)
{
    process.once('SIGINT', conn.close.bind(conn));
    return conn.createChannel();
})
.then(function (ch)
{
    return ch.assertExchange(config.rabbit.exchange, 'fanout', {durable: false})
    .then(function (ex) {
        ch.publish(ex.exchange, '', Buffer.from('hello world', 'UTF-8'));
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
nodeCleanup(function()
{
    noble.stopScanning();
},{
    ctrl_C: "Exited"
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
