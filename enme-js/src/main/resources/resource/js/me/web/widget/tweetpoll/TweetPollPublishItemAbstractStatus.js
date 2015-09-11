/**
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
 **/

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.146
 *  @module TweetPoll
 *  @namespace Widget
 *  @class TweetPollPublishItemAbstractStatus
 */
define([
    "dojo/_base/declare",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/form/Button",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/core/enme" ],
    function(
        declare,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        Button,
        main_widget,
        _ENME) {
        return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

           /**
            * Metadaat
            * @property
            */
            metadata : null,

           /**
            * Parent status widget.
            * @property
            */
            parentStatusWidget : null,

            /**
             * i18N Message.
             * @property
             */
            i18nMessage : {},

            /**
             * Post create dojo life cycle.
             * @method postCreate
             */
            postCreate : function () {
                this.inherited(arguments);
            }
        });
    });