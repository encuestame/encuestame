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
dojo.provide("encuestame.org.core.shared.publish.PublishSupport");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('dijit.form.Button');
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.StandBy');
dojo.require('encuestame.org.core.shared.utils.ContextSupport');
dojo.require("encuestame.org.core.commons.support.Wipe");
dojo.require('encuestame.org.core.commons.social.SocialAccountPicker');

/**
 * Build publish support, able to publish on social networks, send email and generate embebed js code.
 */
dojo.declare("encuestame.org.core.shared.publish.PublishSupport", [
        dijit._Widget,
        dijit._Templated,
        encuestame.org.core.shared.utils.ContextSupport], {

    templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/publishSupport.html"),

    /** Allow other widgets in the template. * */
    widgetsInTemplate : true,

    message : "Your (item) has been created successfully.",

    messageClass : "succesfully",

    postCreate : function(){
        this.initializeSocial(false);
        this.initializeEmail(true);
        this.initializeEmbebed(true);
    },

    /*
     *
     */
    _createButton : function(widget, title){
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
     *
     */
    _createWipePanel : function(widget, defaultDisplayHide, title){
       var panel = new encuestame.org.core.shared.publish.PublishPanelItem(
               {contentWidget : widget,
                context : this.context,
                title : title,
                defaultDisplayHide : defaultDisplayHide
               });
       this._createButton(widget, title);
       this._detail.appendChild(panel.domNode);

    },

    initializeEmail : function(defaultDisplayHide){
        var email = new encuestame.org.core.shared.publish.PublishEmailSupport({context : this.context});
        this._createWipePanel(email, defaultDisplayHide, "Email");
    },

    initializeSocial : function(defaultDisplayHide){
        var social = new encuestame.org.core.shared.publish.PublishSocialSupport({context : this.context});
        this._createWipePanel(social, defaultDisplayHide, "Social Networks");
    },

    initializeEmbebed : function(defaultDisplayHide){
        var embebed = new encuestame.org.core.shared.publish.PublishEmbebedSupport({context : this.context});
        this._createWipePanel(embebed, defaultDisplayHide, "Javascript");
    }
});

/**
 *
 */
dojo.declare("encuestame.org.core.shared.publish.PublishPanelItem", [
       dijit._Widget,
       dijit._Templated,
       encuestame.org.core.shared.utils.ContextSupport], {

    templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/publishPanelItem.html"),

    panelWidget : null,

    contentWidget : null,

    title : "replace this title",

    defaultDisplayHide : true,

    _open : false,

    postCreate : function(){
        this._content.appendChild(this.contentWidget.domNode);
        this.panelWidget = new encuestame.org.core.commons.support.Wipe(this._content, 300, 300);
        dojo.connect(this._title, "onclick", dojo.hitch(this, this._onClickItem));
        dojo.subscribe("/encuestame/support/panel/remote/select", this, "remoteClick");
        dojo.subscribe("/encuestame/support/panel/unselect", this, "unselect");
        if (this.defaultDisplayHide) {
            dojo.addClass(this._content, "defaultDisplayHide");
        } else {
            this.panelWidget.wipeOutOne();
            dojo.removeClass(this._content, "defaultDisplayHide");
        }
    },

    /*
     *
     */
    unselect : function(id){
        if (this._open && this != id) {
            this._switchSuport();
        }
    },

    /*
     *
     */
    remoteClick : function(id){
        if(this.contentWidget == id){
            if (!this._open) {
                this._switchSuport();
            }
        }
    },

    /*
     *
     */
    _switchSuport : function(){
        if(this._open){
            this.panelWidget.wipeOutOne();
            //dojo.addClass(this._content, "defaultDisplayHide");
       } else {
            this.panelWidget.wipeInOne();
            dojo.removeClass(this._content, "defaultDisplayHide");
       }
       this._open = !this._open;
    },

    _onClickItem : function(){
        dojo.publish("/encuestame/support/panel/unselect", [this]);
        this._switchSuport();
    }
});



dojo.declare(
        "encuestame.org.core.shared.publish.PublishSocialSupport",
        [dijit._Widget, dijit._Templated,
         encuestame.org.core.shared.utils.ContextSupport],{

        templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/socialPublishSupport.html"),

        widgetsInTemplate: true,

        messageToPublish : "",

        context : "",

        _socialWidget : null,

        /*
        *
        */
       _socialWidget : null,


        postCreate : function(){
            this._socialWidget = new encuestame.org.core.commons.social.SocialAccountPicker(
                 {
                     checkRequiredSocialAccounts : true,
                     enableEasyAddAccount : true
                 }
            );
        },

        initializeSocialNetworks : function(){},
        initializeEmailSupport : function(){},
        initializeEmbebedSuport : function(){},

});

dojo.declare(
        "encuestame.org.core.shared.publish.PublishEmbebedSupport",
        [dijit._Widget, dijit._Templated,
         encuestame.org.core.shared.utils.ContextSupport],{

        templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/embebedSupport.html"),

        widgetsInTemplate: true,

});

dojo.declare(
        "encuestame.org.core.shared.publish.PublishEmailSupport",
        [dijit._Widget, dijit._Templated,
         encuestame.org.core.shared.utils.ContextSupport],{

        templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/emailSupport.html"),

        widgetsInTemplate: true,

});