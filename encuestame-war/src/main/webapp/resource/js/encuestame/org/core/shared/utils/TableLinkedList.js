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
    folder_support : true,

    /*
     *
     */
    folder_scope : null,

    /*
     *
     */
    property : null,


    enableFolderSupport : function(){
         if (this.folder_support) {
             var folder = new encuestame.org.core.shared.utils.FoldersActions({folderContext: this.folder_scope});
             this._folder.appendChild(folder.domNode);
         }
    },


    /*
     *
     */
    loadItems : function(url) {
        var load = dojo.hitch(this, function(data) {
            console.info("load 2 data", data);
            if ("success" in data) {
                this._empty();
                console.debug("pro", data.success[this.property]);
                dojo.forEach(data.success[this.property], dojo.hitch(this, function(
                        data, index) {
                    console.info("for each", data);
                    if (dojo.isFunction(this.processItem)) {
                        this.items_array.push(this.processItem(data, index));
                    }
                }));
            } else {
                console.warn("no success");
            }
        });
        var error = this.handlerError;
        console.info("url", url);
        console.info("this.getParams", this.getParams());
        encuestame.service.xhrGet(url, this.getParams(), load, error);
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