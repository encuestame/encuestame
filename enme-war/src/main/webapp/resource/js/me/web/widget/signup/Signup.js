define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
         "me/core/enme",
         "dijit/registry",
         "dojo/text!me/web/widget/signup/templates/signup.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                hashTagInfo,
                _ENME,
                registry,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

         // template string.
         templateString : template,

        value : 'Sign Up Now',

       userWidget: null,
       passWidget: null,
       emailWidget: null,
       realWidget: null,
       standWidget: null,

       /**
        *
        */
       postCreate : function() {
           this.userWidget = registry.byId("usrva");
           this.passWidget = registry.byId("pssw");
           this.emailWidget = registry.byId("em");
           this.realWidget = registry.byId("rm");
                   if (this.userWidget == null || this.passWidget == null
                   || this.emailWidget == null || this.realWidget == null) {
                       console.error("NO WIDGETS FOUND");
                   }
                   //dojo.connect(this._submit, "onclick", dojo.hitch(this, this._onSubmit()));
                   dojo.connect(this._input, "ondoubleclick", dojo.hitch(this, function(event) {
                       console.debug("calm down cowboy !!");
                   }));
           //}
       },

       /**
        *
        */
       _onSubmit : function(event) {
           dojo.stopEvent(event);
           dijit.byId("standby").startup();
           dijit.byId("standby").start();
           this._checkValidWidgets();
       },

       /**
        *
        */
       createNewAccountService : function(){
            //console.debug("createNewAccountService signupForm", signupForm);
            signupForm.submit();
       },

       /**
        *
        */
       _checkValidWidgets : function(){
           console.debug("standby init 3");
           if (this.userWidget.isValid && this.passWidget.isValid && this.emailWidget.isValid && this.realWidget.isValid) {
               //console.debug("_checkValidWidgets 1");
               this.createNewAccountService(this.userWidget, this.passWidget, this.emailWidget, this.realWidget);
           } else {
               //console.debug("_checkValidWidgets 2");
               dijit.byId("standby").stop();
               this.userWidget.recheck("username");
               this.passWidget.validatePassword();
               this.emailWidget.recheck("email");
               this.realWidget.recheck("real_name");
           }
       },

       /**
        *
        */
       _create : function(event){
           this.uploadImage();
       }

    });
});