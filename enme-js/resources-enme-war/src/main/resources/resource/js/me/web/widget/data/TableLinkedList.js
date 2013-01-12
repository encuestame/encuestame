/**
 * Table Linked List Support to create a table with extensible large datasets, with folder and "load more"
 * support, this widget always extensible and useful to create dinamic administrative interfaces.
 * @author juanATencuestame.org
 * @since 2011-10-23 23:46:29
 */
define([
     "dojo/_base/declare",
     "me/web/widget/ui/More",
     "me/web/widget/folder/FoldersActions",
     "me/core/enme"],
    function(
    declare,
    More,
    FoldersActions,
    _ENME) {

  return declare(null, {

    /**
    * Internal cache to store items retrieved from datase.
    */
   items_array : [],

   /**
    * Enables folder support.
    */
   folder_support : true,

   /**
    * The scope of folder, required parameter.
    * Could be: poll, tweetpoll, survey.
    */
   folder_scope : null,

   /**
    * The root property of data retrieved from server.
    * eg: data { error : null, success : { property : { more data ... } }}
    * Could be: poll, tweetpoll, survey.
    */
   property : null,

   /**
    * more widget reference.
    */
   more_widget : null,

   /**
    * enable the link "more" to add more items to the stream, like in facebook.
    */
   enable_more_support : true,

   /**
    * is in use?
    */
   enable_more_items : true,

   /**
    * enable the more support, this retrieve next X items from provide service.
    */
   enableMoreSupport : function(/** start list value **/ start, /** max values **/ max, /** node to append **/ node) {
       if (node) {
           var pagination = {_start : start, _maxResults : max };
           this.more_widget = new More({
                       pagination: pagination
           });
           dojo.place(this.more_widget.domNode, node);
       }
   },

   /**
    * Enable Folder Support.
    */
   enableFolderSupport : function() {
        if (this.folder_support) {
            var folder = new FoldersActions({folderContext: this.folder_scope});
            this._folder.appendChild(folder.domNode);
        }
   },

   /**
    * Display a message if results are equal 0.
    */
   displayEmptyMessage : function() {},

   /**
    * A service support to retrieve items based on list of parameters.
    */
   loadItems : function(url) {
       var load = dojo.hitch(this, function(data) {
           //console.info("load 2 data", data);
           if ("success" in data) {
               this._empty();
               //console.debug("pro", data.success[this.property]);
               var items = data.success[this.property];
               if (items.length > 0) {
                   dojo.forEach(items, dojo.hitch(this, function(
                           data, index) {
                       //console.info("for each", data);
                       if (dojo.isFunction(this.processItem)) {
                           this.items_array.push(this.processItem(data, index));
                       }
                   }));
                   this.more_widget.show();
               } else {
                   this.displayEmptyMessage();
                   this.more_widget.hide();
               }
               this._afterEach();
           } else {
               ///console.warn("no success");
           }
       });
       var error = this.handlerError;
       this.getURLService().get(url, this.getParams(), load, error , dojo.hitch(this, function() {

       }));
   },

   /*
    *
    */
   _afterEach : function() {},

   /*
    *
    */
   _empty : function() {},

   /*
    *
    */
   handlerError : function(){},

   /**
    * Process a items on successfull server response.
    * Always override by child widgets.
    */
   processItem : function(data, index){},

   /**
    * List of parameters, always override by child widgets.
    */
   getParams : function() {
           return {};
   }

  });
});