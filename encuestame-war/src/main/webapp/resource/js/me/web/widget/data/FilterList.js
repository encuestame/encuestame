/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.shared.utils.FilterList");

dojo.require("dojo.hash");
dojo.require("encuestame.org.core.shared.utils.TableLinkedList");

/**
 * A widget to provide filter support to administrative interfaces
 * like tables with large datasets.It's a extension of TableLinkedList.
 */
dojo.declare("encuestame.org.core.shared.utils.FilterList",
        [encuestame.org.core.shared.utils.TableLinkedList], {

    /*
     *
     */
    items_array : [],

    /**
     * Define the current search on load the interface.
     */
    currentSearch : "BYOWNER",

    /**
     * Start dataset default row.
     */
    start : 0,

    /**
     * Update the hash on browser address bar.
     * @param id the id of filter.
     */
    _changeHash : function(id) {
        var hash = dojo.queryToObject(dojo.hash());
        params = {
           f : id
        };
        dojo.hash(dojo.objectToQuery(params));
    },

    /**
     * Search by all.
     * @event dojo event
     */
    _searchByAll : function(event) {
        dojo.stopEvent(event);
        this.currentSearch = "ALL";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        console.debug(event);
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

   /**
    * Reset pagination.
    */
   resetPagination : function() {
       this.start = 0;
   },

    /**
     * Search by account.
     * @event dojo event
     */
    _searchByAccount : function(event) {
        dojo.stopEvent(event);
        this.currentSearch = "BYOWNER";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /**
     * search by favourites.
     * @event dojo event
     */
    _searchByFavourites : function(event) {
        dojo.stopEvent(event);
        this.currentSearch = "FAVOURITES";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /**
     * search by scheduled.
     * @event dojo event
     */
    _searchByScheduled : function(event) {
        dojo.stopEvent(event);
        this.currentSearch = "SCHEDULED";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /**
     * Search by last day.
     * @event dojo event
     */
    _searchByLastDay : function(event) {
        dojo.stopEvent(event);
        this.currentSearch = "LASTDAY";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /**
     * Search by last week.
     * @event dojo event
     */
    _searchByLastWeek : function(event) {
        dojo.stopEvent(event);
        this.currentSearch = "LASTWEEK";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    }
});