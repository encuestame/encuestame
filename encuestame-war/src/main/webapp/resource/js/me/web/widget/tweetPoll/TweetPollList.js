define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollListItem",
         "me/web/widget/folder/FoldersActions",
         "me/core/enme",
         "dojo/_base/lang",
         "dojo/topic",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollList.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TweetPollListItem,
                FoldersActions,
                _ENME,
                _lang,
                topic,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

          url : 'encuestame.service.list.listTweetPoll',

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
          max : 20,

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
            detail_manage_only_completed : _ENME.getMessage("detail_manage_only_completed")
          },


          /*
           * post create.
           */
          postCreate : function() {
              var hash = dojo.queryToObject(dojo.hash());
              if(this.listItems == null){
                  this.loadTweetPolls({typeSearch : (hash.f == null ? this.defaultSearch: hash.f) });
              }
              topic.subscribe(this._publish_update_channel, this, "_checkOptionItem");
              if (hash.f) {
              dojo.query(".optionItem").forEach(function(node, index, arr) {
                 if (node.getAttribute("type") == hash.f) {
                     dojo.addClass(node, "optionItemSelected");
                  }
                });
              }
              if (this.folder_support && this._folder) {
                  //var folder = new FoldersActions({folderContext: "tweetpoll"});
                  //this._folder.appendChild(folder.domNode);
              }
              //enable drag support.
              //Disable, needed review this page http://livedocs.dojotoolkit.org/dojo/dnd
              this.dragSupport = false;
              if (this.dragSupport) {
                  this._tweetpollListSourceWidget  = new dojo.dnd.Source(this._items, {
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
              dojo.query(".optionItem").forEach(function(node, index, arr){
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
              var hash = dojo.queryToObject(dojo.hash());
              params = {
                 f : id
              };
              dojo.hash(dojo.objectToQuery(params));
          },

          /*
           * next search.
           */
          _nextSearch : function(event) {
              dojo.stopEvent(event);
              this.start = this.start + this.max;
              this.loadTweetPolls({typeSearch : this.currentSearch});
          },

          /*
           * search by all.
           */
          _searchByAll : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = "ALL";
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls({typeSearch : "ALL"});
              //console.debug(event);
              topic.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by account.
           */
          _searchByAccount : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = "ALL";
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls({typeSearch : "ALL"});
              topic.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by favourites.
           */
          _searchByFavourites : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = "FAVOURITES";
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls({typeSearch : "FAVOURITES"});
              topic.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by secheduled.
           */
          _searchByScheduled : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = "SCHEDULED";
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls({typeSearch : "SCHEDULED"});
              topic.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by last day.
           */
          _searchByLastDay : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = "LASTDAY";
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls({typeSearch : "LASTDAY"});
              topic.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /*
           * search by last week.
           */
          _searchByLastWeek : function(event) {
              dojo.stopEvent(event);
              this.currentSearch = "LASTWEEK";
              this._changeHash(this.currentSearch);
              this.resetPagination();
              this.loadTweetPolls({typeSearch : "LASTWEEK"});
              topic.publish(this._publish_update_channel, [event.currentTarget]);
          },

          /**
           * Load Tweet Polls.
           */
          loadTweetPolls : function(params) {
              topic.publish("/encuestame/wipe/close/group", ["tp-options"]);
              topic.publish("/encuestame/filters/selected/remove");
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
              _lang.mixin(params,
                  {
                  max : this.max,
                  start : this.start
                  }
              );
              var error = function(error) {
                  console.debug("error", error);
              };
              encuestame.service.xhrGet(this.getURLService().service(this.url), params, load, error);
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

//dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollList");
//
//dojo.require("dijit.form.Form");
//dojo.require("dijit.form.Button");
//dojo.require("dijit.form.TextBox");
//dojo.require("dijit.form.CheckBox");
//dojo.require("dijit._Widget");
//dojo.require("dijit._Templated");
//dojo.require("dijit.Dialog");
//dojo.require("dijit.layout.TabContainer");
//dojo.require("dijit.layout.ContentPane");
//dojo.require("dijit.layout.AccordionContainer");
//dojo.require("dijit.layout.AccordionPane");
//dojo.require("dojox.widget.Dialog");
//dojo.require("dojox.form.Rating");
//dojo.require("dojo.fx");
//dojo.require("dojo.hash");
//dojo.require("dojo.dnd.Source");
//
//dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
//dojo.require("encuestame.org.core.commons.tweetPoll.TweetPoll");
//dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardPie");
//dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");
//dojo.require("encuestame.org.core.commons.support.Wipe");
//dojo.require("encuestame.org.core.shared.utils.FoldersActions");
//dojo.require("encuestame.org.core.commons.stream.HashTagInfo");
//dojo.require("encuestame.org.core.commons.support.ItemsFilterSupport");