var express = require('express');
var router = express.Router();

var rabbit = require('../rabbit/rpc-client');

/* GET users listing. */
router.get('/', function (req, res, next)
{
    rabbit("test", function(msg)
    {
        res.send(msg);
    }, function(){
        res.send("timeout");
    });
});

module.exports = router;
