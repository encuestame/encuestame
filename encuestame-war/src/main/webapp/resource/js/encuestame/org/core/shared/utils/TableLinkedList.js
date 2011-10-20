dojo.provide("encuestame.org.core.shared.utils.TableLinkedList");

/**
 *
 */
dojo.declare("encuestame.org.core.shared.utils.TableLinkedList", null, {

    /*
     *
     */
    items_array : [],

    /*
     *
     */
    property : null,


    /*
     *
     */
    loadItems : function(url) {
        var load = dojo.hitch(this, function(data) {
            if ("success" in data && this.property) {
                this._empty();
                dojo.forEach(data.success[this.constructorproperty], dojo.hitch(this, function(
                        data, index) {
                    if (dojo.isFunction(this.processItem)) {
                        this.items_array.push(this.processItem(data, index));
                    }
                }));
            }
        });
        var error = this.handlerError;
        encuestame.service.xhrGet(this.url, this.getParams, load, error);
    },

    /*
     *
     */
    _empty : function() {},

    /*
     *
     */
    handlerError : function(){},

    /*
     *
     */
    processItem : function(data, index){},

    /*
     *
     */
    getParams : function(){ return {};}

});