dojo.provide("encuestame.org.core.commons.tweetPoll.HashTags");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.Dialog");
dojo.require("encuestame.org.core.shared.utils.Suggest");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.HashTags",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/hashtag.html"),

        widgetsInTemplate: true,

        suggestWidget : null,

        listItems : [],

        postCreate: function() {
            this.suggestWidget = dijit.byId("hashTagSuggest_"+this.id);
            if(this.suggestWidget){
                this.suggestWidget.processSelectedItem = dojo.hitch(this, function(data){
                    //console.info("Processing Item Selected ...", data);
                    this.addNewHashTag(data);
                });
            }
            var hashTagWidget = new encuestame.org.core.commons.tweetPoll.HashTagsSuggest({});
            var node = dojo.byId("hashTagSuggest_"+this.id);
            //console.debug("create suggest", node);
            if (this._suggest){
               //console.debug("create suggest", hashTagWidget.domNode);
               //console.debug("create suggest", hashTagWidget);
               this._suggest.appendChild(hashTagWidget.domNode);
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
            var widget = new encuestame.org.core.commons.tweetPoll.HashTagsItem(
                    {
                     data : data,
                     parentWidget : this
                     });
            this.listItems.push(widget);
            this._items.appendChild(widget.domNode);
            dojo.publish("/encuestame/tweetpoll/updatePreview");
            dojo.publish("/encuestame/tweetpoll/autosave");
        },
        //Get Dialog
        getDialog : function(){
            var dialog = dijit.byId("option_"+this.id);
            return dialog;
        },
        _removeItem : function(event){
            dojo.stopEvent(event);
            var i = dojo.indexOf(this.listItems, this.getDialog().item);
            if(i != -1){
                this.listItems.splice(i, 1);
                dojo.destroy(this.getDialog().item.domNode);
                this.getDialog().hide();
                dojo.publish("/encuestame/tweetpoll/updatePreview");
            } else {
                console.error("Error on remove Item");
            }
        }
    }
);
/**
 * HashTag Item.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.HashTagsItem",
        [dijit._Widget, dijit._Templated],{
        //template
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/hashtagItem.html"),
        //widgets in template
        wigetsInTemplate: true,
         //data
        data : null,

        parentWidget : null,

        postCreate : function(){
            console.debug("new HashTag", this.data);
        },

        _options : function(event){
            var dialog = this.parentWidget.getDialog();
            dialog.item = this;
            dialog.show();
        }
});


dojo.declare(
    "encuestame.org.core.commons.tweetPoll.HashTagsSuggest",
    [encuestame.org.core.shared.utils.Suggest],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/suggest.html")

});

