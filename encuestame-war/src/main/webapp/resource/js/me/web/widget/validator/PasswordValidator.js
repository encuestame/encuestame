dojo.provide("encuestame.org.core.commons.validator.PasswordValidator");

dojo.require("dojo.io.iframe");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.require("encuestame.org.core.commons.validator.AbstractValidatorWidget");

dojo.declare("encuestame.org.core.commons.validator.PasswordValidator",
        [encuestame.org.core.commons.validator.AbstractValidatorWidget], {
    templatePath : dojo.moduleUrl("encuestame.org.core.commons.validator", "templates/passwordValidator.html"),
    widgetsInTemplate : true,
    noEvents : true,
    placeholder : "Write your password",
    exclude : encuestame.constants.passwordExcludes,
    toolTip : false,
    postCreate : function(){
        this.inherited(arguments);
        dojo.connect(this._input, "onkeyup", dojo.hitch(this, function(event) {
             this.inputTextValue = this._input.value.toLowerCase();
             console.debug(this.inputTextValue);
             if(this.inputTextValue.length > 0){
                 this._validate(this.inputTextValue);
             } else {
                 var data = {};
                 data.msg = encuestame.constants.errorCodes["015"];
                 this._showErrorMessage(data);
             }

        }));
    },

    _validateBlackListPassword : function(password){
        var valid = false;
        dojo.forEach(encuestame.constants.passwordExcludes,
                dojo.hitch(this,function(item) {
                    console.debug(item);
        }));
        return valid;
    },

    validatePassword :function() {
        this._validate(this.inputTextValue);
    },

    evaluateStrong : function(passwd) {
        var intScore = 0;
        var strVerdict = "weak";
        var strLog = "";

        if (passwd.length < 5) {
            intScore = (intScore + 3);
        } else if (passwd.length > 4 && passwd.length < 8) {
            intScore = (intScore + 6);
        } else if (passwd.length > 7 && passwd.length < 16) {
            intScore = (intScore + 12);
        } else if (passwd.length > 15) {
            intScore = (intScore + 18);
        }

        if (passwd.match(/[a-z]/)) {
            intScore = (intScore + 1);
        }

        if (passwd.match(/[A-Z]/)) {
            intScore = (intScore + 5);
        }

        if (passwd.match(/\d+/)) {
            intScore = (intScore + 5);
        }

        if (passwd.match(/(.*[0-9].*[0-9].*[0-9])/)) {
            intScore = (intScore + 5);
        }

        if (passwd.match(/.[!,@,#,$,%,^,&,*,?,_,~]/)) {
            intScore = (intScore + 5);
        }

        if (passwd.match(/(.*[!,@,#,$,%,^,&,*,?,_,~].*[!,@,#,$,%,^,&,*,?,_,~])/)) {
            intScore = (intScore + 5);
        }

        if (passwd.match(/([a-z].*[A-Z])|([A-Z].*[a-z])/)) {
            intScore = (intScore + 2);
        }

        if (passwd.match(/([a-zA-Z])/) && passwd.match(/([0-9])/)) {
            intScore = (intScore + 2);
        }

        if (passwd.match(/([a-zA-Z0-9].*[!,@,#,$,%,^,&,*,?,_,~])|([!,@,#,$,%,^,&,*,?,_,~].*[a-zA-Z0-9])/)) {
            intScore = (intScore + 2);
        }

        return intScore;
    },

    /*
    *
    */
   _validate : function(password) {
           var banned = false;
           // check banned password.
           if (encuestame.constants.passwordExcludes.indexOf(password.toLowerCase()) != -1) {
               console.info(password.toLowerCase());
               var data = {};
               data.msg = encuestame.constants.errorCodes["012"];
               banned = true;
               this._showErrorMessage(data);
           }
          if (!banned) {
              var data = {};
              var intScore = this.evaluateStrong(password);
              if (intScore < 16) {
                  data.msg = encuestame.constants.errorCodes["012"];
                  this.isValid = false;
                  this._showErrorMessage(data);
              } else if (intScore > 15 && intScore < 25) {
                  data.msg =  encuestame.constants.errorCodes["013"];
                  this._showErrorMessage(data);
                  this.isValid = false;
              } else if (intScore > 24 && intScore < 35) {
                  data.msg = encuestame.constants.messageCodes["011"];
                  this._showSuccessMessage(data);
                  this.isValid = true;
              } else if (intScore > 34 && intScore < 45) {
                  data.msg = encuestame.constants.messageCodes["010"];
                  this._showSuccessMessage(data);
                  this.isValid = true;
              } else {
                  data.msg = encuestame.constants.messageCodes["009"];
                  this._showSuccessMessage(data);
                  this.isValid = true;
              }
          } else {
                this.isValid = false;
          }
   },


   /*
    *
    */
   _showSuccessMessage : function(data){
       var node = dojo.byId("_message_"+this.id);
       if (node) {
           dojo.empty(node);
           var p = dojo.doc.createElement("p");
           dojo.addClass(p, "success-message");
           p.innerHTML = data.msg;
           node.appendChild(p);
       }
   },

     /*
      *
      */
     _showErrorMessage : function(data){
         var node = dojo.byId("_message_"+this.id);
         console.info("_showSuccessMessage", data);
         if (node) {
             dojo.empty(node);
             var p = dojo.doc.createElement("p");
             dojo.addClass(p, "error-message");
             p.innerHTML = data.msg;
             node.appendChild(p);
         }
     }

});