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

        listItems : [],

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
            if(hashTag && this.listItems){
                this.printHashTag(hashTag);
            }
        },
        //print hashTag
        printHashTag : function(data){
            this.newHashTag(data);
        },
        //new Hash Tag.
        newHashTag : function(data){
            var widget = new encuestame.org.class.commons.tweetPoll.HashTagsItem({data:data});
            this.listItems.push(widget);
            this._items.appendChild(widget.domNode);
            console.debug("Add New HashTag", this.listItems);
        }
    }
);
/**
 * HashTag Item.
 */
dojo.declare(
        "encuestame.org.class.commons.tweetPoll.HashTagsItem",
        [dijit._Widget, dijit._Templated],{
        //template
        templatePath: dojo.moduleUrl("encuestame.org.class.commons.tweetPoll", "templates/hashtagItem.inc"),
        //widgets in template
         wigetsInTemplate: true,
         //data
         data : null,

         postCreate : function(){
            console.debug("new HashTag", this.data);
        }
});
