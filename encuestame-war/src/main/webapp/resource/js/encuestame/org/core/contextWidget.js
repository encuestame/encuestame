dojo.provide("encuestame.org.core.contextWidget");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.contextWidget",
    [dijit._Widget, dijit._Templated],{
        templateString: "<div/>",
        contextPath : "/encuestame",
        postCreate : function(){
            console.debug("Context ", this.contextPath)
        }
    }
);
