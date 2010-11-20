dojo.provide("encuestame.org.class.commons.tweetPoll.TweetPoll");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.require("encuestame.org.class.commons.tweetPoll.Answers");
dojo.require("encuestame.org.class.commons.tweetPoll.HashTags");

dojo.declare(
    "encuestame.org.class.commons.tweetPoll.TweetPoll",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.tweetPoll", "template/tweetpoll.inc"),

        widgetsInTemplate: true,

        postCreate: function() {

        }
    }
);
