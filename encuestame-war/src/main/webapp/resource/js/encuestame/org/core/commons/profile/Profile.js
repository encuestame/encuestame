dojo.provide("encuestame.org.core.commons.profile.Profile");

dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.Form");

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
         *
         */
        postCreate : function() {
        	// create subcribe to display errors.
        	dojo.subscribe("/encuestame/settings/profile/message", this, this._displayMessage);                                   
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
        	console.debug("_displayMessage");
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
                });
                var error = function(error) {
                    //console.debug("error", error);
                };
                //var query = {};
                //query.username =  dijit.byId("username").get("value") ;
                //query.email = dijit.byId("email").get("value");
                //var queryStr = dojo.objectToQuery(query);
                encuestame.service.xhrPost(encuestame.service.list.updateProfile, form, load, error, true);
            } else {
                console.info("form not valid");
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
                	 dojo.publish('/encuestame/settings/profile/message', ["Updated", 'success']);
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
