dojo.provide("encuestame.org.core.commons.hashtags.HashTagGraph");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.chart.RaphaelSupport");
dojo.require('encuestame.org.core.commons.stream.HashTagInfo');
dojo.require("dojo.number");

dojo.declare(
    "encuestame.org.core.commons.hashtags.HashTagGraph",
    [encuestame.org.main.EnmeMainLayoutWidget, encuestame.org.core.commons.chart.RaphaelSupport],{
    
    /**
     * Template path.
     */
    templatePath: dojo.moduleUrl("encuestame.org.core.commons.hashtags", "template/hashTagGraph.html"),
    
    /**
     * Hashtag name.
     */
    hashtagName : "",

    /*
     *
     */
    _createNewChart : function(chart) {
        //console.info("creating new chart....", chart);
    },
    
    /**
     * Create all buttons.
     */
    _createButtons : function() {
    	 var params = {
    	     tagName : this.hashtagName,
    	     filter  : "HASHTAGRATED", // 
    	 }
    	 var load = dojo.hitch(this, function(data) {
    		 if ("success" in data) {
    			 var hashTagButtonStats = data.success.hashTagButtonStats;
    			 usage_by_item = hashTagButtonStats['usage_by_item'],
    			 total_usage_by_social_network = hashTagButtonStats['total_usage_by_social_network'],
    			 total_hits = hashTagButtonStats['total_hits'],
    			 usage_by_votes = hashTagButtonStats['usage_by_votes'];
    			 if (this._stat) { 
	    			 dojo.empty(this._stat);
	    			 this._stat.appendChild(this._createAButton({title : usage_by_item.label, value : usage_by_item.value , sub_label : usage_by_item.sub_label}));
	    			 this._stat.appendChild(this._createAButton({title : total_usage_by_social_network.label, value : total_usage_by_social_network.value , sub_label : total_usage_by_social_network.sub_label}));
	    			 this._stat.appendChild(this._createAButton({title : total_hits.label, value : total_hits.value , sub_label : total_hits.sub_label}));
	    			 this._stat.appendChild(this._createAButton({title : usage_by_votes.label, value : usage_by_votes.value , sub_label : usage_by_votes.sub_label}));
    			 }
    		 }
    	 });  	 
    	 this.callGET(params, encuestame.service.list.rate.buttons, load, null, null);
    },
    
    /**
     * Create a stats button.
     * @param {Object} 
     */
    _createAButton : function(button_data) {
		var button = new encuestame.org.core.commons.hashtags.HashTagGraphStatsButton({
		    _handler : new encuestame.org.core.commons.hashtags.HashTagGraphStatsUsageHandler({
		        _test : {
		            "title" : button_data.title,
		            "value" : button_data.value,
		            "label" : button_data.sub_label
		        },
		    })
		});
		return button.domNode;
    },

    /*
     *
     */
    postCreate : function(){
    	if (this.hashtagName != null) {
	        dojo.subscribe("/encuestame/hashtag/chart/new", this, this._createNewChart);
	        dojo.subscribe("/encuestame/hashtag/chart/reload", this, this._reload);
	        this.createChart();
	        this._createButtons();        
        }
    }

});


dojo.declare(
    "encuestame.org.core.commons.hashtags.HashTagGraphStatsButton",
    [encuestame.org.main.EnmeMainLayoutWidget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.hashtags", "template/hashTagGraphStatsButton.html"),

    /*
     *
     */
    _handler : null,

    /*
     *
     */
    selectedButton : false,


    /*
     *
     */
    postCreate : function(){
        if (this._handler != null) {
            dojo.subscribe("/encuestame/hashtag/buttons", this, this._switchButton);
            dojo.connect(this._button, "onclick", dojo.hitch(this, function(event) {
                this._handler.onClick();
            }));
            this._handler.init(this);
            if (this.selectedButton) {
                dojo.addClass(this.domNode, "selected");
                //this._handler.switchSelected();
            }
            this._button.appendChild(this._handler.domNode);
        }
    },

    /*
     * review the state of the button.
     */
    _switchButton : function(ref) {
        if ( this.domNode == ref.domNode) {
            dojo.addClass(ref.domNode, "selected");
            ref.selectedButton = true;
        } else {
            dojo.removeClass(this.domNode, "selected");
            this.selectedButton = false;
        }
    }
});


dojo.declare(
        "encuestame.org.core.commons.hashtags.HashTagGraphStatsUsageHandler",
        [encuestame.org.main.EnmeMainLayoutWidget],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.hashtags", "template/hashTagGraphStatsUsageHandler.html"),

        /*
         *
         */
        _test : {
            "title" : "Usage",
            "value" : 500000,
            "label" : "times.",
            "typeChart" : "usage"
        },


        _buttonRef : null,

        /*
         *
         */
        postCreate: function(){

        },

        /*
         *
         */
        init : function(ref) {
            this._buttonRef = ref;
            this._dt1.innerHTML = this._test.title;
            this._dt2.innerHTML = dojo.number.format(this._test.value, {places: 0});
            this._dt3.innerHTML = this._test.label;
        },

        /*
         *
         */
        switchSelected : function() {

        },

        /*
         *
         */
        onClick : function(){
            dojo.publish("/encuestame/hashtag/buttons", [this._buttonRef]);
            dojo.publish("/encuestame/hashtag/chart/new", [this._test.typeChart]);
        },

});