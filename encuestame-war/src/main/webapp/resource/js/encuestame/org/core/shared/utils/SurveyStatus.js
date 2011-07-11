dojo.provide("encuestame.org.core.shared.utils.SurveyStatus");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.SurveyStatus",
    [dijit._Widget, dijit._Templated],{

      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/surveyStatus.html"),

      status : false,

      _statusText : ["CLOSED", "OPEN"],

      /*
       * post create.
       */
      postCreate : function() {
          console.debug("SurveyStatus status", this.status);
          if (!this.status) {
              this.setOpen();
          } else {
              this.setClose();
          }
      },

      /*
       * set close status.
       */
      setOpen : function(){
          dojo.addClass(this._status, "open");
          dojo.removeClass(this._status, "close");
          this._status.innerHTML = this._statusText[1];
      },

      /*
       * set close status.
       */
      setClose : function(){
          dojo.removeClass(this._status, "open");
          dojo.addClass(this._status, "close");
          this._status.innerHTML = this._statusText[0];
      }


});