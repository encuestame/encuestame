dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPoll");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.require("encuestame.org.core.commons.tweetPoll.Answers");
dojo.require("encuestame.org.core.commons.tweetPoll.HashTags");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.TweetPoll",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpoll.inc"),

        widgetsInTemplate: true,

        postCreate: function() {

        }
    }
);
