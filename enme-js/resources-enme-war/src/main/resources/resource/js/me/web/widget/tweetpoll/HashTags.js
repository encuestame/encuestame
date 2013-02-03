/**
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module TweetPoll.Hashtag
 *  @namespace Widgets
 *  @class Hashtag
 */

define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/HashTagsSuggest",
         "me/web/widget/tweetpoll/HashTagsItem",
         "me/web/widget/tweetpoll/TweetPollCore",
         "me/core/enme",
         "dijit/registry",
         "dojo/text!me/web/widget/tweetpoll/templates/hashtag.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                HashTagsSuggest,
                HashTagsItem,
                TweetPollCore,
                _ENME,
                registry,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, TweetPollCore, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /**
            * suggest widget.
            */
           suggestWidget : null,

           /**
            * tweetpoll id ref.
            */
           tweetPollId : null,

           /**
            * list of items.
            */
           listItems : [],

           /**
            * item selected.
            */
           _itemsSelected : [],

           /**
            * Message for add butotn
            * @property _hashtahButtonLabel
            */
           _hashtahButtonLabel : _ENME.getMessage("button_add"),

           /**
            * pots create life cycle.
            * @method postCreate
            */
           postCreate: function() {
               //create new hashtahg suggest.
               var hashTagWidget = new HashTagsSuggest({label: this._hashtahButtonLabel});

                // the action after push "add" button.
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
                        // check if the selected item (object saved if user select with the mouse or the keyboard) exist
                        if (typeof hashTagWidget.selectedItem === 'undefined' || hashTagWidget.selectedItem === null) {
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
                         } else {
                            var _selected_item = hashTagWidget.selectedItem.data;
                            _selected_item .newValue = false;
                            hashTagWidget.processSelectedItem(_selected_item);
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
            * @method _addHastahToItem
            */
           _addHastahToItem : function(data) {
               var params = {
                       "id" : data.label,
                       "itemId" : this.tweetPollId
              };
              var load = dojo.hitch(this, function(data) {
                  if ("success" in data) {
                    //if fail
                    //{"error":{},"success":{"r":-1}}
                    //if not
                    //{"error":{},"success":{"hashtag":{"id":235,"size":12,"hashTagName":"nica","hits":1}}}
                    var success = data.success;
                    if ("hashtag" in success) {
                      this.addNewHashTag(data.success.hashtag);
                    } else if ("r" in success) {
                      this.infoMesage("Hashtag is emtpy or has invalid characters.");
                    }
                  }
              });
              var error = dojo.hitch(this, function(error) {
                  this.errorMesage(error.message);
                  dojo.publish("/encuestame/tweetpoll/updatePreview");
              });
              this.getURLService().post(['encuestame.service.list.hashtagsAction.getAction',
                      ["tweetpoll", "add"]],
                      params,
                      load,
                      error);
           },

           //block add more items.
           block : function(){
               //dojo.byId("hashTagSuggest_"+this.id).block();
           },

           //unblock items.
           unblock : function(){
               //dojo.byId("hashTagSuggest_"+this.id).unblock();
           },

           /***
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

           /**
            * get list of hashtags.
            */
           getHashTags : function() {
               var hashtags = [];
               dojo.forEach(
                   this.listItems,
                   dojo.hitch(this, function(tag, index) {
                       hashtags.push(tag.data);
                   }));
               return hashtags;
           },

           //new Hash Tag.
           newHashTag : function(data) {
               //console.debug(data);
               var widget = new HashTagsItem(
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

           /**
            * Get Dialog.
            */
           getDialog : function() {
               var dialog = registry.byId("option_" + this.id);
               return dialog;
           },

           /**
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

    });
});