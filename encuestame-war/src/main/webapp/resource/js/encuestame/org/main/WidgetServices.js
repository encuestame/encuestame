/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.main.WidgetServices");

dojo.require("encuestame.org.core.commons.dialog.ModalBox");

dojo.declare("encuestame.org.main.WidgetServices", null, {

    // constructor
    constructor: function(){},

    /*
     *
     */
    _delay_messages : 5000,

    /*
     *
     */
    _createModalBox : function(type, handler) {
        var modalBox = new encuestame.org.core.commons.dialog.ModalBox(dojo.byId("modal-box"), type, dojo.hitch(handler));
        return modalBox;
    },

    /*
     *
     */
    successMesage : function() {
        console.info("Successfull message");
        encuestame.messages.pubish(encuestame.constants.messageCodes["023"], "message", this._delay_messages);
    },

    /*
     *
     */
    warningMesage : function() {
        encuestame.messages.pubish(encuestame.constants.warningCodes["001"], "warning", this._delay_messages);
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
        modal.show(error == null ? encuestame.constants.errorCodes["023"] : error);
    },

    /*
     *
     */
    infoMesage : function(info) {
         var modal = this._createModalBox("alert", null);
         modal.show(info);
    },

    /*
     *
     */
    fatalMesage : function() {
        encuestame.messages.pubish(encuestame.constants.errorCodes["023"], "fatal", this._delay_messages);
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
