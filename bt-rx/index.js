'use strict';
var noble = require('noble');
var amqp = require('amqplib');
var nodeCleanup = require('node-cleanup');

var config = require('./config.json');
var Log = require('log');
var nobleLogger = new Log('debug');
var amqpLogger = new Log('debug');

var serialNumber;
require('serial-number')(function(e,v)
{
    serialNumber = e ? config.id : v;
    amqpLogger.debug('serial-number: ' + serialNumber);
});

var channel;
amqp.connect(config.rabbit.url)
.then(function(conn)
{
    process.once('SIGINT', conn.close.bind(conn));
    amqpLogger.debug("sigint bound");
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

var perfs = {};
noble.on('discover', function(perf)
{
    if (!perfs.hasOwnProperty(perf.id))
    {
        nobleLogger.info('new device "%s" found', perf.advertisement.localName);
        perfs[perf.id] = perf.advertisement.localName;
    }
    if(process.argv.length > 2 &&
       process.argv[2].indexOf(perf.advertisement.localName) !== -1)
    {
        //nobelLogger('id: %s', perf.id);
        nobleLogger.debug('device: %s', perf.advertisement.localName);
        //nobelLogger('uuid: %s', perf.advertisement.serviceUuids);
        nobleLogger.debug('RSSI: %s', perf.rssi);
        nobleLogger.debug('dist: %s', toDist(perf.rssi));
        nobleLogger.debug('TX: %s', perf.advertisement.txPowerLevel);
        nobleLogger.debug('------------------------');
    }

    if (typeof channel !== 'undefined')
    {
        var data = {id: serialNumber, device: perf.advertisement.localName || 'undefined', rssi: perf.rssi, time: Date.now()};
        channel.publish(config.rabbit.exchange, '', Buffer.from(JSON.stringify(data), 'UTF-8'), {timestamp:data.time});
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
