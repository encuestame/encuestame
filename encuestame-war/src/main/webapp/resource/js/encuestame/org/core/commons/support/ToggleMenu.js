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
dojo.provide("encuestame.org.core.commons.support.ToggleMenu");

dojo.require("dojox.charting.Chart2D");
dojo.require("dojox.charting.plot2d.Pie");
dojo.require("dojox.charting.action2d.Highlight");
dojo.require("dojox.charting.action2d.MoveSlice");
dojo.require("dojox.charting.action2d.Tooltip");
dojo.require("dojox.charting.themes.MiamiNice");
dojo.require("dojox.charting.widget.Legend");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 * Toggle Menu Support.
 * @author Picado, Juan juanATencuestame.org
 * @since 14/01/12
 */
dojo.declare("encuestame.org.core.commons.support.ToggleMenu", [encuestame.org.main.EnmeMainLayoutWidget], {

   /*
    *
    */
   _openBox : false,

   /*
    *
    */
   _classReplace : "",

   /*
    * Add switch menu support to node based on a defined event.
    * @param event
    * @param node
    */
   addMenuSupport : function(node) {
       dojo.connect(node, "onclick", this, this._switchMenu);
   },

   /*
    * Switch the menu based on boolean value.
    * @event set by dojo.
    */
   _switchMenu: function(event) {
       console.debug("_switchMenu", this._classReplace);
       console.debug("_switchMenu", this._menu);
       console.debug("_switchMenu", this._openBox);
       dojo.stopEvent(event);
       if (this._openBox) {
           dojo.removeClass(this._menu, this._classReplace);
           this._openBox = false;
       } else {
           dojo.addClass(this._menu, this._classReplace);
           this._openBox = true;
       }
   }
});
