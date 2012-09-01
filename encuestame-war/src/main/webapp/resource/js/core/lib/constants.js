require(["dojo/parser", "ready!"], function(parser) {
    // Parse the page
    //parser.parse();
 
	ENME.namespace("ENME.CONST");
	
	/*
	 * List of constants used on user interface.
	 */
	ENME.CONST = ( function() {
			
			var fn = {
					SUCCESS : "success",
					
					// - Date Range parameters - //
					// last year
					YEAR  : '365', 
					// last 24 hours
					DAY : '24',
					// last 7 days
					WEEK : '7',
					// last 30 days
					MONTH : '30',
					// all item
					ALL : 'all',
					// Hashtag rated filter	
					HASHTAGRATED :	"HASHTAGRATED",
					// default status
					STATUS : ['SUCCESS','FAILED', 'STAND_BY', 'RE_SCHEDULED', 'RE_SEND'],
					// messages
					MSG : {
						SUCCESS : 'success',
						ERROR : 'error',
						WARN : 'warn',
						FATAL : 'fatal'
					},
					// type of surveys   
					TYPE_SURVEYS : ['TWEETPOLL', 'POLL', 'SURVEY', 'HASHTAG'],
					//image sizes
					IMAGES_SIZE : {
					    thumbnail : "thumbnail",
					    defaultType : "default",
					    profile : "profile",
					    preview : "preview",
					    web : "web"
					},
			};
				
			return fn;
	})();
});