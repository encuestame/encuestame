define([
    "dojo/_base/declare",
	"dojo/_base/lang",
    "me/core/main_widgets/EnmeMainLayoutWidget",
	"me/core/URLServices",
    "me/core/enme"],
    function(
    declare,
    lang,
    main_widget,
    URLServices,
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
   _actions : ["create", "update", "move", "list", "remove", "items"],

   /*
    *
    */
   _ready : false,

   /*
    *
    */
   postCreate : function() {
        if (this.folderContext !== null) {
            this._ready = true;
        } else {
            this._showError("folder context missing");
        }
   },


   /*
    * load folders.
    */
   _callFolderService : function(onLoad, params, action, enableStorFormat) {
       var load = lang.hitch(this, onLoad);
       var error = lang.hitch(this,  function(error) {
           this._showError(error, null);
       });
       lang.mixin(params, { store : enableStorFormat });
       if (this._ready) {
	       var ac = this._actions[action];
           var url = this._getContextUrlService(ac);
	       if (ac === 'create') {
		       URLServices.post(url, params, load, error);
	       } else if (ac === 'move' || ac === 'update') {
		       URLServices.put(url, params, load, error);
	       } else if (ac === 'remove') {
		       URLServices.del(url, params, load, error);
	       } else {
		       URLServices.get(url, params, load, error);
	       }
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
   _serviceAction : function(type, context) {
       if (type == this._actions[0]) {
            return ['encuestame.service.folder.create', [context]];
      } else if (type == this._actions[1]) {
            return ['encuestame.service.folder.update', [context]];
      } else if (type == this._actions[2]) {
            return ['encuestame.service.folder.move', [context]];
      } else if (type == this._actions[3]) {
            return ['encuestame.service.folder.list', [context]];
      } else if (type == this._actions[4]) {
            return ['encuestame.service.folder.remove', [context]];
       } else if (type == this._actions[5]) {
	       return ['encuestame.service.folder.items', [context]];
       }
   }

  });
});