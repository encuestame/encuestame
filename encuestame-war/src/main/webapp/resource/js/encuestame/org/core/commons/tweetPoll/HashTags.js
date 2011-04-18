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

        _itemsSelected : [],

        postCreate: function() {
             var hashTagWidget = new encuestame.org.core.commons.tweetPoll.HashTagsSuggest({});
             hashTagWidget.processSelectedItemButton = dojo.hitch(this, function(){
                 console.debug("customized add button");
                 if(hashTagWidget.textBoxWidget && hashTagWidget.addButton){
                     var newValue = {id:null, label:"", newValue: true};
                     newValue.label = hashTagWidget.textBoxWidget.get("value");
                     hashTagWidget.selectedItem = newValue;
                     if(newValue.label != ''){
                         hashTagWidget.processSelectedItem(hashTagWidget.selectedItem);
                     }
                     hashTagWidget.hide();
                 }
             });
             //if user click on space bar.
             hashTagWidget.processSpaceAction =  dojo.hitch(this, function(){
                 if (hashTagWidget.textBoxWidget) {

                 }
             });
             var node = dojo.byId("hashTagSuggest_"+this.id);
             if (this._suggest){
                this._suggest.appendChild(hashTagWidget.domNode);
             }
            this.suggestWidget = hashTagWidget;
            if(this.suggestWidget){
                this.suggestWidget.processSelectedItem = dojo.hitch(this, function(data){
                    console.info("Processing Item Selected ...", data);
                    this.addNewHashTag(data);
                    if (data.id != null) {
                        this.suggestWidget.exclude.push(data.id);
                    }
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
            console.debug(data);
            var widget = new encuestame.org.core.commons.tweetPoll.HashTagsItem(
                    {
                     data : data,
                     parentWidget : this
                     });
            this.listItems.push(widget);
            this._itemsSelected.push(data.id);
            this._items.appendChild(widget.domNode);
            dojo.publish("/encuestame/tweetpoll/updatePreview");
            dojo.publish("/encuestame/tweetpoll/autosave");
        },

        /*
         * Get Dialog.
         */
        getDialog : function(){
            var dialog = dijit.byId("option_"+this.id);
            return dialog;
        },

        /*
         * remove hashtag.
         */
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

