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
dojo.provide("encuestame.org.core.commons.poll.detail.PollChartDetail");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
dojo.require('encuestame.org.core.commons.dashboard.chart.EncuestamePieChart');
dojo.require('encuestame.org.core.commons.chart.AbstractChartVoteSupport');

/**
 * Widget to display a poll chart detail.
 */
dojo.declare(
    "encuestame.org.core.commons.poll.detail.PollChartDetail",
    [encuestame.org.main.EnmeMainLayoutWidget, 
     encuestame.org.core.commons.chart.AbstractChartVoteSupport],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll.detail", "templates/detail_poll.html"),
        
        /**
         * 
         */
        pollId : null,
        
        /**
         * Owner of the tweetpoll.
         */
        username : "",                
        
        /**
         * Post create.
         */
        postCreate : function(){
        	this._loadVotes();
        	this.enableVoteTime(this._live);
        },
        
        /**
         * Override _loadVotes.
         * Load current votes for a poll.
         */
        _loadVotes : function() {
            var response = dojo.hitch(this, function(dataJson) {
                if ("success" in dataJson) {
                    var votes = dataJson.success.votesResult,
                    totalVotes = 0;
                    if (votes.length > 0) {
                    var results = [];
                    dojo.forEach(
                            votes,
                            dojo.hitch(this, function(data, index) {
                            	var votes = data.answer_votes == null ? 0 : data.answer_votes;
                                var answer = [data.answer.answers, (votes), data.color];
                                results.push(answer);
                                totalVotes += votes;
                                //dojo.publish("/encuestame/poll/detail/answer/reload", [data.id, [votes, data.percent]]);
                    }));
                    //clean chart node.
                    dojo.empty(this._chart);
                    //check if votes are 0
                    if (totalVotes > 0) {
	                    var id = this.id+"_chart";	                    
	                    //create new chart
	                    this.createChart(id, results, null);
	                    //render the chart
	                    this.render();
	                    } else {
	                        this._noVotes();
	                    }
                    } else {
                    	console.info("NO VOTES");
                    }
                  }
                //dojo.publish("/encuestame/poll/detail/answer/reload");
              });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet( encuestame.service.list.poll.getVotes(this.username), { id : this.pollId}, response, error);
        }
});