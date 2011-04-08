dojo.provide("encuestame.org.core.commons.security.Password");

dojo.declare(
    "encuestame.org.core.commons.security.Password",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.security", "templates/password.html"),

        widgetsInTemplate: true

});