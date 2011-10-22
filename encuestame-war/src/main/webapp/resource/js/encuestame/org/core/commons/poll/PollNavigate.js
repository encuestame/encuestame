dojo.provide("encuestame.org.core.commons.poll.PollNavigate");

dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.shared.utils.FoldersActions");;
dojo.require("encuestame.org.core.shared.utils.FilterList");
dojo.require("encuestame.org.core.shared.utils.TableLinkedList");

dojo.declare(
    "encuestame.org.core.commons.poll.PollNavigate",
    [encuestame.org.main.EnmeMainLayoutWidget,
     encuestame.org.core.shared.utils.FilterList],{

        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollNavigate.html"),

        /*
         *
         */
        _rows : [],

        /*
         *
         */
        property : "poll",

        /*
         * folder scope.
         */
        folder_scope : "poll",

        /*
         *
         */
        _cache_items : [],

        /*
         * default parameters.
         */
        _params : { typeSearch : "BYOWNER", keyword : null, max : null, start : 0},

        /*
         *
         */
        postCreate : function() {
            var def = new dojo.Deferred();
            dojo.subscribe("/encuestame/filter/list/call", this, "_callFilterList");
            try {
                //def.then(this.jota1);
                def.then(dojo.hitch(this, this._callServiceSearch));
                def.then(this._printRows);
                def.callback(true);
            } catch(e) {
               def.errback(new Error("load poll failed."));
            }
            if (this.folder_support && this._folder) {
               this.enableFolderSupport();
            }
        },

        _empty : function() {
            console.debug("empty items");
            dojo.empty(this._items);
        },

        /*
         *
         */
        _callFilterList : function(typeSearch) {
            this._params.typeSearch = typeSearch;
            console.info("_callFilterList", typeSearch);
            console.info("_callFilterList", this._params);
            this._callServiceSearch();
        },

        /*
         *
         */
        _callServiceSearch : function() {
            dojo.hitch(this, this.loadItems(encuestame.service.list.listPoll));
        },


        /*
         * customize service params.
         */
        getParams : function() {
            return this._params;
        },

        getUrl : function(){
            return encuestame.service.list.listPoll;
        },


        /*
         * process item.
         */
        processItem : function(/** poll data**/  data, /** position **/ index){
            console.info(data);
            var row = new encuestame.org.core.commons.poll.PollNavigateItem({ data: data});
            this._rows.push(row);
            dojo.place(row.domNode, this._items);
            console.info("row added");
        },

        /*
         *
         */
        _printRows : function(){
             dojo.forEach(this._rows,
                  dojo.hitch(this, function(data, index) {
                      this._cache_items.push(data);
             }));
        }
});

/**
 *
 */
dojo.declare(
        "encuestame.org.core.commons.poll.PollNavigateItem", [encuestame.org.main.EnmeMainLayoutWidget],{

        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollListItem.html"),

        /*
         *
         */
        data : null,

        /*
         *
         */
        postCreate : function() {

        }
});