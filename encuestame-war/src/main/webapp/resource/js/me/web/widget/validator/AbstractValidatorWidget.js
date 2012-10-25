define([
     "dojo/_base/declare",
     "me/core/enme"],
    function(
    declare,
    _ENME) {

  return declare(null, {

    templatePath : null,
    widgetsInTemplate : true,
    noEvents : false,
    focusDefault : false,
    enviroment : "external",
    isValid : false,
    inputTextValue : "",
    toolTip : true,
    placeholder:"",

    /**
     * ?????????????????????
     */
    postCreate : function() {
        this.inherited(arguments);
        if (this.focusDefault) {
            dijit.focus( dojo.byId("input_" + this.id));
        }
        if (!this.noEvents) {
            dojo.connect(this._input, "onchange", dojo.hitch(this, function(event) {
                this._validate(event);
            }));
        }
    },

    /**
     *
     */
    getServiceUrl : function(){
        return "";
    },

    /**
     *
     */
    recheck : function(data){
         this._loadService(
                   this.getServiceUrl(), {
                   context : this.enviroment,
                   data : this.inputTextValue
               }, this.error);
    },

    /**
     *
     */
    error : function() {
        console.info("override");
    },

    /**
     *
     */
    _showToolTip : function(){
        var position = dojo.position(dojo.byId("input_"+this.id), true);
        var node = dojo.byId("_tooltip_"+this.id);
        //console.info("_showToolTip", position);
        if (node) {
            dojo.style(node, "opacity", "0");
            var fadeArgs = {
                    node: node,
                    duration: 500
                };
            dojo.style(node, "display", "block");
            dojo.fadeIn(fadeArgs).play();
            dojo.style(node, "top",  "0px");
            //console.info("_showToolTip", node);
        }
    },

    /**
     *
     */
    _hideToolTip : function(){
        var node = dojo.byId("_tooltip_"+this.id);
        //console.info("_hideToolTip", node);
        if (node) {
            dojo.style(node, "opacity", "1");
            var fadeArgs = {
                node: node,
                duration: 500
            };
            dojo.fadeOut(fadeArgs).play();
            //dojo.style(node, "display", "none");
        }
    },


    /**
     *
     */
    _evaluateShadowMessage : function(event) {
        if (this._shadow && dojo.byId("input_"+this.id) != null) {
            var textLenght = dojo.byId("input_"+this.id).value.length;
            //console.info(textLenght);
            if (textLenght > 0) {
               dojo.style(this._shadow, "display", "none");
            } else {
                dojo.style(this._shadow, "display", "block");
            }
        }
    },


    /**
     *
     */
    _loadService : function(service, params, error) {
            var i = false;
            var load = dojo.hitch(this, function(data) {
                    if (data.success.valid) {
                        this.isValid = true;
                        this._showSuccessMessage(data.success);
                        this._additionalSuccessHandler(data.success);
                    } else {
                        this.isValid = false;
                        this._showErrorMessage(data.success);
                        this._additionalErrorHandler(data.success);
                    }
            });
            // dojo.mixin(params,
            // {
            // max : this.max,
            // start : this.start
            // }
            // );
            // var error = function(error) {
            // console.debug("error", error);
            // };
            encuestame.service.xhrGet(this.getURLService().service(service), params, load, error);
        },

        /**
         *
         */
        _additionalErrorHandler : function(data){},

        /**
         *
         */
        _additionalSuccessHandler : function(data){},

        /**
         *
         */
        _showSuccessMessage : function(data){
             var node = dojo.byId("_message_"+this.id);
             if (node) {
                 dojo.empty(node);
                 var p = dojo.doc.createElement("p");
                 dojo.addClass(p, "success-message");
                 p.innerHTML = data.msg;
                 node.appendChild(p);
             }
        },

        /**
         *
         */
        _showErrorMessage : function(data){
            var node = dojo.byId("_message_"+this.id);
            //console.info("_showSuccessMessage", data);
            if (node) {
                dojo.empty(node);
                var p = dojo.doc.createElement("p");
                dojo.addClass(p, "error-message");
                p.innerHTML = data.msg;
                node.appendChild(p);
            }
        },

        /**
         *
         */
        _validate : function(event) {
            //console.debug("validate", event);
        }


  });
});