dojo.provide("encuestame.org.core.gadget.TweetPollVotes");

dojo.require("encuestame.org.core.gadget.Gadget");

dojo.declare(
    "encuestame.org.core.gadget.TweetPollVotes",
    [encuestame.org.core.gadget.Gadget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.gadget", "template/activity.html"),

});