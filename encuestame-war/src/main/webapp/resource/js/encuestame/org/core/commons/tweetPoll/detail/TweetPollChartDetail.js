dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollChartDetail.html"),
        //widget
        widgetsInTemplate: true


});