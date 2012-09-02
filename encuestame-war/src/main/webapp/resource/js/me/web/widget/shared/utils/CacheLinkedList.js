/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.shared.utils.CacheLinkedList");
dojo.require("encuestame.org.core.shared.utils.More");

/**
 * Cache a data set with "load more" support.
 * This widget display a short number of items and the  
 * @author juanATencuestame.org
 * @since 2012-06-10 23:46:29
 */
dojo.declare("encuestame.org.core.shared.utils.CacheLinkedList", null, {

    /**
     * Internal cache to store items retrieved from datase.
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
     * enable the more support, this retrieve next X items from provide service.
     */
    enableMoreSupport : function(/** start list value **/ start, /** max values **/ max, /** node to append **/ node) {
        if (node) {
        	var channel =  "/encuestame/more/"+this.id;
            var pagination = {_start : start, _maxResults : max };
            this.more_widget = new encuestame.org.core.shared.utils.More({
                        pagination: pagination,
                        channel : channel
            });
            dojo.place(this.more_widget.domNode, node);
            dojo.subscribe(channel, this, dojo.hitch(this, function() {
            	
            }));
        }
    },

    /**
     * A service support to retrieve items based on list of parameters.
     */
    loadItems : function(url) {
    	var real_url = this.getUrl() || url;
        var load = dojo.hitch(this, function(data) {
            //console.info("load 2 data", data);
            if ("success" in data) {
                this._empty();
                //console.debug("pro", data.success[this.property]);
                var items = data.success[this.property];
                if (items && items.length > 0) {
                    dojo.forEach(items, dojo.hitch(this, function(
                            data, index) {
                        //console.info("for each", data);
                        if (dojo.isFunction(this.processItem)) {
                            this.items_array.push(this.processItem(data, index));
                        }
                    }));
                    if (this.more_widget) {
                    	this.more_widget.show();
                    }
                } else {
                    this.displayEmptyMessage();
                    if (this.more_widget) {
                    	this.more_widget.hide();
                    }
                }
                this._afterEach();
            } else {
               this.handlerError("error");
            }
        });
        var error = this.handlerError;
        //console.info("url", url);
        //console.info("this.getParams", this.getParams());
        if (real_url) {
        	encuestame.service.xhrGet(real_url, this.getParams(), load, error);
        } else {
        	this.handlerError("error");
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