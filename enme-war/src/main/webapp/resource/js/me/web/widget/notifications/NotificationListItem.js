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
 *  @module Notifications
 *  @namespace Widget
 *  @class NotificationListItem
 */

define([
         "dojo",
         'dojo/_base/json',
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/notifications/NotificationListItem",
         "me/core/enme",
         "dojo/text!me/web/widget/notifications/template/notificationListItem.html" ],
        function(
                dojo,
                json,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                NotificationListItem,
                _ENME,
                 template) {
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
        item : null,

        /**
         *
         * @property
         */
        category : null,

        /**
         *
         * @method
         */
        postCreate : function() {
            if (this.item.url != null) {
                this._description
                .appendChild(encuestame.notification
                .buildURLDescription(
                        this.item.type,
                        this.item.additionalDescription,
                        this.item.url));
            } else {
                this._description.innerHTML = this.item.additionalDescription;
            }
        },

        /**
         * remove notification.
         * @method _removeNotification
         */
        _remove : function(event) {
            //TODO: display dialog.
            this._removeNotification();
        },

        /**
         * remove notification.
         * @method _removeNotification
         */
        _removeNotification : function() {
            var load = dojo.hitch(this, function(data) {
                 dojo.destroy(this.domNode);
             });
             var error = function(error) {
                 console.debug("error", error);
             };
             var params = {
                    notificationId : this.item.id
                };
             this.getURLService().get("encuestame.service.list.removeNotification", params, load, error , dojo.hitch(this, function() { }));
        }

    });
});