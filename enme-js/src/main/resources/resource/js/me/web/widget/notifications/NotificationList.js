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

define( [
         "dojo",
         "dojo/_base/json",
         "dojo/dom-construct",
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
                domConstruct,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                NotificationListItem,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

        /**
         * Template string.
         * @property templateString
         */
        templateString: template,

        /**
         *
         * @property arrayNotifications
         */
        arrayNotifications: null,

        /**
         *
         * @property mobile
         */
        mobile: false,

        /**
         *
         * @property _start
         */
        _start: 0,

        /**
         *
         * @property _limit
         */
        _limit:  600,

        /**
         *
         * @property
         */
        _seeMoreValue: false,

        /**
         * PostCreate life cycle
         * @method postCreate
         */
        postCreate: function() {
            this._loadNotifications( this._start );
        },

        /**
         * Load notifications.
         * @method _loadNotifications
         */
        _loadNotifications: function( start ) {

             //
            var load = dojo.hitch( this, function( data ) {
                 this.arrayNotifications = data.success.notifications;
                 this._showListCategories();
                 console.debug("notifications", this.arrayNotifications );
             });

             //
             var error = function( error ) {
                 console.debug("error", error );
             };

             //
             if ( start === null ) {
                 start = 0;
             }

             // Params
             var params = {
                    limit: this._limit,
                    start: start,
                    categorized: true
                };
             this.getURLService().get("encuestame.service.list.getAllNotifications", params, load, error, dojo.hitch( this, function() { }) );
        },

        /*
         *
         * @method _loadMoreNotifications
         */
        _loadMoreNotifications: function( event ) {

                //Console.debug("MORE ENCUESTAME-234", event);
        },

        /**
         * See more items.
         * @method _seeMore
         */
        _seeMore: function() {

            //Only for mobile interface. override.
        },

        /**
         * Build notification category section.
         * @method _buildSection
         */
        _buildSection: function( name, content ) {
             var section = dojo.doc.createElement("div");
             dojo.addClass( section, "section");

             var title = domConstruct.create("div"),
             inner_title = domConstruct.create("span");
             dojo.addClass( title, "web-sub-tittle" );
             dojo.addClass( title, "web-b-top-bottom" );
             dojo.addClass( title, "web-bottomborder" );
             inner_title.innerHTML = name;

             //Add title
             title.appendChild( inner_title );
             section.appendChild( title );

             //Add content
             section.appendChild( content );

             //Add see more
             if ( this._seeMoreValue ) {
                 section.appendChild( this._seeMore() );
             }
             this._list.appendChild( section );
        },

        /**
         * Create a notification list item
         * @method _createNotificationItem
         */
        _createNotificationItem: function( item, category ) {
             var widget = new NotificationListItem({
                 item: item,
                 category: "TODAY" });
            return widget;
        },

        /*
         * Show list categories.
         */
        _showListCategories: function() {
            var today = this.arrayNotifications.TODAY;
            if ( today.length > 0 ) {
                var items = dojo.doc.createElement("ul");
                dojo.forEach( today, dojo.hitch( this, function( item, index ) {
                    var todayWidget = this._createNotificationItem( item, "TODAY");
                    items.appendChild( todayWidget.domNode );
                }) );
               this._buildSection("Today", items );
            }

            //TODO: ENCUESTAME-
            var thisWeek = this.arrayNotifications.THIS_WEEK;
            if ( thisWeek.length > 0 ) {
                var items_week = dojo.doc.createElement("ul");
                dojo.forEach( thisWeek, dojo.hitch( this, function( item, index ) {
                    var todayWidget = this._createNotificationItem( item, "THIS WEEK");
                    items_week.appendChild( todayWidget.domNode );
                }) );
               this._buildSection("This Week", items_week );
            }
            var thisMonth = this.arrayNotifications.THIS_MONTH;
             if ( thisMonth.length > 0 ) {
                var items_month = dojo.doc.createElement("ul");
                dojo.forEach( thisMonth, dojo.hitch( this, function( item, index ) {
                    var todayWidget = this._createNotificationItem( item, "THIS MONTH");
                    items_month.appendChild( todayWidget.domNode );
                }) );
               this._buildSection("This Month", items_month );
            }
            var lastMonth = this.arrayNotifications.LAST_MONTH;
            if ( lastMonth.length > 0 ) {
                var items_last_month = dojo.doc.createElement("ul");
                dojo.forEach( lastMonth, dojo.hitch( this, function( item, index ) {
                    var todayWidget = this._createNotificationItem( item, "LAST MONTH");
                    items_last_month.appendChild( todayWidget.domNode );
                }) );
               this._buildSection("Last Month", items_last_month );
            }
            var fewMonthsAgo = this.arrayNotifications.FEW_MONTHS_AGO;
            if ( fewMonthsAgo.length > 0 ) {
                var items_months_ago = dojo.doc.createElement("ul");
                dojo.forEach( fewMonthsAgo, dojo.hitch( this, function( item, index ) {
                    var todayWidget = this._createNotificationItem( item, "FEW MOTNHS AGO");
                    items_months_ago.appendChild( todayWidget.domNode );
                }) );
               this._buildSection("Few Months Ago", items_months_ago );
            }
            var lastYear = this.arrayNotifications.LAST_YEAR;
            if ( lastYear.length > 0 ) {
                var items_last_year = dojo.doc.createElement("ul");
                dojo.forEach( lastYear, dojo.hitch( this, function( item, index ) {
                    var todayWidget = this._createNotificationItem( item, "LAST YEAR");
                    items_last_year.appendChild( todayWidget.domNode );
                }) );
               this._buildSection("Last Year", items_last_year );
            }
            var longTimeAgo = this.arrayNotifications.LONG_TIME_AGO;
            if ( longTimeAgo.length > 0 ) {
                var items_time_ago = dojo.doc.createElement("ul");
                dojo.forEach( longTimeAgo, dojo.hitch( this, function( item, index ) {
                    var todayWidget = this._createNotificationItem( item, "LONG TIME AGO");
                    items_time_ago.appendChild( todayWidget.domNode );
                }) );
               this._buildSection("Long Time Ago", items_time_ago );
            }

            // Console.debug("thisWeek", thisWeek);
            // console.debug("thisMonth", thisMonth);
            // console.debug("lastMonth", lastMonth);
            // console.debug("fewMonthsAgo", fewMonthsAgo);
            // console.debug("lastYear", lastYear);
            // console.debug("longTimeAgo", longTimeAgo);
        }

    });
});
