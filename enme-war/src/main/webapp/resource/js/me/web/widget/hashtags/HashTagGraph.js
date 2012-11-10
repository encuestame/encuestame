define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/chart/RaphaelSupport",
         "me/web/widget/hashtags/HashtagChart",
         "me/web/widget/hashtags/HashTagGraphStatsButton",
         "me/web/widget/hashtags/HashTagGraphStatsUsageHandler",
         "me/core/enme",
         "dojo/text!me/web/widget/hashtags/template/hashTagGraph.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                raphaelSupport,
                hashtagChart,
                hashTagGraphStatsButton,
                hashTagGraphStatsUsageHandler,
                _ENME,
                 template) {
            return declare([ _WidgetBase,
                             _TemplatedMixin,
                             main_widget,
                             raphaelSupport,
                             hashtagChart,
                             _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /**
            * Hashtag name.
            */
           hashtagName : "",

           /**
            * Default filter.
            */
           default_filter : "HASHTAG",

           /**
            * Default filter.
            */
           default_range : _ENME.YEAR,

           /**
            * Define the channel to refresh the graph.
            */
           channel : "",

           /**
            * Create all buttons.
            * @param period {String} Define the period to filter the data.
            */
           _createButtons : function(period) {
              var params = {
                  tagName : this.hashtagName,
                  filter  : _ENME.HASHTAGRATED
              };
              if ( period) {
                params.period = period;
              }
              var load = dojo.hitch(this, function(data) {
                if (_ENME.SUCCESS in data) {
                  var hashTagButtonStats = data.success.hashTagButtonStats;
                  usage_by_item = hashTagButtonStats['usage_by_item'],
                  total_usage_by_social_network = hashTagButtonStats['total_usage_by_social_network'],
                  total_hits = hashTagButtonStats['total_hits'],
                  usage_by_votes = hashTagButtonStats['usage_by_votes'];
                  if (this._stat) {
                    dojo.empty(this._stat);
                    this._stat.appendChild(this._createAButton({title : usage_by_item.label, value : usage_by_item.value , sub_label : usage_by_item.sub_label, filter : usage_by_item.filter }, true, period));
                    this._stat.appendChild(this._createAButton({title : total_usage_by_social_network.label, value : total_usage_by_social_network.value , sub_label : total_usage_by_social_network.sub_label,  filter : total_usage_by_social_network.filter}, false, period));
                    this._stat.appendChild(this._createAButton({title : total_hits.label, value : total_hits.value , sub_label : total_hits.sub_label, filter : total_hits.filter}, false, period));
                    this._stat.appendChild(this._createAButton({title : usage_by_votes.label, value : usage_by_votes.value , sub_label : usage_by_votes.sub_label, filter : usage_by_votes.filter}, false, period));
                  }
                }
              });
              this.getURLService().get('encuestame.service.list.rate.buttons', params, load, null, null);
           },

           /**
            * Create a stats button.
            * @param {Object}  button_data
            * @param {Boolean} selectedButton
            */
           _createAButton : function (button_data, selectedButton, period) {
               var handler_params = {
                     data : {
                         "title" : button_data.title,
                         "value" : button_data.value,
                         "label" : button_data.sub_label,
                         "filter" : button_data.filter
                     },
             };
               //if period exist, override the default period.
               if (period) {
                 handler_params.period = period;
               }
               var params = {
                       selectedButton : selectedButton || false,
                     _handler : new hashTagGraphStatsUsageHandler(handler_params)
               };
             var button = new hashTagGraphStatsButton(params);
             return button.domNode;
           },

           /**
            * Create a new chart
            */
           _createNewChart : function(filter, range) {
             this._callRateService(filter, range);
           },

           /**
            * Reload the chart.
            */
           _reload : function () {

           },

           /**
            * Create a line graph.
            * @param valuesx
            * @param valuesy
            */
           _createGraph : function(valuesx, valuesy) {
             dojo.empty(this._graph);
             var graph = this._graph.id;
             if (graph) {
               var option = {
                   id : graph,
                   valuesx : valuesx,
                   valuesy : valuesy,
                   width : 630,
                   radius : 1,
                   height : 280,
               };
               //create a line graph
               this.line(option);
             }
           },

           /**
            * Call a hashtag rate service.
            * @param filter
            */
           _callRateService : function (filter, range) {
             var params = {
                     tagName : this.hashtagName,
                     period : range || this.default_range,
                     filter  : filter || this.default_filter,
                 };
                 var load = dojo.hitch(this, function(data) {
                   /*
                    * Example of range service.
                    * {
                   "error": { },
                   "success": {
                       "statsByRange": [
                           {
                               "label": "3",
                               "value": 3,
                               "sub_label": "Marzo"
                           },
                           {
                               "label": "2",
                               "value": 2,
                               "sub_label": "Febrero"
                           }
                       ]
                   }

               }
                    */
                   var parent = this;
                   var buildGrahpArray = function (data, totalItems) {
                       var array_stats = [[],[]];
                   for (var i = 1; i <= totalItems ; i++) {
                     array_stats[0].push(i);
                     array_stats[1].push(0);
                   }
                   _ENME.log(array_stats);
                      dojo.forEach(data,
                                 dojo.hitch(this,function(item) {
                                 /*
                                  * Object { label="8", value=14, sub_label="Agosto"}
                                  */
                               array_stats[1][item.label] = item.value;
                        }));
                       parent._createGraph(array_stats[0], array_stats[1]);
                   };

                   if ("success" in data) { //TODOOOOO: EL PROBLEMA ESTA AKI NECESITO CREAR MAS DATOS PARA PERIODOS
                      //ENME.log(data.success);
                      var stats = data.success.statsByRange;
                      if (params.period === _ENME.YEAR) {
                        buildGrahpArray(stats, 12);
                      } else if (params.period === _ENME.ALL) {
                        //TODO: pending
                      } else if (params.period === _ENME.DAY) {
                        buildGrahpArray(stats, 24);
                      } else if (params.period === _ENME.WEEK) {
                        buildGrahpArray(stats, 7);
                      } else if (params.period === _ENME.MONTH) {
                      buildGrahpArray(stats, 31);
                      }
                   }
                 });
                 //this.callGET(params, this.getURLService().service('encuestame.service.list.range.hashtag'), load, null, null);
                 this.getURLService().get('encuestame.service.list.range.hashtag', params, load, null, null);
           },

           /**
            * Start the post create cycle.
            */
           postCreate : function() {
             if (this.hashtagName) {
                 dojo.subscribe("/encuestame/hashtag/chart/new", this, this._createNewChart);
                 dojo.subscribe("/encuestame/hashtag/chart/reload", this, this._reload);
                 //create the button
                 this._createButtons(this.default_range);
                 dojo.addOnLoad(dojo.hitch(this, function() {
                   if (typeof(this.default_filter) === 'string') {
                     this._callRateService(this.default_filter, this.default_range);
                   }
                 }));
                 dojo.subscribe(this.channel, this, this._refreshPublish);
             }
           },

           /**
            *
            * @param period
            */
           _refreshPublish : function (period) {
             //ENME.log(period);
             this._callRateService(this.default_filter, period);
             this._createButtons(period);
           }

            });
        });
