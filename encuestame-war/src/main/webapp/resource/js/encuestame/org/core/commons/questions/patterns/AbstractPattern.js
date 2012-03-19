dojo.provide("encuestame.org.core.commons.questions.patterns.AbstractPattern");


dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.commons.questions.patterns.AbstractPattern",
    [encuestame.org.main.EnmeMainLayoutWidget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.questions.patterns", "templates/single.html"),

        widgetsInTemplate: true,

        postCreate : function() {

        }
});