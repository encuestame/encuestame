dojo.provide("encuestame.org.class.commons.tweetPoll.HashTags");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("encuestame.org.class.shared.utils.Suggest");

dojo.declare(
    "encuestame.org.class.commons.tweetPoll.HashTags",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.tweetPoll", "templates/hashtag.inc"),

        widgetsInTemplate: true,

        suggestWidget : null,

        postCreate: function() {
            this.suggestWidget = dijit.byId("hashTagSuggest_"+this.id);
            if(this.suggestWidget){
                this.suggestWidget.processSelectedItem = dojo.hitch(this, function(data){
                    console.info("Processing Item Selected ...", data);
                    this.addNewHashTag(data);
                });
            }
        },
        //Add New Hash Tag.
        addNewHashTag : function(hashTag){
            console.debug("Add New HashTag", hashTag);
        }
    }
);
