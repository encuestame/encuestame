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
 *  @module UI
 *  @namespace Widget
 *  @class Message
 */
define( [
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/enme",
     "dojo/text!me/web/widget/ui/templates/alert.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    _ENME,
     template ) {

  return declare( [ _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin ], {

     /**
      * Template string.
      * @property
      */
     templateString: template,

     /**
      *
      * @property message
      */
     message: "message test",

     /**
      *
      * @property type
      */
     type_message: "warn",

      /**
       * Post create
       * @method postCreate
       */
     postCreate: function() {
         if ( this.message ) {
          var div = dojo.create( "div" );
          div.innerHTML = this.message;
            this._message.appendChild( div );
         }
         if ( this.type_message === "info") {
            dojo.addClass( this.domNode, "alert-info");
         } else if ( this.type_message === "error") {
            dojo.addClass( this.domNode, "alert-error");
         } else if ( this.type_message === "success") {
            dojo.addClass( this.domNode, "alert-success");
         }
      }
  });
});
