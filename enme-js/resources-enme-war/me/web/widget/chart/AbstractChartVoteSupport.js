//dojo.require('dojox.timing');
define([
     "dojo/_base/declare",
     "me/web/widget/chart/EncuestamePieChart",
     "me/web/widget/chart/EnMeLineChart",
    "me/web/widget/chart/EnMeBarChart",
     "me/core/enme"],
    function(
    declare,
    EncuestamePieChart,
    EnMeLineChart,
    EnMeBarChart,
    _ENME) {

  return declare(null, {

        widgetChart : null,

        /**
         * AbstractChartVoteSupport.
         */
        constructor: function() {},

        /**
         * Timer object.
         */
        _timer : null,

      /**
       *
       */
        question : "",

        /**
         * Display percents instead names.
         */
        percents : false,

        /**
         * Default delay.
         */
        delay : 31000,

        /**
         * Enable live votes.
         */
        enableLiveVotes : true,

        /**
         * Define if is completed.
         */
        completed : false,

      /**
       * Enum with the available list of charts
       */
       type_chart : {
            PIE : "PIE",
            BAR : "BAR"
       },

      /**
       * default type of chart selected
       */
       _defaultChart : "PIE",

        /**
         *
         * @param id
         * @param results
         */
        createChart : function(id, results, type) {
            if (type === this.type_chart.PIE) {
                this.widgetChart = new EncuestamePieChart(
                    id,
                    results,
                    110,
                    this.question);
            } else if (type === this.type_chart.BAR) {
                this.widgetChart = new EnMeBarChart(
                    id,
                    results,
                    110,
                    this.question);
            }
          return this.widgetChart;
        },

        /**
         *
         */
        _noVotes : function() {
            console.info('|== NO VOTES ==|');
        },


        /**
         * Render.
         */
        render : function(){
            //this.widgetChart._buildSeries();
            //this.widgetChart.render();
        },

        /**
         * Enable reload votes.
         */
        enableVoteTime : function(liveNode) {
          dojo.addOnLoad(dojo.hitch(this, function() {
                if (this.enableLiveVotes) {
                    // /this.setTimer();
                    // Disabled because dojo timing need to be migrated to amd modules
                    // http://dojotoolkit.org/reference-guide/1.8/dojox/timing.html
                    //liveNode.innerHTML = "ON LIVE: Results refreshed every "+(this.delay/1000)+" seconds";
                    dojo.removeClass(liveNode, "defaultDisplayHide");
                } else{
                    dojo.addClass(liveNode, "defaultDisplayHide");
                }
            }));
        },

      /**
       *
       * @param e
       * @private
       */
      _getLineChart : function(e) {
          console.log(e);
          e.preventDefault();
          this._defaultChart = this.type_chart.BAR;
          this._loadVotes();
      },

      _widgetDialog : function(e) {

      },

      /**
       *
       * @param e
       * @private
       */
      _getPieChart : function(e) {
          e.preventDefault();
          this._defaultChart = this.type_chart.PIE;
          this._loadVotes();
      },


        /**
         * set timer to reload votes.
         */
        setTimer : function(){
            var father = this;
            this._timer = new dojox.timing.Timer(this.delay);
            this._timer.onTick = function() {
                if (!father.completed) {
                father._loadVotes();
                } else {
                  father._timer.stop();
                }
            };
            this._timer.onStart = function() {
            };
            this._timer.start();
        },

        _loadVotes : function(){}

  });
});