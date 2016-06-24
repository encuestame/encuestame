/**
 * Table Linked List Support to create a table with extensible large datasets, with folder and "load more"
 * support, this widget always extensible and useful to create dinamic administrative interfaces.
 * @author juanATencuestame.org
 * @since 2011-10-23 23:46:29
 */
define( [
	"dojo/_base/declare",
  	"dojo/_base/array",
	"dojo/_base/lang",
	"me/web/widget/ui/More",
	"me/web/widget/folder/FoldersActions",
	"me/core/enme" ],
    function(
    declare,
    array,
    lang,
    More,
    FoldersActions,
    _ENME ) {

  return declare( null, {

    /**
    * Internal cache to store items retrieved from datase.
    */
   items_array: [],

   /**
    * Enables folder support.
    */
   folder_support: true,

   /**
    * The scope of folder, required parameter.
    * Could be: poll, tweetpoll, survey.
    */
   folder_scope: null,

   /**
    * The root property of data retrieved from server.
    * eg: data { error : null, success : { property : { more data ... } }}
    * Could be: poll, tweetpoll, survey.
    */
   property: null,

   /**
    * More widget reference.
    */
   more_widget: null,

   /**
    * Enable the link "more" to add more items to the stream, like in facebook.
    */
   enable_more_support: true,

   /**
    * Is in use?
    */
   enable_more_items: true,

   /**
    * Enable the more support, this retrieve next X items from provide service.
    */
   enableMoreSupport: function( /** start list value **/ start, /** max values **/ max, /** node to append **/ node ) {
       if ( node ) {
           var pagination = { _start: start, _maxResults: max };
           this.more_widget = new More({
                       pagination: pagination
           });
           dojo.place( this.more_widget.domNode, node );
       }
   },

   /**
    * Enable Folder Support.
    */
   enableFolderSupport: function() {
        if ( this.folder_support ) {
            var folder = new FoldersActions({ folderContext: this.folder_scope });
            this._folder.appendChild( folder.domNode );
        }
   },

  /**
   * Print items
   * @param data
   */
   printItems: function( data ) {
	  this._empty();
	  var items = data;
	  if ( items.length > 0 ) {
		  array.forEach( items, lang.hitch( this, function( data, index ) {
			  if ( dojo.isFunction( this.processItem ) ) {
				  this.items_array.push( this.processItem( data, index ) );
			  }
		  }) );
		  this.more_widget.show();
	  } else {
		  this.displayEmptyMessage();
		  this.more_widget.hide();
	  }
	  this._afterEach();
   },

   /**
    * Display a message if results are equal 0.
    */
   displayEmptyMessage: function() {},

   /**
    * A service support to retrieve items based on list of parameters.
    */
   loadItems: function( url ) {
       var load = lang.hitch( this, function( data ) {
           if ("success" in data ) {
               this.printItems( data.success[ this.property ] );
           }
       });
       var error = this.handlerError;
       return this.getURLService().get( url, this.getParams(), load, error, lang.hitch( this, function() {

       }), true );
   },

   /*
    *
    */
   _afterEach: function() {},

   /*
    *
    */
   _empty: function() {},

   /*
    *
    */
   handlerError: function() {},

   /**
    * Process a items on successfull server response.
    * Always override by child widgets.
    */
   processItem: function( data, index ) {},

   /**
    * List of parameters, always override by child widgets.
    */
   getParams: function() {
           return {};
   }

  });
});
