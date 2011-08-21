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
dojo.provide("encuestame.org.core.commons.support.DnD");

dojo.require("dojo.dnd.Manager");
dojo.require("dojo.dnd.Container");
dojo.require("dojo.dnd.Selector");
dojo.require("dojo.dnd.Source");

/**
 * Dnd Support.
 * @author Picado, Juan juanATencuestame.org
 * @since 21/08/2011
 */
dojo.declare("encuestame.org.core.commons.support.DnD", null, {

        node : null,

        sourceDndWidget : null,

        accept : [],

        copyOnly : false,

        selfCopy : false,

        selfAccept : true,

        withHandles : true,

        autoSync : true,

        isSource : true,

        constructor: function() {
            this.node = null;
        },

        enableDndSupport : function(node) {
            this.node = node;
            var source  = new dojo.dnd.Source(this.node, {
                accept: this.accept,
                copyOnly: this.copyOnly,
                selfCopy : this.selfCopy,
                selfAccept: this.selfAccept,
                withHandles : this.withHandles,
                autoSync : this.autoSync,
                isSource : this.isSource
                //creator: this.dndNodeCreator
                });
                this.sourceDndWidget = source;
                console.debug("enabled DND Source on ", this.node);
        },

        /*
         * dnd node creator.
         */
        dndNodeCreator : function (item, hint) {
            //console.debug("hint", hint);
            //console.debug("item", item);
            var tr = document.createElement("div");
            tr.innerHTML = "Item Dropped...";
            return {node: tr, data: item, type: "tweetpoll"};
        }

        /**
         * TODO: in this place should be move all DnD code repeated in another widgets.
         * TweetpollList.
         * DashboardLayout.
         * FolderActions.
         */

});
