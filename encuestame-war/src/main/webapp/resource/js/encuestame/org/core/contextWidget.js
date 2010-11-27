dojo.provide("encuestame.org.class.contextWidget");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.class.contextWidget",
    [dijit._Widget, dijit._Templated],{
        templateString: "<div/>",
        contextPath : ""
    }
);
