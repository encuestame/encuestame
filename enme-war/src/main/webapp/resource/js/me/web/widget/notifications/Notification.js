dojo.require('dojox.timing');

define([
         "dojo",
         'dojo/_base/json',
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
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
                hashTagInfo,
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
         limit: _ENME.config('notification_limit'),

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
                console.log("enviando jota /service/notification/status");
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
         open: function(event){
             dojo.stopEvent(event);
             if(!this.openNot){
                 dojo.addClass(this._panel, "openLivePanel");
                 if(this._updateNotifications){
                     this.loadNotifications();
                 }
             } else {
                 dojo.removeClass(this._panel, "openLivePanel");
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

         /*
          * load notifications.
          */
         loadNotifications : function() {
             var load = dojo.hitch(this, function(data){
                 this.notifications = data.success.notifications;
                 this.buildNotifications();
                 this._updateNotifications = false;
             });
             var error =  dojo.hitch(this, function(error) {
                 this.timer.stop();
             });
             dojo.empty(this._not);
             encuestame.service.xhrGet(encuestame.service.list.getNotifications, {limit:this.limit}, load, error);
         },

         /*
          * build notifications node.
          */
         buildNotifications : function(){
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
         createNetworkError : function(error, additional){
             var item = {
                 type : "",
                 description : error,
                 additionalDescription: additional,
                 icon : "netWorkErrorImage"
             };
             this.createNotification(item);
         },

         cleanNot : function(){
             dojo.empty(this._not);
         },

         /*
          * Create Notification.
          */
         createNotification : function(item){
             var not = new encuestame.org.core.commons.notifications.NotificationItem({item:item});
             this._not.appendChild(not.domNode);
         }

            });
        });

//dojo.provide("encuestame.org.core.commons.notifications.Notification");
//
//dojo.require('dojox.timing');
//dojo.require("dojox.widget.Dialog");
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//dojo.require("dijit.layout.ContentPane");
//dojo.require('encuestame.org.core.commons');
//dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
//dojo.require('encuestame.org.core.commons.notifications.NotificationItem');
//
//dojo.declare(
//    "encuestame.org.core.commons.notifications.Notification",
//    [encuestame.org.main.EnmeMainLayoutWidget],{
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.notifications", "template/notification.html"),
//

//    }
//);