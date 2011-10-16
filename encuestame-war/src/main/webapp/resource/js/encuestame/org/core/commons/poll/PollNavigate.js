dojo.provide("encuestame.org.core.commons.poll.PollNavigate");

dojo.require("encuestame.org.core.shared.utils.TableLinkedList");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

dojo.declare(
    "encuestame.org.core.commons.poll.PollNavigate",
    [encuestame.org.main.EnmeMainLayoutWidget,
     encuestame.org.core.shared.utils.TableLinkedList],{

        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/pollNavigate.html"),

        postCreate : function() {
            var def = new dojo.Deferred();
        },

});