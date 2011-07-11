dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");

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
dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");
dojo.require("encuestame.org.core.shared.utils.YesNoWidget");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.TweetPollListDetail",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollListDetail.html"),
        //widget
        widgetsInTemplate: true,
        //data
        data: null,

        widgetChart : null,

        typeChart : ['Bars', 'Pie', 'Lines'],
        //post create
        postCreate : function(){
            dojo.subscribe("/encuestame/tweetpoll/detail/update", this, "updateDetail");
            dojo.subscribe("/encuestame/tweetpoll/detail/chart/render", this, "render");
        },

        /**
         * Update Detail.
         */
        updateDetail : function(data){
            console.debug("data 1", data);
            if(data != null){
                this.loadContent(data);
            } else {
                this.error();
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
           encuestame.service.xhrGet(url, params, load, error);
       },

       _setAllowLiveResults : function(){
           var load = dojo.hitch(this, function(data){
               this.data.allowLiveResults = !this.data.allowLiveResults;
           });
           dojo.hitch(this, this._callService(load, encuestame.service.list.liveResultsTweetPoll));
       },

       _setResumeLiveResults : function(){
           var load = dojo.hitch(this, function(data){
               this.data.resumeLiveResults = !this.data.resumeLiveResults;
           });
           dojo.hitch(this, this._callService(load, encuestame.service.list.resumeliveResultsTweetPoll));
       },

       _setCaptcha : function(){
           var load = dojo.hitch(this, function(data){
               this.data.captcha = !this.data.captcha;
           });
           dojo.hitch(this, this._callService(load, encuestame.service.list.captchaTweetPoll));
       },

       _setNotification : function(){
           var load = dojo.hitch(this, function(data){
               this.data.closeNotification = !this.data.closeNotification;
           });
           dojo.hitch(this, this._callService(load, encuestame.service.list.notificationTweetPoll));
       },

       _setRepeated : function(){
           var load = dojo.hitch(this, function(data){
               this.data.allowRepeatedVotes = !this.data.allowRepeatedVotes;
           });
           dojo.hitch(this, this._callService(load, encuestame.service.list.repeatedTweetPoll));
       },

        error : function(){
            console.error("error");
        },

        /**
         * Load Content.
         */
        loadContent : function(data){
            this.data = data;
            this._title.innerHTML = this.data.questionBean.questionName;
            this.displayChart(this.typeChart[1]);
            //Build Detail.
            dojo.empty(this._detailItems);
            this.addDetail(this.builDetailRow("Public Link", this.createTextContent("http://www.google.es")));
            this.addDetail(this.builDetailRow("Created Date", this.createTextContent(this.data.createDate)));
            this.addDetail(this.builDetailRow("Captcha", this.addYesNoWidget(this.data.captcha,
                     dojo.hitch(this,this._setCaptcha))));
            this.addDetail(this.builDetailRow("Allow Live Results", this.addYesNoWidget(this.data.allowLiveResults
                            , dojo.hitch(this, this._setAllowLiveResults))));
            this.addDetail(this.builDetailRow("Allow Resume Live Results", this.addYesNoWidget(this.data.resumeLiveResults
                            , dojo.hitch(this, this._setResumeLiveResults))));
            this.addDetail(this.builDetailRow("Allow Repeated Votes", this.addYesNoWidget(
                    this.data.allowRepeatedVotes
                    , dojo.hitch(this, this._setRepeated))));
            this.addDetail(this.builDetailRow("Notifications", this.addYesNoWidget(
                    this.data.closeNotification
                    , dojo.hitch(this, this._setNotification))));
        },

        addDetail : function(node){
            this._detailItems.appendChild(node);
        },

        /**
         * Yes / No.
         */
        addYesNoWidget : function(value, onChange){
            var widget = new encuestame.org.core.shared.utils.YesNoWidget({data: value});
            if(onChange != null){
                widget._onChange = onChange;
            }
            return widget.domNode;
        },

        createTextContent : function(text){
            var textData = dojo.doc.createElement('div');
            textData.innerHTML = text;
            return textData;
        },

        /**
         * Build Detail Row.
         */
        builDetailRow : function(labelText, dataContet){
            var rowDetail = dojo.doc.createElement('div');
            dojo.addClass(rowDetail, "rownDetail");
            var label = dojo.doc.createElement('div');
            dojo.addClass(label, "label");
            var labelItem = dojo.doc.createElement('label');
            labelItem.innerHTML = labelText;
            label.appendChild(labelItem);
            rowDetail.appendChild(label);
            var data = dojo.doc.createElement('div');
            dojo.addClass(data, "data");
            data.appendChild(dataContet);
            rowDetail.appendChild(data);
            return rowDetail;
        },

        /**
         * Display Data.
         */
        displayChart : function(type){
            dojo.empty(this._chart);var widget;
            this._callVotes(type);
        },

        /**
         * Call Votes.
         */
        _callVotes : function(type){
            var response = dojo.hitch(this, function(dataJson){
                var votes = dataJson.success.votesResult;
                var results = [];
                dojo.forEach(
                        votes,
                        dojo.hitch(this, function(data, index) {
                            var answer = [data.question_label, (data.votes == null ? 0: data.votes), data.color];
                            console.debug("Re answer", answer);
                            results.push(answer);
                }));
                var id = this.id+"_chart";
                dojo.empty(this._chart);
                if(this.widgetChart != null){
                    this.widgetChart = null;
                }
                if(type == this.typeChart[0]){
                    this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(id, results);
                } else if(type == this.typeChart[1]){
                    this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(id, results);
                }
                this.render();
            });;
            this._callService(response, encuestame.service.list.VotesTweetPoll);
        },

        /**
         * Render.
         */
        render : function(){
            this.widgetChart._buildSeries();
            this.widgetChart.render();
        }
});