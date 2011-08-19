dojo.provide("encuestame.org.core.shared.utils.FolderOperations");

dojo.require("dojo.dnd.Source");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.InlineEditBox");
dojo.require('encuestame.org.core.commons');


dojo.declare(
    "encuestame.org.core.shared.utils.FolderOperations",
    [dijit._Widget, dijit._Templated],{

        /*
         * folder context.
         */
        folderContext : null,

        /*
         * context.
         */
        _context : ["tweetpoll", "poll", "survey"],

        /*
         * actions.
         */
        _actions : ["create", "update", "move", "list", "remove"],

        /*
         *
         */
        _ready : false,

        /*
         *
         */
        postCreate : function(){
             if (this.folderContext != null) {
                 this._ready = true;
             } else {
                 this._showError("folder context missing");
             }
        },


        /*
         * load folders.
         */
        _callFolderService : function(onLoad, params, action, enableStorFormat) {
            var load = dojo.hitch(this, onLoad );
            var error = function(error) {
                this._showError(error, null);
            };
            dojo.mixin(params, { store : enableStorFormat });
            if (this._ready) {
                encuestame.service.xhrGet(
                           this._getContextUrlService(this._actions[action]),
                           params,
                           load,
                           error);
            }
        },

        /*
         *
         */
        _showError : function(node, error){
        },

        /*
         * get action.
         */
        getAction : function(action){
            var position = dojo.indexOf(this._actions, action);
            console.info("getAction", position);
            if (position == -1) {
                console.error("invalid action");
            } else {
                return position;
            }
        },

        /*
         * get service by context.
         */
        _getContextUrlService : function(type){
           if (this.folderContext == this._context[0]) {
               return this._serviceAction(type, this._context[0]);
           } else if (this.folderContext == this._context[1]) {
               return this._serviceAction(type,this._context[1]);
           } else if (this.folderContext == this._context[2]) {
               return this._serviceAction(type, this._context[2]);
           }
        },

        /*
         * get service by action.
         */
        _serviceAction : function(type, context){
            if (type == this._actions[0]) {
                return encuestame.service.folder.create(context);
           } else if (type == this._actions[1]) {
               return encuestame.service.folder.update(context);
           } else if (type == this._actions[2]) {
               return encuestame.service.folder.move(context);
           } else if (type == this._actions[3]) {
               return encuestame.service.folder.list(context);
           } else if (type == this._actions[4]) {
               return encuestame.service.folder.remove(context);
           }
        }

});