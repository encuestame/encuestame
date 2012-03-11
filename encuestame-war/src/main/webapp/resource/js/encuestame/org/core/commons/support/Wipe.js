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

        /*
         * node.
         */
        node : null,
        /*
         * duration.
         */
        duration : 200,
        /*
         * height.
         */
        height : 300,

        /*
         * default status.
         */
        _open : false,

        /*
         * default group.
         */
        group : "default",

        /*
         * id.
         */
        id : "",

        /*
         * Constructor of wipe.
         */
        constructor: function(node, duration, height, group, id) {
            dojo.subscribe("/encuestame/wipe/close", this, "_close");
            this.node = node;
            this.duration = (duration == null) ? this.duration : duration;
            this.height = (height == null) ? this.height : height;
            this.group = (group == null) ? this.group : group;
            this.id = (id == null) ? this.id : id;
        },

       /*
        * on wite in.
        */
       wipeInOne: function() {
           dojox.fx.wipeTo({
                node: this.node,
               duration: this.duration,
               height: this.height
           }).play();
       },

       /*
        * close the wipe.
        */
       _close : function(id, group) {
           if (id !== this.id && group === this.group) {
               this.wipeOutOne();
           }
       },

       /*
        * on wipe out.
        */
       wipeOutOne : function() {
           if (this.node) {
               dojox.fx.wipeOut({
                   node: this.node,
                  duration: this.duration
               }).play();
           }
       },

       /*
        * provide toggle suport to wipe panel.
        */
       togglePanel : function() {
           if (this._open) {
               this.wipeOutOne();
            } else {
               this.wipeInOne();
            }
            this._open =!this._open;
       }
});
