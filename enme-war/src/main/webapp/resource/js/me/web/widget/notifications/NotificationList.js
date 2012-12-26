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
 *  @class NotificationList
 */

define([
         "dojo",
         'dojo/_base/json',
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/notifications/NotificationListItem",
         "me/core/enme",
         "dojo/text!me/web/widget/notifications/template/notificationList.html" ],
        function(
                dojo,
                json,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                NotificationListItem,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

        /**
         * template string.
         * @property templateString
         */
        templateString : template,

        /**
         *
         * @property arrayNotifications
         */
        arrayNotifications : null,

        /**
         *
         * @property mobile
         */
        mobile : false,

        /**
         *
         * @property _start
         */
        _start : 0,

        /**
         *
         * @property _limit
         */
        _limit :  _ENME.config('activity').limit,

        /**
         *
         * @property
         */
        _seeMoreValue : false,

        /**
         * postCreate life cycle
         * @method postCreate
         */
        postCreate : function() {
            this._loadNotifications(this._start);
        },

        /**
         * Load notifications.
         * @method _loadNotifications
         */
        _loadNotifications : function(start) {
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
             var params = {
                    limit : this._limit,
                    start: start,
                    categorized: true
                };
             this.getURLService().get("encuestame.service.list.getAllNotifications", params, load, error , dojo.hitch(this, function() { }));
        },

        /*
         *
         * @method _loadMoreNotifications
         */
        _loadMoreNotifications : function(event){
                //console.debug("MORE ENCUESTAME-234", event);
        },

        /**
         * see more items.
         * @method _seeMore
         */
        _seeMore : function(){
            //only for mobile interface. override.
        },

        /**
         * build notification category section.
         * @method _buildSection
         */
        _buildSection : function(name, content) {
             var section = dojo.doc.createElement("div");
             dojo.addClass(section, "section");

             var title = dojo.doc.createElement("h3");
             title.innerHTML = name;
             //add title
             section.appendChild(title);
             //add content
             section.appendChild(content);
             //add see more
             if( this._seeMoreValue) {
                 section.appendChild(this._seeMore());
             }
             this._list.appendChild(section);
        },

        /**
         * Create a notification list item
         * @method _createNotificationItem
         */
        _createNotificationItem : function(item, category) {
             var widget = new NotificationListItem({
                 item : item,
                 category : "TODAY"});
            return widget;
        },

        /*
         * show list categories.
         */
        _showListCategories : function() {
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
});