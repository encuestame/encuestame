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
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollList",
        [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollList.inc"),
        widgetsInTemplate: true,
        url : encuestame.service.list.listTweetPoll,
        listItems : null,
        defaultSearch : "LASTDAY",
        postCreate : function(){
            console.debug("TweetPollList", this.listItems);
            if(this.listItems == null){
                console.debug("loadTweetPolls");
                this.loadTweetPolls({typeSearch : this.defaultSearch});
            }
        },

        /**
         * Load Tweet Polls.
         */
        loadTweetPolls : function(params){
            var load = dojo.hitch(this, function(data){
                console.debug("loadTweetPolls", data);
                dojo.forEach(
                        data.success.tweetPolls,
                        dojo.hitch(this, function(data, index) {
                            this.createTweetPollItem(data);
                }));
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(this.url, params, load, error);
        },

        createTweetPollItem : function(data){
            var widget = new encuestame.org.core.commons.tweetPoll.TweetPollListItem({data : data});
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
            console.debug(this.data);
            this.showInfo();
        },

        showInfo : function(){

        }
});