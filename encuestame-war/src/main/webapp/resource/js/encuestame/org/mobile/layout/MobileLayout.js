dojo.provide("encuestame.org.mobile.layout.MobileLayout");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");

dojo.declare(
    "encuestame.org.mobile.layout.MobileLayout",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollListDetail.html"),
        //widget
        widgetsInTemplate: true,
        //data
        data: null,

        widgetChart : null
});