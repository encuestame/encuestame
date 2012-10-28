define([ "dojo/_base/declare",
     "me/core/URLServices",
     "me/web/widget/dialog/ModalBox",
     "dijit/registry",
     "me/core/enme"], function(
    declare,
    _URL,
    ModalBox,
    registry,
    _ENME) {

  return declare(null, {

      /*
       *
       */
      getURLService : function() {
        return _URL;
      },

      /**
       * Create a modal box.
       */
      _createModalBox : function(type, handler) {
        var modal = dojo.byId("modal-box");
        if (modal != null) {
          var modalBox = new ModalBox(dojo.byId("modal-box"), type, dojo.hitch(handler));
          return modalBox;
        } else {
          return null;
        }
      },

      /**
       * Display the loading process.
       */
      loading_show : function (message) {
        var loading = registry.byId("loading");
        if ( loading != null) {
          loading.show(message);
        }
      },

      /**
       * Hide the loading process.
       */
      loading_hide : function () {
        var loading = registry.byId("loading");
        if ( loading != null) {
          loading.hide();
        }
      },

      /**
       * Publish a message on the context
       * @param message the message
       * @param type error, warning, info, success
       */
      publishMessage : function (message, type, desc) {
        if (type === 'success') {
          this.successMesage(message, desc);
        } else if (type === 'error') {
          this.errorMessage(message, desc);
        } else if (type === 'warn') {
          this.warningMesage(message, desc);
        } else if (type === 'fatal') {
          this.fatalMesage(message, desc);
        }
      },

      /**
       * Display a success message.
       */
      successMesage : function(message, description) {
          //console.info("Successfull message");
        _ENME.success(message, description);
      },

      /**
       * Display a warning message.
       */
      warningMesage : function(message, description) {
        _ENME.warning(message, description);
      },

      /**
       * Display a warning message.
       */
      errorMessage : function(message, description) {
        _ENME.success(message, description);
      },

     /**
      * Display a fatal message
      */
     fatalMesage : function(message) {
       _ENME.fatal(message, description);
     },

      /**
       * Display a default loader.
       */
      loadingDefaultMessage : function() {
       var loading = {
             init : function(){
                 console.debug("init");
             },
             end : function(){
                     console.debug("end");
             }
       };
       return loading;
      },

      /**
       * Retrieve the default services response.
       * Succesfull === {"error":{},"success":{"r":0}}
       * or
       * Failed === {"error":{},"success":{"r":-1}}
       */
      getDefaultResponse : function(data) {
        if ("success" in data) {
          var r = parseInt(data.success.r);
          if (r === 0) {
            return true;
          } else {
            return false;
          }
        } else {
          return false;
        }
      },

      /*
       *
       */
      errorMesage : function(error) {
          var modal = this._createModalBox("alert", null);
          if (modal != null) {
            modal.show(error == null ? _ENME.getMessage("e_023") : error);
          }
      },

      /*
       *
       */
      infoMesage : function(info) {
           var modal = this._createModalBox("alert", null);
           if (modal != null) {
             modal.show(info);
           }
      },

       /**
        *
        * @param e
        */
       stopEvent : function (e) {
         _ENME.stopEvent(e);
       }

  });
});