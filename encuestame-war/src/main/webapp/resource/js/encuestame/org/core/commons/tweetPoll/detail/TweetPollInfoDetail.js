dojo.provide("encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll.detail", "templates/tweetPollInfoDetail.html"),
        //widget
        widgetsInTemplate: true


});