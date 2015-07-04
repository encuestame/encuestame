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
 *  @module FrontEnd
 *  @namespace Wifget
 *  @class FrontEnd
 */
define([
    "dojo/parser",
    "dijit/registry",
    "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme",
     "me/web/widget/stream/FrontEndItem",
     "me/web/widget/stream/FrontEndItemMobile",
     "me/web/widget/ui/More",
     "dojo/dom-construct",
     "dojo/dom-class",
     "dojo/dom-attr",
     "dojo/on",
     "dojo/text!me/web/widget/stream/templates/frontEnd.html" ],
    function(
        parser,
        registry,
        declare,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        main_widget,
        _ENME,
        FrontEndItem,
        FrontEndItemMobile,
        More,
        domConstruct,
        domClass,
        domAttr,
        on,
        template) {

      return declare([ _WidgetBase, _TemplatedMixin, main_widget,
          _WidgetsInTemplateMixin ], {

            /**
             * template string.
             * @property templateString
             */
            templateString : template,

            /**
             * Items max to load.
             * @property _maxResults
             */
            _maxResults : 10,

            /**
             * Flag to filter by items
             * @property viewFilter
             */
            viewFilter:  "all",


            /**
             * Starting from.
             * @property _start
             */
            _start : 10,

            /**
             * Control the current items are displayed
             * @property items
             */
            items : 0,

            /**
             * Display profile pictures.
             * @property
             */
            enableImage : true,


          /**
            * i18n Message.
            */
           i18nMessage : {
                 home_item_comments : _ENME.getMessage("home_item_comments"),
                 submited_by : _ENME.getMessage("submited_by"),
                 home_item_votes : _ENME.getMessage("home_item_votes"),
                 home_item_views : _ENME.getMessage("home_item_views"),
                 added : _ENME.getMessage("added")
           },

            /**
             * Post create cycle.
             * @method postCreate
             */
            postCreate : function() {
              var parent = this;
              this.more = new More({
                   parentWidget : this,
                   more_max : 5
              });

              this.more.loadItems = dojo.hitch(this, function () {
                  parent._loadItems();
              });
              // if more
              if (this._more) {
                this._more.appendChild(this.more.domNode);
              }
            },

            /**
             *
             * @method _printItems
             */
            _printItems : function(items) {
                dojo.forEach(items,
                    dojo.hitch(this,function(item) {
                    var option =  {   questionName : item.question.question_name,
                        id : item.id,
                        owner : item.owner_username,
                        item : item,
                        enableImage : this.enableImage,
                        contextPath : _ENME.config('contextPath'),
                        slugName : item.question.slug,
                        votes : _ENME.shortAmmount(item.total_votes),
                        views : 0,
                        votesMessage : this.i18nMessage.home_item_votes,
                        viewMessage : this.i18nMessage.home_item_views,
                        totalCommentMessage : this.i18nMessage.home_item_comments,
                        addedMessage: this.i18nMessage.added,
                        submiteddByMessage : this.i18nMessage.submited_by,
                        relativeTime: item.relative_time,
                        hashtags : item.hastags_string
                    };
                    var widget = null;
                      if (!this.isMobile) {
                          widget = new FrontEndItem(option);
                      } else {
                          widget = new FrontEndItemMobile(option);
                      }
                      this._list.appendChild(widget.domNode);
                }));
            },

            /*
             *
             */
            _fadeOutTrigger : function(){
                dojo.style(this._more, "opacity", "1");
                var fadeArgs = {
                    node: this._more,
                    onEnd: dojo.hitch(this, function() {
                        // executed when the animation is done
                        this.more.removeEvent();
                    })
                };
                dojo.fadeOut(fadeArgs).play();
            },

            /**
             * load all items
             * @method _loadItems
             */
            _loadItems : function() {
              var load = dojo.hitch(this, function(data) {
                    var items = data.success.frontendItems;
                    this.items += items.length;
                    if(items.length > 0) {
                        this._printItems(items);
                    } else {
                        this._fadeOutTrigger();
                    }
                });

                var error = function(error) {
                    console.debug("error", error);
                };
                var query = _ENME.getURLParametersAsObject();
                this.getURLService().get("encuestame.service.stream",
                        {
                            period : query.period || "all",
                            maxResults : 5,
                            type : this.viewFilter,
                            start: this.more.pagination.start
                          }, load, error , dojo.hitch(this, function() {

                }));
            }
  });
});
