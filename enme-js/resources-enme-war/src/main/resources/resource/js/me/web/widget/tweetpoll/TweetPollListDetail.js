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
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module TweetpollListDetail
 *  @namespace Widgets
 *  @class TweetpollListDetail
 */

define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/detail/TweetPollAnswer",
         "me/web/widget/options/YesNoWidget",
         "me/web/widget/chart/EncuestamePieChart",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollListDetail.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TweetPollAnswer,
                YesNoWidget,
                EncuestamePieChart,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

          //data
          data: null,

          widgetChart : null,

          /**
           * i18n message for this widget.
           */
          i18nMessage : {
            tweetpoo_detail_tab_detail : _ENME.getMessage("tweetpoo_detail_tab_detail"),
            tweetpoo_detail_tab_stats : _ENME.getMessage("tweetpoo_detail_tab_stats"),
            tweetpoo_detail_tab_comments : _ENME.getMessage("tweetpoo_detail_tab_comments"),
            tweetpoo_detail_tab_social : _ENME.getMessage("tweetpoo_detail_tab_social"),
            tweetpoo_detail_tab_delete : _ENME.getMessage("tweetpoo_detail_tab_delete"),
            tweetpoo_detail_answers_title_link : _ENME.getMessage("tweetpoo_detail_answers_title_link"),
            tweetpoo_detail_answers_title_count : _ENME.getMessage("tweetpoo_detail_answers_title_count"),
            tweetpoo_detail_answers_title_percent : _ENME.getMessage("tweetpoo_detail_answers_title_percent")
          },

          typeChart : ['Bars', 'Pie', 'Lines'],
          //post create
          postCreate : function(){
              //console.debug("DETAIL", this.data);
              dojo.subscribe("/encuestame/tweetpoll/detail/update", this, "updateDetail");
              dojo.subscribe("/encuestame/tweetpoll/detail/chart/render", this, "render");
              this.updateDetail(this.data);
              if (this._extra) {
                  dojo.connect(this._extra, "onclick", dojo.hitch(this, function(event){
                      //console.debug("click extra");
                      dojo.stopEvent(event);
                  }));
              }
              //tab links.
              //TODOL: future.

          },

          /***
           * Update Detail.
           */
          updateDetail : function(data) {
              if(data != null){
                  this.loadContent(data);
              } else {
                  this.error();
              }
          },

         /***
          * Call Service.
          */
         _callService : function(/** function after response */ load, url) {
             var error = function(error) {
                 console.debug("error", error);
             };
             var params = {
                     tweetPollId : this.data.id
             };
             this.getURLService().get(url, params, load, error , dojo.hitch(this, function() {

             }));
         },

         /***
          *
          */
         successDetailUpdateMessage : function () {
           this.publishMessage(_ENME.getMessage('commons_success'), _ENME.MSG.SUCCESS);
         },

         /***
          *
          */
         _setAllowLiveResults : function() {
             var load = dojo.hitch(this, function(data){
                 this.data.allowLiveResults = !this.data.allowLiveResults;
                 this.successDetailUpdateMessage();
             });
             dojo.hitch(this, this._callService(load, 'encuestame.service.list.liveResultsTweetPoll'));
         },

         /***
          *
          */
         _setResumeLiveResults : function(){
             var load = dojo.hitch(this, function(data){
                 this.data.resumeLiveResults = !this.data.resumeLiveResults;
                 this.successDetailUpdateMessage();
             });
             dojo.hitch(this, this._callService(load, 'encuestame.service.list.resumeliveResultsTweetPoll'));
         },

         /***
          *
          */
         _setCaptcha : function() {
             var load = dojo.hitch(this, function(data){
                 this.data.captcha = !this.data.captcha;
                 this.successDetailUpdateMessage();
             });
             dojo.hitch(this, this._callService(load, 'encuestame.service.list.captchaTweetPoll'));
         },

         /***
          *
          */
         _setNotification : function() {
             var load = dojo.hitch(this, function(data) {
                 this.data.closeNotification = !this.data.closeNotification;
                 this.successDetailUpdateMessage();
             });
             dojo.hitch(this, this._callService(load, 'encuestame.service.list.notificationTweetPoll'));
         },

         /***
          * Set as repeated
          */
         _setRepeated : function(){
             var load = dojo.hitch(this, function(data) {
                 this.data.allowRepeatedVotes = !this.data.allowRepeatedVotes;
                 this.successDetailUpdateMessage();
             });
             dojo.hitch(this, this._callService(load, 'encuestame.service.list.repeatedTweetPoll'));
         },

         /***
          * Error messages.
          */
          error : function() {
              this.errorMesage();
              this.publishMessage(_ENME.getMessage('e_023'), _ENME.MSG.ERROR);
          },

          /***
           * Load Content.
           * @param data
           */
          loadContent : function(data) {
              this.data = data;
              this.displayChart(this.typeChart[1]);
              //Build Detail.
              dojo.empty(this._detailItems);
              //this.addDetail(this.builDetailRow("Public Link", this.createTextContent("http://www.google.es")));
              this.addDetail(this.builDetailRow(_ENME.getMessage("commons_created_date"),
                  this.createTextContent(_ENME.fromNow(this.data.createDate, "YYYY-MM-DD"))));
              this.addDetail(this.builDetailRow(_ENME.getMessage("commons_captcha"), this.addYesNoWidget(this.data.captcha,
                       dojo.hitch(this,this._setCaptcha))));
              this.addDetail(this.builDetailRow(_ENME.getMessage("tp_options_allow_results"),
                               this.addYesNoWidget(this.data.allowLiveResults
                              , dojo.hitch(this, this._setAllowLiveResults))));
              this.addDetail(this.builDetailRow(_ENME.getMessage("tp_options_follow_dashboard"), this.addYesNoWidget(this.data.resumeLiveResults
                              , dojo.hitch(this, this._setResumeLiveResults))));
              this.addDetail(this.builDetailRow(_ENME.getMessage("tp_options_allow_repeated_votes"), this.addYesNoWidget(
                      this.data.allowRepeatedVotes
                      , dojo.hitch(this, this._setRepeated))));
              this.addDetail(this.builDetailRow(_ENME.getMessage("tp_options_notifications"), this.addYesNoWidget(
                      this.data.closeNotification
                      , dojo.hitch(this, this._setNotification))));
              if (this._extra) {
                  dojo.forEach(
                      this.data.tweetpoll_answers,
                      dojo.hitch(this, function(data, index) {
                      var param = {
                              aId : data.id,
                              color : data.results.color,
                              label : data.results.question_label,
                              owner : this.data.ownerUsername,
                              completed : this.data.completed,
                              url : data.short_url || data.relative_url
                          };
                      var row = new TweetPollAnswer(
                              param
                              , "tr");
                      this._extra.appendChild(row.domNode);
                      dojo.publish("/encuestame/tweetpoll/detail/answer/reload", [data.id, [data.results.votes, data.results.percent]]);
               }));
              }
          },

          /***
           *  Add a detail
           *  @param the node
           */
          addDetail : function(node) {
              this._detailItems.appendChild(node);
          },

          /***
           * Yes / No.
           * @param value
           * @param onChange
           */
          addYesNoWidget : function(value, onChange) {
              var widget = new YesNoWidget({data: value});
              if (onChange != null) {
                  widget._onChange = onChange;
              }
              return widget.domNode;
          },

          /***
           * Create a text content.
           * //TODO move to WidgetServices
           */
          createTextContent : function(text){
              var textData = dojo.doc.createElement('div');
              textData.innerHTML = text;
              return textData;
          },

          /***
           * Build Detail Row.
           * @param labelText
           * @param dataContent
           */
          builDetailRow : function(labelText, dataContet) {
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

          /***
           * Display Data.
           * @type
           */
          displayChart : function(type) {
              dojo.empty(this._chart);
              //var widget;
              this._callVotes(type);
          },

          /***
           * Call Votes.
           * @param type
           */
          _callVotes : function(type) {
              var response = dojo.hitch(this, function(dataJson) {
                  var votes = dataJson.success.votesResult;
                  var results = [];
                  dojo.forEach(
                          votes,
                          dojo.hitch(this, function(data, index) {
                              var answer = [data.question_label, (data.votes == null ? 0: data.votes), data.color];
                              //console.debug("Re answer", answer);
                              results.push(answer);
                              dojo.publish("/encuestame/tweetpoll/detail/answer/reload", [data.id, [data.votes, data.percent]]);
                  }));
                  var id = this.id+"_chart";
                  dojo.empty(this._chart);
                  if(this.widgetChart != null){
                      this.widgetChart = null;
                  }
                  if(type == this.typeChart[0]){
                      this.widgetChart = new EncuestamePieChart(id, results);
                  } else if(type == this.typeChart[1]){
                      this.widgetChart = new EncuestamePieChart(id, results);
                  }
                  //this.render();
              });;
              this._callService(response, 'encuestame.service.list.VotesTweetPoll');
          },

          /***
           * Render.
           */
          render : function() {
              this.widgetChart._buildSeries();
              this.widgetChart.render();
          }
    });
});