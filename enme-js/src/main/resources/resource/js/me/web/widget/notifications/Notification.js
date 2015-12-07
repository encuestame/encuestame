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

//Dojo.require('dojox.timing');

define( [
         "dojo",
         "dojo/_base/json",
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
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

          // Template string.
          templateString: template,

          /*
          * Delay to retrieve new notification.
          */

         //Delay: _ENME.config('activity').delay,

         /*
          * Limit of notifications.
          */
         limit: 10,

        /**
         *
         * @property
         */
         i18nMessage: {
           not_view_all: _ENME.getMessage("not_view_all")
         },

         /*
          *
          */
         notifications: null,

         /*
          *
          */
         totalNot: 0,

         /*
          *
          */
         timer: null,

         /*
          *
          */
         _updateNotifications: true,

         /*
          *
          */
         openNot: false,

         /*
          *
          */
         _originalTitle: null,

         /**
          *
          * @property
          */
          storage_key: "enme-not",

         /*
          *
          */
         postCreate: function() {

            // After refresh, clean all possible storage key.
            _ENME.removeItem( this.storage_key );

            //  // get the current activity
            this.activity = _ENME.getActivity();

            dojo.subscribe("/notifications/service/messages", this, "_updateStatus");
            dojo.subscribe("/notifications/service/update", this, function() {

                //_timer();
            });
            this._originalTitle = document.title;

            this.callTotals();
         },

         /**
          *
          * @method
          */
         callTotals: function() {
             if ( this.activity ) {
               this.activity.subscribe({
                  type: "subscribe",
                  suffix: false,
                  callback: function( data ) {
                    dojo.publish( "/notifications/service/messages", data );
                  },
                  channel: "/app/notifications"
              });
           }
         },

         /*
          * Load Timer.
          */
         loadTimer: function() {
             var father = this;
             this.timer = new dojox.timing.Timer( this.delay );
             this.timer.onTick = function() {
                 father.loadStatus();
             };
             this.timer.onStart = function() {
             };
             this.timer.start();
         },

         /*
          * Open dialog notification.
          */
         open: function( event ) {
             dojo.stopEvent( event );
             if ( !this.openNot ) {
                 dojo.addClass( this._panel, "openLivePanel");
                 dojo.addClass( this._parent_wrapper, "not-menu-not-selected" );

                 //If(this._updateNotifications){
                     this.loadNotifications();

                 //}
             } else {
                 dojo.removeClass( this._panel, "openLivePanel");
                 dojo.removeClass( this._parent_wrapper, "not-menu-not-selected" );
             }
             this.openNot = !this.openNot;
         },

         /*
          * View all.
          */
         _viewAll: function( event ) {
              dojo.stopEvent( event );

              //Dijit.byId("allNot").show();
              document.location.href = _ENME.config( "contextPath" ) + "/user/notifications";
         },

         /*
          * Update label status.
          * @param totalNew
          * @param lastNew
          */
         _updateStatus: function( notStatus ) {
             var _storage =  ( json.fromJson( _ENME.restoreItem( this.storage_key ) ) || ({ n: null, t: null }) );
             if ( notStatus.totalNewNot < _storage.n || notStatus.totalNewNot == _storage.n ) {

                 //Highlight new notifications.
                 this._updateNotifications = true;
                 this._displayNewHighlight();

                 //Update title to show number of new notifications
                 var newTitle = this._originalTitle + " (" +  notStatus.totalNewNot + ")";

                 //Console.debug("newTitle", newTitle);
                 document.title = newTitle;
             } else {
                 this._hideNewHighlight();
                 this._updateNotifications = false;
             }

             // Update cookie
             _ENME.storeItem( this.storage_key, {
                t: notStatus.totalNot,
                n: notStatus.totalNewNot
             });

             //Encuestame.session.activity.updateNot(total, notStatus.totalNewNot);
             notStatus.totalNot = notStatus.totalNewNot;
             this._count.innerHTML = _ENME.relativeQuantity( notStatus.totalNot );
         },

         /*
          *
          */
         _displayNewHighlight: function() {
             dojo.addClass( this._count, "new");
         },

         _hideNewHighlight: function() {
             dojo.removeClass( this._count, "new");
         },

         /*
          * Load notifications
          */
         loadStatus: function() {

             // Encuestame.activity.cometd.startBatch();
             // encuestame.activity.cometd.publish('/service/notification/status', {});
             // encuestame.activity.cometd.endBatch();
         },

         /**
          * Load notifications.
          * @method
          */
         loadNotifications: function() {
             var parent = this,
             load = dojo.hitch( this, function( data ) {

                 //Parent.activity.cometd.publish('/service/notification/status', { r : 0 });
                 this.callTotals();
                 this.notifications = data.success.notifications;
                 this.buildNotifications();
                 this._updateNotifications = false;
             }),
             error =  dojo.hitch( this, function( error ) {
                 this.timer.stop();
             });
             dojo.empty( this._not );
             this.getURLService().get("encuestame.service.list.getNotifications", { limit: this.limit }, load, error, dojo.hitch( this, function() {
             }) );
         },

         /*
          * Build notifications node.
          */
         buildNotifications: function() {
              dojo.empty( this._not );
              dojo.forEach( this.notifications,
                      dojo.hitch( this, function( item, index ) {
                      this.createNotification( item );
               }) );
         },

         /*
          * Clean nodes.
          */
         cleanNodeName: function() {
              var name = dojo.byId( this.nodeName );
              if ( name !== null ) {
                 name.innerHTML = "";
              }
         },

         /*
          * Create Network Error.
          */
         createNetworkError: function( error, additional ) {
             var item = {
                 type: "",
                 description: error,
                 additionalDescription: additional,
                 icon: "netWorkErrorImage"
             };
             this.createNotification( item );
         },

         cleanNot: function() {
             dojo.empty( this._not );
         },

         /*
          * Create Notification.
          */
         createNotification: function( item ) {
             var not = new NotificationItem({
              item: item,
              parentWidget: this
             });

             //Console.log("ITEM", item);
             this._not.appendChild( not.domNode );
         }

    });
});
