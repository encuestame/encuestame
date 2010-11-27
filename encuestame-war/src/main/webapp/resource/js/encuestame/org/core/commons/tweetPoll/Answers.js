dojo.provide("encuestame.org.core.commons.tweetPoll.Answers");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.Answers",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/answer.inc"),

        widgetsInTemplate: true,

        postCreate: function() {

        }
    }
);
