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
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module UI
 *  @namespace Widgets
 *  @class HelpContextual
 */
define([
         "dojo/_base/declare",
         "dojo/dom-construct",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
         "me/core/enme",
         "dojo/text!me/web/widget/ui/templates/help_context.html" ],
        function(
                declare,
                domConstruct,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                hashTagInfo,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

       /**
         * @property  template string
         */
        templateString : template,

        /**
         * Define if the initial status of
         * @property status
         */
        status : true,

        /**
         * Default Messages.
         * @property messages
         */
        messages: [_ENME.getMessage("common_hide"), _ENME.getMessage("common_show")],


        /**
         * i18n Message.
         */
        i18nMessage : {
	        tp_help_question : _ENME.getMessage("tp_help_question")
        },

        /**
         * @method PostCreate.
         */
        postCreate : function() {
              this.switchStatus();
              dojo.forEach(this.list_messages, dojo.hitch(this, function(entry, i) {
                  if (typeof entry === 'string') {
                      var li = domConstruct.create("li"),
                      h2 = domConstruct.create("h2");
                      h2.innerHTML = entry;
                      domConstruct.place(h2, li);
                      domConstruct.place(li, this._help_item);
                  }
              }));

              // to collapse the help
              dojo.connect(this._help_switch, "onclick", dojo.hitch(this, function(event) {
                    if (this.status) {
                        this._help_switch.innerHTML = this.messages[1];
                    } else {
                        this._help_switch.innerHTML = this.messages[0];
                    }
                    this.status = !this.status;
                    this.switchStatus();
              }));
        },

        /**
         * Switch the status of the helper.
         * @method switchStatus
         */
        switchStatus : function () {
              if (this.status) {
                  dojo.removeClass(this._help_content, 'hidden');
              } else {
                  dojo.addClass(this._help_content, 'hidden');
              }
        },

        /**
         * Hide the help.
         * @method hide
         */
        hide : function () {
            this.status = !this.status;
            this.switchStatus();
        },

        /**
         * Default messages to display as title.
         * @property title
         */
        title : _ENME.getMessage("tp_help_question"),

        /**
         * list of message to display.
         * @property list_messages
         */
        list_messages : [
          "help1",
          "help2",
          "help3"
        ]

    });
});