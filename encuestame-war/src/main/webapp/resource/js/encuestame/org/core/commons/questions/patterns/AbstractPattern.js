dojo.provide("encuestame.org.core.commons.questions.patterns.AbstractPattern");


dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.commons.questions.patterns.AbstractPattern",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.questions.patterns", "templates/single.html"),

        widgetsInTemplate: true,

        postCreate : function(){

        }
});