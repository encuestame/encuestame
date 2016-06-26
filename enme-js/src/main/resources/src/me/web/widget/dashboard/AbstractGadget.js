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
 *  @module Dashboard
 *  @namespace Widget
 *  @class Gadget
 */
define( [
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme",
     "dojo/text!me/web/widget/dashboard/template/emtpy-gadget.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    _ENME,
     template ) {
        return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

    /**
     * Template string.
     * @property templateString
     */
    templateString: template,

    /**
     *
     * @method
     */
    postCreate: function() {
        try {
            this.initGadget();
        } catch ( error ) {
            this.errorLoadGadget( error );
        }
    },

    /**
     *
     * @method
     */
    errorLoadGadget: function() {

    },

    /**
     *
     * @method
     */
    loadingGadget: function() {

    },

    /**
     *
     * @method
     */
    minimizeGadget: function() {

    }

    });
});
