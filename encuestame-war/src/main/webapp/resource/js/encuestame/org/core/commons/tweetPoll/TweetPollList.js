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
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPoll");
dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardPie");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollList",
        [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollList.inc"),
        widgetsInTemplate: true,
        url : encuestame.service.list.listTweetPoll,
        listItems : null,
        defaultSearch : "LASTDAY",
        max : 8,
        start : 0,
        postCreate : function(){
            if(this.listItems == null){
                this.loadTweetPolls({typeSearch : this.defaultSearch});
            }
        },

        _searchByAll : function(event){
            dojo.stopEvent(event);
            this.loadTweetPolls({typeSearch : "ALL"});
        },

        _searchByAccount : function(event){
            dojo.stopEvent(event);
            this.loadTweetPolls({typeSearch : "ALL"});
        },

        _searchByFavourites : function(event){
            dojo.stopEvent(event);
            this.loadTweetPolls({typeSearch : "FAVOURITES"});
        },

        _searchByScheduled : function(event){
            dojo.stopEvent(event);
            this.loadTweetPolls({typeSearch : "SCHEDULED"});
        },

        _searchByLastDay : function(event){
            dojo.stopEvent(event);
            this.loadTweetPolls({typeSearch : "LASTDAY"});
        },

        _searchByLastWeek : function(event){
            dojo.stopEvent(event);
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

dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollListItem",
        [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollListItem.inc"),
        //widget
        widgetsInTemplate: true,
        //data
        data: null,
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
        },

        showInfo : function(){

        },

        unselect : function(data){
            if(data != this.data){
                dojo.removeClass(this.domNode, "listItemTweetSeleted");
            }
        },

        /**
         * Call Service.
         */
        _callService : function(/* function after response */ load, url){
            var error = function(error) {
                console.debug("error", error);
            };
            var params = {
                    tweetPollId : this.data.id
            };
            console.debug("_callService", url);
            encuestame.service.xhrGet(url, params, load, error);
        },

        _setFavourite : function(event){
            dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data){
                console.debug(data);
            });
            this._callService(load, encuestame.service.list.favouriteTweetPoll);
        },

        _setCaptcha : function(event){
             dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data){
                console.debug(data);
            });
            this._callService(load, encuestame.service.list.captchaTweetPoll);
        },

        _setAllowLiveResults : function(event){
             dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data){
                console.debug(data);
            });
            this._callService(load, encuestame.service.list.liveResultsTweetPoll);
        },

        _setResumeLiveResults : function(event){
             dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data){
                console.debug(data);
            });
            this._callService(load, encuestame.service.list.resumeliveResultsTweetPoll);
        },

        _setCloseTweetPoll : function(event){
             dojo.stopEvent(event);
            var load = dojo.hitch(this, function(data){
                console.debug(data);
            });
            this._callService(load, encuestame.service.list.changeTweetPollStatus);
        },

        _changeBackGroundSelected : function(){
            dojo.addClass(this.domNode, "listItemTweetSeleted");
        },

        _onClickItem : function(event){
             dojo.stopEvent(event);
             console.debug("_onClickItem");
             dojo.publish("/encuestame/tweetpoll/detail/update", [this.data]);
             this._changeBackGroundSelected();
             dojo.publish("/encuestame/tweetpoll/item/unselect", [this.data]);
        }
});