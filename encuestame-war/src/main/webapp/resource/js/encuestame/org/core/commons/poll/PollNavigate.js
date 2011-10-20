dojo.provide("encuestame.org.core.commons.poll.PollNavigate");

dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.shared.utils.FilterList");
dojo.require("encuestame.org.core.shared.utils.TableLinkedList");

dojo.declare(
    "encuestame.org.core.commons.poll.PollNavigate",
    [encuestame.org.main.EnmeMainLayoutWidget, encuestame.org.core.shared.utils.FilterList],{

        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollNavigate.html"),

        /*
         *
         */
        _rows : {},

        /*
         *
         */
        property : "poll",

        /*
         *
         */
        _cache_items : [],

        /*
         * default parameters.
         */
        _params : { typeSearch : "ALL", keyword : null, max : null, start : 0},

        /*
         *
         */
        postCreate : function() {
            var def = new dojo.Deferred();
            try {
                //def.then(this.jota1);
                def.then(dojo.hitch(this, this._callServiceSearch));
                def.then(this._printRows);
                def.callback(true);
            } catch(e) {
               def.errback(new Error("load poll failed."));
            }
        },

        /*
         *
         */
        _callServiceSearch : function() {
            //this._params = {};
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