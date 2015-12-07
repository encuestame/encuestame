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
 *  @module Dashboard
 *  @namespace Widget
 *  @class GadgetDirectory
 */
define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/dashboard/template/directory.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

          // Template string.
            templateString: template,

            /*
            * Dasboard widget.
            */
           dashboardWidget: null,

           /*
            * Post create.
            */
           postCreate: function() {
               this._loadDirectory();
           },

           /*
            * Load the gadget directory.
            * gadgets: [
               {
                   name: "stream"
                   id: "stream"
                   description: "abc abc abc"
                   image: "/images/gadgets/activity.png"
               }
               -
               {
                   name: "comments"
                   id: "comments"
                   description: "abc abc abc abc"
                   image: "/images/gadgets/comments.png"
               }
               -
               {
                   name: "tweetpoll-votes"
                   id: "tweetpoll-votes"
                   description: "abc abc abc abc"
                   image: "/images/gadgets/tweetpoll-votes.png"
               }
   ]
            */
           _loadDirectory: function() {
               var load = dojo.hitch( this, function( data ) {
                   if ( data.success ) {
                       var gadgets = data.success.gadgets;
                        dojo.forEach(
                                gadgets,
                                dojo.hitch( this, function( data, index ) {
                                    var node = this._createItemDirectory( data );
                                    this._dir.appendChild( node );
                        }) );
                   }
               });
               var error = function( error ) {
                   console.debug("error", error );
               };
               this.getURLService().get( "encuestame.service.gadget.directory", {}, load, error, dojo.hitch( this, function() {

               }) );
           },

           /**
            * Close the gadget dialog.
            * @method
            */
           _close: function() {
              this.dialog.hide();
           },

           /*
            * Create directory item.
            * @param gadget
            * @returns
            */
           _createItemDirectory: function( gadget ) {
               var id_generic = "gadget_item_" + gadget.id;
               var item = dojo.create("div");
               item.setAttribute("ga", gadget.id );
               item.setAttribute("id", id_generic );
               dojo.addClass( item, "web-directory-item");
               dojo.addClass( item, "row");

               // Title
               var title = dojo.create("div", { innerHTML: "<div>" + gadget.name + "</div>" }, item );
               var img = dojo.create("img", { src: _ENME.getImage( gadget.image ) }, title );
               dojo.addClass( title, "web-directory-item-title");
               dojo.addClass( title, "span2");

               // Description
               var description = dojo.create("div", {
	               innerHTML: "<p>" + _ENME.getMessage( gadget.description ) + "</p>"
               }, item );
               dojo.addClass( description, "web-directory-item-description");
               dojo.addClass( description, "span5");

               // Actions
               var actions = dojo.create("div", null, item );
               dojo.addClass( actions, "web-directory-item-actions");
               dojo.addClass( actions, "span1");

               //FIXME: 2 img objects?
               var img2 = dojo.create("img", { src: _ENME.getImage( "icons/enme-add.png" ) }, actions );
               img2.id = id_generic + "_img";
               dojo.connect( img2, "onclick", dojo.hitch( this, function() {
                   dojo.publish("/encuestame/dashboard/gadget/add", [ this.dashboardWidget._dasboardId, gadget.id ] );
               }) );
               return item;
           }

    });
});
