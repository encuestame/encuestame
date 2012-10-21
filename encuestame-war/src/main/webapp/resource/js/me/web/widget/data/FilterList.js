/**
 * A widget to provide filter support to administrative interfaces
 * like tables with large datasets.It's a extension of TableLinkedList.
 */
define([
     "dojo/_base/declare",
     "dojo/hash",
     "dojo/io-query",
     "me/web/widget/data/TableLinkedList",
     "me/core/enme"],
    function(
    declare,
    hash,
    ioQuery,
    TableLinkedList,
    _ENME) {

  return declare([TableLinkedList], {

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
       var hash = ioQuery.queryToObject(hash());
       params = {
          f : id
       };
       hash(ioQuery.objectToQuery(params));
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
});