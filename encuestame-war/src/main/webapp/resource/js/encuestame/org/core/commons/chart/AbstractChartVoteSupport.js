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
dojo.provide("encuestame.org.core.commons.chart.AbstractChartVoteSupport");

dojo.require('dojox.timing');
dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");

dojo.declare("encuestame.org.core.commons.chart.AbstractChartVoteSupport", null, {

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
    	this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(
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

    _loadVotes : function(){},
});
