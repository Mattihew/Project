var noble = require('noble');
var ignore = ['48d6d5ef5178', 'e4f0421d6ac2', '807abf11e20c', '5460095ffb07', '5460097fc0c7'];

noble.on('stateChange', function(state)
{
    if (state === 'poweredOn')
    {
        noble.startScanning();
    }
    else
    {
        noble.stopScanning();
    }
});

noble.on('discover', function(perf)
{
    if(ignore.indexOf(perf.id) === -1)
    {
        console.log('id: ' + perf.id);
        console.log('RSSI: ' + perf.rssi);
        console.log('address: ' + perf.address);
        console.log('------------------------');
    }
});
