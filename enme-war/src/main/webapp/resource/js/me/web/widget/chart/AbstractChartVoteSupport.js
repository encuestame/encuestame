//dojo.require('dojox.timing');
define([
     "dojo/_base/declare",
     "me/web/widget/chart/EncuestamePieChart",
     "me/core/enme"],
    function(
    declare,
    encuestamePieChart,
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
     * timer
     */
    _timer : null,

    /**
     * Define if is completed.
     */
    completed : false,

    /**
     *
     * @param id
     * @param results
     */
    createChart : function(id, results, type) {
      this.widgetChart = new encuestamePieChart(
            id,
            /** array of results **/ results,
            /** **/ 110);
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
        this.widgetChart._buildSeries();
        this.widgetChart.render();
    },

    /**
     * Enable reload votes.
     */
    enableVoteTime : function(liveNode) {
      dojo.addOnLoad(dojo.hitch(this, function() {
            if (this.enableLiveVotes) {
                this.setTimer();
                //liveNode.innerHTML = "ON LIVE: Results refreshed every "+(this.delay/1000)+" seconds";
                dojo.removeClass(liveNode, "defaultDisplayHide");
            } else{
                dojo.addClass(liveNode, "defaultDisplayHide");
            }
        }));
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