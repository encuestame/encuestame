dojo.provide("encuestame.org.mobile.notifications.NotificationList");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.commons.notifications.NotificationList');

dojo.declare(
    "encuestame.org.mobile.notifications.NotificationList",
    [encuestame.org.core.commons.notifications.NotificationList],{
        templatePath: dojo.moduleUrl("encuestame.org.mobile.notifications", "template/notificationList.html"),

        widgetsInTemplate: true,

        postCreate : function(){
            this.inherited(arguments);
        }
    }
);

/**
*
*/
dojo.declare(
       "encuestame.org.mobile.notifications.NotificationListItem",
       [dijit._Widget, dijit._Templated],{
           templatePath: dojo.moduleUrl("encuestame.org.mobile.notifications", "template/notificationListItem.html"),

           widgetsInTemplate: true,

           item : null,

           category : null,

           postCreate : function() {
               console.debug("item", this.item);
           }

});

dojo.extend(encuestame.org.core.commons.notifications.NotificationList, {
    _createNotificationItem : function(item, category){
        var widget = new encuestame.org.mobile.notifications.NotificationListItem({
            item : item,
            category : "TODAY"});
        console.debug("widget", widget);
       return widget;
   }
});