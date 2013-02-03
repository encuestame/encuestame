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
 *  @version 1.146
 *  @module TweetPoll
 *  @namespace Widget
 *  @class TweetPollList
 */
define([
         "dojo",
         'dojo/_base/json',
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/LoggedUtilities",
         "me/web/widget/tweetpoll/TweetPollListItem",
         "me/web/widget/folder/FoldersActions",
         "me/web/widget/support/ItemsFilterSupport",
         "me/web/widget/ui/MessageSearch",
         "me/core/enme",
         "dojo/_base/lang",
         "dojo/topic",
         "dojo/dnd/Source",
         "dojo/hash",
         "dojo/io-query",
         "dojo/dom-construct",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollList.html" ],
        function(
                dojo,
                json,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                LoggedUtilities,
                TweetPollListItem,
                FoldersActions,
                ItemsFilterSupport,
                MessageSearch,
                _ENME,
                _lang,
                topic,
                Source,
                hash,
                ioQuery,
                domConstruct,
                template) {
            return declare([ _WidgetBase,
                             _TemplatedMixin,
                             LoggedUtilities,
                             _WidgetsInTemplateMixin], {

          // template string.
          templateString : template,

          /*
           * the url to search tweetpoll
           */
          url : 'encuestame.service.list.listTweetPoll',

          /*
           * main channel
           */
          _publish_update_channel : "/encuestame/tweetpoll/list/updateOptions",
          /*
           * store list of items.
           */
          listItems : null,
          /*
           * default filter.
           */
          defaultSearch : "ALL",
          /*
           * store the current search.
           */
          currentSearch : "",
          /*
           * enable folder support.
           */
          folder_support : true,

          /*
           * max of items to show.
           */
          max : 60,

          /*
           * start from 0.
           */
          start : 0,

          /*
           * enable drag support.
           */
          dragSupport : true,

          /*
           *
           */
          _tweetpollListSourceWidget : null,

          /*
           * The storage  key.
           */
          _tp_storage_key : "tp-key",

          /*
           * Tist of filters, this content should coincide with api enum.
           */
          _type_filter : {
              BYOWNER : 'BYOWNER',
              LASTDAY : 'LASTDAY',
              LASTWEEK : 'LASTWEEK',
              FAVOURITES : 'FAVOURITES',
              SCHEDULED : 'SCHEDULED',
              ALL : 'ALL'
          },

          /*
           * i18n message for this widget.
           */
          i18nMessage : {
              detail_manage_by_account : _ENME.getMessage("detail_manage_by_account"),
              detail_manage_today : _ENME.getMessage("detail_manage_today"),
              detail_manage_last_week : _ENME.getMessage("detail_manage_last_week"),
              detail_manage_favorites : _ENME.getMessage("detail_manage_favorites"),
              detail_manage_scheduled : _ENME.getMessage("detail_manage_scheduled"),
              detail_manage_all : _ENME.getMessage("detail_manage_all"),
              detail_manage_published : _ENME.getMessage("detail_manage_published"),
              detail_manage_unpublished : _ENME.getMessage("detail_manage_unpublished"),
              detail_manage_only_completed : _ENME.getMessage("detail_manage_only_completed"),
              detail_clean_filters : _ENME.getMessage("detail_clean_filters"),
              loading_message : _ENME.getMessage("loading_message")
          },

          /*
           * post create.
           */
          postCreate : function() {
              this._loading = new MessageSearch();
              domConstruct.place(this._loading.domNode, this._custom_loading);
              var _hash = this._restoreHash();
              // load item by first time.
              if (this.listItems === null) {
                  //this.getFilterData({typeSearch : this._type_filter.ALL})
                  this.loadTweetPolls(this.getFilterData({typeSearch : (_hash === null ? this.defaultSearch: _hash) }));
                  if (!_hash) {
                    var node = dojo.query('div.optionItem[type="' + this.defaultSearch + '"]');
                    node.forEach(function(node, index, arr) {
                          dojo.addClass(node, "optionItemSelected");
                    });
                  } else {
                    dojo.query('div.optionItem[type="' + _hash + '"]').forEach(function(node, index, arr) {
                          dojo.addClass(node, "optionItemSelected");
                      });
                  }
              }
              // channel to invoke search service
              dojo.subscribe("/encuestame/tweetpoll/list/search", this, "loadTweetPolls");

              dojo.subscribe(this._publish_update_channel, this, "_checkOptionItem");
              if (this.folder_support && this._folder) {
                  var folder = new FoldersActions({folderContext: "tweetpoll"});
                  this._folder.appendChild(folder.domNode);
              }
              //enable drag support.
              //Disable, needed review this page http://livedocs.dojotoolkit.org/dojo/dnd
              this.dragSupport = false;
              if (this.dragSupport) {
                  this._tweetpollListSourceWidget  = new Source(this._items, {
                      accept: [],
                      copyOnly: true,
                      selfCopy : false,
                      selfAccept: false,
                      withHandles : true,
                      autoSync : true,
                      isSource : true,
                      creator: this.dndNodeCreator
                      });
              }
          },

          /*
           * init folder support.
           */
          _initFolderSupport : function() {},

          /*
           * dnd node creator.
           */
          dndNodeCreator : function (item, hint) {
              //console.debug("hint", hint);
              var tr = document.createElement("div");
              tr.innerHTML = "Item Dropped...";
              return {node: tr, data: item, type: "tweetpoll"};
          },

          /*
           *
           */
          _checkOptionItem : function(node) {
               dojo.query(".optionItem").forEach(function(node, index, arr) {
                  dojo.removeClass(node, "optionItemSelected");
                });
               dojo.addClass(node, "optionItemSelected");
          },

          /*
           * reset the pagination.
           */
          resetPagination : function() {
              this.start = 0;
          },

          /*
           * update the url hash.
           */
          _changeHash : function(id) {
              if(typeof id === 'string'){
                _ENME.storeItem(this._tp_storage_key, {key : id.toString()});
              }
          },

          /**
           *
           */
          _restoreHash : function () {
            var _r = _ENME.restoreItem(this._tp_storage_key);
            return _r == null ? null : json.fromJson(_r).key;
          },

          /*
           * next search.
           */
          _nextSearch : function(event) {
              dojo.stopEvent(event);
              this.start = this.start + this.max;
              this.loadTweetPolls({typeSearch : this.currentSearch});
          },

          /**
           * Get filter data, if exist
           */
          getFilterData : function (params) {
            if (this._filters) {
              _lang.mixin(params, this._filters.getFilterData());
            }
            return params;
          },

          /**
           * Clean filters trigger function
           */
          _cleanFilters : function (e) {
                dojo.stopEvent(e);
                this.cleanFilterData();
          },

          /**
           * Clean the widget
           */
          cleanFilterData : function () {
            if (this._filters) {
                this._filters.cleanFilterData();
            }
          },

          /*
           * search by all.
           */
          _searchByAll : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = "ALL";
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls(this.getFilterData({typeSearch : this._type_filter.ALL}));
              //console.debug(event);
              dojo.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by account.
           */
          _searchByAccount : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = this._type_filter.BYOWNER;
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls(this.getFilterData({typeSearch : this._type_filter.BYOWNER}));
              dojo.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by favourites.
           */
          _searchByFavourites : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = this._type_filter.FAVOURITES;
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls(this.getFilterData({typeSearch : this._type_filter.FAVOURITES}));
              dojo.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by secheduled.
           */
          _searchByScheduled : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = this._type_filter.SCHEDULED;
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls(this.getFilterData({typeSearch : this._type_filter.SCHEDULED}));
              dojo.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by last day.
           */
          _searchByLastDay : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = this._type_filter.LASTDAY;
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls(this.getFilterData({typeSearch : this._type_filter.LASTDAY}));
              dojo.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by last week.
           */
          _searchByLastWeek : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = this._type_filter.LASTWEEK;
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls(this.getFilterData({typeSearch : this._type_filter.LASTWEEK}));
              dojo.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /**
           * Load Tweet Polls.
           */
          loadTweetPolls : function(params) {
              //dojo.publish("/encuestame/wipe/close/group", "tp-options");
              //dojo.publish("/encuestame/filters/selected/remove");
              var i = false;
              var load = dojo.hitch(this, function(data){
                  dojo.empty(this._items);
                  var itemArray = [];
                  dojo.forEach(
                          data.success.tweetPolls,
                          dojo.hitch(this, function(data, index) {
                              var widget = this.createTweetPollItem(data, i);
                              if(!i) {
                                  i = true;
                              }
                              if (this.dropSupport) {
                                  itemArray.push(widget.domNode);
                              } else {
                                  this._items.appendChild(widget.domNode);
                              }
                  }));
                  if (this.dropSupport) {
                      this._tweetpollListSourceWidget.insertNodes(false, itemArray);
                  }
              });

              //check if typeSearch is missing
              if (!("typeSearch" in params)) {
                  var _hash = this._restoreHash();
                  params.typeSearch = _hash == null ? this.defaultSearch : _hash;
              }
              // mixin params with required params
              _lang.mixin(params,
                  {
                  max : this.max,
                  start : this.start
                  }
              );
              // error handlers
              var parent = this;
              var error = function(error) {
                  _ENME.log("TweetPollList error : ", error);
                  parent._loading.hide();
                  parent.errorMessage(error);
              };

              //
              this._loading.show(this.i18nMessage.loading_message, _ENME.MESSAGES_TYPE.WARNING);
              this.getURLService().get(this.url, params, load, error , dojo.hitch(this, function() {
                  this._loading.hide();
              }));
          },

          /*
           * create item.
           */
          createTweetPollItem : function(data, i) {
              var widget = new TweetPollListItem({data : data });
              if (!i) {
                  widget._changeBackGroundSelected();
              }
              return widget;
          }

    });
});