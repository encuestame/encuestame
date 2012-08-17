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

	/**
	 * Template path.
	 */
    templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
            	  "template/settings_switch.html"),
            	  
    _widget_references : [],

    /**
     * Post create.
     */
    postCreate : function() {
    	 dojo.query("> [dojoType]", this.srcNodeRef).forEach(
                 dojo.hitch(this, function(node) {
                    ENME.log(node);
                    //this._responses.appendChild(node);
                    //var widget = dijit.byId()
//                    /console.log('widget', node);
                    var enabled = node.getAttribute("data-enabled");
                    this._createDetail(node.getAttribute('id'), node.getAttribute('data-label'), node, enabled);
                    this._cretateButton(node.getAttribute('id'), node.getAttribute('data-label'), node.getAttribute('data-label'), enabled);
                 })
             );
    	 // hide details
         dojo.subscribe("/encuestame/settings/hide/all", this, dojo.hitch(this, function(type) {
        	 dojo.query("div.setting-detail", this._detail).forEach(
                     dojo.hitch(this, function(node) {
                    	 dojo.addClass(node, "hidden");
                     })
             );                   
         }));
    },
    
    /*
     *
     */
   _createDetail : function(id, provider, widget, enabled) {
	   //_widget_references.
	   if (!ENME.getBoolean(enabled)) {
		   dojo.addClass(widget, "hidden");
	   }
       dojo.addClass(widget, "setting-detail");
       this._detail.appendChild(widget);
   },

   /**
    * Create a settings button.
    */
   _cretateButton : function(id, label, provider, enabled) {
       var widget = new encuestame.org.core.shared.utils.SettingsButton(
               {
                   ref_id : id,
                   label : label,
                   provider : provider
               });
       this._buttons.appendChild(widget.domNode);
       if (ENME.getBoolean(enabled)) {
    	    dojo.addClass(widget.domNode, "selected");
       }
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
            	console.log(id);
            	var widget = dijit.byId(id);
            	dojo.removeClass(widget.domNode, "hidden");
            },

            /**
             * 
             * @param event
             */
            _click : function (event) {
                dojo.publish("/encuestame/settings/clean/buttons");
                dojo.publish('/encuestame/settings/hide/all');
                var hash = dojo.queryToObject(dojo.hash());
                //console.debug("click button");
                this.clickEvent(this.ref_id);
                params = {
                   provider : this.provider
                };
                dojo.hash(dojo.objectToQuery(params));
                dojo.addClass(this.domNode, "selected");
            }
 });