dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dojox.widget.Dialog");
dojo.require("dojox.form.Rating");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPoll");
dojo.require("encuestame.org.core.commons.dashboard.chart.EncuestamePieChart");
dojo.require("encuestame.org.core.shared.utils.YesNoWidget");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.TweetPollListDetail",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollListDetail.inc"),
        //widget
        widgetsInTemplate: true,
        //data
        data: null,

        widgetChart : null,

        typeChart : ['Bars', 'Pie', 'Lines'],
        //post create
        postCreate : function(){
            dojo.subscribe("/encuestame/tweetpoll/detail/update", this, "updateDetail");
            dojo.subscribe("/encuestame/tweetpoll/detail/chart/render", this, "render");
        },

        /**
         * Update Detail.
         */
        updateDetail : function(data){
            console.debug("data 1", data);
            if(data != null){
                this.loadContent(data);
            } else {
                this.error();
            }
        },

        error : function(){
            console.error("error");
        },

        /**
         * Load Content.
         */
        loadContent : function(data){
            this.data = data;
            this._title.innerHTML = this.data.questionBean.questionName;
            this.displayChart(this.typeChart[1], {});
            //Build Detail.
            dojo.empty(this._detailItems);
            this.addDetail(this.builDetailRow("Public Link", this.createTextContent("http://www.google.es3")));
            this.addDetail(this.builDetailRow("Created Date", this.createTextContent(this.data.captcha)));
            this.addDetail(this.builDetailRow("Captcha", this.addYesNoWidget(this.data.captcha)));
            this.addDetail(this.builDetailRow("Allow Live Results", this.addYesNoWidget(this.data.allowLiveResults)));
            this.addDetail(this.builDetailRow("Allow Resume Live Results", this.addYesNoWidget(this.data.resumeLiveResults)));
            //allowRepatedVotes
        },

        addDetail : function(node){
            this._detailItems.appendChild(node);
        },

        /**
         * Yes / No.
         */
        addYesNoWidget : function(value){
            var widget = new encuestame.org.core.shared.utils.YesNoWidget({});
            return widget.domNode;
        },

        createTextContent : function(text){
            var textData = dojo.doc.createElement('div');
            textData.innerHTML = text;
            return textData;
        },

        /**
         * Build Detail Row.
         */
        builDetailRow : function(labelText, dataContet){
            var rowDetail = dojo.doc.createElement('div');
            dojo.addClass(rowDetail, "rownDetail");
            var label = dojo.doc.createElement('div');
            dojo.addClass(label, "label");
            var labelItem = dojo.doc.createElement('label');
            labelItem.innerHTML = labelText;
            label.appendChild(labelItem);
            rowDetail.appendChild(label);
            var data = dojo.doc.createElement('div');
            dojo.addClass(data, "data");
            data.appendChild(dataContet);
            rowDetail.appendChild(data);
            return rowDetail;
        },

        /**
         * Display Data.
         */
        displayChart : function(type, data){
            dojo.empty(this._chart);var widget;
            var id = this.id+"_chart";
            if(type == this.typeChart[0]){
                this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(id);
            } else if(type == this.typeChart[1]){
                this.widgetChart = new encuestame.org.core.commons.dashboard.chart.EncuestamePieChart(id);
            }
            this.render();
        },

        /**
         * Render.
         */
        render : function(){
            this.widgetChart.render();
        }
});