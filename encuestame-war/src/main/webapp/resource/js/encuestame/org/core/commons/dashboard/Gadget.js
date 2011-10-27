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
dojo.provide("encuestame.org.core.commons.dashboard.Gadget");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.require("encuestame.org.core.gadget.Gadget");
dojo.require("dojo.parser");
dojo.require("encuestame.org.core.gadget.Activity");
dojo.require("encuestame.org.core.gadget.Comments");
dojo.require("encuestame.org.core.gadget.TweetPollVotes");

dojo.declare(
    "encuestame.org.core.commons.dashboard.Gadget",
    [dijit._Widget, dijit._Templated],{

        /*
         *
         */
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/gadget.html"),

        /*
         * enable widgets on template.
         */
        widgetsInTemplate: true,

        /*
         * gadget info.
         */
        data : null,

        dashboardId  : null,

        _widgetInside : null,

        /*
         * Post create.
         */
        postCreate: function() {
            dojo.subscribe("/encuestame/dashboard/gadget/remove", this, "_removeGadget");
            //var parent = this;
            if (this.data) {
                console.debug("gadget data",  this.data);
                if (this.data.id) {
                    this.domNode.setAttribute("gid",  this.data.id);
                    this._initialize();
                }
                //dojo.connect(this._remove, "onclick", dojo.hitch(this, function(event) {
                //    dojo.stopEvent(event);
                //    parent._removeGadget();
                //    console.info("remove ..... gadget");
                //}));
            }
        },

        /*
         * set gadget title.
         */
        _setTitle : function(title) {
            if (title) {
                this._title.innerHTML = title;
            }
        },

        /*
         *
         */
        _initialize : function() {
             var load = dojo.hitch(this, function(data) {
                 if (data.success) {
                     this._widgetInside = this._loadGadget(this.data.gadget_name, {gadgetId : this.data.id});
                     this._widgetInside.placeAt(this._content);
                     this._widgetInside.startup();
                     //this._setTitle(this.data.gadget_name); //TODO: the title should be more specific.
                 }
             });
             var error = function(error) {
                 console.debug("error", error);
             };
             encuestame.service.xhrGet(encuestame.service.gadget.load, {gadgetId: this.data.gadget_name}, load, error);
        },

        /*
         * remove the gadget.
         */
        _removeGadget : function(controlId) {
            console.info("REMOVING controlId", controlId);
            if (controlId == this.data.id) {
                console.info("REMOVING gadgetId", this.data.id);
                var params = { gadgetId: this.data.id, dashboardId: this.dashboardId};
                var load = dojo.hitch(this, function(data) {
                    if ("success" in data) {
                       dojo.publish("/encuestame/dashboard/gadget/unsubscribe", [this.data.id]);
                       this._widgetInside.destroyRecursive(true);
                       this.destroyRecursive(true);
                    }
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                encuestame.service.xhrGet(encuestame.service.gadget.remove, params, load, error);
            }
       },

         /**
          *
          * @param type
          * @param params
          * @returns
          */
        _loadGadget : function(type, params) {
            console.debug("load gadget", type);
            if (type == "stream") {
                return new encuestame.org.core.gadget.Activity(params);
            } else if (type == "comments") {
                return new encuestame.org.core.gadget.Comments(params);
            }  else {
                return new encuestame.org.core.gadget.Activity(params);
            }
        },

        _editConfiguration : function(){},
        _updateConfiguration : function(){},
        _minimizeGadget : function(){},
        _maximizeGadget : function(){}
    }
);
