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
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/gadget.html"),

        widgetsInTemplate: true,

        data : null,

        /*
         * Post create.
         */
        postCreate: function() {
            if (this.data) {
                console.debug("gadget data",  this.data);
                if (this.data.id) {
                    this.domNode.setAttribute("gid",  this.data.id);
                    this._initialize();
                }
            }
        },

        /*
         * set gadget title.
         */
        _setTitle : function() {

        },

        _parseGadget : function(id, widget){
            console.debug("widgetGadget2",  widgetGadget2);
             var widgetGadget = new encuestame.org.core.gadget.Gadget(id, widget);
             var node = widgetGadget.render();
             this._content.appendChild(node);
             console.debug("node",  node);
             //console.debug("dojo.byId(id)", );
             dojo.parser.parse(node);
        },

        /*
         *
         */
        _initialize : function() {
             var load = dojo.hitch(this, function(data) {
                 if (data.success) {
                     console.info("loaded gadget", data);
                     //this._parseGadget(this.data.id, "");
                     var widget = this._loadGadget("stream", {});
                     widget.placeAt(this._content);
                 }
             });
             var error = function(error) {
                 console.debug("error", error);
             };
             encuestame.service.xhrGet(encuestame.service.gadget.load, {gadgetId: this.data.gadget_name}, load, error);
        },


        _loadGadget : function(type, params) {
            if (type == "stream") {
                return new encuestame.org.core.gadget.Activity(params);
            } else {
                return new encuestame.org.core.gadget.Comments(params);
            }
        },

        _editConfiguration : function(){},
        _updateConfiguration : function(){},
        _removeGadget : function(){},
        _minimizeGadget : function(){},
        _maximizeGadget : function(){}
    }
);
