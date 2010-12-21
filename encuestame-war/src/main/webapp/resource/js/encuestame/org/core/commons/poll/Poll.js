dojo.provide("encuestame.org.core.commons.poll.Poll");

dojo.declare(
    "encuestame.org.core.commons.poll.Poll",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/poll.inc"),

        widgetsInTemplate: true

});