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
 *  @class SignUp
 */
define([
         "dojo/on",
         "dojo/_base/declare",
         "dojo/request/iframe",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/enme",
         "dijit/registry",
         "dijit/Dialog",
         "dijit/form/TextBox",
         "dijit/form/Button",
         "dojo/text!me/web/widget/signup/templates/login_dialog.html" ],
        function(
                on,
                declare,
                iframe,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                _ENME,
                registry,
                Dialog,
                TextBox,
                Button,
                template) {
            return declare([ _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {

      /**
       * Template string.
       * @property templateString
       */
       templateString: template,

       /**
        *
        * @method action
        */
       action_url: "",

        /**
         *
         */
       callbackSuccessLogin : function(){},

       /**
        *
        * @method postCreate
        */
       postCreate : function() {
          var parent = this;
          var buttonYes = new Button({
               label: "Login",
               "class": "success",
               onClick:function(e) {
                   parent.sendLogin(e);
               }
           });
           this._button.appendChild(buttonYes.domNode);
           this._dialog.show();
       },

       /**
        *
        * @method login
        */
       sendLogin: function(e) {
	     e.preventDefault();
         var params = {
            j_username : this._username.get('value'),
            j_password: this._password.get('value')
         };
         var load = dojo.hitch(this, function(data) {
              if (!data.loggedIn) {
                dojo.removeClass(this._alert, "hidden");
              } else {
                this._dialog.hide();
                this.callbackSuccessLogin();
              }
          });
          var error = function(error) {
              console.debug("error", error);
          };
          _ENME.xhr.post('encuestame.user.login', params, load, error, dojo.hitch(this, function() {

          }), true);
          return false;
       }


    });
});