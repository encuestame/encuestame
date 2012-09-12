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
dojo.provide("encuestame.org.core.shared.options.ConstrainNumberPicker");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.CheckBox");
dojo.require('dijit.form.NumberSpinner');
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.ContextSupport');
dojo.require('encuestame.org.core.shared.utils.PublishSupport');

/**
 * Represents a option to repeate votes.
 */
dojo.declare(
    "encuestame.org.core.shared.options.ConstrainNumberPicker",
    [dijit._Widget,
     dijit._Templated,
     encuestame.org.core.shared.utils.ContextSupport,
     encuestame.org.core.shared.utils.PublishSupport],{

        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.options", "templates/constrainNumberPicker.html"),

        /*
         * Allow Repeated Votes.
         */
        checkWidget  : null,

        /*
         *
         */
        numberSpinner : null,

        /*
         * Allow other widgets in the template.
         */
        widgetsInTemplate: true,

        /*
         *
         */
        label : "Allow Repeated Votes.",

        /*
         *
         */
        contextPath : encuestame.contextDefault,

        /*
         * to enable publish support, replace null value for publish valid url.
         * eg: /encuestame/tweetpoll/autosave
         */
        publish_url : null,

        /*
         *
         */
        options : { checked : false, items : 0},


        constraints : "{min:2, max:10}",

        /*
         *
         */
        postCreate : function(){
            this.checkWidget = dijit.byId("check_widget_"+this.id);
            this.checkWidget.onChange = dojo.hitch(this, function(event){
                //console.debug("checkWidget", event);
                if (event) {
                    dojo.removeClass(this._repeatedNumbers, "defaultDisplayHide");
                } else {
                    dojo.addClass(this._repeatedNumbers, "defaultDisplayHide");
                }
                this.options.checked = event;
                this.publish({});
            });
            this.numberSpinner = dijit.byId("spinner_"+this.id);
            this.numberSpinner.onChange = dojo.hitch(this, function(event){
            //console.debug("maxLimitVotes ", this.numberSpinner.get("value"));
            this.options.items = this.numberSpinner.get("value");
            if (this.publish_url != null) {
                this.publish({});
            }
            });
        },

        /*
         *
         */
        getOptions : function() {
            if (this.checkWidget.get('checked')){
                            dojo.mixin(this.options, {
                            checked : true,
                            items : this.numberSpinner.get("value")
            });
            }
            return this.options;
        }
});