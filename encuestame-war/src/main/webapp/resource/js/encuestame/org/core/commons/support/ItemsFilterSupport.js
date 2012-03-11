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
dojo.provide("encuestame.org.core.commons.support.ItemsFilterSupport");

dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require("encuestame.org.core.commons.support.Wipe");

/**
 * Filter Items Support.
 * @author Picado, Juan juanATencuestame.org
 */
dojo.declare("encuestame.org.core.commons.support.ItemsFilterSupport",
     [encuestame.org.main.EnmeMainLayoutWidget], {

     templatePath: dojo.moduleUrl("encuestame.org.core.commons.support", "templates/filters.html"),

     optionsWidget : { search : null, filter : null, order : null, social : null, votes : null},

     _wipe : { height : 145, duration : 100},

     postCreate : function(){
         dojo.connect(this._search, "onclick", dojo.hitch(this, this._openSearch));
         dojo.connect(this._filter, "onclick", dojo.hitch(this, this._openFilter));
         dojo.connect(this._order, "onclick", dojo.hitch(this, this._openOrder));
         dojo.connect(this._social, "onclick", dojo.hitch(this, this._openSocial));
         dojo.connect(this._votes, "onclick", dojo.hitch(this, this._openVotes));
         this.optionsWidget.search = new encuestame.org.core.commons.support.Wipe(this._search_o, this._wipe.duration, this._wipe.height, "tp-options", "1");
         this.optionsWidget.filter = new encuestame.org.core.commons.support.Wipe(this._filter_o, this._wipe.duration, this._wipe.height, "tp-options", "2");
         this.optionsWidget.order = new encuestame.org.core.commons.support.Wipe(this._order_o, this._wipe.duration, this._wipe.height, "tp-options", "3");
         this.optionsWidget.social = new encuestame.org.core.commons.support.Wipe(this._social_o, this._wipe.duration, this._wipe.height, "tp-options", "4");
         this.optionsWidget.votes = new encuestame.org.core.commons.support.Wipe(this._votes_o, this._wipe.duration, this._wipe.height, "tp-options", "5");
     },

     /*
      *
      */
     _openSearch : function(event) {
         this.optionsWidget.search.togglePanel();
         dojo.publish("/encuestame/wipe/close", [this.optionsWidget.search.id, "tp-options"]);
      },

     /*
      *
      */
     _openFilter : function(event) {
         this.optionsWidget.filter.togglePanel();
         dojo.publish("/encuestame/wipe/close", [this.optionsWidget.filter.id, "tp-options"]);
      },

     /*
      *
      */
     _openOrder : function(event) {
         this.optionsWidget.order.togglePanel();
         dojo.publish("/encuestame/wipe/close", [this.optionsWidget.order.id, "tp-options"]);
      },

     /*
      *
      */
     _openSocial : function(event) {
         this.optionsWidget.social.togglePanel();
         dojo.publish("/encuestame/wipe/close", [this.optionsWidget.social.id, "tp-options"]);
      },

     /*
      *
      */
     _openVotes : function(event) {
         this.optionsWidget.votes.togglePanel();
         dojo.publish("/encuestame/wipe/close", [this.optionsWidget.votes.id, "tp-options"]);
      }

});
