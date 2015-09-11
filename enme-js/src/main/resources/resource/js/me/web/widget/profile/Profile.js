/*
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.146
 *  @module Profile
 *  @namespace Widget
 *  @class Profile
 */
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


  lang.extend(ValidationTextBox, {

      /*
       * validate back end.
       */
      validateBackEnd : function(type) {
          var isValidUsername = _ENME.validateCharacterDigits(this.textbox.value);
          var message;
          if (isValidUsername) {
              if (type !== null) {
                  var load = dojo.hitch(this, function(response) {
                      if (!response.success.validate) {
                          message = response.success.messages[type];
                          this.invalidMessage = message;
                          this.getErrorMessage(true);
                          this._maskValidSubsetError = true;
                          this.displayMessage(message);
                          dojo.publish('/encuestame/settings/profile/message', [message, 'error']);
                      } else {
                          message = response.success.messages[type] || "Not Defined";
                          dojo.publish('/encuestame/settings/profile/message', [message, 'success']);
                      }
                  });
                  var error = function(error) {
                      dojo.publish('/encuestame/settings/profile/message', [message, 'error']);
                  };
                  _ENME.xhr.get('encuestame.service.list.checkProfile', {
                    type:type,
                    value : this.textbox.value
                  },
                  load, error);
              }
          } else {
              message = "Username is not valid. Only characters and digits";
              this.invalidMessage = message;
              this.getErrorMessage(true);
              this._maskValidSubsetError = true;
              this.displayMessage(message);
              dojo.publish('/encuestame/settings/profile/message', [message, 'error']);
          }
      }
  });

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          /**
           * template string.
           * @property templateString
           */
           templateString : template,

          /**
           *
           * @property
           */
           _openBox : false,

           /**
            *
            * @property
            */
           username : "",

           /**
            *
            * @method
            */
           email : "",

           /**
            *
            * @method
            */
           complete_name : "",

           /**
            *
            * @method
            */
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
                 //console.info("PRESSS");
               });
               email.onKeyDown = dojo.hitch(this, function() {
                 //console.info("onKeyDown");
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
                       if (profile !== null) {
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
                   _ENME.log("error", error);
               };
              URLServices.get('encuestame.service.list.profile.my', {}, load, error , dojo.hitch(this, function() {

              }));
           },

           /*
            * update profile.
            */
           _updateProfile : function(event) {
               dojo.stopEvent(event);
               var form = dojo.byId("profileForm");
               var formDijit = registry.byId("profileForm");
               if (formDijit.isValid()) {
                   var params = formDijit.getValues();
                   var load = dojo.hitch(this, function(data) {
                     if ("success" in data) {
                       var message = data.success.message;
                       if (message != 'undefined') {
                          this.successMesage(message);
                       }
                     }
                     //this._submit.cancel();
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
                    URLServices.post('encuestame.service.list.updateProfile',  params, load, error , dojo.hitch(this, function() {

                    }));
               } else {
                 dojo.publish('/encuestame/settings/profile/message', [_ENME.getMessage('settings_config_profile_form_not_valid'), 'error']);
               }
           }

    });
});