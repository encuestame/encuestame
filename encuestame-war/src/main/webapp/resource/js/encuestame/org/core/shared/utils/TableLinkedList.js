dojo.provide("encuestame.org.core.shared.utils.TableLinkedList");


dojo.require("encuestame.org.core.shared.utils.More");

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
     * enable
     */
    enable_more_items : true,

    /*
     *
     */
    property : null,

    /*
     * more widget reference.
     */
    more_widget : null,

    /*
     *
     */
    enable_more_support : true,

    /*
     * enable the more support, this retrieve next X items from provide service.
     */
    enableMoreSupport : function(/** start list value **/ start, /** max values **/ max, /** node to append **/ node) {
        if (node) {
            var pagination = {_start : start, _maxResults : max };
            this.more_widget = new encuestame.org.core.shared.utils.More({
                        pagination: pagination
            });
            dojo.place(this.more_widget.domNode, node);
        }
    },


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
                this._afterEach();
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
    _afterEach : function(){
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