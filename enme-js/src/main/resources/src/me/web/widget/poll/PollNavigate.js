define( [
  "dojo/_base/declare",
  "dojo/Deferred",
  "dojo/topic",
  "dojo/_base/lang",
  "dojo/store/Memory",
  "dojo/DeferredList",
  "dijit/_WidgetBase",
  "dijit/_TemplatedMixin",
  "dijit/_WidgetsInTemplateMixin",
  "me/core/main_widgets/EnmeMainLayoutWidget",
  "me/web/widget/menu/DropDownMenuSelect",
  "me/web/widget/menu/DropDownMenuItem",
  "me/web/widget/support/ItemsFilterSupport",
  "me/web/widget/data/FilterSupport",
  "me/web/widget/poll/PollNavigateItem",
  "me/web/widget/data/FilterList",
  "me/core/enme",
  "dojo/text!me/web/widget/poll/templates/pollNavigate.html" ],
  function(
  declare,
  Deferred,
  topic,
  lang,
  Memory,
  DeferredList,
  _WidgetBase,
  _TemplatedMixin,
  _WidgetsInTemplateMixin,
  main_widget,
  DropDownMenuSelect,
  DropDownMenuItem,
  ItemsFilterSupport,
  FilterSupport,
  PollNavigateItem,
  FilterList,
  _ENME,
   template ) {

  "use strict";

  return declare( [
  _WidgetBase,
  _TemplatedMixin,
  FilterSupport,
  main_widget,
  FilterList,
  _WidgetsInTemplateMixin ], {

  // Template string.
  templateString: template,

  /*
   *
   */
  _rows: [],

  /**
   * Override property field.
   */
  property: "poll",

  /**
   * Override  folder scope field.
   */
  folder_scope: "poll",

  _started_search: false,

  /*
   * I18n message for this widget.
   */
  i18nMessage: {
    detail_manage_by_account: _ENME.getMessage("detail_manage_by_account"),
    detail_manage_today: _ENME.getMessage("detail_manage_today"),
    detail_manage_last_week: _ENME.getMessage("detail_manage_last_week"),
    detail_manage_favorites: _ENME.getMessage("detail_manage_favorites"),
    detail_manage_scheduled: _ENME.getMessage("detail_manage_scheduled"),
    detail_manage_all: _ENME.getMessage("detail_manage_all"),
    detail_manage_published: _ENME.getMessage("detail_manage_published"),
    detail_manage_unpublished: _ENME.getMessage("detail_manage_unpublished"),
    detail_manage_only_completed: _ENME.getMessage("detail_manage_only_completed"),
    detail_manage_poll_title: _ENME.getMessage("detail_manage_poll_title"),
    detail_manage_filters: _ENME.getMessage("detail_manage_filters"),
    detail_manage_poll_dropdown_title: _ENME.getMessage("detail_manage_poll_dropdown_title")
  },

    helpSteps: [
        {
            element: ".admon-table-options > .tb-right",
            intro: _ENME.getMessage( "help_poll_button_new" )
        },
        {
            element: ".web-tweetpoll-options",
            intro: _ENME.getMessage( "help_poll_aside_bar" )
        },
        {
            element: ".web-folder-wrapper",
            intro: _ENME.getMessage( "help_poll_folder_view" )
        },
        {
            element: ".web-twettpoll-list-menu",
            intro: _ENME.getMessage( "help_poll_advanced_search_view" )
        },
        {
            element: ".tp-items-wrapper",
            intro: _ENME.getMessage( "help_poll_detail_view" )
        }
    ],

  /*
   *
   */
  _cache_items: [],

  folderStore: null,

  /**
   * Poll Navigate default parameters.
   */
  _params: { typeSearch: "BYOWNER", keyword: null, max: 10, start: 0 },

  /**
   * Post Create Cycle Life.
   */
  postCreate: function() {
    topic.subscribe("/encuestame/folder/distribute/load", lang.hitch( this, function( foldersData ) {
      if ( foldersData && "success" in foldersData ) {
        var folderData = foldersData.success.folders;
        this.folderStore = new Memory({
          data: folderData
        });
      }
    }) );

      //Required subscribe to filter support.
      //should be in the parent class??
    topic.subscribe("/encuestame/filter/list/call", lang.hitch( this, function( item ) {
      this._callFilterList( item );
    }) );

    // Advanced search
      topic.subscribe("/encuestame/make/search", lang.hitch( this, function() {

        // The subscribe is able to recieve params, in this case is not required, that
        // job is made it by the FilterSupport
        if ( this._started_search ) {
          this._callServiceSearch();
        }
      }) );
    topic.subscribe("/encuestame/list/items/print", lang.hitch( this, function( data ) {
      this.printItems( data );
    }) );

    //Enable more support.
    if ( this.enable_more_support ) {
      this.enableMoreSupport( this._params.start, this._params.max, this._more );
    }

    //
    var dl = new DeferredList( [ this.restoreSearch() ], true, true );
    dl.then( dojo.hitch( this, function() {

      //Enable folder support.
      if ( this.folder_support && this._folder ) {
        this.enableFolderSupport();
      }

      //Restore the search filter if has been saved before
      if ( !("typeSearch" in this._params ) ) {
        var _hash = this._restoreHash();
        this._params.typeSearch = _hash === null ? this.currentSearch : _hash;
      }

      //Help links
      this.initHelpLinks( dojo.hitch( this, function() {
        this.updateHelpPageStatus( _ENME.config( "currentPath" ), true );
      }) );
    }) );
  },

  /*
   * @override
   */
  displayEmptyMessage: function() {
      var node = dojo.create("div");
      dojo.addClass( node, "web-items-no-results");
      node.innerHTML = _ENME.getMessage("commons_no_results");
      dojo.place( node, this._items );
  },

  /**
   * Function to clean _items node.
   */
  _empty: function() {
      dojo.empty( this._items );
  },

  /**
   * Subscribe function on filter search
   * @param typeSearch set the type of search
   */
  _callFilterList: function( typeSearch ) {
      this._params.typeSearch = typeSearch;
      return this._callServiceSearch();
  },

  /**
  * Mark as selected each main filter selected by the user.
  */
  updateMenu: function( id ) {
     dojo.query( ".optionItem" ).removeClass("optionItemSelected");
     var item = dojo.query( '.optionItem[type="' + id + '"]' );
     if ( item.length > 0 ) {
         dojo.addClass( item[ 0 ], "optionItemSelected" );
     }
  },

  /**
   *
   */
  _afterEach: function() {

     // TODO future.
     // var more = new encuestame.org.core.shared.utils.More();
  },

  /**
   * Call a service to retrieve a list of poll based on a previous filter parameters.
   */
  _callServiceSearch: function() {
      this._params = this.getFilterData( this._params );
      return this.loadItems( "encuestame.service.list.listPoll" );
  },

  // /**
  //  *
  //  * @property
  //  */
  // getFilterParams : function(params){
  //   this.getFilterData({typeSearch : (_hash === null ? this.defaultSearch: _hash) });
  //   return params;
  // },

  /**
   * Customize service params.
   */
  getParams: function() {
      return this._params;
  },

  /**
   * The url json service.
   * @returns
   */
  getUrl: function() {
      return "encuestame.service.list.listPoll";
  },

  /**
   * Create a new PollNavigateItem.
   */
  processItem: function( /** poll data**/  data, /** position **/ index ) {
      var row = new PollNavigateItem({
        data: data,
        folderStore: this.folderStore
      });
      this._rows.push( row );
      dojo.place( row.domNode, this._items );
  },

  /**
   *
   */
  _printRows: function() {
       dojo.forEach( this._rows,
            dojo.hitch( this, function( data, index ) {
                this._cache_items.push( data );
       }) );
  }
 });
});
