/**
 * Copyright 2014 encuestame
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

define( [
        "dojo/_base/declare",
        "dojo/on",
        "me/web/widget/options/EmbebedOptions",
        "dijit/Dialog",
        "dijit/_WidgetBase",
        "dijit/_TemplatedMixin",
        "dijit/_WidgetsInTemplateMixin",
        "me/core/main_widgets/EnmeMainLayoutWidget",
        "me/core/enme",
        "dojo/text!me/web/widget/support/templates/link-embebed.html" ],
    function(
        declare,
        on,
        EmbebedOptions,
        Dialog,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        main_widget,
        _ENME,
        template ) {
        return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

            /*
             * Template string.
             */
            templateString: template,

            /**
             *  Embebed data
             */
            data: {
                id: null,
                type: null
            },

            /*
             * I18n message for this widget.
             */
            i18nMessage: {
                open_embebed: _ENME.getMessage("common_open_embebed")
            },

            /**
             *
             */
            postCreate: function() {
                on( this._embebed, "click", dojo.hitch( this, function( e ) {
                    this.stopEvent( e );
                    this._embebed_options.initialize();
                    this._embebed_options_dialog.show();
                }) );
                this._embebed_options.dialogWidget = this._embebed_options_dialog;
            },

            _closeEmbebedDialog: function( e ) {
                this._embebed_options_dialog.hide();
            }

        });
    });
