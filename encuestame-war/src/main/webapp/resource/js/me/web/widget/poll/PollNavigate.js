define([
         "dojo/_base/declare",
         "dojo/Deferred",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/menu/DropDownMenuSelect",
         "me/web/widget/menu/DropDownMenuItem",
         "me/web/widget/poll/PollNavigateItem",
         "me/web/widget/data/FilterList",
         "me/core/enme",
         "dojo/text!me/web/widget/poll/templates/pollNavigate.html" ],
        function(
                declare,
                Deferred,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                DropDownMenuSelect,
                DropDownMenuItem,
                PollNavigateItem,
                FilterList,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, FilterList, _WidgetsInTemplateMixin], {

          // template string.
           templateString : template,

          /*
           *
           */
          _rows : [],

          /**
           * Override property field.
           */
          property : "poll",

          /**
           * Override  folder scope field.
           */
          folder_scope : "poll",

          /*
           * i18n message for this widget.
           */
          i18nMessage : {
            detail_manage_by_account : _ENME.getMessage("detail_manage_by_account"),
            detail_manage_today : ENME.getMessage("detail_manage_today"),
            detail_manage_last_week : _ENME.getMessage("detail_manage_last_week"),
            detail_manage_favorites : _ENME.getMessage("detail_manage_favorites"),
            detail_manage_scheduled : _ENME.getMessage("detail_manage_scheduled"),
            detail_manage_all : _ENME.getMessage("detail_manage_all"),
            detail_manage_published : _ENME.getMessage("detail_manage_published"),
            detail_manage_unpublished : _ENME.getMessage("detail_manage_unpublished"),
            detail_manage_only_completed : _ENME.getMessage("detail_manage_only_completed"),

            detail_manage_poll_title : _ENME.getMessage("detail_manage_poll_title"),
            detail_manage_filters : _ENME.getMessage("detail_manage_filters"),
            detail_manage_filters : _ENME.getMessage("detail_manage_filters"),
            detail_manage_poll_dropdown_title : _ENME.getMessage("detail_manage_poll_dropdown_title")
          },

          /*
           *
           */
          _cache_items : [],

          /**
           * Poll Navigate default parameters.
           */
          _params : { typeSearch : "BYOWNER", keyword : null, max : 10, start : 0},

          /**
           * Post Create Cycle Life.
           */
          postCreate : function() {
              //required subscribe to filter support.
              //should be in the parent class??
              dojo.subscribe("/encuestame/filter/list/call", this, "_callFilterList");

//              var def = new Deferred();
//              try {
//                  def.then(dojo.hitch(this, this._callServiceSearch));
//                  def.then(dojo.hitch(this,this._printRows));
//                  def.callback(true);
//              } catch(e) {
//                 def.errback(new Error("load poll failed."));
//              }

              var deferred = new Deferred(function(reason){
                  // do something when the Deferred is cancelled
                });

                // do something asynchronously

                // provide an update on progress:
                deferred.progress(function(e){
                    console.log('progress', e);
                });

                // when the process finishes:
                deferred.resolve(function(e){
                    console.log('resolve', e);
                });

                // performing "callbacks" with the process:
                deferred.then(dojo.hitch(this, this._callServiceSearch), function(err){
                  // Do something when the process errors out
                }, function(update){
                  // Do something when the process provides progress information
                });

                deferred.then(dojo.hitch(this,this._printRows), function(err){
                    // Do something when the process errors out
                  }, function(update){
                    // Do something when the process provides progress information
                  });

                // to cancel the asynchronous process:
                //deferred.cancel(reason);

              //enable folder support.
              if (this.folder_support && this._folder) {
                 this.enableFolderSupport();
              }
              //enable more support.
              if (this.enable_more_support) {
                  this.enableMoreSupport(this._params.start, this._params.max, this._more);
              }

              //dojo.addOnLoad(function() {
              //    dojo.connect(dojo.byId('strapline'), 'onclick', function(event) {
              //        dojo.publish('myMessages', [{ message: 'Qwerty', type: "error", duration: 0}]);
              //    });
              //});

              var menu_widget = this._dropdownmenu;
              var newPoll = new DropDownMenuItem({
                  label : _ENME.getMessage("poll_admon_poll_new"),
                  url : "/user/poll/new"
              });
              menu_widget._appendItem(newPoll);
          },

          /*
           * @override
           */
          displayEmptyMessage : function() {
              var node = dojo.create("div");
              dojo.addClass(node, "web-items-no-results");
              node.innerHTML = _ENME.getMessage("024");
              dojo.place(node, this._items);
          },

          /**
           * Function to clean _items node.
           */
          _empty : function() {
              dojo.empty(this._items);
          },

          /**
           * Subscribe function on filter search
           * @param typeSearch set the type of search
           */
          _callFilterList : function(typeSearch) {
              this._params.typeSearch = typeSearch;
              this._callServiceSearch();
          },

          /*
           *
           */
          _afterEach : function() {
            //TODO future.
              //var more = new encuestame.org.core.shared.utils.More();
          },

          /**
           * Call a service to retrieve a list of poll based on a previous filter parameters.
           */
          _callServiceSearch : function() {
              dojo.hitch(this, this.loadItems('encuestame.service.list.listPoll'));
          },


          /**
           * customize service params.
           */
          getParams : function() {
              return this._params;
          },

          /**
           * The url json service.
           * @returns
           */
          getUrl : function() {
              return 'encuestame.service.list.listPoll';
          },


          /**
           * Create a new PollNavigateItem.
           */
          processItem : function(/** poll data**/  data, /** position **/ index) {
              var row = new PollNavigateItem({ data: data});
              this._rows.push(row);
              dojo.place(row.domNode, this._items);
          },

          /**
           *
           */
          _printRows : function() {
               dojo.forEach(this._rows,
                    dojo.hitch(this, function(data, index) {
                        this._cache_items.push(data);
               }));
          }
    });
});
///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//dojo.provide("encuestame.org.core.commons.poll.PollNavigate");
//
//dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
//dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
//dojo.require("encuestame.org.core.shared.utils.FoldersActions");;
//dojo.require("encuestame.org.core.shared.utils.FilterList");
//dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
//dojo.require("encuestame.org.core.shared.utils.DropDownMenuSelect");
//dojo.require("encuestame.org.core.shared.utils.More");
//dojo.require("encuestame.org.core.commons.support.PanelWipe");
//dojo.require("encuestame.org.core.shared.utils.StandBy");
//dojo.require("encuestame.org.core.commons.chart.ChartLayerSupport");
//dojo.require("encuestame.org.core.shared.utils.UpdateDefaultOptions");
//
//dojo.require("dijit.InlineEditBox");
//dojo.require("dijit.form.Textarea");
//dojo.require("dojox.widget.Toaster");
//dojo.require("dijit.form.CheckBox");
//
///**
// * Poll Administration Widget.
// */
//dojo.declare(
//    "encuestame.org.core.commons.poll.PollNavigate",
//    [encuestame.org.main.EnmeMainLayoutWidget,
//     encuestame.org.core.shared.utils.FilterList],{
//
//        /**
//         * Poll navigate template.
//         */
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollNavigate.html"),

//});
//
///**
// * Poll Navigate Item.
// */
//dojo.declare(
//        "encuestame.org.core.commons.poll.PollNavigateItem",
//            [encuestame.org.main.EnmeMainLayoutWidget], {
//
//        /*
//         *
//         */
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollListItem.html"),
//
//        /*
//         *
//         */
//        data : null,
//
//        /*
//         * i18n message for this widget.
//         */
//        i18nMessage : {
//        	poll_admon_poll_edit : _ENME.getMessage("poll_admon_poll_edit"),
//        	poll_admon_poll_preview : _ENME.getMessage("poll_admon_poll_preview"),
//        	poll_admon_poll_publish_options : _ENME.getMessage("poll_admon_poll_publish_options"),
//        	poll_admon_poll_embebed : _ENME.getMessage("poll_admon_poll_embebed"),
//        	poll_admon_poll_votes : _ENME.getMessage("poll_admon_poll_votes")
//        },
//
//        /*
//         *
//         */
//        _standBy : null,
//
//        /**
//         * Poll detail widget reference.
//         */
//        widget_detail : null,
//
//        /**
//         * Post create cycle life.
//         */
//        postCreate : function() {
//            var panel = new encuestame.org.core.commons.support.PanelWipe(this._more, null, null, 390);
//            //add event on click edit link
//            panel.connect(this._edit, dojo.hitch(this, this._callEditInfo));
//            panel.preWipe = dojo.hitch(this, function() {
//                dojo.addClass(this.domNode, "selected-row");
//            });
//            panel.postWipe =  dojo.hitch(this, function() {
//                dojo.removeClass(this.domNode, "selected-row");
//            });
//            //this._standBy = dijit.byId("standby_"+this.id);
//            this.widget_detail = new encuestame.org.core.commons.poll.PollNavigateItemDetail(
//            		{
//            			data : this.data ,
//            			label : _ENME.getMessage('poll_admon_poll_options')
//            		});
//            dojo.addClass(this.widget_detail.domNode, "hidden");
//            dojo.place(this.widget_detail.domNode, this._more);
//            //set votes
//            this._votes.innerHTML = this.data.total_votes == null ? 0 : this.data.total_votes;
//            //set date
//            this._date.innerHTML = _ENME.fromNow(this.data.creation_date);
//        },
//
//        /**
//         * Call Edito Info.
//         */
//        _callEditInfo : function() {
//            var load = dojo.hitch(this, function(data) {
//                if ("success" in data) {
//                    dojo.removeClass(this.widget_detail.domNode, "hidden");
//                    this.widget_detail.setResults(data.success.poll);
//                } else {
//                    this._showErrorMessage(error);
//                }
//            });
//            var error = dojo.hitch(this, function(error) {
//                this._showErrorMessage(error);
//            });
//            var params = {
//                    id : this.data.id
//            };
//            dojo.addClass(this.widget_detail.domNode, "hidden");
//            encuestame.service.xhrGet(encuestame.service.list.poll.detail, params, load, error);
//        }
//});
//
///**
// * Poll Navigation Item Detail Widget.
// */
//dojo.declare(
//        "encuestame.org.core.commons.poll.PollNavigateItemDetail",
//            [encuestame.org.main.EnmeMainLayoutWidget,
//             encuestame.org.core.commons.chart.ChartLayerSupport,
//             encuestame.org.core.shared.utils.UpdateDefaultOptions], {
//
//       /**
//        * HTML Template.
//        */
//       templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollListItemDetail.html"),
//
//       /**
//        * h2 title.
//        */
//       label : "",
//
//       /**
//        * The information of poll detail.
//        */
//       data : {},
//
//       /*
//        * i18n message for this widget.
//        */
//       i18nMessage : {
//    	   poll_admon_poll_answers : _ENME.getMessage("poll_admon_poll_answers"),
//    	   commons_remove : _ENME.getMessage("commons_remove")
//       },
//
//       /**
//        * Post create.
//        */
//       postCreate : function() {
//           //dojo.connect(this._publish, "onClick", dojo.hitch(this, this._validatePoll));
//           this._remove.onClick = dojo.hitch(this, function(){
//               console.info("json service to remove");
//           });
//       },
//
//       /**
//        *
//        * @param property
//        */
//       _updatePollParameters : function(property) {
//           var params = {
//                "pollId" : this.data.id
//           };
//           var load = dojo.hitch(this, function(data) {
//               if ("success" in data) {
//                   console.info("success", data);
//               }
//           });
//
//           var error = dojo.hitch(this, function(error) {
//               this._showErrorMessage(error.message);
//           });
//           if (property) {
//               encuestame.service.xhrPostParam(
//                    encuestame.service.list.poll.setParameter(property), params, load, error);
//           } else {
//               this._showErrorMessage("error on update parameter");
//           }
//       },
//
//       /**
//        * Render the answer in table format.
//        * @param data a object with answer data
//        */
//       reRenderResults : function(data) {
//           dojo.empty(this._detailAnswers);
//           if (data.length > 0) {
//               dojo.forEach(data, dojo.hitch(this, function(answer) {
//               var rowDetail = dojo.create('div');
//                   dojo.addClass(rowDetail, "web-poll-answer-row");
//                   //color
//                   var color = dojo.create('div');
//                   var span_color = dojo.create('span');
//
//                   dojo.style(span_color, "display", "inline-block");
//                   dojo.style(span_color, "width", "30px");
//                   dojo.place(span_color, color);
//                   dojo.addClass(color, "web-poll-answer-row-color");
//                   dojo.style(color, "backgroundColor", answer.color);
//                   //color.innerHTML = answer.color;
//                   dojo.place(color, rowDetail);
//                   //label
//                   var label = dojo.create('div');
//                   dojo.addClass(label, "web-poll-answer-row-label");
//                   label.innerHTML = answer.answers;
//                   dojo.place(label, rowDetail);
//                   //percent
//                   var percent = dojo.create('div');
//                   dojo.addClass(percent, "web-poll-answer-row-percent");
//                   percent.innerHTML = answer.votes == undefined ? 0 : answer.votes;
//                   dojo.place(percent, rowDetail);
//                   //append to root
//                   dojo.place(rowDetail, this._detailAnswers);
//               }));
//           } else {
//               dojo.place(encuestame.utilities.noResults("web-poll-answer-no-results"), this._detailAnswers);
//           }
//       },
//
//       /**
//        * Set results.
//        * @param data a object with answer data
//        */
//       setResults : function(data) {
//           dojo.empty(this._detailItems);
//           this.setNodeAppend(this._detailItems);
//           this.addRow("Close after date", data.poll_bean.is_close_after_date, dojo.hitch(this, this._updatePollParameters), "change-open-status");
//           this.addRow("Close after quota", data.poll_bean.is_close_after_quota, dojo.hitch(this, this._updatePollParameters), "close-after-quota");
//           this.addRow("Enable IP restrictions", data.poll_bean.is_ip_restricted, dojo.hitch(this, this._updatePollParameters), "ip-protection");
//           this.addRow("Enable notifications", data.poll_bean.close_notification, dojo.hitch(this, this._updatePollParameters), "notifications");
//           this.addRow("Enable password restriction", data.poll_bean.is_password_restriction, dojo.hitch(this, this._updatePollParameters), "password-restrictions");
//           this.addRow("Display aditional information", data.poll_bean.is_show_additional_info, dojo.hitch(this, this._updatePollParameters), "additional-info");
//           this.addRow("Make result public", data.poll_bean.show_resultsPoll, dojo.hitch(this, this._updatePollParameters), "change-display-results");
//           this.addRow("Make result public", data.poll_bean.show_resultsPoll, dojo.hitch(this, this._updatePollParameters), "change-display-results");
//           var nodeId = this.id+"_chart";
//           dojo.empty(dojo.byId(nodeId));
//           //if results are empty it's needed display a "no results" option
//           if (data.poll_results.length > 0) {
//			   this.widgetChart = this.buildChart({
//					id : nodeId,
//					results : this._convertToChartAnswer(
//								this._mergeResultsAnswers(
//										data.poll_list_answers,
//										data.poll_results))
//				});
//	           this.renderChart(this.widgetChart);
//           } else {
//        	   var node = dojo.byId(nodeId),
//        	   no_results = dojo.create('div');
//        	   no_results.innerHTML = _ENME.getMessage('commons_no_results');
//        	   dojo.addClass(no_results, "no_results");
//        	   node.appendChild(no_results);
//           }
//           //display the list of answer on a table.
//           this.reRenderResults(this._mergeResultsAnswers(data.poll_list_answers, data.poll_results));
//
//           var comments = dojo.create("div");
//           dojo.addClass(comments, "ui-comments");
//           comments.innerHTML = data.poll_bean.total_comments;
//
//           var hits = dojo.create("div");
//           dojo.addClass(hits, "ui-hits");
//           hits.innerHTML = data.poll_bean.hits;
//
//           var likes = dojo.create("div");
//           dojo.addClass(likes, "ui-likes");
//           likes.innerHTML = data.poll_bean.like_votes;
//
//           var dislike = dojo.create("div");
//           dojo.addClass(dislike, "ui-dislike");
//           dislike.innerHTML = data.poll_bean.dislike_votes;
//
//           //empty
//           dojo.empty(this._detail_info);
//           //append the info
//           dojo.place(comments, this._detail_info);
//           dojo.place(hits, this._detail_info);
//           dojo.place(likes, this._detail_info);
//           dojo.place(dislike, this._detail_info);
//       },
//
//       /**
//        * Merge the answer with votes, if results exist is merged with the vote.
//        * Added new property, answer.vote = x;
//        * @param list_answers
//        * @param list_results
//        */
//       _mergeResultsAnswers : function(list_answers, list_results) {
//           dojo.forEach(list_answers,
//                   dojo.hitch(this, function(data, index) {
//                       dojo.forEach(list_results, function(data2, index) {
//                           if(data2.answer.answer_id === data.answer_id) {
//                               data.votes = data2.answer_votes;
//                               data.percent = 0;
//                               return false;
//                           }
//                       });
//              }));
//           return list_answers;
//       },
//
//       /**
//        * Convert to answer to chat.
//        * @param data a array with answers object
//        */
//       _convertToChartAnswer : function(answers) {
//           var array = [];
//           dojo.forEach(answers, function(answer) {
//              array.push([answer.answers, answer.votes == undefined ? 0 : answer.votes , answer.color]);
//           });
//           return array;
//       }
//});