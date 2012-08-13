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
dojo.provide("encuestame.org.core.shared.utils.SettingsMenuSwitch");

dojo.require("dojo.hash");
dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

dojo.declare("encuestame.org.core.shared.utils.SettingsMenuSwitch", 
				[encuestame.org.main.EnmeMainLayoutWidget ], {

    templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
            	  "template/settings_switch.html"),


    /**
     * Post create.
     */
    postCreate : function() {
    	 dojo.query("> [dojoType]", this.srcNodeRef).forEach(
                 dojo.hitch(this, function(node) {
                    ENME.log(node);
                    //this._responses.appendChild(node);
                    //var widget = dijit.byId()
                    console.log('widget', node);
                    this._createDetail(node.getAttribute('id'), node.getAttribute('data-label'), node);
                    this._cretateButton(node.getAttribute('data-label'), node.getAttribute('data-label'));
                 })
             );
    },
    
    /*
    *
    */
   _createDetail : function(id, provider, widget) {
       dojo.addClass(widget, "hidden");
       this._detail.appendChild(widget);
   },

   /**
    * Create a settings button.
    */
   _cretateButton : function(id, provider) {
       var widget = new encuestame.org.core.shared.utils.SettingsButton(
               {
                   id : id,
                   label : provider
               });
       this._buttons.appendChild(widget.domNode);
   }

});

/*
 * Settings Button Widget.
 */
dojo.declare(
        "encuestame.org.core.shared.utils.SettingsButton",
        [encuestame.org.main.EnmeMainLayoutWidget],{

            label : "define label",

            templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", 
            							 "template/settingsButton.html"),

            /**
             * 
             */
            postCreate : function() {
                dojo.subscribe("/encuestame/settings/clean/buttons", this, dojo.hitch(this, function(type) {
                    dojo.removeClass(this.domNode, "selected");
                }));
                var hash = dojo.queryToObject(dojo.hash());
                if (hash.provider && hash.provider == this.id) {
                    this.clickEvent(hash.provider);
                }
            },
            
            /**
             * 
             */
            clickEvent : function (id) {
            	// default click event
            	ENME.log(id);
            },

            /**
             * 
             * @param event
             */
            _click : function (event) {
                dojo.publish("/encuestame/settings/clean/buttons");
                var hash = dojo.queryToObject(dojo.hash());
                //console.debug("click button");
                this.clickEvent(this.id);
                params = {
                   provider : this.id
                };
                dojo.hash(dojo.objectToQuery(params));
                dojo.addClass(this.domNode, "selected");
            }
 });