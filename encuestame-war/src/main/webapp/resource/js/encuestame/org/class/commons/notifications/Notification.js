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

        limit: 100,

        timer: null,

        postMixInProperties: function(){

        },

        postCreate: function() {
            this.loadNotifications();
            this.loadTimer();
        },

        /**
         * Load Timer.
         */
        loadTimer : function(){
            var father = this;
            this.timer = new dojox.timing.Timer(this.delay);
            this.timer.onTick = function() {
                father.loadNotifications();
            }
            this.timer.onStart = function() {
            }
            this.timer.start();
        },

     // load notifications
        loadNotifications : function() {
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                var array = data.success.notifications;
                this._count.innerHTML = array.length;
            });
            var error = function(error) {
                console.debug("error", error);
                this.timer.stop();
            };
            encuestame.service.xhrGet(encuestame.service.list.getNotifications,{ limit: this.limit}, load, error);
        },

        /*
         * Remove Notification
         */
        removeNotification : function(notificationId){
            var url = '/encuestame/api/remove-notification.json';
            /* var json =
            /*new Ajax.Request(url, {
                method : 'get',
                parameters : {
                notificationId : notificationId
                },
                onSuccess : function(transport) {
                    var tran = transport;
                    if(transport.status == 200){
                       console.debug("ok");
                    } else {
                        if(transport.status == 0){
                             this.createNetworkError("You are lost Internet Connection", "Connection time out");
                        } else {
                            console.error("other error", transport.status);
                        }
                    }
                }.bind(this),

                onComplete : function(complete) {
                    //Remove error messages or something.
                }.bind(this),

                onLoading : function(loading) {
                  console.debug("loading", loading);
                }.bind(this),

                onFailure : function(resp) {
                    console.debug("Oops, there's been an error.", resp);
                    this.createNetworkError("Error", resp);
                }.bind(this)
            });
            */
        },

        cleanNodeName : function(){
             console.debug("cleanNodeName");
             var name = dojo.byId(this.nodeName);
             if(name != null){
                name.innerHTML = '';
             }
        },

        addEvents : function(){
            /* $$(".notificationItem").each(function(item) {
                 Event.observe(item, 'click', function(event){
                     console.debug("click", item.getAttribute("notificationId"));
                     this.removeNotification(item.getAttribute("notificationId"));
                     this.loadNotifications();
                 }.bind(this));
              }.bind(this));
              */
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

        /*
         * Create Notification.
         */
        createNotification : function(item){
            var livePanel = $(this.nodeName);
            var div = new Element('div', { 'class': 'notificationItem', "notificationId" : item.id });
            var divLeft = new Element('div', { 'class': 'left' });
            var span = new Element('span', { 'class': 'image '+item.icon });
            divLeft.update(span);
            div.appendChild(divLeft)
            var divRight = new Element('div', { 'class': 'right' });
            var description = new Element('div', { 'class': 'title' }).update(item.description);
            var question = new Element('div', { 'class': 'question' });
            if(item.type == "TWEETPOLL_PUBLISHED"){
                var a = new Element('a', { 'href': item.additionalDescription, "target": "_blank" }).update(item.additionalDescription);
                question.update(a);
            } else {
                question.update(item.additionalDescription);
            }
            divRight.insert(description)
            divRight.insert(question)
            div.appendChild(divRight)
            livePanel.appendChild(div);
        }
    }
);
