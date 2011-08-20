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
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.StandBy');
dojo.require('encuestame.org.core.shared.utils.ContextSupport');

dojo.declare("encuestame.org.core.shared.publish.PublishSupport", [
        dijit._Widget,
        dijit._Templated,
        encuestame.org.core.shared.utils.ContextSupport], {

    templatePath : dojo.moduleUrl("encuestame.org.core.shared.publish", "templates/publishSupport.html"),

    /** Allow other widgets in the template. * */
    widgetsInTemplate : true


});

dojo.declare(
        "encuestame.org.core.shared.utils.publish.PublishSocialSupport",
        [dijit._Widget, dijit._Templated],{

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
            console.debug("CONTEXT--->", this.getContext(this.context));
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
        [dijit._Widget, dijit._Templated],{

        widgetsInTemplate: true,

});

dojo.declare(
        "encuestame.org.core.shared.publish.PublishEmailSupport",
        [dijit._Widget, dijit._Templated],{

        widgetsInTemplate: true,

});