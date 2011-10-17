dojo.provide("encuestame.org.core.commons.poll.PollNavigate");

dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

dojo.declare(
    "encuestame.org.core.commons.poll.PollNavigate",
    [encuestame.org.main.EnmeMainLayoutWidget],{

        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollNavigate.html"),


        _rows : {},

        _cache_items : [],

        /*
         *
         */
        postCreate : function() {
            var def = new dojo.Deferred();
            try {
                //def.then(this.jota1);
                def.then(this._callServiceSearch);
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
            var load = dojo.hitch(this, function(data){
                console.info("Data", data);
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.list.listPoll, {typeSearch : "BYOWNER"}, load, error);
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