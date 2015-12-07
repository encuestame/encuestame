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
 *  @module Data
 *  @namespace Widget
 *  @class Table
 */
define( [
         "dojo/_base/declare",
         "dojo/dom-construct",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/data/TableRow",
         "me/core/enme",
         "dojo/text!me/web/widget/data/templates/Table.html" ],
        function(
                declare,
                domConstruct,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TableRow,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

          // Template string.
            templateString: template,

            /** Principal service. **/
            jsonServiceUrl: null,

            limit: 10,

            start: 0,

            total: null,

            showPagination: false,

            postMixInProperties: function() {
            },

            postCreate: function() {
                this.loadItems();
            },

            cleanTable: function() {
                if ( this._body ) {
                    dojo.empty( this._body );
                }
            },

            /**
             * Load Users.
             */
            loadItems: function() {
                var load = dojo.hitch( this, function( data ) {
                    this.cleanTable();
                    this.iterateResponseItems( data );
                });
                var error = dojo.hitch( this, function( error ) {
                    console.debug("error table", error );
                });
                this.getURLService().get( this.jsonServiceUrl, {
                    limit: this.limit,
                    start: this.start
                }, load, error, dojo.hitch( this, function() {

                }) );
            },

            /**
             * Iterate Response Items.
             */
            iterateResponseItems: function( response ) {
                console.error( "this function should be override" );
            },

            /**
             * Error Response.
             */
            errorResponse: function( error ) {
                console.error( "this function should be override" );
            },

            /**
             * Build Row.
             */
            buildRow: function( data ) {
                var widgetRow = new TableRow({ data: data });
                this._body.appendChild( widgetRow.domNode );
            },

            /**
             * Next.
             */
            next: function( event ) {
                dojo.stopEvent( event );
                this.start = this.start + this.limit;
                this.loadItems();
            },

            /**
             * Previous.
             */
            previous: function( event ) {
                dojo.stopEvent( event );
                this.start = this.start - this.limit;
                if ( this.start < 0 ) {
                    this.start = 0;
                }
                this.loadItems();
            },

            /**
             * Last.
             */
            last: function( event ) {
                dojo.stopEvent( event );
            },

            /**
             * First.
             */
            first: function( event ) {
              dojo.stopEvent( event );
              this.start = 0;
              this.loadItems();
            }

    });
});
