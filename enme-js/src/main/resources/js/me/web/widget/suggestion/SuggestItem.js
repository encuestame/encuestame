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
 *  @module Suggestion
 *  @namespace Widgets
 *  @class SuggestItem
 */
define( [
         "dojo",
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
         "me/core/enme",
         "dojo/text!me/web/widget/suggestion/templates/suggestItem.html" ],
        function(
                dojo,
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                hashTagInfo,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

        // Template string.
        templateString: template,

        /**
         *
         * @property
         */
         data: null,

         /**
          *
          * @property
          */
         parentWidget: null,

        /**
         * Define if is selected.
         * @property
         */
         _selected: false,

         /**
          *
          * @method postCreate
          */
        postCreate: function() {},

        /**
         * Select this item.
         * @method selected
         */
        selected: function() {
            dojo.addClass( this.domNode, "suggest-selected");
            this._selected = true;
        },

        /**
         * Un select this item
         * @method unSelect
         */
        unSelect: function() {
            dojo.removeClass( this.domNode, "suggest-selected");
            this._selected = false;
        },

        /**
         * Check if is selected
         * @method
         */
        isSelected: function() {
            return this._selected;
        },

       /**
        * Select item
        * @method _selectItem
        */
        _selectItem: function( event ) {
            dojo.stopEvent( event );
            this._selected = false;
            this.parentWidget.selectedItem = this.data;
            this.parentWidget.hide();
            this.processItem( this.data );
        }
    });
});
