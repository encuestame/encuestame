const server = require('./lib/server');
var port = process.env.PORT || 3000;
server.default.listen(port, function() {
	console.log(`listen ${port}`);
});