dojo.provide("encuestame.org.core.commons.tweetPoll.HashTags");

dojo.require("dijit.form.Form");
dojo.require("dijit.Dialog");
dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.core.shared.utils.Suggest");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.tweetPoll.TweetPollCore");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.HashTags",
    [encuestame.org.main.EnmeMainLayoutWidget, encuestame.org.core.commons.tweetPoll.TweetPollCore],{

        /*
         * template.
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/hashtag.html"),

        /*
         * suggest widget.
         */
        suggestWidget : null,

        /*
         * tweetpoll id ref.
         */
        tweetPollId : null,

        /*
         * list of items.
         */
        listItems : [],

        /*
         * item selected.
         */
        _itemsSelected : [],

        _hashtahButtonLabel : "Add Hashtag",

        /*
         * pots create life cycle.
         */
        postCreate: function() {
            //create new hashtahg suggest.
            var hashTagWidget = new encuestame.org.core.commons.tweetPoll.HashTagsSuggest({label: this._hashtahButtonLabel});
             //the action after push "add" button.
             hashTagWidget.processSelectedItemButton = dojo.hitch(this, function() {
                 if (hashTagWidget.textBoxWidget && hashTagWidget.addButton) {
                     var newValue = {id : null, label :"" , newValue : true};
                     newValue.label = dojo.trim(hashTagWidget.textBoxWidget.get("value"));
                     hashTagWidget.selectedItem = newValue;
                     if (newValue.label != '') {
                         hashTagWidget.processSelectedItem(hashTagWidget.selectedItem);
                     }
                     hashTagWidget.hide();
                 }
             });

             //the action if user push on space bar.
             hashTagWidget.processSpaceAction =  dojo.hitch(this, function() {
                 if (hashTagWidget.textBoxWidget) {
                     var currentText = dojo.trim(hashTagWidget.textBoxWidget.get("value"));
                     var added = false;
                     if (hashTagWidget._itemStored.length > 0) {
                         dojo.forEach(
                             hashTagWidget._itemStored,
                             dojo.hitch(this, function(data, index) {
                                 if (!added) {
                                     if (currentText.toLowerCase() == data.i.hashTagName.toLowerCase()){
                                         //console.debug("adding existing item", data.i);
                                         hashTagWidget.processSelectedItem({id:data.i.id, label:data.i.hashTagName, newValue: false});
                                         hashTagWidget.hide();
                                         added = true;
                                     } else { // TODO: this loop is invalid, always is "else" after first loop, works because
                                              //  the unique results always === 1
                                        // console.debug("adding existing NEW item",{id:null, label:currentText, newValue: true} );
                                        if (currentText != '') {
                                          hashTagWidget.processSelectedItem({id:null, label:currentText, newValue: true});
                                          hashTagWidget.hide();
                                          added = true;
                                        }
                                     }
                                }
                             }));
                      } else {
                          hashTagWidget.processSelectedItem({id:null, label: currentText, newValue: true});
                          hashTagWidget.hide();
                      }
                   //console.debug(hashTagWidget._itemStored);
                 }
             });
            //var node = dojo.byId("hashTagSuggest_"+this.id);
            if (this._suggest) {
                this._suggest.appendChild(hashTagWidget.domNode);
            }
            this.suggestWidget = hashTagWidget;
            if (this.suggestWidget) {
                //action  triggered after action selected
                this.suggestWidget.processSelectedItem = dojo.hitch(this, function(data) {
                    //console.info("Processing Item Selected ...", data);
                    this._addHastahToItem(data);                   //
                    if (data.id != null) {
                        this.suggestWidget.exclude.push(data.id);
                    }
                });
            }
            this.enableBlockTweetPollOnProcess();
        },

        /**
         * Add hashtag item.
         */
        _addHastahToItem : function(data) {
            var params = {
                    "id" : data.label,
                    "itemId" : this.tweetPollId
           };
           var load = dojo.hitch(this, function(data) {
               if ("success" in data) {
                   this.addNewHashTag(data.success.hashtag);
               }
           });
           var error = dojo.hitch(this, function(error) {
               this.errorMesage(error.message);
           });
           encuestame.service.xhrPostParam(
                   encuestame.service.list.hashtagsAction.getAction("tweetpoll", "add"), params, load, error);
        },

        //block add more items.
        block : function(){
            //dojo.byId("hashTagSuggest_"+this.id).block();
        },

        //unblock items.
        unblock : function(){
            //dojo.byId("hashTagSuggest_"+this.id).unblock();
        },

        /**
         * Add New Hash Tag.
         * @param hashTag hashtag item
         */
        addNewHashTag : function(hashTag) {
            if (hashTag && this.listItems) {
                this.printHashTag(hashTag);
            }
        },

        //print hashTag
        printHashTag : function(data) {
            this.newHashTag(data);
        },

        /*
         * get list of hashtags.
         */
        getHashTags : function() {
            var hashtags = [];
            dojo.forEach(
                this.listItems,
                dojo.hitch(this, function(tag, index) {
                    hashtags.push(tag.data);
                }));
            //console.debug("hashtags", hashtags);
            return hashtags;
        },

        //new Hash Tag.
        newHashTag : function(data) {
            console.debug(data);
            var widget = new encuestame.org.core.commons.tweetPoll.HashTagsItem(
                    {
                     label : data.hashTagName,
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
        _removeItem : function(event) {
            dojo.stopEvent(event);
            var i = dojo.indexOf(this.listItems, this.getDialog().item);
            if (i != -1) {
                this.listItems.splice(i, 1);
                dojo.destroy(this.getDialog().item.domNode);
                this.getDialog().hide();
                dojo.publish("/encuestame/tweetpoll/updatePreview");
            } else {
                console.error("error on remove Item");
            }
        }
    }
);

/**
 * HashTag Item.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.HashTagsItem",
        [encuestame.org.main.EnmeMainLayoutWidget],{
        //template
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/hashtagItem.html"),
        //widgets in template
        wigetsInTemplate: true,

        /**
         * the body of hashtag.
         */
        data : null,

        /**
         * the label of the hashtag.
         */
        label : null,

        /**
         * Parent widget reference.
         */
        parentWidget : null,

        /**
         *
         */
        postCreate : function() {
            //console.debug("new HashTag", this.label);
        },

        /**
         *
         * @param event
         */
        _options : function(event){
            var dialog = this.parentWidget.getDialog();
            dialog.item = this;
            dialog.show();
        }
});


dojo.declare(
    "encuestame.org.core.commons.tweetPoll.HashTagsSuggest",
    [encuestame.org.core.shared.utils.Suggest],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/suggest.html"),

        block : function(){
        },

        unblock : function(){
        }
});