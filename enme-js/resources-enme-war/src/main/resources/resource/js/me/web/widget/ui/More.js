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
 *  @module UI
 *  @namespace Widget
 *  @class More
 */
define([
    "dojo/_base/declare",
    "dojo/_base/lang",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/core/enme",
    "dojo/text!me/web/widget/ui/templates/more.html" ], function(
      declare,
      lang,
      _WidgetBase,
      _TemplatedMixin,
      _WidgetsInTemplateMixin,
      main_widget,
      _ENME,
      template) {
  return declare([ _WidgetBase,
                   _TemplatedMixin,
                    main_widget,
                   _WidgetsInTemplateMixin ], {

    /**
     * template string.
     * @property templateString
     */
    templateString : template,

    /**
     *
     * @property pagination
     */
    pagination : {
       end : 10,
       start : 0
    },

    /**
     *
     * @method
     */
    more_max : 5,

    /**
     *
     * @method
     */
    parentWidget : 0,

   /**
    * save the connect event
    */
    _event : null,

    /**
     * Merge external object with more pagination
     * @property
     */
    merge : function (params) {
      return lang.mixin(this.pagination, params);
    },

    /**
     *
     * @property
     */
    postCreate : function() {
      this._event = dojo.connect(this._stream, "onclick", dojo.hitch(this, function(event) {
        if (dojo.isFunction(this.loadItems)) {
           this.pagination.start = this.parentWidget.items;
           this.pagination.end = this.pagination.start + this.more_max;
           this.loadItems();
           this.pagination.start = this.pagination.start + this.pagination.end;
        }
      }));
    },

   /**
    * Remove onclick the load items event
    */
    removeEvent : function() {
        if (this._event !== null) {
            dojo.disconnect(this._event);
        }
    },

    /**
     *
     */
    loadItems : function() {
        // override method
    },

    /**
     *
     */
    hide : function() {
      dojo.addClass(this.domNode, "hidden");
    },

    /**
     *
     */
    show : function() {
      dojo.removeClass(this.domNode, "hidden");
    }

  });
});