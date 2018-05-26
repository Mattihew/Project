var express = require('express');
var router = express.Router({});
var queue = require('../rabbit/queue-listener');

/* GET home page. */
router.get('/', function (req, res)
{
    res.render('feed', {});
});

module.exports = router;
