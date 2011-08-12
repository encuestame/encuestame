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

        /*
         * Post create.
         */
        postCreate: function() {
            var parent = this;
            if (this.data) {
                console.debug("gadget data",  this.data);
                if (this.data.id) {
                    this.domNode.setAttribute("gid",  this.data.id);
                    this._initialize();
                }
                dojo.connect(this._remove, "onclick", dojo.hitch(this, function(event) {
                    dojo.stopEvent(event);
                    parent._removeGadget();
                    console.info("remove ..... gadget");
                }));
            }
        },

        /*
         * set gadget title.
         */
        _setTitle : function() {

        },

        /*
         *
         */
        _initialize : function() {
             var load = dojo.hitch(this, function(data) {
                 if (data.success) {
                     console.info("loaded gadget", data);
                     var widget = this._loadGadget(this.data.gadget_name, {});
                     widget.placeAt(this._content);
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
        _removeGadget : function() {
            console.info("_removeGadget ");
            var params = { gadgetId: this.data.id, dashboardId: this.dashboardId};
            console.info("_removeGadget ", params);
            var load = dojo.hitch(this, function(data) {
                if (data.success) {
                    console.info("remove gadget", data);
                    //this.destroyRecursive(true);
                    dojo.destroy(this.domNode);
                }
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(encuestame.service.gadget.remove, params, load, error);
       },


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
