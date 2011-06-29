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

        _seeMoreValue : true,

        _limit : 10,

        postCreate : function(){
            this.inherited(arguments);
        },

        //@override
        _seeMore : function(){
            var more = dojo.doc.createElement("div");
            dojo.addClass(more, "mobile-more");
            var a = dojo.doc.createElement("a");
            a.innerHTML = "See Next 10 Items..";
            dojo.connect(a, "onclick", dojo.hitch(this, function(event) {
                this._loadMoreNotifications(event);
            }));
            more.appendChild(a);
            return more;
        }
    }
);

/**
*
*/
dojo.declare(
       "encuestame.org.mobile.notifications.NotificationListItem",
       [encuestame.org.core.commons.notifications.NotificationListItem],{
           templatePath: dojo.moduleUrl("encuestame.org.mobile.notifications", "template/notificationListItem.html"),

           widgetsInTemplate: true,

           item : null,

           category : null,

           postCreate : function() {
               this.inherited(arguments);
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