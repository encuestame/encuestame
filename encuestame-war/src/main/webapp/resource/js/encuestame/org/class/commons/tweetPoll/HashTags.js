dojo.provide("encuestame.org.class.commons.tweetPoll.HashTags");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("encuestame.org.class.shared.utils.Suggest");

dojo.declare(
    "encuestame.org.class.commons.tweetPoll.HashTags",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.tweetPoll", "template/hashtag.inc"),

        widgetsInTemplate: true,

        postCreate: function() {

        }
    }
);
