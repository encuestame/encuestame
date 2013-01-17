define([
         "dojo",
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/ValidationTextBox",
         "dijit/form/Form",
         "dijit/form/Select",
         "dojo/_base/lang",
         "dijit/registry",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/URLServices",
         "me/core/enme",
         "dijit/form/Button",
         "dojo/text!me/web/widget/profile/templates/profile.html" ],
        function(
                dojo,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                ValidationTextBox,
                Form,
                Select,
                lang,
                registry,
                main_widget,
                URLServices,
                _ENME,
                BusyButton,
                 template) {


//dojo.require("dojox.form.BusyButton");

  lang.extend(ValidationTextBox, {

  //
//	    isValid: function(/*Boolean*/ isFocused){
//	        // summary:
//	        //		Tests if value is valid.
//	        //		Can override with your own routine in a subclass.
//	        // tags:
//	        //		protected
//	        var defaultValid =  this.validator(this.textbox.value, this.constraints);
//	        var backEndValid =  true;
//	        if(isFocused){
//	            backEndValid = this.validateBackEnd(this.textbox.value);
//	        }
//	        return (defaultValid && backEndValid);
//	    },

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
                  console.debug("error", error);
              };
              encuestame.service.xhrGet(URLServices.service('encuestame.service.list.checkProfile'), {type:type, value: this.textbox.value}, load, error);
//              URLServices.get('encuestame.service.list.checkProfile',  {type:type, value: this.textbox.value}, load, error , dojo.hitch(this, function() {
//
//              }));
          }
      }
  });

            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

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
             settings_config_profile_title : _ENME.getMessage("settings_config_profile_title"),
             settings_config_profile_description : _ENME.getMessage("settings_config_profile_description"),
             settings_config_profile_email : _ENME.getMessage("settings_config_profile_email"),
             settings_config_profile_email_description : _ENME.getMessage("settings_config_profile_email_description"),
             settings_config_profile_username : _ENME.getMessage("settings_config_profile_username"),
             settings_config_profile_username_description : _ENME.getMessage("settings_config_profile_username_description"),
             settings_config_profile_complete_name : _ENME.getMessage("settings_config_profile_complete_name"),
             settings_config_profile_language : _ENME.getMessage("settings_config_profile_language"),
             e_005 :  _ENME.getMessage("e_005"),
             commons_update :  _ENME.getMessage("commons_update")
           },

           /**
            *
            */
           postCreate : function() {
             // create subcribe to display errors.
             dojo.subscribe("/encuestame/settings/profile/message", this, this._displayMessage);
             this.events();

             //TODO: review the click event
             // http://dojotoolkit.org/reference-guide/1.8/dojox/form/BusyButton.html
             // Replaced by dijit button because http://bugs.dojotoolkit.org/ticket/9075 a long term bug on IE8
//             dojo.connect(this._submit, "onClick", dojo.hitch(this, function(event) {
//                 this._updateProfile(event);
//             }));
//               var button = new dojox.form.BusyButton({
//                   id: "submit",
//                   busyLabel: "Updating...",
//                   label: this.i18nMessage.commons_update,
//                   timeout: 5000
//                   }, this._submit);
//               button.onClick(function(){
//                 console.log('ffff');
//               });
////                dojo.connect(this._submit, "_onClick", function(event){
////                    this._updateProfile(event);
////                });
           },

           /**
            *
            */
           events : function() {
               var email = registry.byId("email");
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
               var username = registry.byId("username");
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
                           var email = registry.byId("email");
                           email.set('value', profile.email);
                           //console.info("EMAIL");
                           var username = registry.byId("username");
                           username.set('value', profile.username);
                           //console.info("USERNAMe");
                           var completeName = registry.byId("completeName");
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
               encuestame.service.xhrGet(this.getURLService().service('encuestame.service.list.profile.my'), {}, load, error);
           },

           /*
            * update profile.
            */
           _updateProfile : function(event) {
               dojo.stopEvent(event);
               var form = dojo.byId("profileForm");
               //console.debug("form ", form);
               var formDijit = registry.byId("profileForm");
               //console.debug("form", formDijit);
               if (formDijit.isValid()) {
                   var  params = {
                      //TODO: LLENAR ESTOS PARAMETROS
                   };
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
                    //query.username =  registry.byId("username").get("value") ;
                    //query.email = registry.byId("email").get("value");
                    //var queryStr = dojo.objectToQuery(query);
                    //encuestame.service.xhrPost(this.getURLService().service('encuestame.service.list.updateProfile'), form, load, error, true);
                    URLServices.post('encuestame.service.list.updateProfile',  params, load, error , dojo.hitch(this, function() {

                    }));
               } else {
                 dojo.publish('/encuestame/settings/profile/message', [_ENME.getMessage('settings_config_profile_form_not_valid'), 'error']);
               }
           }

    });
});