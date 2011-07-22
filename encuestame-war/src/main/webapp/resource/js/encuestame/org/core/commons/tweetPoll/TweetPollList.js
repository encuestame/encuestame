dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollList");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dijit.layout.TabContainer");
dojo.require("dijit.layout.ContentPane");
dojo.require("dijit.layout.AccordionContainer");
dojo.require("dijit.layout.AccordionPane");
dojo.require("dojox.widget.Dialog");
dojo.require("dojox.form.Rating");
dojo.require("dojo.fx");
dojo.require("dojo.hash");

dojo.require("encuestame.org.core.commons.tweetPoll.TweetPoll");
dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardPie");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");
dojo.require("encuestame.org.core.commons.support.Wipe");
dojo.require("encuestame.org.core.shared.utils.FoldersActions");
dojo.require("encuestame.org.core.commons.stream.HashTagInfo");

dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollList",
        [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollList.html"),
        widgetsInTemplate: true,
        url : encuestame.service.list.listTweetPoll,
        listItems : null,
        defaultSearch : "ALL",
        currentSearch : "",
        contextPath : "",
        showNew : false,
        folder_support : true,
        max : 8,
        start : 0,
        postCreate : function() {
            var hash = dojo.queryToObject(dojo.hash());
            if(this.listItems == null){
                this.loadTweetPolls({typeSearch : (hash.f == null ? this.defaultSearch: hash.f) });
            }
            dojo.subscribe("/encuestame/tweetpoll/list/updateOptions", this, "_checkOptionItem");
            if (hash.f) {
            dojo.query(".optionItem").forEach(function(node, index, arr) {
               if (node.getAttribute("type") == hash.f) {
                   dojo.addClass(node, "optionItemSelected");
                }
              });
            }
            if (this.folder_support && this._folder) {
                var folder = new encuestame.org.core.shared.utils.FoldersActions({folderContext: "tweetpoll"});
                this._folder.appendChild(folder.domNode);
            }
        },

        _initFolderSupport : function() {

        },

        /*
         *
         */
        _checkOptionItem : function(node){
            dojo.query(".optionItem").forEach(function(node, index, arr){
                dojo.removeClass(node, "optionItemSelected");
              });
             dojo.addClass(node, "optionItemSelected");
        },

        /*
         *
         */
        resetPagination : function(){
            this.start = 0;
        },

        _changeHash : function(id){
            var hash = dojo.queryToObject(dojo.hash());
            params = {
               f : id
            };
            dojo.hash(dojo.objectToQuery(params));
        },

        _nextSearch : function(event){
            dojo.stopEvent(event);
            this.start = this.start + this.max;
            this.loadTweetPolls({typeSearch : this.currentSearch});
        },

        _searchByAll : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "ALL";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "ALL"});
            console.debug(event);
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        _searchByAccount : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "ALL";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "ALL"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        _searchByFavourites : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "FAVOURITES";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "FAVOURITES"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        _searchByScheduled : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "SCHEDULED";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "SCHEDULED"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        _searchByLastDay : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "LASTDAY";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "LASTDAY"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        _searchByLastWeek : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "LASTWEEK";
            this._changeHash(this.currentSearch);
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "LASTWEEK"});
            dojo.publish("/encuestame/tweetpoll/list/updateOptions", [event.currentTarget]);
        },

        /**
         * Load Tweet Polls.
         */
        loadTweetPolls : function(params){
            var i = false;
            var load = dojo.hitch(this, function(data){
                dojo.empty(this._items);
                dojo.forEach(
                        data.success.tweetPolls,
                        dojo.hitch(this, function(data, index) {
                            this.createTweetPollItem(data, i);
                            if(!i) {
                                i = true;
                            }
                }));
            });
            dojo.mixin(params,
                {
                max : this.max,
                start : this.start
                }
            );
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(this.url, params, load, error);
        },

        /*
         * create item.
         */
        createTweetPollItem : function(data, i){
            dojo.addOnLoad(dojo.hitch(this, function() {
                var widget = new encuestame.org.core.commons.tweetPoll.TweetPollListItem({data : data });
                this._items.appendChild(widget.domNode);
                if (!i) {
                    widget._changeBackGroundSelected();
                }
            }));
        }
 });

/**
 * Represents a row for each item of the tweetpoll list.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollListItem",
        [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollListItem.html"),
        //enable widgets on template.
        widgetsInTemplate: true,
        //data
        data: null,
        //seleted
        selected : false,

        panelWidget : null,

        //post create
        postCreate : function() {
            dojo.subscribe("/encuestame/tweetpoll/item/unselect", this, "unselect");
            dojo.connect(this._title, "onclick", dojo.hitch(this, this._onClickItem));
            if(!this.data.favourites){
                dojo.addClass(this._favourite, "emptyFavourite");
                dojo.removeClass(this._favourite, "selectedFavourite");
            } else {
                dojo.addClass(this._favourite, "selectedFavourite");
                dojo.removeClass(this._favourite, "emptyFavourite");
            }

            this.panelWidget = new encuestame.org.core.commons.support.Wipe(this._panel);
            if (this.data.hashTags) {
                dojo.forEach(this.data.hashTags,
                dojo.hitch(this,function(item) {
                    var hashtag = new encuestame.org.core.commons.stream.HashTagInfo(
                            {
                             hashTagName: item.hashTagName,
                             url: encuestame.contextDefault+"/tag/"+item.hashTagName+"/"
                            }
                            );
                    this._hashtags.appendChild(hashtag.domNode);
                }));
            }
        },

        /*
         * Call Service.
         */
        _callService : function(/* function after response */ load, url){
            var error = function(error) {
                console.debug("error", error);
            };
            var params = {
                    tweetPollId : this.data.id
            };
            encuestame.service.xhrGet(url, params, load, error);
        },

        /*
         * set favourite this item.
         */
        _setFavourite : function(event){
            dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data){
                this.data.favourites = !this.data.favourites;
                if(this.data.favourites){
                    dojo.addClass(this._favourite, "selectedFavourite");
                    dojo.removeClass(this._favourite, "emptyFavourite");
                } else {
                    dojo.addClass(this._favourite, "emptyFavourite");
                    dojo.removeClass(this._favourite, "selectedFavourite");
                }
            });
            this._callService(load, encuestame.service.list.favouriteTweetPoll);
        },

        /*
         *
         */
        _setCloseTweetPoll : function(event){
             dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data){
                console.debug(data);
            });
            this._callService(load, encuestame.service.list.changeTweetPollStatus);
        },

        /*
         * change background color on if selected.
         */
        _changeBackGroundSelected : function(){
            if (this.selected) {
               this._setUnselected();
               this.panelWidget.wipeOutOne();
               dojo.empty(this._panel);
            } else {
               dojo.addClass(this.domNode, "listItemTweetSeleted");
               dojo.removeClass(this._hashtags, "defaultDisplayHide");
               this._createDetail(this.data);
               this.panelWidget.wipeInOne();
            }
            this.selected =!this.selected;
        },

        _setUnselected : function(){
            dojo.removeClass(this.domNode, "listItemTweetSeleted");
            dojo.addClass(this._hashtags, "defaultDisplayHide");
        },

        /*
         * unselect this item.
         */
        unselect : function(id) {
            if (this.selected && this != id) {
                this._changeBackGroundSelected();
            }
        },

        /*
         * create detail.
         */
        _createDetail : function(data) {
            var detail = new encuestame.org.core.commons.tweetPoll.TweetPollListDetail({data: data});
            this._panel.appendChild(detail.domNode);
        },

        /*
         * on click on the widget dom node.
         */
        _onClickItem : function(event){
             dojo.stopEvent(event);
             this._changeBackGroundSelected();
             dojo.publish("/encuestame/tweetpoll/item/unselect", [this]);
        }
});
