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
dojo.provide("encuestame.org.core.commons.support.PanelWipe");

dojo.require("encuestame.org.core.commons.support.Wipe");

/**
 * Panel Wipe Support.
 * @author Picado, Juan juanATencuestame.org
 */
dojo.declare("encuestame.org.core.commons.support.PanelWipe",
            null, {

        /*
         *
         */
        content : null,

        /*
         *
         */
        duration : 200,

        /*
         *
         */
        height : 300,

        /*
         *
         */
        selected : false,

        /*
         *
         */
        constructor: function(/* node */ content, /** title */ title, /* selected by default */ selected) {
            if (content) {
                this.content = content;
            } else {
                throw new Error("content is required");
            }
        },

       /**
        *
        */
       wipeInOne: function() {
           //console.info("connect wipeInOne", this.content);
           dojox.fx.wipeTo({
                node: this.content,
               duration: this.duration,
               height: this.height
           }).play();
       },

       /*
        *
        */
       wipeOutOne : function() {
           //console.info("connect wipeOutOne", this.content);
           if (this.content) {
               dojox.fx.wipeOut({
                   node: this.content,
                  duration: this.duration
               }).play();
           }
       },

        // connect the node with wipe effect
        connect : function(node, functionCall) {
            //console.info("connect with", node);
            if (node) {
                dojo.connect(node, "onclick", dojo.hitch(this, function(event) {
                    //console.info("connect click", node);
                    if (this.selected) {
                        this.wipeOutOne();
                    } else {
                         this.wipeInOne();
                         functionCall();
                    }
                    this.selected =!this.selected;
                    //console.info("connect click", this.selected);
                }));
            }
        },

});
