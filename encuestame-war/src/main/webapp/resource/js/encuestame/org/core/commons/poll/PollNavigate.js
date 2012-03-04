/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.commons.poll.PollNavigate");

dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.shared.utils.FoldersActions");;
dojo.require("encuestame.org.core.shared.utils.FilterList");
dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.core.shared.utils.DropDownMenuSelect");
dojo.require("encuestame.org.core.shared.utils.More");
dojo.require("encuestame.org.core.commons.support.PanelWipe");
dojo.require("encuestame.org.core.shared.utils.StandBy");
dojo.require("encuestame.org.core.commons.chart.ChartLayerSupport");
dojo.require("encuestame.org.core.shared.utils.UpdateDefaultOptions");

dojo.require("dijit.InlineEditBox");
dojo.require("dijit.form.Textarea");
dojo.require("dojox.widget.Toaster");
dojo.require("dijit.form.CheckBox");

/**
 * Poll Administration Widget.
 */
dojo.declare(
    "encuestame.org.core.commons.poll.PollNavigate",
    [encuestame.org.main.EnmeMainLayoutWidget,
     encuestame.org.core.shared.utils.FilterList],{

        /**
         * Poll navigate template.
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollNavigate.html"),

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
            var def = new dojo.Deferred();
            //required subscribe to filter support.
            //should be in the parent class??
            dojo.subscribe("/encuestame/filter/list/call", this, "_callFilterList");
            try {
                def.then(dojo.hitch(this, this._callServiceSearch));
                def.then(this._printRows);
                def.callback(true);
            } catch(e) {
               def.errback(new Error("load poll failed."));
            }
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
        },

        /*
         * @override
         */
        displayEmptyMessage : function() {
            var node = dojo.create("div");
            dojo.addClass(node, "web-items-no-results");
            node.innerHTML = encuestame.constants.messageCodes["024"];
            dojo.place(node, this._items);
        },

        /**
         * Function to clean _items node.
         */
        _empty : function() {
            //console.debug("empty items");
            dojo.empty(this._items);
        },

        /**
         * Subscribe function on filter search
         * @param typeSearch set the type of search
         */
        _callFilterList : function(typeSearch) {
            this._params.typeSearch = typeSearch;
            //console.info("_callFilterList", typeSearch);
            ///console.info("_callFilterList", this._params);
            this._callServiceSearch();
        },

        /*
         *
         */
        _afterEach : function() {
            //var more = new encuestame.org.core.shared.utils.More();
        },

        /**
         * Call a service to retrieve a list of poll based on a previous filter parameters.
         */
        _callServiceSearch : function() {
            dojo.hitch(this, this.loadItems(encuestame.service.list.listPoll));
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
            return encuestame.service.list.listPoll;
        },


        /**
         * Create a new PollNavigateItem.
         */
        processItem : function(/** poll data**/  data, /** position **/ index) {
            //console.info(data);
            var row = new encuestame.org.core.commons.poll.PollNavigateItem({ data: data});
            this._rows.push(row);
            dojo.place(row.domNode, this._items);
            //console.info("row added");
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

/**
 * Poll Navigate Item.
 */
dojo.declare(
        "encuestame.org.core.commons.poll.PollNavigateItem",
            [encuestame.org.main.EnmeMainLayoutWidget], {

        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollListItem.html"),

        /*
         *
         */
        data : null,

        /*
         *
         */
        _standBy : null,

        /**
         * Poll detail widget reference.
         */
        widget_detail : null,

        /**
         * Post create cycle life.
         */
        postCreate : function() {
            //console.debug("row data", this.data);
            var panel = new encuestame.org.core.commons.support.PanelWipe(this._more, null, null, 390);
            //add event on click edit link
            panel.connect(this._edit, dojo.hitch(this, this._callEditInfo));
            panel.preWipe = dojo.hitch(this, function() {
                dojo.addClass(this.domNode, "selected-row");
            });
            panel.postWipe =  dojo.hitch(this, function() {
                dojo.removeClass(this.domNode, "selected-row");
            });
            //this._standBy = dijit.byId("standby_"+this.id);
            this.widget_detail = new encuestame.org.core.commons.poll.PollNavigateItemDetail({ data : this.data , label : "Poll Options"});
            dojo.addClass(this.widget_detail.domNode, "hidden");
            dojo.place(this.widget_detail.domNode, this._more);
        },

        /**
         * Call Edito Info.
         */
        _callEditInfo : function() {
            //console.info("_callEditInfo");
            var load = dojo.hitch(this, function(data) {
                console.info("_callEditInfo", data);
                dojo.removeClass(this.widget_detail.domNode, "hidden");
                data = {
                        answers : [
                                   {
                                       answer: "answer 1",
                                       type : "text",
                                       percent : 43,
                                       color : "#A6B4BF"

                                   },
                                   {
                                       answer: "answer 2",
                                       type : "text",
                                       percent : 13,
                                       color : "#FEFEF"

                                   },
                                   {
                                       answer: "answer 3",
                                       type : "text",
                                       percent : 3,
                                       color : "#44444"

                                   },
                                   {
                                       answer: "answer 4",
                                       type : "text",
                                       percent : 14,
                                       color : "#888888"

                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   },
                                   {
                                       answer: "answer5",
                                       type : "text",
                                       percent : 27,
                                       color : "#FF0000"
                                   }
                                   ]
                };
                this.widget_detail.setResults(data);
                //this._standBy.stop();
                //console.info("poll detail", data);
            });
            var error = dojo.hitch(this, function(error) {
                //this._standBy.stop();
                console.error("error", error);
            });
            var params = {
                    id : this.data.id
            };
            //this._standBy.startup();
            //this._standBy.start();
            dojo.addClass(this.widget_detail.domNode, "hidden");
            encuestame.service.xhrGet(encuestame.service.list.poll.detail, params, load, error);
        },
});

/**
 * Poll Navigation Item Detail Widget.
 */
dojo.declare(
        "encuestame.org.core.commons.poll.PollNavigateItemDetail",
            [encuestame.org.main.EnmeMainLayoutWidget,
             encuestame.org.core.commons.chart.ChartLayerSupport,
             encuestame.org.core.shared.utils.UpdateDefaultOptions], {

       /**
        * HTML Template.
        */
       templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollListItemDetail.html"),

       /**
        * h2 title.
        */
       label : "",

       /**
        * The information of poll detail.
        */
       data : {},

       /**
        * Post create.
        */
       postCreate : function() {
           dojo.empty(this._detailItems);
           this.setNodeAppend(this._detailItems);
           this.addRow("Close after date", 1, dojo.hitch(this, this._updatePollParameters), "change-open-status");
           this.addRow("Cloase after quota", 1, dojo.hitch(this, this._updatePollParameters), "close-after-quota");
           this.addRow("Enable IP restrictions", 1,dojo.hitch(this, this._updatePollParameters), "ip-protection");
           this.addRow("Enable notifications", 1,dojo.hitch(this, this._updatePollParameters), "notifications");
           this.addRow("Enable password restriction", 1,dojo.hitch(this, this._updatePollParameters), "password-restrictions");
           this.addRow("Display aditional information", 1,dojo.hitch(this, this._updatePollParameters), "additional-info");
           this.addRow("Make result public", 1,dojo.hitch(this, this._updatePollParameters), "change-display-results");
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
               }
           });

           var error = dojo.hitch(this, function(error) {
               this._showErrorMessage(error.message);
           });
           if (property) {
               encuestame.service.xhrPostParam(
                    encuestame.service.list.poll.setParameter(property), params, load, error);
           } else {
               this._showErrorMessage("error on update parameter");
           }
       },

       /**
        * Render the answer in table format.
        * @param data a object with answer data
        */
       reRenderResults : function(data) {
           if (data.answers.length > 0) {
               dojo.empty(this._detailAnswers);
               dojo.forEach(data.answers, dojo.hitch(this, function(answer) {
               var rowDetail = dojo.create('div');
                   dojo.addClass(rowDetail, "web-poll-answer-row");
                   //color
                   var color = dojo.create('div');
                   var span_color = dojo.create('span');
                   dojo.style(span_color, "background-color", answer.color);
                   dojo.style(span_color, "display", "inline-block");
                   dojo.style(span_color, "width", "30px");
                   dojo.place(span_color, color);
                   dojo.addClass(color, "web-poll-answer-row-color");
                   //color.innerHTML = answer.color;
                   dojo.place(color, rowDetail);
                   //label
                   var label = dojo.create('div');
                   dojo.addClass(label, "web-poll-answer-row-label");
                   label.innerHTML = answer.answer;
                   dojo.place(label, rowDetail);
                   //percent
                   var percent = dojo.create('div');
                   dojo.addClass(percent, "web-poll-answer-row-percent");
                   percent.innerHTML = answer.percent;
                   dojo.place(percent, rowDetail);
                   //append to root
                   dojo.place(rowDetail, this._detailAnswers);
               }));
           } else {
               dojo.place(encuestame.utilities.noResults("web-poll-answer-no-results"), this._detailAnswers);
           }
       },

       /**
        * Set results.
        * @param data a object with answer data
        */
       setResults : function(data) {
           var nodeId = this.id+"_chart";
           dojo.empty(dojo.byId(nodeId));
           this.widgetChart = this.buildChart({id : nodeId, results : this._convertToChartAnswer(data.answers)});
           this.renderChart(this.widgetChart);
           this.reRenderResults(data);
       },

       /**
        * Convert to answer to chat.
        * @param data a array with answers object
        */
       _convertToChartAnswer : function(answers) {
           var array = [];
           dojo.forEach(answers, function(answer) {
              array.push([answer.answer, answer.percent, answer.color]);
           });
           return array;
       }
});