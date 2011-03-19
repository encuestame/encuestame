dojo.provide("encuestame.org.core.commons.notifications.Notification");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.commons.notifications.Notification",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.notifications", "template/notification.inc"),

        widgetsInTemplate: true,

        delay: 20000,

        limit: 8,

        notifications : null,

        lastNew : 0,

        totalNot : 0,

        timer: null,

        _updateNotifications : false,

        openNot : false,

        postCreate: function() {
            dojo.addOnLoad(dojo.hitch(this, function(){
                this.loadStatus();
                this.loadTimer();
            }));
            dojo.subscribe("/encuestame/notifications/update/status", this, "_updateStatus");
        },

        /*
         * Load Timer.
         */
        loadTimer : function(){
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
                if(!this.notifications){
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
             dijit.byId("allNot").show();
        },

        /*
         * Update label status.
         * @param totalNew
         * @param lastNew
         */
        _updateStatus : function(totalNew, lastNew){
            var total = parseInt(totalNew);
            var totalNew = parseInt(lastNew);
            if (totalNew > this.lastNew) {
               //highligth new notifications.
                this._displayNewHighlight();
            } else if (totalNew == this.lastNew) {
                this._displayNewHighlight();
            } else {
                this._hideNewHighlight();
            }
            this.lastNew = totalNew;
            this.totalNot = total;
            this._count.innerHTML = total;
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
//            var load = dojo.hitch(this, function(data){
//                var total = data.success.t;
//                var totalNew = data.success.n;
//                this.lastNew = totalNew;
//                this._count.innerHTML = data.success.t;
//            });
//            var error =  dojo.hitch(this, function(error) {
//                this.timer.stop();
//            });
            // Publish on a service channel since the message is for
            // the server only
            cometd.publish('/service/notification/status', {});
            //encuestame.service.xhrGet(encuestame.service.list.getStatusNotifications, {}, load, error);
        },

        // load notifications
        loadNotifications : function() {
            var load = dojo.hitch(this, function(data){
                this.notifications = data.success.notifications;
                this.buildNotifications();
            });
            var error =  dojo.hitch(this, function(error) {
                this.timer.stop();
            });
            encuestame.service.xhrGet(encuestame.service.list.getNotifications, {limit:this.limit}, load, error);
        },

        buildNotifications : function(){
             dojo.empty(this._not);
             dojo.forEach(this.notifications,
                     dojo.hitch(this, function(item, index) {
                     this.createNotification(item);
              }));
        },

        cleanNodeName : function(){
             console.debug("cleanNodeName");
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
    }
);

dojo.declare(
        "encuestame.org.core.commons.notifications.NotificationItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.notifications", "template/notificationItem.inc"),

            widgetsInTemplate: true,

            item : null,

            postCreate : function(){
                console.debug("item", this.item);
            },

            /*
             * Remove Notification
             */
            removeNotification : function(notificationId){
                var url = '/encuestame/api/remove-notification.json';
            }

        });
