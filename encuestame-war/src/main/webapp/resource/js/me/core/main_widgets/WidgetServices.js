define([ "dojo/_base/declare", 
		 "me/core/URLServices",
		 "dijit/registry",
		 "me/core/enme"], function(
		declare,
		_URL,
		registry,
		_ENME) {
	
	return declare(null, {		
		
	    /**
	     * Create a modal box.
	     */
	    _createModalBox : function(type, handler) {
	    	var modal = dojo.byId("modal-box");
	    	if (modal != null) {
	    		var modalBox = new encuestame.org.core.commons.dialog.ModalBox(dojo.byId("modal-box"), type, dojo.hitch(handler));
	    		return modalBox;
	    	} else {
	    		return null;
	    	}
	    },
	    
	    /**
	     * Display the loading process.
	     */
	    loading_show : function (message) {
	    	var loading = registry.byId("loading");
	    	if ( loading != null) {
	    		loading.show(message);
	    	}
	    },
	    
	    /**
	     * Hide the loading process.
	     */
	    loading_hide : function () {
	    	var loading = registry.byId("loading");
	    	if ( loading != null) {
	    		loading.hide();
	    	}
	    },    
	
	    /**
	     * Publish a message on the context
	     * @param message the message
	     * @param type error, warning, info, success
	     */
	    publishMessage : function (message, type, desc) {
	    	if (type === 'success') {
	    		this.successMesage(message, desc);
	    	} else if (type === 'error') {
	    		this.errorMessage(message, desc);
	    	} else if (type === 'warn') {
	    		this.warningMesage(message, desc);
	    	} else if (type === 'fatal') {
	    		this.fatalMesage(message, desc);
	    	}
	    },
	
	    /**
	     * Display a success message.
	     */
	    successMesage : function(message, description) {
	        //console.info("Successfull message");
	        ENME.messages.success(message, description);
	    },
	
	    /**
	     * Display a warning message.
	     */
	    warningMesage : function(message, description) {
	    	ENME.messages.warning(message, description);
	    },
	    
	    /**
	     * Display a warning message.
	     */
	    errorMessage : function(message, description) {
	    	ENME.messages.success(message, description);
	    },    
	    
	   /**
	    * Display a fatal message
	    */
	   fatalMesage : function(message) {
		   ENME.messages.fatal(message, description);
	   },    
	    
	    /**
	     * Display a default loader.
	     */
	    loadingDefaultMessage : function() { 
	 		var loading = {
	 		  	  init : function(){
	 		  				console.debug("init");
	 		  	  }, 
	 		  	  end : function(){
	 		  	      		console.debug("end");
	 		  	  }
	 		};
	 		return loading;
	    },
	    
	    /**
	     * Retrieve the default services response.
	     * Succesfull === {"error":{},"success":{"r":0}}
	     * or 
	     * Failed === {"error":{},"success":{"r":-1}}
	     */
	    getDefaultResponse : function(data) {
	    	if ("success" in data) {
	    		var r = parseInt(data.success.r);
	    		if (r === 0) {
	    			return true;
	    		} else {
	    			return false;
	    		}
	    	} else {
	    		return false;
	    	}
	    },	
	
	    /*
	     *
	     */
	    errorMesage : function(error) {
	        var modal = this._createModalBox("alert", null);
	        if (modal != null) {
	        	modal.show(error == null ? ENME.getMessage("e_023") : error);
	        }
	    },
	
	    /*
	     *
	     */
	    infoMesage : function(info) {
	         var modal = this._createModalBox("alert", null);
	         if (modal != null) {
	        	 modal.show(info);
	         }
	    },
	    
	    /**
	     * Make a POST call to backend via JSON service.
	     * @param params {Object} service's parameters
	     * @param load {Function} function to load if the request is success
	     * @param service {String} the rest ful service.
	     * @param loadingFunction {Function} overwrite the default loading
	     * @param error function to trigger a error if the request is failed
	     */
	    callPOST : function(params, load, service, loadingFunction, error) {
	    	 params = params || {};
	    	service = service || "";
	    	 var errorFunction = (error == null  ? dojo.hitch(this, function(errorMessage) {
	        	 this.infoMesage(errorMessage);
	        }) : error);
	    	if (dojo.isFunction(load)) {
	    		 encuestame.service.xhrPostParam(service, params, load, errorFunction, null, loadingFunction);
	    	}
	     },
	     
	    /**
	     * Make a GET call to backend via JSON service.
	     * @param params {Object} service's parameters
	     * @param load {Function} function to load if the request is success
	     * @param service {String} the rest ful service.
	     * @param loadingFunction {Function} overwrite the default loading
	     * @param error function to trigger a error if the request is failed
	     * 
	     * url, params, load, error, logginHandler
	     */
	    callGET : function(params, service, load, error, loadingFunction) {
	    	 params = params || {};
	    	service = service || "";
	        var errorFunction = (error == null  ? dojo.hitch(this, function(errorMessage) {
	        	 this.infoMesage(errorMessage);
	        }) : error);
	        if (dojo.isFunction(load)) {
	        	encuestame.service.GET(service, params, load, errorFunction, loadingFunction);
	        }
	     },
	     
	     /**
	      * 
	      * @param e
	      */
	     stopEvent : function (e) {
	    	 ENME.stopEvent(e);
	     }
		
	});
});

///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//dojo.provide("encuestame.org.main.WidgetServices");
//
//dojo.require("encuestame.org.core.commons.dialog.ModalBox");
//
//dojo.declare("encuestame.org.main.WidgetServices", null, {
//
//    // constructor
//    constructor: function(){},
//
//    /**
//     * Create a modal box.
//     */
//    _createModalBox : function(type, handler) {
//    	var modal = dojo.byId("modal-box");
//    	if (modal != null) {
//    		var modalBox = new encuestame.org.core.commons.dialog.ModalBox(dojo.byId("modal-box"), type, dojo.hitch(handler));
//    		return modalBox;
//    	} else {
//    		return null;
//    	}
//    },
//    
//    /**
//     * Display the loading process.
//     */
//    loading_show : function (message) {
//    	var loading = registry.byId("loading");
//    	if ( loading != null) {
//    		loading.show(message);
//    	}
//    },
//    
//    /**
//     * Hide the loading process.
//     */
//    loading_hide : function () {
//    	var loading = registry.byId("loading");
//    	if ( loading != null) {
//    		loading.hide();
//    	}
//    },    
//
//    /**
//     * Publish a message on the context
//     * @param message the message
//     * @param type error, warning, info, success
//     */
//    publishMessage : function (message, type, desc) {
//    	if (type === 'success') {
//    		this.successMesage(message, desc);
//    	} else if (type === 'error') {
//    		this.errorMessage(message, desc);
//    	} else if (type === 'warn') {
//    		this.warningMesage(message, desc);
//    	} else if (type === 'fatal') {
//    		this.fatalMesage(message, desc);
//    	}
//    },
//
//    /**
//     * Display a success message.
//     */
//    successMesage : function(message, description) {
//        //console.info("Successfull message");
//        ENME.messages.success(message, description);
//    },
//
//    /**
//     * Display a warning message.
//     */
//    warningMesage : function(message, description) {
//    	ENME.messages.warning(message, description);
//    },
//    
//    /**
//     * Display a warning message.
//     */
//    errorMessage : function(message, description) {
//    	ENME.messages.success(message, description);
//    },    
//    
//   /**
//    * Display a fatal message
//    */
//   fatalMesage : function(message) {
//	   ENME.messages.fatal(message, description);
//   },    
//    
//    /**
//     * Display a default loader.
//     */
//    loadingDefaultMessage : function() { 
// 		var loading = {
// 		  	  init : function(){
// 		  				console.debug("init");
// 		  	  }, 
// 		  	  end : function(){
// 		  	      		console.debug("end");
// 		  	  }
// 		};
// 		return loading;
//    },
//    
//    /**
//     * Retrieve the default services response.
//     * Succesfull === {"error":{},"success":{"r":0}}
//     * or 
//     * Failed === {"error":{},"success":{"r":-1}}
//     */
//    getDefaultResponse : function(data) {
//    	if ("success" in data) {
//    		var r = parseInt(data.success.r);
//    		if (r === 0) {
//    			return true;
//    		} else {
//    			return false;
//    		}
//    	} else {
//    		return false;
//    	}
//    },	
//
//    /*
//     *
//     */
//    errorMesage : function(error) {
//        var modal = this._createModalBox("alert", null);
//        if (modal != null) {
//        	modal.show(error == null ? ENME.getMessage("e_023") : error);
//        }
//    },
//
//    /*
//     *
//     */
//    infoMesage : function(info) {
//         var modal = this._createModalBox("alert", null);
//         if (modal != null) {
//        	 modal.show(info);
//         }
//    },
//    
//    /**
//     * Make a POST call to backend via JSON service.
//     * @param params {Object} service's parameters
//     * @param load {Function} function to load if the request is success
//     * @param service {String} the rest ful service.
//     * @param loadingFunction {Function} overwrite the default loading
//     * @param error function to trigger a error if the request is failed
//     */
//    callPOST : function(params, load, service, loadingFunction, error) {
//    	 params = params || {};
//    	service = service || "";
//    	 var errorFunction = (error == null  ? dojo.hitch(this, function(errorMessage) {
//        	 this.infoMesage(errorMessage);
//        }) : error);
//    	if (dojo.isFunction(load)) {
//    		 encuestame.service.xhrPostParam(service, params, load, errorFunction, null, loadingFunction);
//    	}
//     },
//     
//    /**
//     * Make a GET call to backend via JSON service.
//     * @param params {Object} service's parameters
//     * @param load {Function} function to load if the request is success
//     * @param service {String} the rest ful service.
//     * @param loadingFunction {Function} overwrite the default loading
//     * @param error function to trigger a error if the request is failed
//     * 
//     * url, params, load, error, logginHandler
//     */
//    callGET : function(params, service, load, error, loadingFunction) {
//    	 params = params || {};
//    	service = service || "";
//        var errorFunction = (error == null  ? dojo.hitch(this, function(errorMessage) {
//        	 this.infoMesage(errorMessage);
//        }) : error);
//        if (dojo.isFunction(load)) {
//        	encuestame.service.GET(service, params, load, errorFunction, loadingFunction);
//        }
//     },
//     
//     /**
//      * 
//      * @param e
//      */
//     stopEvent : function (e) {
//    	 ENME.stopEvent(e);
//     }
//
//});
