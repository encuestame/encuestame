dojo.provide("encuestame.org.core.shared.utils.CountDown");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dojox.data.QueryReadStore");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.CountDown",
    [dijit._Widget, dijit._Templated],{

      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/countDown.html")


});