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
 *  @class NotificationItem
 */
define([
         "dojo",
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/notifications/template/notificationItem.html" ],
        function(
                dojo,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

            /**
             * template as stirng
             * @property templateString
             */
            templateString : template,

            /**
             * item
             * @property
             */
            item : null,

            /**
             *
             * @property
             */
            clickItem : null,

            /**
             * Post create life cycle.
             * @method postCreate
             */
            postCreate : function() {
                this.activity = _ENME.getActivity();
                this.clickItem = dojo.connect(this.domNode, "onclick", dojo.hitch(this, function(e) {
                    this._markAsReaded();
               }));
            },

            /**
             * Mark the notification as readed
             * @method _markAsReaded
             */
            _markAsReaded : function() {
                var parent = this,
                 load = dojo.hitch(this, function(data) {
                     parent.activity.cometd.publish('/service/notification/status', { r : 0 });
                     dojo.disconnect(this.clickItem);
                     dojo.addClass(parent.domNode, "web-not-item-removed");
                 }),
                 error =  dojo.hitch(this, function(error) {
                    //TODO: error handler.
                 });
                 this.getURLService().get("encuestame.service.list.changeStatusNotification", {id:this.item.id}, load, error , dojo.hitch(this, function() {
                  }));
            },

            /**
             * Remove Notification
             * @method removeNotification
             */
            removeNotification : function(notificationId){
                var url = '/encuestame/api/remove-notification.json';
            }

    });
});