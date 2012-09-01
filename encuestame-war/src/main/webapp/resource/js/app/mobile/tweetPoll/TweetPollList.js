dojo.provide("encuestame.org.mobile.tweetPoll.TweetPollList");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dojox.widget.Dialog");
dojo.require("dojox.form.Rating");
dojo.require("dojo.fx");

dojo.require("encuestame.org.mobile.tweetPoll.TweetPollList");
dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardPie");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollListDetail");

dojo.declare(
    "encuestame.org.mobile.tweetPoll.TweetPollList",
    [encuestame.org.core.commons.tweetPoll.TweetPollList], {
    templatePath: dojo.moduleUrl("encuestame.org.mobile.tweetPoll", "templates/tweetPollList.html")

});