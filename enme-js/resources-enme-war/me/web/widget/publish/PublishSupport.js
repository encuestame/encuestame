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
 *  @module Publish
 *  @namespace Widget
 *  @class PublishSupport
 */
define([
         "dojo/_base/declare",
         "dojo/Deferred",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         'dijit/form/Button',
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/utils/ContextSupport",
         "me/web/widget/publish/PublishPanelItem",
         "me/web/widget/publish/PublishEmailSupport",
         "me/web/widget/publish/PublishSocialSupport",
         "me/web/widget/publish/PublishEmbebedSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/publish/templates/publishSupport.html" ],
        function(
                declare,
                Deferred,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Button,
                main_widget,
                ContextSupport,
                PublishPanelItem,
                PublishEmailSupport,
                PublishSocialSupport,
                PublishEmbebedSupport,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, ContextSupport, _WidgetsInTemplateMixin], {

         /*
          * template string.
          */
         templateString : template,

         /*
          *
          */
         text_message : "Your item has been created successfully",

         /*
          *
          */
         cssClass : "succesfully",

         /**
          * Define if close button must be appear or not
          */
         done_button : false,

         /*
          *
          */
         item : { id: null, name : null , url : null },

         /*
          *
          */
         dialogContext : null,

         /**
          *
          * @property
          */
         _buttons : [],

         /*
          *
          */
         postMixInProperties: function() {
             this.text_message = "Your "+ this.context + " <b>" + this.item.name + "</b> has been created successfully.";
         },

         /*
          *
          */
         postCreate : function() {
             this.initializeSocial(false);
             //this.initializeEmail(false);
             //this.initializeEmbebed(true);
             if (this.done_button) {
               dojo.connect(this._close, "onClick", dojo.hitch(this, function() {
                   document.location.href = _ENME.config('contextPath') + "/user/" + this.context + "/list";
               }));
             } else {
                dojo.destroy(this._close.domNode);
                dojo.destroy(this._footer);                
             }
             dojo.subscribe("/encuestame/support/publish/buttons/unselect", this, "unselect");
         },

         /**
          *
          * @property
          */
         removeTitle: function() {
            dojo.destroy(this._title);
         },

         /**
          *
          * @method
          */
         unselect : function () {
            dojo.forEach(this._buttons, dojo.hitch(this,function(item) {
                dojo.removeClass(item, 'li-pb-selected');
            }));
         },

         /*
          * Create a button.
          * @param widget widget reference
          * @para title the button title
          * @param selected define if is selected by default
          */
         _createButton : function(widget, title, selected) {
             //  <li><a href="#">Social Networks</a>
             var li = dojo.create("li");
              dojo.connect(li, "onclick", dojo.hitch(this, function(){
                  dojo.publish('/encuestame/support/panel/unselect');
                  dojo.publish("/encuestame/support/panel/remote/select", [widget]);
                  dojo.publish('/encuestame/support/publish/buttons/unselect');
                  dojo.addClass(li, 'li-pb-selected');
              }));
             var a = dojo.create("a");
             a.innerHTML = title;
             li.appendChild(a);
             if (!selected) {
                dojo.addClass(li, 'li-pb-selected');
             }
             this._buttons.push(li);
             this._ul.appendChild(li);
         },

         /*
          * Create a wipe panel.
          * @param widget the widget to display in the panel
          * @param defaultDisplayHide define if visible by default
          * @param title title of the button
          */
         _createWipePanel : function(widget, defaultDisplayHide, title) {
            var panel = new PublishPanelItem(
                    {
                     contentWidget : widget,
                     context : this.context,
                     title : title,
                     defaultDisplayHide : defaultDisplayHide
                    });
            this._createButton(panel, title, defaultDisplayHide);
            this._detail.appendChild(panel.domNode);

         },

         /*
          * Initialize the email support.
          * @param defaultDisplayHide
          */
         initializeEmail : function(defaultDisplayHide) {
             var email = new PublishEmailSupport(
                     {
                         context : this.context,
                         itemId : this.item.id
                     }
                     );
             this._createWipePanel(email, defaultDisplayHide, "Email");
         },

         /**
          * Initialize the social picker support.
          * @param defaultDisplayHide
          * @methodinitializeSocial
          */
         initializeSocial : function(defaultDisplayHide) {
             var social = new PublishSocialSupport({
                 context : this.context,
                 itemId : this.item.id
             });
             this._createWipePanel(social, defaultDisplayHide, "Social Networks");
         },

         /*
          * Initialize the embebed support.
          * @param defaultDisplayHide
          */
         initializeEmbebed : function(defaultDisplayHide) {
             var embebed = new PublishEmbebedSupport(
                 {
                     context : this.context,
                     itemId : this.item.id,
                     name : this.item.name
                 });
             this._createWipePanel(embebed, defaultDisplayHide, "Javascript");
         }

    });
});