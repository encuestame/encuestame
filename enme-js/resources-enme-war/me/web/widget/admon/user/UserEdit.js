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
 *  @class UserEdit
 */
define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/registry",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "dijit/form/ToggleButton",
         "dijit/form/Button",
         "me/web/widget/admon/user/UserPermissions",
         "me/core/enme",
         "dojo/text!me/web/widget/admon/user/template/UserEdit.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                registry,
                _WidgetsInTemplateMixin,
                main_widget,
                ToggleButton,
                Button,
                UserPermissions,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            user: null,

            permissions: [],

            widgetPermissions : [],

            postCreate: function() {
                var widget = new UserPermissions({
                    user : this.user
                })
                this._permissions.appendChild(widget.domNode);
            },

            /**
             *
             * @method
             */
            _close : function () {
                var edit = registry.byId("userEdit");
                    edit.hide();
            }
    });
});