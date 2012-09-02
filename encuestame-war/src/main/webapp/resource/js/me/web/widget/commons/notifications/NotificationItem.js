dojo.provide("encuestame.org.core.commons.notifications.NotificationItem");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');


dojo.declare(
        "encuestame.org.core.commons.notifications.NotificationItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.notifications", "template/notificationItem.html"),

            widgetsInTemplate: true,

            item : null,

            clickItem : null,

            postCreate : function(){
                this.clickItem = dojo.connect(this.domNode, "onclick", dojo.hitch(this, function(e) {
                    this._markAsReaded();
               }));
            },

            _markAsReaded : function(){
                 var load = dojo.hitch(this, function(data){
                     encuestame.activity.cometd.publish('/service/notification/status', {});
                     dojo.disconnect(this.clickItem);
                 });
                 var error =  dojo.hitch(this, function(error) {
                 });
                 encuestame.service.xhrGet(encuestame.service.list.changeStatusNotification, {id:this.item.id}, load, error);
            },

            /*
             * Remove Notification
             */
            removeNotification : function(notificationId){
                var url = '/encuestame/api/remove-notification.json';
            }

        });
