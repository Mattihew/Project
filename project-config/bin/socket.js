var socket = require('socket.io');

module.exports = function(server)
{
    var io = socket.listen(server);
    io.on('connection', function(socket)
    {
        socket.on('join', function(room)
        {

        });
    });
};
