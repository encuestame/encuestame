dojo.provide("encuestame.org.core.shared.utils.TableLinkedList");

dojo.declare("encuestame.org.core.shared.utils.TableLinkedList", null, {

    items_array : [],

    /*
     *
     */
    loadItems : function(items, url) {
        var load = dojo.hitch(this, function(data) {
            dojo.empty(this._items);
            dojo.forEach(items, dojo.hitch(this, function(
                    data, index) {
                if (dojo.isFunction(this.processItem)) {
                    this.items_array.push(this.processItem(data, index));
                }
            }));
        });
        var error = this.handlerError;
        encuestame.service.xhrGet(this.url, this.getParams, load, error);
    },

    /*
     *
     */
    handlerError : function(){

    },

    /*
     *
     */
    processItem : function(data, index){

    },

    /*
     *
     */
    getParams : function(){
        return {};
    }

});