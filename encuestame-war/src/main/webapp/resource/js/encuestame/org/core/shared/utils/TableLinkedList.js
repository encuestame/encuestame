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
dojo.provide("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.core.shared.utils.More");

/**
 * Table Linked List Support to create a table with extensible large datasets, with folder and "load more"
 * support, this widget always extensible and useful to create dinamic administrative interfaces.
 * @author juanATencuestame.org
 * @since 2011-10-23 23:46:29
 */
dojo.declare("encuestame.org.core.shared.utils.TableLinkedList", null, {

    /**
     * Internal cache to store items retrieved from datase.
     */
    items_array : [],

    /**
     * Enables folder support.
     */
    folder_support : true,

    /**
     * The scope of folder, required parameter.
     * Could be: poll, tweetpoll, survey.
     */
    folder_scope : null,

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
     * is in use?
     */
    enable_more_items : true,

    /**
     * enable the more support, this retrieve next X items from provide service.
     */
    enableMoreSupport : function(/** start list value **/ start, /** max values **/ max, /** node to append **/ node) {
        if (node) {
            var pagination = {_start : start, _maxResults : max };
            this.more_widget = new encuestame.org.core.shared.utils.More({
                        pagination: pagination
            });
            dojo.place(this.more_widget.domNode, node);
        }
    },

    /**
     * Enable Folder Support.
     */
    enableFolderSupport : function() {
         if (this.folder_support) {
             var folder = new encuestame.org.core.shared.utils.FoldersActions({folderContext: this.folder_scope});
             this._folder.appendChild(folder.domNode);
         }
    },


    /**
     * A service support to retrieve items based on list of parameters.
     */
    loadItems : function(url) {
        var load = dojo.hitch(this, function(data) {
            //console.info("load 2 data", data);
            if ("success" in data) {
                this._empty();
                //console.debug("pro", data.success[this.property]);
                dojo.forEach(data.success[this.property], dojo.hitch(this, function(
                        data, index) {
                    //console.info("for each", data);
                    if (dojo.isFunction(this.processItem)) {
                        this.items_array.push(this.processItem(data, index));
                    }
                }));
                this._afterEach();
            } else {
                ///console.warn("no success");
            }
        });
        var error = this.handlerError;
        //console.info("url", url);
        //console.info("this.getParams", this.getParams());
        encuestame.service.xhrGet(url, this.getParams(), load, error);
    },

    /*
     *
     */
    _afterEach : function() {
    },

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