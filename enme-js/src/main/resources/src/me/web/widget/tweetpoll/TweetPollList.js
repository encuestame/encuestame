/*
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.147
 *  @module TweetPoll
 *  @namespace Widget
 *  @class TweetPollList
 */
define( [
 "dojo",
 "dojo/_base/json",
 "dojo/_base/declare",
 "dojo/_base/array",
 "dojo/query",
 "dojo/DeferredList",
 "dojo/topic",
 "dojo/dom-class",
 "dojo/_base/lang",
 "dijit/_WidgetBase",
 "dijit/_TemplatedMixin",
 "dijit/_WidgetsInTemplateMixin",
 "me/core/main_widgets/LoggedUtilities",
 "me/web/widget/tweetpoll/TweetPollListItem",
 "me/web/widget/folder/FoldersActions",
 "me/web/widget/ui/MessageSearch",
 "me/web/widget/data/FilterSupport",
 "me/core/enme",
 "dojo/_base/lang",
 "dojo/dnd/Source",
 "dojo/hash",
 "dojo/io-query",
 "dojo/dom-construct",
 "dojo/text!me/web/widget/tweetpoll/templates/tweetPollList.html" ],
function(
        dojo,
        json,
        declare,
        array,
        query,
        DeferredList,
        topic,
        domClass,
        lang,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        LoggedUtilities,
        TweetPollListItem,
        FoldersActions,
        MessageSearch,
        FilterSupport,
        _ENME,
        _lang,
        Source,
        hash,
        ioQuery,
        domConstruct,
        template ) {
    return declare( [ _WidgetBase,
                     _TemplatedMixin,
                     LoggedUtilities,
                     FilterSupport,
                     _WidgetsInTemplateMixin ], {

        /**
         * Template string.
         */
        templateString: template,

        /*
        * The url to search tweetpoll
        */
        url: "encuestame.service.list.listTweetPoll",

        /*
        * Main channel
        */
        _publish_update_channel: "/encuestame/tweetpoll/list/updateOptions",
        /*
        * Store list of items.
        */
        listItems: null,
        /*
        * Default filter.
        */
        defaultSearch: "ALL",
        /*
        * Store the current search.
        */
        currentSearch: "",
        /*
        * Enable folder support.
        */
        folder_support: true,

        /*
        * Max of items to show.
        */
        max: 60,

        /*
        * Start from 0.
        */
        start: 0,

        /*
        * Enable drag support.
        */
        dragSupport: true,

        /**
         * Flag to control nested calls
         */
        _loadingService: false,

        /*
        *
        */
        _tweetpollListSourceWidget: null,

        /*
        * The storage  key.
        */
        _tp_storage_key: "tp-key",

        /*
        * Tist of filters, this content should coincide with api enum.
        */
        _type_filter: {
          BYOWNER: "BYOWNER",
          LASTDAY: "LASTDAY",
          LASTWEEK: "LASTWEEK",
          FAVOURITES: "FAVOURITES",
          SCHEDULED: "SCHEDULED",
          ALL: "ALL"
        },

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
          detail_clean_filters: _ENME.getMessage("detail_clean_filters"),
          loading_message: _ENME.getMessage("loading_message")
        },

        helpSteps: [
            {
                element: ".admon-table-options > .tb-right",
                intro: _ENME.getMessage( "help_tp_button_new" )
            },
            {
                element: ".web-tweetpoll-options",
                intro: _ENME.getMessage( "help_tp_aside_bar" )
            },
            {
                element: ".web-folder-wrapper",
                intro: _ENME.getMessage( "help_tp_folder_view" )
            },
            {
                element: ".web-twettpoll-list-menu",
                intro: _ENME.getMessage( "help_tp_advanced_search_view" )
            },
            {
                element: ".tp-items-wrapper",
                intro: _ENME.getMessage( "help_tp_detail_view" )
            }
        ],

        /*
        * Post create.
        */
        postCreate: function() {
          this._loading = new MessageSearch();
          domConstruct.place( this._loading.domNode, this._custom_loading );
          var _hash = this._restoreHash();

          // Load item by first time.
          if ( this.listItems === null ) {
              var defTweetpolls = this.loadTweetPolls( this.getFilterData({ typeSearch: ( _hash === null ? this.defaultSearch : _hash ) }) );
	          var dl = new DeferredList( [ defTweetpolls ], true, true );
	          dl.then( dojo.hitch( this, function() {

		          // Load folders
		          if ( this.folder_support && this._folder ) {
			          var folder = new FoldersActions({
				          folderContext: "tweetpoll",
				          loadFolderItems: true
			          });
			          this._folder.appendChild( folder.domNode );
		          }
	          }) );
          }

	      //FIXME: updated with this._updateMenu
			if ( !_hash ) {
			    var node = query( '.optionItem[type="' + this.defaultSearch + '"]' );
			    node.forEach( function( node, index, arr ) {
			        domClass.add( node, "optionItemSelected");
			    });
			} else {
			    query( '.optionItem[type="' + _hash + '"]' ).forEach( function( node, index, arr ) {
			        domClass.add( node, "optionItemSelected");
			    });
			}

	        // Channel to invoke search service
	        topic.subscribe("/encuestame/make/search", lang.hitch( this, this.loadTweetPolls ) );
		    topic.subscribe("/encuestame/list/items/print", lang.hitch( this, function( data ) {
			    this.printItems( data, "ALL");
		    }) );

	        //
	        topic.subscribe("/encuestame/tweetpoll/loading/status", lang.hitch( this, function( status ) {
					if ( status ) {
						this._loading.show( this.i18nMessage.loading_message, _ENME.MESSAGES_TYPE.WARNING );
					} else {

					}
	        }) );

          topic.subscribe( this._publish_update_channel, lang.hitch( this, this._checkOptionItem ) );
        },

        /*
        * Init folder support.
        */
        _initFolderSupport: function() {},

       /*
        *
        */
        _checkOptionItem: function( id ) {
	        this.updateMenu( id );
        },

        /*
        * Reset the pagination.
        */
        resetPagination: function() {
          this.start = 0;
        },

        /*
        * Update the url hash.
        */
        _changeHash: function( id ) {
	      var _hash = ioQuery.queryToObject( hash() );
	      var params = {
		      f: id
	      };
	      hash( ioQuery.objectToQuery( params ) );
          if ( typeof id === "string" ) {
            _ENME.storeItem( this._tp_storage_key, { key: id.toString() });
          }
        },

	    /**
	     * Add a selected
	     * @param id
	     */
	    updateMenu: function( id ) {
		    query( ".optionItem" ).removeClass("optionItemSelected");
		    var item = query( '.optionItem[type="' + id + '"]' );
		    if ( item.length > 0 ) {
			    var i = item[ 0 ];
			    domClass.add( i, "optionItemSelected" );
		    }
	    },

        /**
        *
        */
        _restoreHash: function() {
        var _r = _ENME.restoreItem( this._tp_storage_key );
        return _r === null ? null : json.fromJson( _r ).key;
        },

        /*
        * Next search.
        */
        _nextSearch: function( event ) {
          event.preventDefault();
          this.start = this.start + this.max;
          this.loadTweetPolls({ typeSearch: this.currentSearch });
        },

        /**
         *
         */
        applyExtraFilters: function( params ) {
            if ( params.typeSearch === this._type_filter.SCHEDULED ) {

                //TODO: must disable the "published" checkbox into the advanced filter
                params._published = false; //Must be false, none of the tweetpoll could be published and scheduled at the same time
            }
            return params;
        },

        /*
        * Search by all.
        */
        _searchByAll: function( event ) {
          event.preventDefault();
          this.currentSearch = "ALL";
          this._changeHash( this.currentSearch );
          this.resetPagination();
          this.loadTweetPolls( this.getFilterData({ typeSearch: this._type_filter.ALL }) ).then( function() {
	          topic.publish( "/encuestame/folder/distribute/update" );
          });
	      topic.publish( this._publish_update_channel, this.currentSearch );
        },

        /*
        * Search by account.
        */
        _searchByAccount: function( event ) {
          event.preventDefault();
          this.currentSearch = this._type_filter.BYOWNER;
          this._changeHash( this.currentSearch );
          this.resetPagination();
          this.loadTweetPolls( this.getFilterData({ typeSearch: this._type_filter.BYOWNER }) ).then( function() {
	          topic.publish( "/encuestame/folder/distribute/update" );
          });
          topic.publish( this._publish_update_channel, this.currentSearch );
        },

        /*
        * Search by favourites.
        */
        _searchByFavourites: function( event ) {
          event.preventDefault();
          this.currentSearch = this._type_filter.FAVOURITES;
          this._changeHash( this.currentSearch );
          this.resetPagination();
          this.loadTweetPolls( this.getFilterData({ typeSearch: this._type_filter.FAVOURITES }) ).then( function() {
	          topic.publish( "/encuestame/folder/distribute/update" );
          });
          topic.publish( this._publish_update_channel, this.currentSearch );
        },

        /*
        * Search by secheduled.
        */
        _searchByScheduled: function( event ) {
          event.preventDefault();
          this.currentSearch = this._type_filter.SCHEDULED;
          this._changeHash( this.currentSearch );
          this.resetPagination();
          this.loadTweetPolls( this.getFilterData({ typeSearch: this._type_filter.SCHEDULED }) ).then( function() {
	          topic.publish( "/encuestame/folder/distribute/update" );
          });
	      topic.publish( this._publish_update_channel, this.currentSearch );
        },

        /*
        * Search by last day.
        */
        _searchByLastDay: function( event ) {
          event.preventDefault();
          this.currentSearch = this._type_filter.LASTDAY;
          this._changeHash( this.currentSearch );
          this.resetPagination();
          this.loadTweetPolls( this.getFilterData({
	          typeSearch: this._type_filter.LASTDAY
          }) ).then( function() {
	          topic.publish( "/encuestame/folder/distribute/update" );
          });
          topic.publish( this._publish_update_channel, this.currentSearch );
        },

        /*
        * Search by last week.
        */
        _searchByLastWeek: function( event ) {
          event.preventDefault();
          this.currentSearch = this._type_filter.LASTWEEK;
          this._changeHash( this.currentSearch );
          this.resetPagination();
          this.loadTweetPolls( this.getFilterData({ typeSearch: this._type_filter.LASTWEEK }) ).then( function() {
	          topic.publish( "/encuestame/folder/distribute/update" );
          });
          topic.publish( this._publish_update_channel, this.currentSearch );
        },

	    /**
	     * Print items
	     * @param data
	     */
	    printItems: function( data, typeSearch ) {
		    var i = false;
		    console.log("type_search", typeSearch );
		    domConstruct.empty( this._items );
		    var itemArray = data;
		    if ( itemArray.length > 0 ) {
			    array.forEach( itemArray, lang.hitch( this, function( data, index ) {
				    var widget = this.createTweetPollItem( data, i, typeSearch );
				    this._items.appendChild( widget.domNode );

				    // Expand the first item
				    if ( !i ) {
					    i = true;
					    widget._changeBackGroundSelected();
					    widget.startup();
				    }
			    }) );
		    } else {
			    var node = domConstruct.create("div");
			    domClass.add( node, "web-items-no-results");
			    node.innerHTML = _ENME.getMessage("commons_no_results");
			    domConstruct.place( node, this._items );
		    }
		    this._loadingService = false;
	    },

        /**
        * Load Tweet Polls.
        */
        loadTweetPolls: function( params ) {
          var load = lang.hitch( this, function( data ) {
	          if ( "success" in data ) {
		          this.printItems( data.success.tweetPolls, params.typeSearch );
	          }
          });

          //Check if typeSearch is missing
          if ( !("typeSearch" in params ) ) {
              var _hash = this._restoreHash();
              params.typeSearch = _hash === null ? this.defaultSearch : _hash;
          }

          // Mixin params with required params
          _lang.mixin( params,
              {
              max: this.max,
              start: this.start
              }
          );

          // Error handlers
          var parent = this;
          var error = function( error ) {
              _ENME.log("TweetPollList error : ", error );
              parent._loading.hide();
              parent.errorMessage( error );
              this._loadingService = false;
          };

          //
          this._loading.show( this.i18nMessage.loading_message, _ENME.MESSAGES_TYPE.WARNING );
          if ( !this._loadingService ) {
              this._loadingService = true;
              return this.getURLService().get( this.url, params, load, error, lang.hitch( this, function() {
                  this._loading.hide();
                  this._loadingService = false;
              }), true );
          }
        },

        /*
        * Create a TweetPoll Item
        */
        createTweetPollItem: function( data, i, typeSearch ) {
          return new TweetPollListItem({
              data: data,
              type_filter: typeSearch
          });
        }

    });
});
