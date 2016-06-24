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

define( [
         "dojo/_base/declare",
         "dojo/on",
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
                on,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                HashTagsSuggest,
                HashTagsItem,
                TweetPollCore,
                _ENME,
                registry,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, TweetPollCore, _WidgetsInTemplateMixin ], {

          // Template string.
            templateString: template,

            /**
            * Suggest widget.
            */
           suggestWidget: null,

           /**
            * Tweetpoll id ref.
            */
           tweetPollId: null,

           /**
            * List of items.
            */
           listItems: [],

           /**
            * Item selected.
            */
           _itemsSelected: [],

           /**
            * Message for add butotn
            * @property _hashtahButtonLabel
            */
           _hashtahButtonLabel: _ENME.getMessage("button_add"),

           /**
            * Pots create life cycle.
            * @method postCreate
            */
           postCreate: function() {

               //Create new hashtahg suggest.
	           var self = this;
               this.hashTagWidget = new HashTagsSuggest({
	               label: this._hashtahButtonLabel
               });

                // The action after push "add" button.
                this.hashTagWidget.processSelectedItemButton = dojo.hitch( this, function() {
                    if ( self.hashTagWidget.textBoxWidget && self.hashTagWidget.addButton ) {
                        var newValue = { id: null, label:"", newValue: true };
                        newValue.label = dojo.trim( self.hashTagWidget.textBoxWidget.get("value") );
                        self.hashTagWidget.selectedItem = newValue;
                        if ( newValue.label !== "" ) {
                            self.hashTagWidget.processSelectedItem( self.hashTagWidget.selectedItem );
                        }
                        self.hashTagWidget.hide();
                    }
                });

                //The action if user push on space bar.
                self.hashTagWidget.processSpaceAction =  dojo.hitch( this, function() {
                    if ( self.hashTagWidget.textBoxWidget ) {

                        // Check if the selected item (object saved if user select with the mouse or the keyboard) exist
                        if ( typeof self.hashTagWidget.selectedItem === "undefined" || self.hashTagWidget.selectedItem === null ) {
                            var currentText = dojo.trim( self.hashTagWidget.textBoxWidget.get("value") );
                            var added = false;
                            if ( self.hashTagWidget._itemStored.length > 0 ) {
                                dojo.forEach(
                                    self.hashTagWidget._itemStored,
                                    dojo.hitch( this, function( data, index ) {
                                        if ( !added ) {
                                            if ( currentText.toLowerCase() == data.i.hashTagName.toLowerCase() ) {

                                                //Console.debug("adding existing item", data.i);
                                                self.hashTagWidget.processSelectedItem({ id:data.i.id, label:data.i.hashTagName, newValue: false });
                                                self.hashTagWidget.hide();
                                                added = true;
                                            } else { // TODO: this loop is invalid, always is "else" after first loop, works because
                                                     //  the unique results always === 1
                                               // console.debug("adding existing NEW item",{id:null, label:currentText, newValue: true} );
                                               if ( currentText !== "" ) {
                                                 self.hashTagWidget.processSelectedItem({ id:null, label:currentText, newValue: true });
                                                 self.hashTagWidget.hide();
                                                 added = true;
                                               }
                                            }
                                       }
                                    }) );
                             } else {
                                 self.hashTagWidget.processSelectedItem({ id:null, label: currentText, newValue: true });
                                 self.hashTagWidget.hide();
                             }
                         } else {
                            var _selected_item = self.hashTagWidget.selectedItem.data;
                            _selected_item .newValue = false;
                            self.hashTagWidget.processSelectedItem( _selected_item );
                            self.hashTagWidget.hide();
                         }

                      //Console.debug(self.hashTagWidget._itemStored);
                    }
                });

               //Var node = dojo.byId("hashTagSuggest_"+this.id);
               if ( this._suggest ) {
                   this._suggest.appendChild( self.hashTagWidget.domNode );
               }
               this.suggestWidget = self.hashTagWidget;
               if ( this.suggestWidget ) {

                   //Action  triggered after action selected
                   this.suggestWidget.processSelectedItem = dojo.hitch( this, function( data ) {
                       this._addHastahToItem( data );                   //
                       if ( data.id !== null ) {
                           this.suggestWidget.exclude.push( data.id );
                       }
                   });
               }
               this.enableBlockTweetPollOnProcess();
           },

           /**
            * Add hashtag item.
            * @method _addHastahToItem
            */
           _addHastahToItem: function( data ) {
               var params = {
                       "id": data.label,
                       "itemId": this.tweetPollId
              };
              var load = dojo.hitch( this, function( data ) {
                  if ("success" in data ) {

                    //If fail
                    //{"error":{},"success":{"r":-1}}
                    //if not
                    //{"error":{},"success":{"hashtag":{"id":235,"size":12,"hashTagName":"nica","hits":1}}}
                    var success = data.success;
                    if ("hashtag" in success ) {
                      this.addNewHashTag( data.success.hashtag );
                    } else if ("r" in success ) {
                      this.infoMesage("Hashtag is emtpy or has invalid characters.");
                    }
                  }
              });
              var error = dojo.hitch( this, function( error ) {
                  this.errorMesage( error.message );
                  dojo.publish("/encuestame/tweetpoll/updatePreview");
              });
              this.getURLService().post( [ "encuestame.service.list.hashtagsAction.getAction",
                      [ "tweetpoll", "add" ]],
                      params,
                      load,
                      error );
           },

           //Block add more items.
           block: function() {

               //Dojo.byId("hashTagSuggest_"+this.id).block();
           },

           //Unblock items.
           unblock: function() {

               //Dojo.byId("hashTagSuggest_"+this.id).unblock();
           },

           /***
            * Add New Hash Tag.
            * @param hashTag hashtag item
            */
           addNewHashTag: function( hashTag ) {
               if ( hashTag && this.listItems ) {
                   this.printHashTag( hashTag );
               }
           },

           //Print hashTag
           printHashTag: function( data ) {
               this.newHashTag( data );
           },

           /**
            * Get list of hashtags.
            * @method getHashTags
            * @param key decide if return an element of the objetc o all object
            */
           getHashTags: function( key ) {
               var hashtags = [];
               dojo.forEach(
                   this.listItems,
                   dojo.hitch( this, function( tag, index ) {
                       hashtags.push( key ? tag.data[ key ] : tag.data );
                   }) );
               return hashtags;
           },

           /**
            * Add new Hash Tag.
            * @method newHashTag
            */
           newHashTag: function( data ) {
               var widget = new HashTagsItem(
                       {
                        label: data.hashTagName,
                        data: data,
                        parentWidget: this
                        });
               this.listItems.push( widget );
               this._itemsSelected.push( data.id );
               this._items.appendChild( widget.domNode );
               dojo.publish("/encuestame/tweetpoll/updatePreview");
               dojo.publish("/encuestame/tweetpoll/autosave");

               on( widget.domNode, "click", dojo.hitch( this, function( event ) {
                  dojo.stopEvent( event );
                  this.createConfirmDialog("Do you want remove this Hashtag?", null, dojo.hitch( this, function() {
                    this._removeItem( widget );
                  }) );
              }) );

           },

           /**
            * Get Dialog.
            */
           getDialog: function() {
               var dialog = registry.byId("option_" + this.id );
               return dialog;
           },

           /**
            * Remove hashtag.
            */
           _removeItem: function( item ) {
               var i = dojo.indexOf( this.listItems, item );
               if ( i != -1 ) {
                   this.listItems.splice( i, 1 );
                   dojo.destroy( item.domNode );
                   dojo.publish("/encuestame/tweetpoll/updatePreview");
               } else {
                   this.errorMessage("Error on remobe Hashtag");
               }
           }

    });
});
