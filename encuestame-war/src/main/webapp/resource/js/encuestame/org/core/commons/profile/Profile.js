dojo.provide("encuestame.org.core.commons.profile.Profile");

dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.Form");
dojo.require("dojox.form.BusyButton");

dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 *
 */
dojo.declare(
    "encuestame.org.core.commons.profile.Profile",
    [encuestame.org.main.EnmeMainLayoutWidget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.profile", "templates/profile.html"),
        
        /**
         * 
         */
        _openBox : false,
        
        username : "",
        
        email : "",
        
        complete_name : "",
        	
        language : "",
        
        /**
         * i18n Message.
         */
        i18nMessage : {
        	settings_config_profile_title : ENME.getMessage("settings_config_profile_title"),
        	settings_config_profile_description : ENME.getMessage("settings_config_profile_description"),
        	settings_config_profile_email : ENME.getMessage("settings_config_profile_email"),
        	settings_config_profile_email_description : ENME.getMessage("settings_config_profile_email_description"),
        	settings_config_profile_username : ENME.getMessage("settings_config_profile_username"),
        	settings_config_profile_username_description : ENME.getMessage("settings_config_profile_username_description"),
        	settings_config_profile_complete_name : ENME.getMessage("settings_config_profile_complete_name"),
        	settings_config_profile_language : ENME.getMessage("settings_config_profile_language"),
        	e_005 :  ENME.getMessage("e_005"),
        	commons_update :  ENME.getMessage("commons_update")
        },        

        /**
         *
         */
        postCreate : function() {
        	// create subcribe to display errors.
        	dojo.subscribe("/encuestame/settings/profile/message", this, this._displayMessage);    
        	this.events();
        },
        
        /**
         * 
         */
        events : function() {
            var email = dijit.byId("email");
            email.onChange = dojo.hitch(this, function() {
                //console.debug("change");
                email.validateBackEnd("email");
            });
            email.onKeyPress = dojo.hitch(this, function() {
            	console.info("PRESSS");
            });
            email.onKeyDown = dojo.hitch(this, function() {
            	console.info("onKeyDown");
            });            
            var username = dijit.byId("username");
            username.onChange = dojo.hitch(this, function() {
                //console.debug("change");
                username.validateBackEnd("username");
            });
        },
        
        /**
         * 
         */
        _displayMessage : function(message, type) {
        	if (type === 'error') {
        		this.errorMessage(message);
        	} else if (type === 'success') {
        		this.successMesage(message);
        	}
        },

        /*
         * get profile.
         */
        _getMyProfile :function() {
            var load = dojo.hitch(this, function(response) {
                if (response.success) {
                    var profile = response.success.account;
                    if (profile != null) {
                        var email = dijit.byId("email");
                        email.set('value', profile.email);
                        console.info("EMAIL");
                        var username = dijit.byId("username");
                        username.set('value', profile.username);
                        console.info("USERNAMe");
                        var completeName = dijit.byId("completeName");
                        completeName.set('value', profile.name);
                    }
                }
                // attach the events
                //console.debug("EVENTS");
                this.events();
            });
            var error = function(error) {
                console.error("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.list.profile.my, {}, load, error);
        },

        /*
         * update profile.
         */
        _updateProfile : function(event) {
            dojo.stopEvent(event);
            var form = dojo.byId("profileForm");
            //console.debug("form ", form);
            var formDijit = dijit.byId("profileForm");
            //console.debug("form", formDijit);
            if (formDijit.isValid()) {
                var load = dojo.hitch(this, function(data) {
                    //console.debug(data);
                	if ("success" in data) {
                		var message = data.success.message;
                		if (message != 'undefined') {
                			dojo.publish('/encuestame/settings/profile/message', [message, 'success']);
                		}
                	}
                	this._submit.cancel();
                });
                var error = dojo.hitch(this,function(error) {
                    //console.debug("error", error);
                	dojo.publish('/encuestame/settings/profile/message', [error.message, 'error']);
                	this._submit.cancel();
                });
                //var query = {};
                //query.username =  dijit.byId("username").get("value") ;
                //query.email = dijit.byId("email").get("value");
                //var queryStr = dojo.objectToQuery(query);
                encuestame.service.xhrPost(encuestame.service.list.updateProfile, form, load, error, true);
            } else {
            	dojo.publish('/encuestame/settings/profile/message', [ENME.getMessage('settings_config_profile_form_not_valid'), 'error']);
            }
        }
});

dojo.extend(dijit.form.ValidationTextBox, {


//
//    isValid: function(/*Boolean*/ isFocused){
//        // summary:
//        //		Tests if value is valid.
//        //		Can override with your own routine in a subclass.
//        // tags:
//        //		protected
//        var defaultValid =  this.validator(this.textbox.value, this.constraints);
//        var backEndValid =  true;
//        if(isFocused){
//            backEndValid = this.validateBackEnd(this.textbox.value);
//        }
//        return (defaultValid && backEndValid);
//    },

    /*
     * validate back end.
     */
    validateBackEnd : function(type) {
        if (type != null) {
            var load = dojo.hitch(this, function(response) {
                if (!response.success.validate) {
                    var message = response.success.messages[type];
                     //console.debug("Error", message);
                    this.invalidMessage = message;
                    this.getErrorMessage(true);
                    this._maskValidSubsetError = true;
                    this.displayMessage(message);
                    //console.debug("set error message");
                    dojo.publish('/encuestame/settings/profile/message', [message, 'error']);
                } else {
                	 var message = response.success.messages[type] || "Not Defined";
                	 dojo.publish('/encuestame/settings/profile/message', [message, 'success']);
                }                
            });
            var error = function(error) {
                //console.debug("error", error);
                //return false;
            };
            encuestame.service.xhrGet(encuestame.service.list.checkProfile, {type:type, value: this.textbox.value}, load, error);
        }
    }
});
