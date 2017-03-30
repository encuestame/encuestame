const server = require('./lib/server');
server.default.listen(3000, function(){
	console.log('listen 3000');
});