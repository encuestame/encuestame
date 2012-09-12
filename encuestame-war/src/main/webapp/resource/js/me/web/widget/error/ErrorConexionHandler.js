dojo.provide("encuestame.org.core.commons.error.ErrorConexionHandler");

dojo.require("dojox.widget.Dialog");
dojo.require("encuestame.org.core.commons.error.AbstractErrorHandler");

dojo.declare(
    "encuestame.org.core.commons.error.ErrorConexionHandler",
    [encuestame.org.core.commons.error.AbstractErrorHandler],{

        widgetsInTemplate: true,

        type : "Conexion Error",

        postMixInProperties: function(){

        },

        postCreate: function() {

        }
    }
);