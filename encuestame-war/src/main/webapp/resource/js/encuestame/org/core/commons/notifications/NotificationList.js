dojo.provide("encuestame.org.core.commons.notifications.NotificationList");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.commons.notifications.NotificationList",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.notifications", "template/notificationList.html"),

        widgetsInTemplate: true,

        arrayNotifications : null,

        mobile : false,

        postCreate : function(){
            this._loadNotifications();
        },

        /*
         * load notifications.
         */
        _loadNotifications : function(){
            var load = dojo.hitch(this, function(data) {
                 this.arrayNotifications = data.success.notifications;
                 this._showListCategories();
                 console.debug("notifications", this.arrayNotifications);
             });
             var error = function(error) {
                 console.debug("error", error);
             };
             encuestame.service.xhrGet(
                     encuestame.service.list.getAllNotifications, {required : 10}, load, error);
        },

        /*
         *
         */
        _loadMoreNotifications : function(){

        },

        /*
         * show list categories.
         */
        _showListCategories : function() {
            console.debug("_showListCategories");
            var today = this.arrayNotifications.TODAY;
            console.debug(today.lenght);
            if (today.length > 0) {
                dojo.forEach(today,
                        dojo.hitch(this, function(item, index) {
                var todayWidget = new encuestame.org.core.commons.notifications.NotificationListItem({
                    item : item,
                    category : "TODAY"});
                    this._list.appendChild(todayWidget.domNode);
                }));
            }
            //TODO: ENCUESTAME-
            var thisWeek = this.arrayNotifications.THIS_WEEK;
            var thisMonth = this.arrayNotifications.THIS_MONTH;
            var lastMonth = this.arrayNotifications.LAST_MONTH;
            var fewMonthsAgo = this.arrayNotifications.FEW_MONTHS_AGO;
            var lastYear = this.arrayNotifications.LAST_YEAR;
            var longTimeAgo = this.arrayNotifications.LONG_TIME_AGO;
            //console.debug("today", today);
            console.debug("thisWeek", thisWeek);
            console.debug("thisMonth", thisMonth);
            console.debug("lastMonth", lastMonth);
            console.debug("fewMonthsAgo", fewMonthsAgo);
            console.debug("lastYear", lastYear);
            console.debug("longTimeAgo", longTimeAgo);
        }
});

/**
 *
 */
dojo.declare(
        "encuestame.org.core.commons.notifications.NotificationListItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.notifications", "template/notificationListItem.html"),

            widgetsInTemplate: true,

            item : null,

            category : null,

            postCreate : function() {

            }

});