dojo.provide("encuestame.org.core.commons.poll.Poll");


dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.core.shared.utils.FolderSelect");

dojo.declare(
    "encuestame.org.core.commons.poll.Poll",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/poll.html"),

        widgetsInTemplate: true,

        _folderWidget : null,

        postCreate : function(){
            this._folderWidget = new encuestame.org.core.shared.utils.FolderSelect({folderContext : "poll"});
            this._folder.appendChild(this._folderWidget.domNode);
        }
});