define([
         "dojo/_base/declare",
         "dojo/Deferred",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
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
         message : "Your (item) has been created successfully.",

         /*
          *
          */
         messageClass : "succesfully",

         /*
          *
          */
         item : { id: null, name : null , url : null },

         /*
          *
          */
         dialogContext : null,

         /*
          *
          */
         postMixInProperties: function() {
             this.message = "Your "+ this.context + " <b>"+this.item.name+"</b> has been created successfully.";
         },

         /*
          *
          */
         postCreate : function() {
             this.initializeSocial(false);
             this.initializeEmail(true);
             this.initializeEmbebed(true);
             //console.info("ITEM", this.item);
             dojo.connect(this._close, "onClick", dojo.hitch(this, function() {
                 // console.info("closing dialog..", this.dialogContext);
                 // this.dialogContext.hide(); //TODO: destroy dialog after close.
                 document.location.href = _ENME.config('contextPath') + "/user/" + this.context + "/list";
             }));
         },

         /*
          * Create a button.
          * @param widget
          * @para title
          */
         _createButton : function(widget, title) {
             //  <li><a href="#">Social Networks</a>
             var li = dojo.create("li");
              dojo.connect(li, "onclick", dojo.hitch(this, function(){
                  dojo.publish("/encuestame/support/panel/remote/select", [widget]);
              }));
             var a = dojo.create("a");
             a.href = "#";
             a.innerHTML = title;
             li.appendChild(a);
             this._ul.appendChild(li);
         },

         /*
          * Create a wipe panel.
          * @param widget
          * @param defaultDisplayHide
          * @param title
          */
         _createWipePanel : function(widget, defaultDisplayHide, title){
            var panel = new PublishPanelItem(
                    {contentWidget : widget,
                     context : this.context,
                     title : title,
                     defaultDisplayHide : defaultDisplayHide
                    });
            this._createButton(widget, title);
            this._detail.appendChild(panel.domNode);

         },

         /*
          * Initialize the email support.
          * @param defaultDisplayHide
          */
         initializeEmail : function(defaultDisplayHide){
             var email = new PublishEmailSupport(
                     {
                         context : this.context,
                         itemId : this.item.id
                     }
                     );
             this._createWipePanel(email, defaultDisplayHide, "Email");
         },

         /*
          * Initialize the social picker support.
          * @param defaultDisplayHide
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
///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//dojo.provide("encuestame.org.core.shared.publish.PublishSupport");
//
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//dojo.require('dijit.form.Button');
//dojo.require('encuestame.org.core.commons');
//dojo.require('encuestame.org.core.shared.utils.StandBy');
//dojo.require('encuestame.org.core.shared.utils.ContextSupport');
//dojo.require("encuestame.org.core.commons.support.Wipe");
//dojo.require('encuestame.org.core.commons.social.SocialAccountPicker');
//
///**
// * Build publish support, able to publish on social networks, send email and generate embebed js code.
// */
//dojo.declare("encuestame.org.core.shared.publish.PublishSupport", [
//        dijit._Widget,
//        dijit._Templated,
//        encuestame.org.core.shared.utils.ContextSupport], {
//
//    templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/publishSupport.html"),
//

//});
//
///**
// *
// */
//dojo.declare("encuestame.org.core.shared.publish.PublishPanelItem", [
//       dijit._Widget,
//       dijit._Templated,
//       encuestame.org.core.shared.utils.ContextSupport], {
//
//    templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/publishPanelItem.html"),
//
//    panelWidget : null,
//
//    contentWidget : null,
//
//    title : "replace this title",
//
//    defaultDisplayHide : true,
//
//    _open : false,
//
//    postCreate : function(){
//        this._content.appendChild(this.contentWidget.domNode);
//        this.panelWidget = new encuestame.org.core.commons.support.Wipe(this._content, 300, 200);
//        dojo.connect(this._title, "onclick", dojo.hitch(this, this._onClickItem));
//        dojo.subscribe("/encuestame/support/panel/remote/select", this, "remoteClick");
//        dojo.subscribe("/encuestame/support/panel/unselect", this, "unselect");
//        if (this.defaultDisplayHide) {
//            dojo.addClass(this._content, "defaultDisplayHide");
//        } else {
//            this.panelWidget.wipeOutOne();
//            dojo.removeClass(this._content, "defaultDisplayHide");
//        }
//    },
//
//    /*
//     *
//     */
//    unselect : function(id){
//        if (this._open && this != id) {
//            this._switchSuport();
//        }
//    },
//
//    /*
//     *
//     */
//    remoteClick : function(id){
//        if(this.contentWidget == id){
//            if (!this._open) {
//                this._switchSuport();
//            }
//        }
//    },
//
//    /*
//     *
//     */
//    _switchSuport : function(){
//        if(this._open){
//            this.panelWidget.wipeOutOne();
//            //dojo.addClass(this._content, "defaultDisplayHide");
//       } else {
//            this.panelWidget.wipeInOne();
//            dojo.removeClass(this._content, "defaultDisplayHide");
//       }
//       this._open = !this._open;
//    },
//
//    _onClickItem : function(){
//        dojo.publish("/encuestame/support/panel/unselect", [this]);
//        this._switchSuport();
//    }
//});
//
//
//dojo.declare(
//        "encuestame.org.core.shared.publish.PublishSocialSupport",
//        [dijit._Widget, dijit._Templated,
//         encuestame.org.core.shared.utils.ContextSupport],{
//
//        templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/socialPublishSupport.html"),
//
//        widgetsInTemplate: true,
//
//        messageToPublish : "",
//
//        context : "",
//
//        _socialWidget : null,
//
//        itemId : null,
//
//        /*
//        *
//        */
//       _socialWidget : null,
//
//
//        postCreate : function(){
//            this._socialWidget = new encuestame.org.core.commons.social.SocialAccountPicker(
//                 {
//                     checkRequiredSocialAccounts : true,
//                     enableEasyAddAccount : true
//                 }
//            );
//        }
//
//});
//
//dojo.declare(
//        "encuestame.org.core.shared.publish.PublishEmbebedSupport",
//        [dijit._Widget, dijit._Templated,
//         encuestame.org.core.shared.utils.ContextSupport],{
//
//        templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/embebedSupport.html"),
//
//        widgetsInTemplate: true,
//
//        itemId : null,
//
//        name : null,
//
//        _domain : null,
//
//        _pollPath : "/poll/",
//
//        postMixInProperties: function(){
//            this._domain = ENME.config('domain');
//        },
//
//        postCreate : function(){
//            this._buildJavascriptEmbebed();
//        },
//
//        _buildJsUrl : function(){
//            return this._domain+this._pollPath+this.itemId+".js";
//        },
//
//        _buildUrl : function(){
//            return this._domain+this._pollPath+this.itemId+"/";
//        },
//
//        /*
//         * <script type="text/javascript" charset="utf-8" src="http://demo.encuestame.org/poll/5439680.js"></script>
//         * <noscript><a href="http://demo.encuestame.org/poll/5439680/">My New Poll</a></noscript>
//         */
//        _buildJavascriptEmbebed : function(){
//            var script = "<script type=\"text/javascript\" charset=\"utf-8\" src=\""+this._buildJsUrl()+"\"></script>";
//            var noscript = "<noscript><a href=\""+this._buildUrl()+"\">"+this.name+"</a></noscript>";
//            this._textarea.value = script + noscript;
//        }
//
//});
//
//dojo.declare(
//        "encuestame.org.core.shared.publish.PublishEmailSupport",
//        [dijit._Widget, dijit._Templated,
//         encuestame.org.core.shared.utils.ContextSupport],{
//
//        templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/emailSupport.html"),
//
//        widgetsInTemplate: true,
//
//        itemId : null
//
//});