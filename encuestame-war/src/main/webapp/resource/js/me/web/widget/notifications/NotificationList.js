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

        _start : 0,

        _limit :  encuestame.notification.load.limit,

        _seeMoreValue : false,

        postCreate : function(){
            this._loadNotifications(this._start);
        },

        /*
         * load notifications.
         */
        _loadNotifications : function(start){
            var load = dojo.hitch(this, function(data) {
                 this.arrayNotifications = data.success.notifications;
                 this._showListCategories();
                 console.debug("notifications", this.arrayNotifications);
             });
             var error = function(error) {
                 console.debug("error", error);
             };
             if (start == null) {
                 start = 0;
             }
             var params = {limit : this._limit, start: start, categorized: true};
             encuestame.service.xhrGet(
                     encuestame.service.list.getAllNotifications, params, load, error);
        },

        /*
         *
         */
        _loadMoreNotifications : function(event){
                console.debug("MORE ENCUESTAME-234", event);
        },

        /*
         * see more items.
         */
        _seeMore : function(){
            //only for mobile interface. override.
        } ,

        /*
         * build notification category section.
         */
        _buildSection : function(name, content){
             var section = dojo.doc.createElement("div");
             dojo.addClass(section, "section");

             var title = dojo.doc.createElement("h3");
             title.innerHTML = name;
             //add title
             section.appendChild(title);
             //add content
             section.appendChild(content);
             //add see more
             if(this._seeMoreValue){
                 section.appendChild(this._seeMore());
             }
             this._list.appendChild(section);
        },

        _createNotificationItem : function(item, category){
             var widget = new encuestame.org.core.commons.notifications.NotificationListItem({
                 item : item,
                 category : "TODAY"});
            return widget;
        },

        /*
         * show list categories.
         */
        _showListCategories : function() {
            console.debug("_showListCategories");
            var today = this.arrayNotifications.TODAY;
            var items = dojo.doc.createElement("div");
            if (today.length > 0) {
                dojo.forEach(today,
                        dojo.hitch(this, function(item, index) {
                var todayWidget = this._createNotificationItem(item, "TODAY");
                items.appendChild(todayWidget.domNode);
                }));
               this._buildSection("Today", items);
            }
            //TODO: ENCUESTAME-
            var thisWeek = this.arrayNotifications.THIS_WEEK;
            var thisMonth = this.arrayNotifications.THIS_MONTH;
            var lastMonth = this.arrayNotifications.LAST_MONTH;
            var fewMonthsAgo = this.arrayNotifications.FEW_MONTHS_AGO;
            var lastYear = this.arrayNotifications.LAST_YEAR;
            var longTimeAgo = this.arrayNotifications.LONG_TIME_AGO;
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
                console.debug("item", this.item);
                if (this.item.url != null) {
                    this._description
                    .appendChild(encuestame.notification
                    .buildURLDescription(
                            this.item.type,
                            this.item.additionalDescription,
                            this.item.url));
                } else {
                    this._description.innerHTML = this.item.additionalDescription;
                }
            },

            /*
             * remove.
             */
            _remove : function(event){
                //TODO: display dialog.
                this._removeNotification();
            },

            /*
             * remove notification
             */
            _removeNotification : function(){
                var load = dojo.hitch(this, function(data) {
                     dojo.destroy(this.domNode);
                 });
                 var error = function(error) {
                     console.debug("error", error);
                 };
                 var params = {notificationId : this.item.id};
                 encuestame.service.xhrGet(
                         encuestame.service.list.removeNotification, params, load, error);
            }

});