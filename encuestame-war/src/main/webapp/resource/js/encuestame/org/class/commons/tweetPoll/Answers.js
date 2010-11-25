dojo.provide("encuestame.org.class.commons.tweetPoll.Answers");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.class.commons.tweetPoll.Answers",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.tweetPoll", "templates/answer.inc"),

        widgetsInTemplate: true,

        postCreate: function() {

        }
    }
);
