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
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.146
 *  @module
 *  @namespace
 *  @class
 */
define( [
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/web/widget/stream/HashTagInfo",
    "me/core/enme",
    "dojo/text!me/web/widget/stats/templates/stats.html" ], function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    hashTagInfo,
    _ENME,
    template ) {
  return declare( [ _WidgetBase,
      _TemplatedMixin,
      main_widget,
      _WidgetsInTemplateMixin ], {

    // Template string.
    templateString: template,

    /**
     * Service json string.
     */
    _service: "encuestame.service.list.generic.stats",

    /**
     * TypeGeneric of stats.
     */
    typeGeneric: "",

    /**
     *
     * @property hashtagName
     */
    hashtagName: "",

    /**
     * Item id.
     */
    generic: "",

    /*
     *
     */
    postCreate: function() {
      this._callGenericStats();
    },

    /**
     *
     */
    _callGenericStats: function() {
      var parent = this,
      params = {
        id: this.generic,
        filter: this.typeGeneric
      },
      load = function( data ) {
        if ("success" in data ) {
          parent._buildStats( data.success.generic );
        }
      };
      this.getURLService().get( this._service, params,  load, null, null );
    },

    /**
     *  Build the stats table.
     *  @param {Object} Array of stats.
     */
    _buildStats: function( stats ) {

      // Summary: list of stats
          // average: 0
          // created_at: "2 months ago"
          // created_by: null
          // hits: 47
          // like_dislike_rate: 0
      this._voteCounter.innerHTML = stats.hits || 0;
      this._vote.innerHTML = ( stats.average || 0 )  + " average";
      this._badge.innerHTML = stats.hits || 0  + " rate";

//      If (stats.like_dislike_rate >= 0) {
//        dojo.addClass(this._badge, 'badge-success');
//      } else {
//        dojo.addClass(this._badge, 'badge-important');
//      }
    },

    /**
     * Create a row.
     * @param {String} header
     * @param {String} value
     */
    _createRow: function( header, value ) {
      var tr = dojo.create("tr");
      var label = dojo.create("td", null, tr );
      label.innerHTML = header;
      label.innerHTML = value;
      this._rows.appendChild( tr );
    }

  });
});
