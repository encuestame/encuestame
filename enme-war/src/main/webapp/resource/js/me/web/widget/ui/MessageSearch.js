define([ "dojo/_base/declare",
        "me/core/ui/Loading",
        "me/web/widget/support/Wipe",
        "me/core/enme",
        "dojo/text!me/web/widget/ui/templates/loading_search.html" ],
        function(
                declare,
                Loading,
                Wipe,
                _ENME,
                template) {
    return declare([Loading], {

        /*
         * template string.
         */
        templateString : template,

        /*
        *
        */
       channel_on : "encuestame/search/loading/display/on",

       /*
        *
        */
       channel_off : "encuestame/search/loading/display/off",

       /*
        *
        */
       _wipeWidget : null,

       /*
        *
        */
       _wipe_duration : 100,

       /*
        *
        */
       _wipe_height : 30,

        /*
         *
         */
        postCreate : function() {
            this.inherited(arguments);
            this._wipeWidget = new Wipe(
                    this._loading,
                    this._wipe_duration,
                    this._wipe_height,
                    'tp-list-message');
        },

        /*
         * Show the loader.
         */
        show : function (message, color, _limit) {
          //dojo.removeClass(this._loading, "hidden");
          if (message) {
            this._message.innerHTML = message;
          } else {
            this._message.innerHTML = this.i18nMessage.loading;
          }
          if (color) {
              if (color === _ENME.MESSAGES_TYPE.MESSAGE) {
                  dojo.addClass(this._message, 'success-color');
              } else if (color === _ENME.MESSAGES_TYPE.WARNING) {
                  dojo.addClass(this._message, 'warning-color');
              } else if (color === _ENME.MESSAGES_TYPE.ERROR) {
                  dojo.addClass(this._message, 'red-color');
              } else if (color === _ENME.MESSAGES_TYPE.FATAL) {
                  dojo.addClass(this._message, 'red-color');
              } else {
                 dojo.addClass(this._message, 'success-color');
              }
          }
          this._wipeWidget.wipeInOne();
          if (_limit) {
              var _p = this;
              var x = function(){
                  _p._wipeWidget.wipeOutOne();
              };
              setTimeout(x, _limit);
          }
        },
        /**
         * Hide the loader.
         */
        hide : function () {
            _ENME.log("HIDEEE MESSAGEEEE");
            this._wipeWidget.wipeOutOne();
        }
    });
});