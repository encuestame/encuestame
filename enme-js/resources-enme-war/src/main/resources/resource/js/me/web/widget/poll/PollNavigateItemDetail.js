define([
         "dojo/_base/declare",
         "dojo/query",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/Button",
         "me/web/widget/chart/ChartLayerSupport",
         "me/web/widget/utils/UpdateDefaultOptions",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/URLServices",
         "me/web/widget/dialog/Confirm",
         "me/core/enme",
         "dojo/text!me/web/widget/poll/templates/pollListItemDetail.html" ],
        function(
                declare,
                query,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Button,
                ChartLayerSupport,
                UpdateDefaultOptions,
                main_widget,
                URLServices,
                Confirm,
                _ENME,
                 template) {
            return declare([ _WidgetBase,
                             _TemplatedMixin,
                             main_widget,
                             ChartLayerSupport,
                             UpdateDefaultOptions,
                             _WidgetsInTemplateMixin], {

          // template string.
           templateString : template,

           /**
           * h2 title.
           */
          label : "",

          /**
           *
           * @property
           */
          parentWidget : null,

          /**
           * The information of poll detail.
           */
          data : {},

          /*
           * i18n message for this widget.
           */
          i18nMessage : {
            poll_admon_poll_answers : _ENME.getMessage("poll_admon_poll_answers"),
            commons_remove : _ENME.getMessage("commons_remove"),
            poll_options_close : _ENME.getMessage("poll_options_close"),
            poll_options_quota : _ENME.getMessage("poll_options_quota"),
            poll_options_ip : _ENME.getMessage("poll_options_ip"),
            poll_options_password : _ENME.getMessage("poll_options_password"),
            poll_options_info : _ENME.getMessage("poll_options_info"),
            poll_options_public : _ENME.getMessage("poll_options_public"),
            poll_options_close : _ENME.getMessage("poll_options_close"),
            poll_options_notifications : _ENME.getMessage("poll_options_notifications"),
            commons_confirm : _ENME.getMessage("commons_confirm"),
            commons_yes : _ENME.getMessage("commons_yes"),
            commons_no : _ENME.getMessage("commons_no")
          },

          /**
           * Post create.
           */
          postCreate : function() {
              this._remove.onClick = dojo.hitch(this, function() {
                  this._openDialog("", "");
              });
          },

          /**
           * open dialog.
           * @param title
           * @param content
           */
          _openDialog : function(title, content) {
              var myDialog = new Confirm({
                  title: title,
                  content: content,
                  style: "width: 350px",
                  label : {
                      question : this.i18nMessage.commons_confirm,
                      yes : this.i18nMessage.commons_yes,
                      no : this.i18nMessage.commons_no
                  }
              });
              // success handler
              var load = dojo.hitch(this, function() {
                  this.successMesage("Poll Removed");
                   myDialog.hide();
                   dojo.destroy(this.parentWidget.domNode);
              });
              // error handler
              var error = dojo.hitch(this, function(error) {
                 myDialog.hide();
                 this.errorMesage(error);
              });
              myDialog.functionYes = dojo.hitch(this, function(){
                     this.getURLService().del("encuestame.service.list.poll.remove", { pollId : this.data.id} , load, error , dojo.hitch(this, function() {

                     }));
                    myDialog.hide();
                });
               myDialog.show();
            },

          /**
           *
           * @param property
           */
          _updatePollParameters : function(property) {
              var params = {
                   "pollId" : this.data.id
              };
              var load = dojo.hitch(this, function(data) {
                  if ("success" in data) {
                      console.info("success", data);
                      this.successMesage(_ENME.getMessage("commons_success"));
                  }
              });

              var error = dojo.hitch(this, function(error) {
                  this._showErrorMessage(error.message);
              });
              if (property) {
                  this.getURLService().post(["encuestame.service.list.poll.setParameter",[property]], params, load, error , dojo.hitch(this, function() {
                  }));
              } else {
                  this._showErrorMessage("error on update parameter");
              }
          },

          /**
           * Render the answer in table format.
           * @param data a object with answer data
           */
          reRenderResults : function(data) {
              dojo.empty(this._detailAnswers);
              if (data.length > 0) {
                  dojo.forEach(data, dojo.hitch(this, function(answer) {
                  var rowDetail = dojo.create('div');
                      dojo.addClass(rowDetail, "web-poll-answer-row");
                      //color
                      var color = dojo.create('div');
                      var span_color = dojo.create('span');

                      dojo.style(span_color, "display", "inline-block");
                      dojo.style(span_color, "width", "30px");
                      dojo.place(span_color, color);
                      dojo.addClass(color, "web-poll-answer-row-color");
                      dojo.style(color, "backgroundColor", answer.color);
                      //color.innerHTML = answer.color;
                      dojo.place(color, rowDetail);
                      //label
                      var label = dojo.create('div');
                      dojo.addClass(label, "web-poll-answer-row-label");
                      label.innerHTML = answer.answers;
                      dojo.place(label, rowDetail);
                      //percent
                      var percent = dojo.create('div');
                      dojo.addClass(percent, "web-poll-answer-row-percent");
                      percent.innerHTML = answer.votes == undefined ? 0 : answer.votes;
                      dojo.place(percent, rowDetail);
                      //append to root
                      dojo.place(rowDetail, this._detailAnswers);
                  }));
              } else {
                  dojo.place(encuestame.utilities.noResults("web-poll-answer-no-results"), this._detailAnswers);
              }
          },

          /**
           *
           * @method _displayAnswers
           */
          _displayAnswers : function (e) {
              dojo.addClass(this._detailWrapperItems, 'hidden');
              dojo.removeClass(this._detailWrapperAnswers, 'hidden');
              console.log(e.target);
              query(".selected", this.domNode).forEach(function(node){
                    dojo.removeClass(node, 'selected');
              });
              dojo.addClass(e.target, 'selected');
          },

          /**
           *
           * @method _displayOptions
           */
          _displayOptions : function (e) {
              dojo.addClass(this._detailWrapperAnswers, 'hidden');
              dojo.removeClass(this._detailWrapperItems, 'hidden');
              query(".selected", this.domNode).forEach(function(node){
                    dojo.removeClass(node, 'selected');
              });
              dojo.addClass(e.target, 'selected');
          },

          /**
           * Set results.
           * @param data a object with answer data
           */
          setResults : function(data) {
              dojo.empty(this._detailItems);
              this.setNodeAppend(this._detailItems);
              this.addRow(this.i18nMessage.poll_options_close, data.poll_bean.is_close_after_date, dojo.hitch(this, this._updatePollParameters), "change-open-status");
              this.addRow(this.i18nMessage.poll_options_quota, data.poll_bean.is_close_after_quota, dojo.hitch(this, this._updatePollParameters), "close-after-quota");
              this.addRow(this.i18nMessage.poll_options_ip, data.poll_bean.is_ip_restricted, dojo.hitch(this, this._updatePollParameters), "ip-protection");
              this.addRow(this.i18nMessage.poll_options_notifications, data.poll_bean.close_notification, dojo.hitch(this, this._updatePollParameters), "notifications");
              this.addRow(this.i18nMessage.poll_options_password, data.poll_bean.is_password_restriction, dojo.hitch(this, this._updatePollParameters), "password-restrictions");
              this.addRow(this.i18nMessage.poll_options_info, data.poll_bean.is_show_additional_info, dojo.hitch(this, this._updatePollParameters), "additional-info");
              this.addRow(this.i18nMessage.poll_options_public, data.poll_bean.show_resultsPoll, dojo.hitch(this, this._updatePollParameters), "change-display-results");
              var nodeId = this.id + "_chart";
              dojo.empty(dojo.byId(nodeId));
              //if results are empty it's needed display a "no results" option
              if (data.poll_results.length > 0) {
                 this.widgetChart = this.buildChart({
                 id : nodeId,
                 results : this._convertToChartAnswer(
                       this._mergeResultsAnswers(
                           data.poll_list_answers,
                           data.poll_results))
                 });
                 this.renderChart(this.widgetChart);
              } else {
                var node = dojo.byId(nodeId),
                no_results = dojo.create('div');
                no_results.innerHTML = _ENME.getMessage('commons_no_results');
                dojo.addClass(no_results, "no_results");
                node.appendChild(no_results);
              }
              //display the list of answer on a table.
              this.reRenderResults(this._mergeResultsAnswers(data.poll_list_answers, data.poll_results));

              var comments = dojo.create("div");
              dojo.addClass(comments, "ui-comments");
              comments.innerHTML = data.poll_bean.total_comments;

              var hits = dojo.create("div");
              dojo.addClass(hits, "ui-hits");
              hits.innerHTML = data.poll_bean.hits;

              var likes = dojo.create("div");
              dojo.addClass(likes, "ui-likes");
              likes.innerHTML = data.poll_bean.like_votes;

              var dislike = dojo.create("div");
              dojo.addClass(dislike, "ui-dislike");
              dislike.innerHTML = data.poll_bean.dislike_votes;

              //empty
              dojo.empty(this._detail_info);
              //append the info
              dojo.place(comments, this._detail_info);
              dojo.place(hits, this._detail_info);
              dojo.place(likes, this._detail_info);
              dojo.place(dislike, this._detail_info);
          },

          /**
           * Merge the answer with votes, if results exist is merged with the vote.
           * Added new property, answer.vote = x;
           * @param list_answers
           * @param list_results
           */
          _mergeResultsAnswers : function(list_answers, list_results) {
              dojo.forEach(list_answers,
                      dojo.hitch(this, function(data, index) {
                          dojo.forEach(list_results, function(data2, index) {
                              if(data2.answer.answer_id === data.answer_id) {
                                  data.votes = data2.answer_votes;
                                  data.percent = 0;
                                  return false;
                              }
                          });
                 }));
              return list_answers;
          },

          /**
           * Convert to answer to chat.
           * @param data a array with answers object
           */
          _convertToChartAnswer : function(answers) {
              var array = [];
              dojo.forEach(answers, function(answer) {
                 array.push([answer.answers.substring(0, 8), answer.votes == undefined ? 0 : answer.votes , answer.color]);
              });
              return array;
          }
    });
});