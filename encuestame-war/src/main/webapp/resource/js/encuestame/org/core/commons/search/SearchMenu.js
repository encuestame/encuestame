dojo.provide("encuestame.org.core.commons.search.SearchMenu");

dojo.declare(
    "encuestame.org.core.commons.search.SearchMenu",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.search", "templates/searchMenu.inc"),

        widgetsInTemplate: true

});