dojo.provide("encuestame.org.core.commons.hashtags.HashTagGraph");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.chart.RaphaelSupport");
dojo.require('encuestame.org.core.commons.stream.HashTagInfo');
dojo.require("dojo.number");

dojo.declare(
    "encuestame.org.core.commons.hashtags.HashTagGraph",
    [encuestame.org.main.EnmeMainLayoutWidget, encuestame.org.core.commons.chart.RaphaelSupport],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.hashtags", "template/hashTagGraph.html"),

    /*
     *
     */
    _createNewChart : function(chart) {
        console.info("creating new chart....", chart);
    },

    /*
     *
     */
    postCreate : function(){
        dojo.subscribe("/encuestame/hashtag/chart/new", this, this._createNewChart);
        dojo.subscribe("/encuestame/hashtag/chart/reload", this, this._reload);
        this.createChart();
        var button1 = new encuestame.org.core.commons.hashtags.HashTagGraphStatsButton({
            _handler : new encuestame.org.core.commons.hashtags.HashTagGraphStatsUsageHandler({
                _test : {
                    "title" : "Usage",
                    "value" : 500000,
                    "label" : "times."
                },
            }),
            selectedButton : true
        });
        this._stat.appendChild(button1.domNode);
        var button2 = new encuestame.org.core.commons.hashtags.HashTagGraphStatsButton({
            _handler : new encuestame.org.core.commons.hashtags.HashTagGraphStatsUsageHandler({
                _test : {
                    "title" : "Social Network Use",
                    "value" : 1500,
                    "label" : "Tweets"
                },
            })
        });
        this._stat.appendChild(button2.domNode);
        var button3 = new encuestame.org.core.commons.hashtags.HashTagGraphStatsButton({
            _handler : new encuestame.org.core.commons.hashtags.HashTagGraphStatsUsageHandler({
                _test : {
                    "title" : "Visited",
                    "value" : 67342,
                    "label" : "times."
                },
            })
        });
        this._stat.appendChild(button3.domNode);
        var button4 = new encuestame.org.core.commons.hashtags.HashTagGraphStatsButton({
            _handler : new encuestame.org.core.commons.hashtags.HashTagGraphStatsUsageHandler({
                _test : {
                    "title" : "Used on",
                    "value" : 64232,
                    "label" : "votes."
                },
            })
        });
        this._stat.appendChild(button4.domNode);
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
            "typeChart" : "usage",
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
        init : function(ref){
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