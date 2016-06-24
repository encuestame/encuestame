
/**
 * A widget to provide filter support to administrative interfaces
 * like tables with large datasets.It's a extension of TableLinkedList.
 */
define( [
     "dojo/_base/declare",
     "dojo/hash",
	 "dojo/Deferred",
     "dojo/io-query",
     "me/web/widget/data/TableLinkedList",
     "me/core/enme" ],
    function(
    declare,
    hash,
    Deferred,
    ioQuery,
    TableLinkedList,
    _ENME ) {

  "use strict";

  return declare( [ TableLinkedList ], {

    /*
    *
    */
   items_array: [],

   /**
    * Define the current search on load the interface.
    */
   currentSearch: "BYOWNER",

  /**
   * The storage  key.
   * @property _storage_key
   */
   _storage_key: "poll-list-key",

   /**
    * Start dataset default row.
    */
   start: 0,

  /**
   * Define if the first search has been done
   */
	_started_search: false,

   /**
    * Update the hash on browser address bar.
    * @param id the id of filter.
    */
   _changeHash: function( id ) {
       var _hash = ioQuery.queryToObject( hash() );
       var params = {
          f: id
       };
       hash( ioQuery.objectToQuery( params ) );
       this.updateMenu( id );

       //Store the selected filter
       _ENME.storeItem( this._storage_key, { key: id.toString() });
   },

   /**
     * Update something in the menu, method should be implemented.
    */
   updateMenu: function() {},

    /**
     *
     */
    _restoreHash: function() {
      var _r = _ENME.restoreItem( this._storage_key );
      var key = dojo.fromJson( _r ).key;
      return ( _r === null ? null : key );
    },

   /**
    *
    * @property
    */
    getFilterParams: function( params ) { return params; },

	// Override in the child
	_callFilterList: function() {},

    /**
     * Restore the search.
     * @return Promise
     */
    restoreSearch: function() {
	    var def = new Deferred();
	    var that = this;
        var _r = _ENME.restoreItem( this._storage_key );
        var _key = _r ? dojo.fromJson( _r ).key : this.currentSearch;
        this.updateMenu( _key );
        this._callFilterList( _key ).then( function() {
	        that._started_search = true;
	        def.resolve("success");
        }, function() {
			def.reject("error");
        });
	    return def.promise;
    },

   /**
    * Search by all.
    * @event event
    */
   _searchByAll: function( e ) {
	   e.preventDefault();
       this.currentSearch = "ALL";
       this._changeHash( this.currentSearch );
       this.resetPagination();
       this._callFilterList( this.currentSearch );
   },

  /**
   * Reset pagination.
   */
  resetPagination: function() {
      this.start = 0;
  },

   /**
    * Search by account.
    * @event dojo event
    */
   _searchByAccount: function( event ) {
       dojo.stopEvent( event );
       this.currentSearch = "BYOWNER";
       this._changeHash( this.currentSearch );
       this.resetPagination();
       this._callFilterList( this.currentSearch );
   },

   /**
    * Search by favourites.
    * @event dojo event
    */
   _searchByFavourites: function( event ) {
       dojo.stopEvent( event );
       this.currentSearch = "FAVOURITES";
       this._changeHash( this.currentSearch );
       this.resetPagination();
       this._callFilterList( this.currentSearch );
   },

   /**
    * Search by scheduled.
    * @event dojo event
    */
   _searchByScheduled: function( event ) {
       dojo.stopEvent( event );
       this.currentSearch = "SCHEDULED";
       this._changeHash( this.currentSearch );
       this.resetPagination();
       this._callFilterList( this.currentSearch );
   },

   /**
    * Search by last day.
    * @event dojo event
    */
   _searchByLastDay: function( event ) {
       dojo.stopEvent( event );
       this.currentSearch = "LASTDAY";
       this._changeHash( this.currentSearch );
       this.resetPagination();
	   this._callFilterList( this.currentSearch );
   },

   /**
    * Search by last week.
    * @event dojo event
    */
   _searchByLastWeek: function( event ) {
       dojo.stopEvent( event );
       this.currentSearch = "LASTWEEK";
       this._changeHash( this.currentSearch );
       this.resetPagination();
	   this._callFilterList( this.currentSearch );
   }

  });
});
