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
 *  @class EmailValidator
 */
define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/welcome/templates/welcomeWidget.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template ) {
            return declare( [
                _WidgetBase,
                _TemplatedMixin,
                main_widget,
                _WidgetsInTemplateMixin ], {

        /**
         * Template string.
         * @property
         */
        templateString: template,

        /**
         * Placeholder
         * @property
         */
        placeholder: "Write your email",

        /**
         *
         * @method
         */
        postCreate: function() {
            this.inherited( arguments );
        },

        /**
         *
         * @method
         */
        _validate: function( event ) {
               this.inputTextValue = this._input.value;
               this._loadService(
                   this.getServiceUrl(), {
                   context: this.enviroment,
                   email: this._input.value
               }, this.error );
        },

        /**
         *
         * @method
         */
        getValue: function() {
            return this._input.value;
        },

        /**
         *
         * @method
         */
        clear: function() {
              this.cleanMessage();
              this._input.value = "";
        },

       /**
         *
         * @method
         */
        getServiceUrl: function() {
            return "encuestame.service.publicService.validate.email";
        },

       /**
        *
        */
        error: function( error ) {

           //Console.error("error", error);
        }

    });
});
