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
 *  @class Notification
 */
dojo.require('dojox.timing');

define([
         "dojo",
         'dojo/_base/json',
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/notifications/NotificationItem",
         "me/core/enme",
         "dojo/text!me/web/widget/notifications/template/notification.html" ],
        function(
                dojo,
                json,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                NotificationItem,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
          templateString : template,

          /*
          * delay to retrieve new notification.
          */
         delay: _ENME.config('activity').delay,

         /*
          * limit of notifications.
          */
         limit: _ENME.config('activity').limit,

         i18nMessage : {
           not_view_all : _ENME.getMessage("not_view_all"),
         },

         /*
          *
          */
         notifications : null,

         /*
          *
          */
         totalNot : 0,

         /*
          *
          */
         timer: null,

         /*
          *
          */
         _updateNotifications : true,

         /*
          *
          */
         openNot : false,

         /*
          *
          */
         _originalTitle : null,

         /**
          *
          * @property
          */
          storage_key : "enme-not",

         /*
          *
          */
         postCreate: function() {
            // after refresh, clean all possible storage key.
             _ENME.removeItem(this.storage_key);
             // get the current activity
             this.activity = _ENME.getActivity();
             var parent = this;

             // create a timer to get possible new notifications
             var _timer = function () {
                parent.activity.cometd.publish('/service/notification/status', { r : 0 });
             };
            window.setInterval(_timer, this.delay || 20000);

            dojo.subscribe("/notifications/service/messages", this, "_updateStatus");

            dojo.addOnUnload(function() {
                if (subscriptionNotification !== null) {
                    parent.activity.cometd.unsubscribe(subscriptionNotification);
                }
            });
            this._originalTitle = document.title;
         },

         /*
          * Load Timer.
          */
         loadTimer : function() {
             var father = this;
             this.timer = new dojox.timing.Timer(this.delay);
             this.timer.onTick = function() {
                 father.loadStatus();
             };
             this.timer.onStart = function() {
             };
             this.timer.start();
         },

         /*
          * open dialog notification.
          */
         open: function(event) {
             dojo.stopEvent(event);
             if (!this.openNot) {
                 dojo.addClass(this._panel, "openLivePanel");
                 dojo.addClass(this._parent_wrapper, 'not-menu-not-selected');
                 //if(this._updateNotifications){
                     this.loadNotifications();
                 //}
             } else {
                 dojo.removeClass(this._panel, "openLivePanel");
                 dojo.removeClass(this._parent_wrapper, 'not-menu-not-selected');
             }
             this.openNot = !this.openNot;
         },

         /*
          * view all.
          */
         _viewAll : function(event){
              dojo.stopEvent(event);
              //dijit.byId("allNot").show();
              document.location.href = encuestame.contextDefault+"/user/notifications";
         },

         /*
          * Update label status.
          * @param totalNew
          * @param lastNew
          */
         _updateStatus : function(notStatus) {
             var _storage =  (json.fromJson(_ENME.restoreItem(this.storage_key)) || ({ n : null , t : null }));
             if (notStatus.totalNewNot < _storage.n || notStatus.totalNewNot == _storage.n) {
                 //highlight new notifications.
                 this._updateNotifications = true;
                 this._displayNewHighlight();
                 //update title to show number of new notifications
                 var newTitle = this._originalTitle + " ("+  notStatus.totalNewNot + ")";
                 //console.debug("newTitle", newTitle);
                 document.title = newTitle;
             } else {
                 this._hideNewHighlight();
                 this._updateNotifications = false;
             }
             // update cookie
             _ENME.storeItem(this.storage_key, {
                t : notStatus.totalNot,
                n : notStatus.totalNewNot
             });
             //encuestame.session.activity.updateNot(total, notStatus.totalNewNot);
             notStatus.totalNot = notStatus.totalNewNot;
             this._count.innerHTML = notStatus.totalNot;
         },

         /*
          *
          */
         _displayNewHighlight : function(){
             dojo.addClass(this._count, "new");
         },

         _hideNewHighlight : function(){
             dojo.removeClass(this._count, "new");
         },

         /*
          * load notifications
          */
         loadStatus : function() {
             encuestame.activity.cometd.startBatch();
             encuestame.activity.cometd.publish('/service/notification/status', {});
             encuestame.activity.cometd.endBatch();
         },

         /**
          * load notifications.
          * @method
          */
         loadNotifications : function() {
             var parent = this,
             load = dojo.hitch(this, function(data) {
                 parent.activity.cometd.publish('/service/notification/status', { r : 0 });
                 this.notifications = data.success.notifications;
                 this.buildNotifications();
                 this._updateNotifications = false;
             }),
             error =  dojo.hitch(this, function(error) {
                 this.timer.stop();
             });
             dojo.empty(this._not);
             this.getURLService().get("encuestame.service.list.getNotifications", { limit: this.limit}, load, error , dojo.hitch(this, function() {
             }));
         },

         /*
          * build notifications node.
          */
         buildNotifications : function() {
              dojo.empty(this._not);
              dojo.forEach(this.notifications,
                      dojo.hitch(this, function(item, index) {
                      this.createNotification(item);
               }));
         },

         /*
          * clean nodes.
          */
         cleanNodeName : function(){
              var name = dojo.byId(this.nodeName);
              if(name != null){
                 name.innerHTML = '';
              }
         },

         /*
          * Create Network Error.
          */
         createNetworkError : function(error, additional) {
             var item = {
                 type : "",
                 description : error,
                 additionalDescription: additional,
                 icon : "netWorkErrorImage"
             };
             this.createNotification(item);
         },

         cleanNot : function() {
             dojo.empty(this._not);
         },

         /*
          * Create Notification.
          */
         createNotification : function(item){
             var not = new NotificationItem({
              item : item
             });
             console.log("ITEM", item);
             this._not.appendChild(not.domNode);
         }

    });
});