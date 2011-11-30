dojo.provide("encuestame.org.core.commons.poll.PollNavigate");

dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.shared.utils.FoldersActions");;
dojo.require("encuestame.org.core.shared.utils.FilterList");
dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.core.shared.utils.DropDownMenuSelect");
dojo.require("encuestame.org.core.shared.utils.More");
dojo.require("encuestame.org.core.commons.support.PanelWipe");
dojo.require("encuestame.org.core.shared.utils.StandBy");

dojo.require("dijit.InlineEditBox");
dojo.require("dijit.form.Textarea");

dojo.require("dojox.widget.Toaster");

dojo.require("dijit.form.CheckBox");

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
        _params : { typeSearch : "BYOWNER", keyword : null, max : 10, start : 0},

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
            //enable folder support.
            if (this.folder_support && this._folder) {
               this.enableFolderSupport();
            }
            //enable more support.
            if (this.enable_more_support) {
                this.enableMoreSupport(this._params.start, this._params.max, this._more);
            }
            dojo.addOnLoad(function() {
                dojo.connect(dojo.byId('strapline'), 'onclick', function(event) {
                    dojo.publish('myMessages', [{ message: 'Qwerty', type: "error", duration: 0}])
                });
            });
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
        _afterEach : function() {
            //var more = new encuestame.org.core.shared.utils.More();
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
        _standBy : null,


        widget_detail : null,

        /*
         *
         */
        postCreate : function() {
            console.debug("row data", this.data);
            var panel = new encuestame.org.core.commons.support.PanelWipe(this._more, null, null);
            panel.connect(this._edit, dojo.hitch(this, this._callEditInfo));
            this._standBy = dijit.byId("standby_"+this.id);
            this.widget_detail = new encuestame.org.core.commons.poll.PollNavigateItemDetail({ data : this.data});
            dojo.place(this.widget_detail.domNode, this._more);
        },

        /*
         *
         */
        _callEditInfo : function() {
            console.info("_callEditInfo");
            var load = dojo.hitch(this, function(data) {
                this._standBy.stop();
                console.info("poll detail", data);
            });
            var error = dojo.hitch(this, function(error) {
                this._standBy.stop();
                console.debug("error", error);
            });
            var params = {
                    id : this.data.id
            };
            this._standBy.startup();
            this._standBy.start();
            encuestame.service.xhrGet(encuestame.service.list.poll.detail, params, load, error);
        },
});


dojo.declare(
        "encuestame.org.core.commons.poll.PollNavigateItemDetail", [encuestame.org.main.EnmeMainLayoutWidget],{

       /*
        *
        */
       templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollListItemDetail.html"),


       postCreate : function(){

       }
});