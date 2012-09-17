define(["dojo", "me/core/enme"], function(dojo, _ENME) {
	
	//config context path
	var context_path = _ENME.config("contextPath");
	
	//
	var _appendContext = function(service) {
		var url = "";
		url.concat(context_path);
		url.concat(service);
		return url;
	};
	
	var _service_store = {
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.getNotifications" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/notifications/list.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json"),
	  "encuestame.service.list.userList" : _appendContext("/api/admon/users.json")
	};
	
	return {			
		service : function (key) {
			return _service_store[key];
		}				
	};
});