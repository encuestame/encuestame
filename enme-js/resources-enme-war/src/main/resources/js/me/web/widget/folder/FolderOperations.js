define([
     "dojo/_base/declare",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme"],
    function(
    declare,
    main_widget,
    _ENME) {

  return declare([main_widget], {

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
   postCreate : function() {
        if (this.folderContext != null) {
            this._ready = true;
        } else {
            this._showError("folder context missing");
        }
   },


   /*
    * load folders.
    */
   //this.getURLService().service(
   _callFolderService : function(onLoad, params, action, enableStorFormat) {
       var load = dojo.hitch(this, onLoad );
       var error = dojo.hitch(this,  function(error) {
           this._showError(error, null);
       });
       dojo.mixin(params, { store : enableStorFormat });
       if (this._ready) {
           var url = this._getContextUrlService(this._actions[action]);
           encuestame.service.xhrGet(
                      url,
                      params,
                      load,
                      error);
       }
   },

   /*
    *
    */
   _showError : function(node, error) {},

   /*
    * get action.
    */
   getAction : function(action) {
       var position = dojo.indexOf(this._actions, action);
       //console.info("getAction position", position);
       if (position == -1) {
           console.error("invalid action");
       } else {
           return position;
       }
   },

   /*
    * get service by context.
    */
   _getContextUrlService : function(type) {
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
   //this.getURLService().service(
   _serviceAction : function(type, context) {
       if (type == this._actions[0]) {
           return this.getURLService().service('encuestame.service.folder.create', [context]);
      } else if (type == this._actions[1]) {
        return this.getURLService().service('encuestame.service.folder.update', [context]);
      } else if (type == this._actions[2]) {
        return this.getURLService().service('encuestame.service.folder.move', [context]);
      } else if (type == this._actions[3]) {
        return this.getURLService().service('encuestame.service.folder.list', [context]);
      } else if (type == this._actions[4]) {
        return this.getURLService().service('encuestame.service.folder.remove', [context]);
      }
   }

  });
});