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
 *  @module Admon
 *  @namespace Widget
 *  @class UserPermission
 */
define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/Tooltip",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "dijit/form/ToggleButton",
         "me/core/enme",
         "dojo/text!me/web/widget/admon/user/template/UserPermissions.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                Tooltip,
                _WidgetsInTemplateMixin,
                main_widget,
                ToggleButton,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

             // Template string.
            templateString: template,

            /**
             *
             * @property
             */
            user: {},

            /**
             *
             * @property
             */
            permissions: [],

            /**
             * All default permissions.
             * @property all_permissions
             */
            all_permissions: [
                    { "id":1, "permission":"ENCUESTAME_ADMIN", "description":"ENCUESTAME_ADMIN" },
                    { "id":2, "permission":"ENCUESTAME_OWNER", "description":"ENCUESTAME_OWNER" },
                    { "id":3, "permission":"ENCUESTAME_PUBLISHER", "description":"ENCUESTAME_PUBLISHER" },
                    { "id":4, "permission":"ENCUESTAME_EDITOR", "description":"ENCUESTAME_EDITOR" },
                    { "id":5, "permission":"ENCUESTAME_USER", "description":"ENCUESTAME_USER" }
            ],

            /**
             * Store all permissions widget.
             * @property widgetPermissions
             */
            widgetPermissions: [],

            /**
             *
             * @method postCreate
             */
            postCreate: function() {

                //
                this.i18n.permission_title = "Permissions";

                //
                this.loadPermisions();
            },

            /**
             * Load all permissions and build the permissions buttons
             * @method loadPermisions
             */
            loadPermisions: function() {
                    dojo.forEach( this.all_permissions,
                         dojo.hitch( this, function( data, index ) {
                             this.buildPermission( data );
                    }) );
            },

            /**
             * Reset all widgets.
             * @method resetWidgets resetWidgets
             */
            resetWidgets: function() {
                 dojo.forEach( this.widgetPermissions,
                         dojo.hitch( this, function( data, index ) {
                             data.checked = false;
                 }) );
            },

            /**
             * Check if the permission is assiged to the user.
             * @method checkPermission
             */
            checkPermission: function( data ) {
                var isAssigned = false;
                 dojo.forEach( this.user.listPermission, dojo.hitch( this, function( permission, index ) {
                        if ( data.permission == permission.permission ) {
                           isAssigned = true;
                        }
                }) );
                return isAssigned;
            },

            /**
             * Build a Permission Widget.
             * @param response
             * @method buildPermission
             */
            buildPermission: function( response ) {
                 var widget = new ToggleButton({
                     showLabel: true,
                     checked: this.checkPermission( response ),
                     layoutAlign: "left",
                     value: response.permission,
                     iconClass: "dijitCheckBoxIcon",
                     onChange:  dojo.hitch( this, function( val ) {
                         var error = function( error ) {
                             this.errorMessage("Error on Save");
                         };
                         var load = dojo.hitch( this, function( response ) {
                            this.successMesage("Saved");
                         });
                         var service;
                         if ( val ) {
                            service = "encuestame.service.list.addPermission";
                         } else {
                            service = "encuestame.service.list.removePermission";
                         }
                       this.getURLService().get( service, {
                                      id: this.user.id,
                                      permission: response.description
                                  }, load, error, dojo.hitch( this, function() {}) );
                     }),
                     label: response.description
                 }, "programmatic");
                 widget.permission = response.permission;
                 this.widgetPermissions.push( widget );
                 var div = dojo.doc.createElement( "div" ),
                 node = widget.domNode;
                 div.appendChild( node );
                div.id = ( response.permission ).toLowerCase();
                 new Tooltip({
                    connectId: node,
                    label: response.description
                 });
                 this._permissions.appendChild( div );
            }

    });
});
