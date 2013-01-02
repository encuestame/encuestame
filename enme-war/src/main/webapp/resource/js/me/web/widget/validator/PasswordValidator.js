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
 *  @module SignUp
 *  @namespace Widgets
 *  @class PasswordValidator
 */
define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/validator/AbstractValidatorWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/validator/templates/passwordValidator.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                abstractValidatorWidget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, abstractValidatorWidget, _WidgetsInTemplateMixin], {

        // template string.
        templateString : template,

        /**
         *
         */
        noEvents : true,

        /**
         *
         */
        placeholder : "Write your password",

        /**
         *
         */
        exclude : 'encuestame.constants.passwordExcludes',

        /**
         *
         */
        toolTip : false,

        /**
         *
         */
        postCreate : function() {
            this.inherited(arguments);
            dojo.connect(this._input, "onkeyup", dojo.hitch(this, function(e) {
              if (dojo.keys.TAB != e.keyCode) {
                dojo.stopEvent(e);
                this.inputTextValue = this._input.value.toLowerCase();
                this._validate(this.inputTextValue);
              }
            }));
        },

        /**
         *
         */
        _validateBlackListPassword : function(password){
            var valid = false;
            dojo.forEach(encuestame.constants.passwordExcludes,
                    dojo.hitch(this,function(item) {
                        //console.debug(item);
            }));
            return valid;
        },

        /**
         *
         */
        validatePassword : function() {
            this._validate(this.inputTextValue, true);
        },

        /**
         * Evaluate the password strong
         * @method
         */
        evaluateStrong : function(passwd) {
            var intScore = 0,
            strVerdict = "weak",
            strLog = "";
            if (passwd.length < 5) {
                intScore = (intScore + 3);
            } else if (passwd.length > 4 && passwd.length < 8) {
                intScore = (intScore + 6);
            } else if (passwd.length > 7 && passwd.length < 16) {
                intScore = (intScore + 12);
            } else if (passwd.length > 15) {
                intScore = (intScore + 18);
            }
            if (passwd.match(/[a-z]/)) {
                intScore = (intScore + 1);
            }
            if (passwd.match(/[A-Z]/)) {
                intScore = (intScore + 5);
            }
            if (passwd.match(/\d+/)) {
                intScore = (intScore + 5);
            }
            if (passwd.match(/(.*[0-9].*[0-9].*[0-9])/)) {
                intScore = (intScore + 5);
            }
            if (passwd.match(/.[!,@,#,$,%,^,&,*,?,_,~]/)) {
                intScore = (intScore + 5);
            }
            if (passwd.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/)) {
                intScore = (intScore + 5);
            }
            if (passwd.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) {
                intScore = (intScore + 2);
            }
            if (passwd.match(/([a-zA-Z])/) && passwd.match(/([0-9])/)) {
                intScore = (intScore + 2);
            }
            if (passwd.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)) {
                intScore = (intScore + 2);
            }
            return intScore;
        },

       /**
        *
        */
       _validate : function(password, validateBlank) {
               var banned = false,
               validateBlank = validateBlank || false;
               // check banned password.
              if (!banned) {
                  var data = {};
                  var intScore = this.evaluateStrong(password);
                  if (validateBlank && password === '') {
                    data.msg = encuestame.constants.errorCodes["015"];
                      this.isValid = false;
                      this._showErrorMessage(data);
                  } else if (encuestame.constants.passwordExcludes.length > 0 && encuestame.constants.passwordExcludes.indexOf(password.toLowerCase()) != -1) {
                     var data = {};
                     data.msg = encuestame.constants.errorCodes["012"];
                     this.isValid = false;
                     this._showErrorMessage(data);
                  } else if (intScore < 16 && intScore > 1) {
                      data.msg = encuestame.constants.errorCodes["012"];
                      this.isValid = false;
                      this._showErrorMessage(data);
                  } else if (intScore > 15 && intScore < 25) {
                      data.msg =  encuestame.constants.errorCodes["013"];
                      this._showErrorMessage(data);
                      this.isValid = false;
                  } else if (intScore > 24 && intScore < 35) {
                      data.msg = encuestame.constants.messageCodes["011"];
                      this._showSuccessMessage(data);
                      this.isValid = true;
                  } else if (intScore > 34 && intScore < 45) {
                      data.msg = encuestame.constants.messageCodes["010"];
                      this._showSuccessMessage(data);
                      this.isValid = true;
                  } else {
                      data.msg = encuestame.constants.messageCodes["009"];
                      this._showSuccessMessage(data);
                      this.isValid = true;
                  }
              } else {
                    this.isValid = false;
              }
       },


       /**
        *
        */
       _showSuccessMessage : function(data){
           var node = dojo.byId("_message_"+this.id);
           if (node) {
               dojo.empty(node);
               var p = dojo.doc.createElement("p");
               dojo.addClass(p, "success-message");
               p.innerHTML = data.msg;
               node.appendChild(p);
           }
       },

         /**
          *
          */
         _showErrorMessage : function(data){
             var node = dojo.byId("_message_"+this.id);
             console.info("_showSuccessMessage", data);
             if (node) {
                 dojo.empty(node);
                 var p = dojo.doc.createElement("p");
                 dojo.addClass(p, "error-message");
                 p.innerHTML = data.msg;
                 node.appendChild(p);
             }
         }

    });
});