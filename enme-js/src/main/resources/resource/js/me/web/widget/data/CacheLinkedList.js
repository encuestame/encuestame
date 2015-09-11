 /*
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

/***
 *  Cache a data set with "load more" support. This widget display a short number of items.
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.147
 *  @module data
 *  @namespace Widget
 *  @class CacheLinkedList
 */
define([
     "dojo/_base/declare",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/ui/More",
     "me/core/enme"],
    function(
    declare,
    main_widget,
    more,
    _ENME) {

  return declare(main_widget, {

    /**
    * Internal cache to store items retrieved from dataset.
    */
   items_array : [],

   /**
    * The root property of data retrieved from server.
    * eg: data { error : null, success : { property : { more data ... } }}
    * Could be: poll, tweetpoll, survey.
    */
   property : null,

   /**
    * more widget reference.
    */
   more_widget : null,

   /**
    * enable the link "more" to add more items to the stream, like in facebook.
    */
   enable_more_support : true,


   /**
    *
    * @property
    */
   //items : 0,

   /**
    * Enable the more support, this retrieve next X items from provide service.
    * @param start start list value
    * @param max max values
    * @param node node to append
    * @method
    */
   enableMoreSupport : function(start, max, node) {
       if (node) {
         var channel =  "/encuestame/more/" + this.id;
           var pagination = {_start : start, _maxResults : max };
           this.more_widget = new more({
                       pagination: pagination,
                       channel : channel
           });
           dojo.place(this.more_widget.domNode, node);
           dojo.subscribe(channel, this, dojo.hitch(this, function() {

           }));
       }
   },

   /**
    *
    * @method
    */
   getItems : function(){},

   /**
    *
    * @method
    */
   setItems : function(){},

   /**
    * A service support to retrieve items based on list of parameters.
    */
   loadItems : function(url) {
     var real_url = this.getUrl() || url;
       var load = dojo.hitch(this, function(data) {
           if ("success" in data) {
               //this._empty();
               var items = data.success[this.property];
               this.setItems(items.length);
               if (items && items.length > 0) {
                   dojo.forEach(items, dojo.hitch(this, function(
                           data, index) {
                       if (dojo.isFunction(this.processItem)) {
                           this.items_array.push(this.processItem(data, index));
                       }
                   }));
                   this.showMore();
               } else {
                   this.displayEmptyMessage();
                   this.hideMore();
               }
               this._afterEach();
           } else {
              this.handlerError("error");
           }
       });
       var error = this.handlerError;
       if (real_url) {
         this.getURLService().get(real_url, this.getParams(), load, error , dojo.hitch(this, function() {

         }));
       } else {
         this.handlerError("error");
       }
   },

   /**
    *
    * @method
    */
   hideMore : function() {
      if (this.more_widget) {
         this.more_widget.hide();
       }
   },

   /**
    *
    * @method
    */
   showMore : function() {
      if (this.more_widget) {
         this.more_widget.show();
       }
   },

   /**
    * Display empty message.
    */
   displayEmptyMessage : function() {},

   /*
    *
    */
   _afterEach : function() {},

   /*
    *
    */
   _empty : function() {},

   /*
    *
    */
   handlerError : function(){},

   /**
    * Process a items on successfull server response.
    * Always override by child widgets.
    */
   processItem : function(data, index){},

   /**
    * List of parameters, always override by child widgets.
    */
   getParams : function() {
           return {};
   }

  });
});