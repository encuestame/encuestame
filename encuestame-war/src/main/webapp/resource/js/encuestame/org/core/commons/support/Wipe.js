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
dojo.provide("encuestame.org.core.commons.support.Wipe");

dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.Highlight");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.themes.MiamiNice");
dojo.require("dojox.charting.widget.Legend");

/**
 * Wipe Support.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
dojo.declare("encuestame.org.core.commons.support.Wipe", null, {

        node : null,
        duration : 200,
        height : 300,

        /*
         *
         */
        constructor: function(node, duration, heigth) {
            //if (node == null) {
                this.node = node;
                this.duration == null ? this.duration : duration;
                this.heigth == null ? this.heigth : heigth;
            //} else {
            //    throw new Error("node is required");
            //}
        },

        /*
        *
        */
       wipeInOne: function() {
           dojox.fx.wipeTo({
                node: this.node,
               duration: this.duration,
               height: this.height
           }).play();
       },

       /*
        *
        */
       wipeOutOne : function() {
           if (this.node) {
               dojox.fx.wipeOut({
                   node: this.node,
                  duration: this.duration
               }).play();
           }
       }
});
