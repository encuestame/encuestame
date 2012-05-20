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
	        //this.createChart();
	        this._createButtons();
	        
	        

            window.onload = function () {
            	var option = {
            			id : 'holder', 
            			valuesx : [1,2,3,4,5],
            			valuesy : [1,2,3,4,5]
            	};
    	        
    	        
            	ENME.graph("line", option)
            	
//            	//var r = Raphael(10, 50, 640, 480),
//                var r = Raphael("holder"),
//                    txtattr = { 
//                		font: "12px sans-serif", color : '#3CB763' };
//                r.text(100, 20, "Hashtag By Use").attr(txtattr);
//                /*
//                 * @param x
//                 * @param y
//                 * @param width
//                 * @param heigth
//                 * @para, valuesx
//                 * @param valuesy
//                 */
//                var lines = r.linechart(14, 14, 750, 280, [1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24], [[3,0,0,0,0,0,15,0,0,1,0,0,0,0,0,0,0,0,0,0,0,10,23]], { 
//                	nostroke: false, 
//                	axis: "0 0 1 1", 
//                	symbol: "circle",
//                	gutter: 20,
//                	colors: ["#66B920"],
//                	shade : false,
//                	width : 1,
//                	smooth: false }).hover(function(){console.log("1", this);}, function(){console.log("2", this);});

//                var lines = r.linechart(10, 10, 300, 220, [[1, 2, 3, 4, 5, 6, 7],[3.5, 4.5, 5.5, 6.5, 7, 8]], [[12, 32, 23, 15, 17, 27, 22], [10, 20, 30, 25, 15, 28]], { nostroke: false, axis: "0 0 1 1", symbol: "circle", smooth: true }).hoverColumn(function () {
//                    this.tags = r.set();
//
//                    for (var i = 0, ii = this.y.length; i < ii; i++) {
//                        this.tags.push(r.tag(this.x, this.y[i], this.values[i], 160, 10).insertBefore(this).attr([{ fill: "#fff" }, { fill: this.symbols[i].attr("fill") }]));
//                    }
//                }, function () {
//                    this.tags && this.tags.remove();
//                });
//                r.linechart(330, 10, 300, 220, x, [y.slice(0, 1e4), y2.slice(0, 1e4), y3.slice(0, 1e4)], { shade: true });
//                r.linechart(10, 250, 300, 220, x, [y, y2, y3], { nostroke: true, shade: true });
//
//                var lines = r.linechart(330, 250, 300, 220, [[1, 2, 3, 4, 5, 6, 7],[3.5, 4.5, 5.5, 6.5, 7, 8]], [[12, 32, 23, 15, 17, 27, 22], [10, 20, 30, 25, 15, 28]], { nostroke: false, axis: "0 0 1 1", symbol: "circle", smooth: true }).hoverColumn(function () {
//                    this.tags = r.set();
//
//                    for (var i = 0, ii = this.y.length; i < ii; i++) {
//                        this.tags.push(r.tag(this.x, this.y[i], this.values[i], 160, 10).insertBefore(this).attr([{ fill: "#fff" }, { fill: this.symbols[i].attr("fill") }]));
//                    }
//                }, function () {
//                    this.tags && this.tags.remove();
//                });
//
//               lines.symbols.attr({ r: 2 });
//               // lines.lines[0].animate({"stroke-width": 2}, 1000);
//                lines.symbols[0].attr({stroke: "#888"});
               // lines.symbols[0][1].animate({fill: "#f00"}, 1000);
                //dojo.byId("holder").appendChild(r.canvas);
            };
        
        
        
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