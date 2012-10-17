//dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");
//
//dojo.require("dijit.form.Form");
//dojo.require("dijit.form.Button");
//dojo.require("dijit.form.TextBox");
//dojo.require("dijit.form.CheckBox");
//dojo.require("dijit._Widget");
//dojo.require("dijit._Templated");
//dojo.require("dijit.Dialog");
//dojo.require("dojox.widget.Dialog");
//dojo.require("dojox.form.Rating");
//dojo.require("encuestame.org.core.commons.tweetPoll.TweetPoll");
//dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");
//dojo.require("encuestame.org.core.shared.utils.YesNoWidget");
//dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer");
//dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
//
//dojo.declare(
//    "encuestame.org.core.commons.tweetPoll.TweetPollListDetail",
//    [encuestame.org.main.EnmeMainLayoutWidget],{
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollListDetail.html"),
//        //data
//        data: null,
//
//        widgetChart : null,
//
//        /*
//         * i18n message for this widget.
//         */
//        i18nMessage : {
//        	tweetpoo_detail_tab_detail : ENME.getMessage("tweetpoo_detail_tab_detail"),
//        	tweetpoo_detail_tab_stats : ENME.getMessage("tweetpoo_detail_tab_stats"),
//        	tweetpoo_detail_tab_comments : ENME.getMessage("tweetpoo_detail_tab_comments"),
//        	tweetpoo_detail_tab_social : ENME.getMessage("tweetpoo_detail_tab_social"),
//        	tweetpoo_detail_tab_delete : ENME.getMessage("tweetpoo_detail_tab_delete"),
//        	tweetpoo_detail_answers_title_link : ENME.getMessage("tweetpoo_detail_answers_title_link"),
//        	tweetpoo_detail_answers_title_count : ENME.getMessage("tweetpoo_detail_answers_title_count"),
//        	tweetpoo_detail_answers_title_percent : ENME.getMessage("tweetpoo_detail_answers_title_percent")
//        },
//
//        typeChart : ['Bars', 'Pie', 'Lines'],
//        //post create
//        postCreate : function(){
//            //console.debug("DETAIL", this.data);
//            dojo.subscribe("/encuestame/tweetpoll/detail/update", this, "updateDetail");
//            dojo.subscribe("/encuestame/tweetpoll/detail/chart/render", this, "render");
//            this.updateDetail(this.data);
//            if (this._extra) {
//                dojo.connect(this._extra, "onclick", dojo.hitch(this, function(event){
//                    //console.debug("click extra");
//                    dojo.stopEvent(event);
//                }));
//            }
//            //tab links.
//            //TODOL: future.
//
//        },
//
//        /**
//         * Update Detail.
//         */
//        updateDetail : function(data) {
//            if(data != null){
//                this.loadContent(data);
//            } else {
//                this.error();
//            }
//        },
//
//       /**
//        * Call Service.
//        */
//       _callService : function(/* function after response */ load, url){
//           var error = function(error) {
//               console.debug("error", error);
//           };
//           var params = {
//                   tweetPollId : this.data.id
//           };
//           encuestame.service.xhrGet(url, params, load, error);
//       },
//
//       /**
//        *
//        */
//       successDetailUpdateMessage : function () {
//    	   this.publishMessage(ENME.getMessage('commons_success'), ENME.CONST.MSG.SUCCESS);
//       },
//
//       /**
//        *
//        */
//       _setAllowLiveResults : function() {
//           var load = dojo.hitch(this, function(data){
//               this.data.allowLiveResults = !this.data.allowLiveResults;
//               this.successDetailUpdateMessage();
//           });
//           dojo.hitch(this, this._callService(load, encuestame.service.list.liveResultsTweetPoll));
//       },
//
//       _setResumeLiveResults : function(){
//           var load = dojo.hitch(this, function(data){
//               this.data.resumeLiveResults = !this.data.resumeLiveResults;
//               this.successDetailUpdateMessage();
//           });
//           dojo.hitch(this, this._callService(load, encuestame.service.list.resumeliveResultsTweetPoll));
//       },
//
//       _setCaptcha : function(){
//           var load = dojo.hitch(this, function(data){
//               this.data.captcha = !this.data.captcha;
//               this.successDetailUpdateMessage();
//           });
//           dojo.hitch(this, this._callService(load, encuestame.service.list.captchaTweetPoll));
//       },
//
//       _setNotification : function(){
//           var load = dojo.hitch(this, function(data){
//               this.data.closeNotification = !this.data.closeNotification;
//               this.successDetailUpdateMessage();
//           });
//           dojo.hitch(this, this._callService(load, encuestame.service.list.notificationTweetPoll));
//       },
//
//       /**
//        * Set as repeated
//        */
//       _setRepeated : function(){
//           var load = dojo.hitch(this, function(data) {
//               this.data.allowRepeatedVotes = !this.data.allowRepeatedVotes;
//               this.successDetailUpdateMessage();
//           });
//           dojo.hitch(this, this._callService(load, encuestame.service.list.repeatedTweetPoll));
//       },
//
//       /**
//        * Error messages.
//        */
//        error : function() {
//            this.errorMesage();
//            this.publishMessage(ENME.getMessage('e_023'), ENME.CONST.MSG.ERROR);
//        },
//
//        /**
//         * Load Content.
//         */
//        loadContent : function(data){
//            this.data = data;
//            this.displayChart(this.typeChart[1]);
//            //Build Detail.
//            dojo.empty(this._detailItems);
//            //this.addDetail(this.builDetailRow("Public Link", this.createTextContent("http://www.google.es")));
//            this.addDetail(this.builDetailRow(ENME.getMessage("commons_created_date"),
//            		this.createTextContent(ENME.fromNow(this.data.createDate, "YYYY-MM-DD"))));
//            this.addDetail(this.builDetailRow(ENME.getMessage("commons_captcha"), this.addYesNoWidget(this.data.captcha,
//                     dojo.hitch(this,this._setCaptcha))));
//            this.addDetail(this.builDetailRow(ENME.getMessage("tp_options_allow_results"), this.addYesNoWidget(this.data.allowLiveResults
//                            , dojo.hitch(this, this._setAllowLiveResults))));
//            this.addDetail(this.builDetailRow(ENME.getMessage("tp_options_follow_dashboard"), this.addYesNoWidget(this.data.resumeLiveResults
//                            , dojo.hitch(this, this._setResumeLiveResults))));
//            this.addDetail(this.builDetailRow(ENME.getMessage("tp_options_allow_repeated_votes"), this.addYesNoWidget(
//                    this.data.allowRepeatedVotes
//                    , dojo.hitch(this, this._setRepeated))));
//            this.addDetail(this.builDetailRow(ENME.getMessage("tp_options_notifications"), this.addYesNoWidget(
//                    this.data.closeNotification
//                    , dojo.hitch(this, this._setNotification))));
//            if (this._extra) {
//                dojo.forEach(
//                            this.data.tweetpoll_answers,
//                            dojo.hitch(this, function(data, index) {
//                            var param = {
//                                    aId : data.id,
//                                    color : data.results.color,
//                                    label : data.results.question_label,
//                                    owner : this.data.ownerUsername,
//                                    completed : this.data.completed,
//                                    url : data.short_url
//                                };
//                            var row = new encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer(
//                                    param
//                                    , "tr");
//                            this._extra.appendChild(row.domNode);
//                            dojo.publish("/encuestame/tweetpoll/detail/answer/reload", [data.id, [data.results.votes, data.results.percent]]);
//                     }));
//            }
//        },
//
//        addDetail : function(node){
//            this._detailItems.appendChild(node);
//        },
//
//        /**
//         * Yes / No.
//         */
//        addYesNoWidget : function(value, onChange) {
//            var widget = new encuestame.org.core.shared.utils.YesNoWidget({data: value});
//            if(onChange != null){
//                widget._onChange = onChange;
//            }
//            return widget.domNode;
//        },
//
//        createTextContent : function(text){
//            var textData = dojo.doc.createElement('div');
//            textData.innerHTML = text;
//            return textData;
//        },
//
//        /**
//         * Build Detail Row.
//         */
//        builDetailRow : function(labelText, dataContet) {
//            var rowDetail = dojo.doc.createElement('div');
//            dojo.addClass(rowDetail, "rownDetail");
//            var label = dojo.doc.createElement('div');
//            dojo.addClass(label, "label");
//            var labelItem = dojo.doc.createElement('label');
//            labelItem.innerHTML = labelText;
//            label.appendChild(labelItem);
//            rowDetail.appendChild(label);
//            var data = dojo.doc.createElement('div');
//            dojo.addClass(data, "data");
//            data.appendChild(dataContet);
//            rowDetail.appendChild(data);
//            return rowDetail;
//        },
//
//        /**
//         * Display Data.
//         */
//        displayChart : function(type){
//            dojo.empty(this._chart);var widget;
//            this._callVotes(type);
//        },
//
//        /**
//         * Call Votes.
//         */
//        _callVotes : function(type){
//            var response = dojo.hitch(this, function(dataJson) {
//                var votes = dataJson.success.votesResult;
//                var results = [];
//                dojo.forEach(
//                        votes,
//                        dojo.hitch(this, function(data, index) {
//                            var answer = [data.question_label, (data.votes == null ? 0: data.votes), data.color];
//                            //console.debug("Re answer", answer);
//                            results.push(answer);
//                            dojo.publish("/encuestame/tweetpoll/detail/answer/reload", [data.id, [data.votes, data.percent]]);
//                }));
//                var id = this.id+"_chart";
//                dojo.empty(this._chart);
//                if(this.widgetChart != null){
//                    this.widgetChart = null;
//                }
//                if(type == this.typeChart[0]){
//                    this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(id, results);
//                } else if(type == this.typeChart[1]){
//                    this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(id, results);
//                }
//                //this.render();
//            });;
//            this._callService(response, encuestame.service.list.VotesTweetPoll);
//        },
//
//        /**
//         * Render.
//         */
//        render : function(){
//            this.widgetChart._buildSeries();
//            this.widgetChart.render();
//        }
//});