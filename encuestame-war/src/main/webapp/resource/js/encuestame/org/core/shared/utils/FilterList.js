dojo.provide("encuestame.org.core.shared.utils.FilterList");

dojo.require("dojo.hash");
dojo.require("encuestame.org.core.shared.utils.TableLinkedList");

/*
 *
 */
dojo.declare("encuestame.org.core.shared.utils.FilterList",
        [encuestame.org.core.shared.utils.TableLinkedList], {

    /*
     *
     */
    items_array : [],

    /*
     *
     */
    currentSearch : "BYOWNER",

    /*
     *
     */
    start : 0,

    /*
     *
     */
    _changeHash : function(id){
        var hash = dojo.queryToObject(dojo.hash());
        params = {
           f : id
        };
        dojo.hash(dojo.objectToQuery(params));
    },

    /*
     * searc by all.
     */
    _searchByAll : function(event){
        dojo.stopEvent(event);
        this.currentSearch = "ALL";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        console.debug(event);
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /*
    *
    */
   resetPagination : function(){
       this.start = 0;
   },

    /*
     * search by account.
     */
    _searchByAccount : function(event){
        dojo.stopEvent(event);
        this.currentSearch = "BYOWNER";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /*
     * search by favourites.
     */
    _searchByFavourites : function(event){
        dojo.stopEvent(event);
        this.currentSearch = "FAVOURITES";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /*
     * search by secheduled.
     */
    _searchByScheduled : function(event){
        dojo.stopEvent(event);
        this.currentSearch = "SCHEDULED";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /*
     * search by last day.
     */
    _searchByLastDay : function(event){
        dojo.stopEvent(event);
        this.currentSearch = "LASTDAY";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    },

    /*
     * search by last week.
     */
    _searchByLastWeek : function(event){
        dojo.stopEvent(event);
        this.currentSearch = "LASTWEEK";
        this._changeHash(this.currentSearch);
        this.resetPagination();
        dojo.publish("/encuestame/filter/list/call", [this.currentSearch]);
    }
});