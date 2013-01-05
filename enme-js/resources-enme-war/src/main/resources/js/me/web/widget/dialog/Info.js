define([
         "dojo/_base/declare",
         "me/web/widget/dialog/Dialog",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/dialog/templates/dialog.html" ],
        function(
                declare,
                Dialog,
                main_widget,
                _ENME,
                 template) {
            return declare([Dialog], {

          // template string.
          templateString : template,

          draggable : false,

          content : null,

          style: "width: 500px",

          /*
           * post create.
           */
          postCreate : function(){
              this.containerNode.appendChild(this.content);
              this.titleNode.innerHTML = "";
              this.inherited(arguments);
          }

    });
});

//dojo.provide("encuestame.org.core.commons.dialog.Info");
//
//dojo.require("encuestame.org.core.commons.dialog.Dialog");
//dojo.require("dijit.form.Button");
//
//dojo.declare(
//    "encuestame.org.core.commons.dialog.Info",
//    [encuestame.org.core.commons.dialog.Dialog],{
//
//        //templatePath: dojo.moduleUrl("encuestame.org.core.commons.dialog", "templates/dialog.html"),
//

//});
