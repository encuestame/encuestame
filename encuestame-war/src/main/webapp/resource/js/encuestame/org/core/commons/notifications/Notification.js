dojo.provide("encuestame.org.class.commons.notifications.Notification");

dojo.require('dojox.timing');
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('encuestame.org.class.commons');

dojo.declare(
    "encuestame.org.class.commons.notifications.Notification",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.notifications", "template/notification.inc"),

        widgetsInTemplate: true,

        delay: 1000,

        limit: 8,

        notifications : null,

        lastNew : 0,

        totalNot : 0,

        timer: null,

        openNot : false,

        postCreate: function() {
            this.loadStatus();
            this.loadTimer();
        },

        /**
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

        open: function(event){
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

     // load notifications
        loadStatus : function() {
            var load = dojo.hitch(this, function(data){
                var total = data.success.t;
                var totalNew = data.success.n;
                this.lastNew = totalNew;
                this._count.innerHTML = data.success.t;
            });
            var error =  dojo.hitch(this, function(error) {
                this.timer.stop();
            });
            encuestame.service.xhrGet(encuestame.service.list.getStatusNotifications, {}, load, error);
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
            var not = new encuestame.org.class.commons.notifications.NotificationItem({item:item});
            this._not.appendChild(not.domNode);
        }
    }
);

dojo.declare(
        "encuestame.org.class.commons.notifications.NotificationItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.class.commons.notifications", "template/notificationItem.inc"),

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
            },


        });