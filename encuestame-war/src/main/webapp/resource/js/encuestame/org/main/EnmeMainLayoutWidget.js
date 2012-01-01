dojo.provide("encuestame.org.main.EnmeMainLayoutWidget");

dojo.require("dojo.cache");
dojo.require("encuestame.org.main.WidgetServices");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.main.EnmeMainLayoutWidget",
    [dijit._Widget, dijit._Templated, encuestame.org.main.WidgetServices],{

        /*
         * enable widgets on html template.
         */
        widgetsInTemplate: true,

        /*
         * default placeholder.
         */
        placeholder : "Type something...",

        /*
         * default context path.
         */
        contextDefaultPath : config.contextPath


});