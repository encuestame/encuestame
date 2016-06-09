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
 *  @class Gadget
 */
define( [
         "dojo/_base/declare",
         "dojo/dom-attr",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/gadget/Activity",
         "me/web/widget/gadget/Comments",
         "me/web/widget/gadget/TweetPollVotes",
         "me/web/widget/support/DnDSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/dashboard/template/gadget.html" ],
        function(
                declare,
                domAttr,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                Activity,
                Comments,
                TweetPollvotes,
                DnDSupport,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin, DnDSupport ], {

            /**
              * Template string.
              */
            templateString: template,

            /*
             * Gadget info.
             */
            data: null,

            /*
             *
             */
            dashboardId: null,

            /*
             *
             */
            _widgetInside: null,

            /*
             * Post create.
             */
            postCreate: function() {
                dojo.subscribe("/encuestame/dashboard/gadget/remove", this, "_removeGadget");
                if ( this.data ) {
                    if ( this.data.id ) {
                        this.domNode.setAttribute("gid",  this.data.id );
                        this._initialize();
                    }
                }
            },

            /*
             * Set gadget title.
             * @method _setTitle
             */
            _setTitle: function( title ) {
                if ( title ) {
                    this._title.innerHTML = title;
                }
            },

           /**
            *
            * @method _initialize
            */
            _initialize: function() {
                try {
                    this._widgetInside = this._loadGadget( this.data.gadget_name, {
                        gadgetId: this.data.id
                    });
                     var node = this.domNode;
                     domAttr.set( node, "data-id", this.data.id );
                     this.dragItem( node, {
                        effectDrop: "move",
                        onDrag: function() {

                        }
                     });
                     this._content.appendChild( this._widgetInside.domNode );
                     this._widgetInside.startup();
                } catch ( error ) {
                     this.errorMessage("Error on gadget " + this.data.gadget_name );
                }
            },

            /**
             * Remove a gadget
             * @method
             */
            _remove: function( e ) {
                e.preventDefault();
                var div = this.createAlert("You are removing this gadget, you can recover it in the gadget directory.", "warn"),
                parent = this;
                this. createConfirmDialog("Are you sure?", div.domNode, function() {
                       parent._removeGadget( parent.data.id );
                });
            },

            /**
             * Remove the gadget.
             * @method _removeGadget
             */
            _removeGadget: function( controlId ) {
                if ( controlId === this.data.id ) {
                    var params = {
                        dashboardId: this.dashboardId
                    };
                    var load = dojo.hitch( this, function( data ) {
                        if ("success" in data ) {
                           this._widgetInside.destroyRecursive( true );
                           this.destroyRecursive( true );
                           dojo.destroy( this.id );
                           this.successMesage("Gadget Removed");
                        }
                    });
                    var error = function( error ) {
                        this.errorMessage("Gadget not removed");
                    };
                    this.getURLService().del( [ "encuestame.service.gadget", [ this.data.id ]], params, load, error, dojo.hitch( this, function() {

                   }), true );
                }
           },

             /**
              * Load the gadget by type.
              * @param type
              * @param params
              * @method _loadGadget
              * @returns
              */
            _loadGadget: function( type, params ) {
                try {
                    if ( type == "stream") {
                        return new Activity( params );
                    } else if ( type == "comments") {
                        return new Comments( params );
                    } else if ( type == "tweetpollsvotes") {
                        return new TweetPollvotes( params );
                    }  else {
                       throw new Error("gadget is missing");
                    }
                } catch ( error ) {
                    console.log( "error loading gadget", error );
                }
            },

            /**
             *
             * @method
             */
            _editConfiguration: function() {}

    });
});
