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
dojo.provide("encuestame.org.core.shared.utils.FolderOperations");

dojo.require("dojo.dnd.Source");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.InlineEditBox");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.main.EnmeMainLayoutWidget');

/**
 * This is a abstract class to provide folder support.
 */
dojo.declare(
    "encuestame.org.core.shared.utils.FolderOperations",
    [encuestame.org.main.EnmeMainLayoutWidget],{

        /*
         * folder context.
         */
        folderContext : null,

        /*
         * context.
         */
        _context : ["tweetpoll", "poll", "survey"],

        /*
         * actions.
         */
        _actions : ["create", "update", "move", "list", "remove"],

        /*
         *
         */
        _ready : false,

        /*
         *
         */
        postCreate : function() {
             if (this.folderContext != null) {
                 this._ready = true;
             } else {
                 this._showError("folder context missing");
             }
        },


        /*
         * load folders.
         */
        _callFolderService : function(onLoad, params, action, enableStorFormat) {
            var load = dojo.hitch(this, onLoad );
            var error = dojo.hitch(this,  function(error) {
                this._showError(error, null);
            });
            dojo.mixin(params, { store : enableStorFormat });
            if (this._ready) {
                var url = this._getContextUrlService(this._actions[action]);
                encuestame.service.xhrGet(
                           url,
                           params,
                           load,
                           error);
            }
        },

        /*
         *
         */
        _showError : function(node, error){
        },

        /*
         * get action.
         */
        getAction : function(action) {
            var position = dojo.indexOf(this._actions, action);
            //console.info("getAction position", position);
            if (position == -1) {
                console.error("invalid action");
            } else {
                return position;
            }
        },

        /*
         * get service by context.
         */
        _getContextUrlService : function(type) {
           if (this.folderContext == this._context[0]) {
               return this._serviceAction(type, this._context[0]);
           } else if (this.folderContext == this._context[1]) {
               return this._serviceAction(type,this._context[1]);
           } else if (this.folderContext == this._context[2]) {
               return this._serviceAction(type, this._context[2]);
           }
        },

        /*
         * get service by action.
         */
        _serviceAction : function(type, context){
            if (type == this._actions[0]) {
                return encuestame.service.folder.create(context);
           } else if (type == this._actions[1]) {
               return encuestame.service.folder.update(context);
           } else if (type == this._actions[2]) {
               return encuestame.service.folder.move(context);
           } else if (type == this._actions[3]) {
               return encuestame.service.folder.list(context);
           } else if (type == this._actions[4]) {
               return encuestame.service.folder.remove(context);
           }
        }
});