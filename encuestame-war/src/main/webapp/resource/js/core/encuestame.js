if (typeof dojo != "undefined") {
		
	var ENME = (function() {

		var isInitialised = false;

		var fn = {
			
			STATUS : ['SUCCESS','FAILED', 'STAND_BY', 'RE_SCHEDULED', 'RE_SEND'],
			
			SURVEYS : ['TWEETPOLL', 'POLl', 'SURVEY', 'HASHTAG'],
			
			IMAGES_SIZE : {
			    thumbnail : "thumbnail",
			    defaultType : "default",
			    profile : "profile",
			    preview : "preview",
			    web : "web"
			},
				
			params : {},

			/**
			 * Returns an HTMLElement reference.
			 * 
			 * @method $
			 * @param {String |
			 *            HTMLElement |Array} el Accepts a string to use as an
			 *            ID for getting a DOM reference, an actual DOM
			 *            reference, or an Array of IDs and/or HTMLElements.
			 * @return {HTMLElement | Array} A DOM reference to an HTML element
			 *         or an array of HTMLElements.
			 */
			$ : dojo,
			
			_$self : this,
	
			/**
			 * 
			 */
			log : function(obj) {
				if (typeof console != "undefined" && console.log ) { //TODO: Add verbose condition.
					console.log(obj);
				}
			},
	
			/**
			 * 
			 */
			stopEvent : function(e) {
				this.$.stopEvent(e);
				return false; 
			},

			/**
			 * 
			 */
			include : function(url) {
				if (!this.contains(included, url)) {
					included.push(url);
					var s = document.createElement("script");
					s.src = url;
					this.$.query("body").append(s);
				}
			},


			toggleClassName : function(element, className) {
				if (!(element = this.$,query(element))) {
					return;
				}
				element.toggleClass(className);
			},


			setVisible : function(element, show) {
				if (!(element = this.$(element))) {
					return;
				}
				var $ = this.$.query;
				$(element).each(function() {
					var isHidden = $(this).hasClass("hidden");
					if (isHidden && show) {
						$(this).removeClass("hidden");
					} else if (!isHidden && !show) {
						$(this).addClass("hidden");
					}
				});
			},

			isVisible : function(element) {
				return !this.$.query(element).hasClass("hidden");
			},


			init : function() {
				var ENME = this;
				this.$.query("#header input[type='hidden']").forEach(
								function() {
									var value = this.value, id = this.title
											|| this.id;
									ENME.params[id] = (value
											.match(/^(tru|fals)e$/i) ? value
											.toLowerCase() == "true" : value);
								});
				isInitialised = true;
			},

			/**
			 * Finds the index of an element in the array.
			 */
			indexOf : function(array, item, fromIndex) {
				var length = array.length;
				if (fromIndex == null) {
					fromIndex = 0;
				} else {
					if (fromIndex < 0) {
						fromIndex = Math.max(0, length + fromIndex);
					}
				}
				for ( var i = fromIndex; i < length; i++) {
					if (array[i] === item)
						return i;
				}
				return -1;
			},
			
			/**
			 * Looks for an element inside the array. 
			 */
			contains : function(array, item) {
				return this.indexOf(array, item) > -1;
			},

			/**
			 * Includes firebug lite for debugging in IE. Especially in IE. 
			 * @method firebug
			 * @usage Type in addressbar "javascript:alert(ENME.firebug());"
			 */
			firebug : function() {
				var script = this.$(document.createElement("script"));
				script.attr("src", "http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js");
				this.$("head").append(script);
				(function() {
					if (window.firebug) {
						firebug.init();
					} else {
						setTimeout(arguments.callee, 0);
					}
				})();
			},
			
			/**
			 * Clones the element specified by the selector and removes the id
			 * attribute.
			 * @param selector a jQuery selector
			 */
			clone : function(selector) {
				var x = this.$.query(selector),
				c = x.clone();
				c.removeAttr("id");
				return x;
			},
			
			/**
			 * 
			 */
			_serviceHander :  function(response, ioargs) {
			    //encuestame.filter.response(response);
			    //console.info(ioargs.xhr.status, error);
			    var message = "";
			    switch (ioargs.xhr.status) {
			    case 200:
			        message = "Good request.";
			        break;
			    case 404:
			        message = "The page you requested was not found.";
			        //encuestame.error.createDialog(message, message);
			        break;
			    case 400:
			        message = "Bad Request";
			        //encuestame.error.createDialog(message, message);
			        break;
			    case 500:
			        break;
			        message = "Service temporarily unavailable.";
			        //encuestame.error.createDialog(message, message);
			        break;
			    case 407:
			        message = "You need to authenticate with a proxy.";
			        //encuestame.error.createDialog(message, message);
			        break;
			    case 0:
			        message = "A network error occurred. Check that you are connected to the internet.";
			        //encuestame.error.conexion(message);
			        break;
			    default:
			        message = "An unknown error occurred";
			        //encuestame.error.unknown(message, ioargs.xhr.status);
			    }
			},
			
			//params, service, load, error, loadingFunction
			/**
			 * Make a GET json call to backend.
			 */
			sGet : function(options) {
					var defaultOptions = {
						handleAs : "json",
						failOk : true,
						timeout : 60000,
						preventCache : true,
					};
			    	params = options.params || {};
			    	service = options.service || "";
			        var errorFunction = (options.error == null  ? this.$.hitch(this, function(errorMessage) {
			        	 //this.infoMesage(errorMessage);
			        	 _$self.log(errorMessage);
			        }) : error);
			        if (this.$.isFunction(load)) {
			        	//encuestame.service.GET(service, params, load, errorFunction, loadingFunction);
			        	var innerLoad = this.$.hitch(this, function(data) {
				    		options.loadingFunction == null ? "" : options.loadingFunction.end();
				    		if (this.$.isFunction(load)) {
				    			load(data);
				    		}
				    	});
				    	// initialize the loading
				        options.loadingFunction == null ? "" : options.loadingFunction.init();
				        var argsGet = {
				                url : url,
						        handleAs : defaultOptions.handleAs,
						        failOk : defaultOptions.failOk, //Indicates whether a request should be allowed to fail
						        // (and therefore no console error message in the event of a failure)
						        timeout : defaultOptions.timeout,
						        content: params,
						        load: innerLoad,
						        preventCache: defaultOptions.preventCache,
						        error: error,
						        handle : _serviceHander
				       }
				       this.$.xhrGet(argsGet);
			        }
			 },
			
			sPost : function(options) {
				
			},
			
			error : function(){
				
			},
			
			redirect : function(){
				
			},
			
			isEmtpy : function() {
				 return (!str || 0 === str.length);
			},
			
			hashtagContext : function(hashtagName){
				 if (hashtagName) {
				        var url = encuestame.contextDefault;
				        url = url.concat("/tag/");
				        url = url.concat(hashtagName);
				        url = url.concat("/");
				        return url;
				    } else {
				        throw new Error("hashtag name is required");
				   }
			},
			
			shortAmmount : function(quantity){
				if (typeof(quantity) === "number") {
			        quantity = ( quantity < 0 ? 0  : quantity);
			        var text = quantity.toString();
			        // 5634 --> 5634k
			        if (quantity > 1000) {
			            var quantityReduced = Math.round(quantity / 100);
			            text = quantityReduced.toString();
			            text = text.concat("K");
			        } 
					return text;
			    } else {
			        throw new Error("invalid number");
			    }
			},
			
			namespace : function(ns_string) {
			    var parts = ns_string.split('.'), parent = ENME, i;
			    // strip redundant leading global
			    if (parts[0] === "ENME") {
			        parts = parts.slice(1);
			    }
			    for (i = 0; i < parts.length; i += 1) {
			        // create a property if it doesn't exist
			        if (typeof parent[parts[i]] === "undefined") {
			            parent[parts[i]] = {};
			        }
			        parent = parent[parts[i]];      
			    }
			    return parent;
			},
			
			getSession : function(){
			    //JSESSIONID=dh3u2xvj7fwd1llbddl33dhcq; path=/encuestame; domain=demo2.encuestame.org
			    var sessionCookie = this.$.cookie("JSESSIONID");
			    if (sessionCookie == undefined) {
			        //encuestame.error.session(encuestame.error.messages.denied);
			    } else {
			        console.info("session is valid");
			    }
			},
			
			shortPicture : function(provider){
			     var url = encuestame.contextDefault + "/resources/images/social/"+provider.toLowerCase()
	               +"/enme_icon_"+provider.toLowerCase()+".png";
			     return url;
			},
			
			/**
			 * 
			 */
			fromNow : function(date, format) {				
				return moment(date, format).fromNow();
			}
		};

		return fn;

	})();
	
	dojo.addOnLoad(function() {
		ENME.init();
	});
	
}

