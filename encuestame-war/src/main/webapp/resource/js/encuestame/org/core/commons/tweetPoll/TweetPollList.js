dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollList");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dojox.widget.Dialog");
dojo.require("dojox.form.Rating");
dojo.require("dojo.fx");

dojo.require("encuestame.org.core.commons.tweetPoll.TweetPoll");
dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardPie");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");
dojo.require("encuestame.org.core.commons.support.Wipe");
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
        max : 8,
        start : 0,
        postCreate : function(){
            if(this.listItems == null){
                this.loadTweetPolls({typeSearch : this.defaultSearch});
            }
        },

        resetPagination : function(){
            this.start = 0;
        },

        _nextSearch : function(event){
            dojo.stopEvent(event);
            this.start = this.start + this.max;
            this.loadTweetPolls({typeSearch : this.currentSearch});
        },

        _searchByAll : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "ALL";
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "ALL"});
        },

        _searchByAccount : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "ALL";
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "ALL"});
        },

        _searchByFavourites : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "SCHEDULED";
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "FAVOURITES"});
        },

        _searchByScheduled : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "SCHEDULED";
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "SCHEDULED"});
        },

        _searchByLastDay : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "LASTDAY";
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "LASTDAY"});
        },

        _searchByLastWeek : function(event){
            dojo.stopEvent(event);
            this.currentSearch = "LASTWEEK";
            this.resetPagination();
            this.loadTweetPolls({typeSearch : "LASTWEEK"});
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
                            if(!i){
                                dojo.publish("/encuestame/tweetpoll/detail/update", [data]);
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
            var widget = new encuestame.org.core.commons.tweetPoll.TweetPollListItem(
                    {
                        data : data
                    }
                    );
            if(!i){
                widget._changeBackGroundSelected();
            }
            this._items.appendChild(widget.domNode);
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
        postCreate : function(){
            this.showInfo();
            dojo.subscribe("/encuestame/tweetpoll/item/unselect", this, "unselect");
            if(!this.data.favourites){
                dojo.addClass(this._favourite, "emptyFavourite");
                dojo.removeClass(this._favourite, "selectedFavourite");
            } else {
                dojo.addClass(this._favourite, "selectedFavourite");
                dojo.removeClass(this._favourite, "emptyFavourite");
            }
            dojo.connect(this.domNode, "onclick", dojo.hitch(this, this._onClickItem));
            this.panelWidget = new encuestame.org.core.commons.support.Wipe(this._panel);
        },

        showInfo : function(){

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
               this._createDetail(this.data);
               this.panelWidget.wipeInOne();
            }
            this.selected =!this.selected;
        },

        _setUnselected : function(){
            dojo.removeClass(this.domNode, "listItemTweetSeleted");
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
            dojo.publish("/encuestame/tweetpoll/detail/update", [data]);
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
