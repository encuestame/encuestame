dojo.provide("encuestame.org.core.gadget.TweetPollVotes");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.gadget.TweetPollVotes",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.gadget", "template/activity.html"),

});