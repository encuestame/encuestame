ENME.namespace("ENME.messages");

/*
 * List of constants used on user interface.
 */
ENME.messages = ( function(d, _e) {
		// channel to publish
		var channel = '/encuestame/message/publish';
		// get the configuration default
		var duration = _e.config('message_delay') || 5000;
		// message type
		var	messageTypes = {
			MESSAGE: "message",
			WARNING: "warning",
			ERROR: "error",
			FATAL: "fatal"
		};
		
		/**
		 * 
		 */
		var _publish = function(message, description, type) {
			description === null ? "" : description;
			if (typeof(message === 'string')) {
				  d.publish(channel, [{
					  message: message, 
					  type: type, 
					  duration: duration
				  }]);
			}
		};
	
		var fn = {
				success : function (message, description) {
					_publish(message, description, messageTypes.MESSAGE);
				},
				
				warning : function (message, description) {
					_publish(message, description, messageTypes.WARNING);
				},
				
				error : function (message, description) {
					_publish(message, description, messageTypes.ERROR);
				},
				
				fatal : function (message, description) {
					_publish(message, description, messageTypes.FATAL);
				}
		};
			
		return fn;
})(dojo, ENME);