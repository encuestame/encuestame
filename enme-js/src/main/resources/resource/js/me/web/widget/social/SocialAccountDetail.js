define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/registry",
         "dijit/form/Form",
         "dijit/form/Button",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "me/web/widget/social/SocialAccountRow",
         "dojo/hash",
         "dojo/io-query",
         "dojo/text!me/web/widget/social/templates/detailAccounts.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                registry,
                Form,
                Button,
                main_widget,
                _ENME,
                SocialAccountRow,
                hash,
                ioQuery,
                template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

      /*
       * Template string.
       */
     templateString: template,

     /*
      *
      */
     _menu: false,

     /*
      *
      */
     socialProvider: null,

     /*
      *
      */
     postCreate: function() {
         dojo.subscribe("/encuestame/social/change", this, function( type ) {
             if ( this.id == type.id ) {
                 dojo.removeClass( this.domNode, "defaultDisplayHide");
                 this._callListSocialAccounts();
             } else {
                 dojo.addClass( this.domNode, "defaultDisplayHide");
             }
          });
         dojo.subscribe("/encuestame/social/list/reload", this, "_callListSocialAccounts");
         var myForm = dojo.byId( this._form );
         myForm.setAttribute("action", _ENME.config( "contextPath" ) + "/connect/" + this.socialProvider.toLowerCase() );
         var _hash = ioQuery.queryToObject( hash() );
         if ( _hash.refresh && _hash.refresh ) {
             if ( _hash.successful && _hash.provider == this.socialProvider ) {
                     this._callListSocialAccounts();
             }
          }
       },

       /*
        *
        */
         _autorize: function( event ) {
             dojo.stopEvent( event );
             this._openAuthorizeWindow();
         },

         /*
          *
          */
         _callListSocialAccounts: function() {
             var load = dojo.hitch( this, function( data ) {

                 //Console.debug("social accounts", data);
                 this._listSocialAccounts = data.success.items;
                 dojo.empty( this._list );
                 this._printListOfAccounts( data.success.items );
             });
             var error = function( error ) {

                 //Console.debug("error", error);
             };
             this.getURLService().get("encuestame.service.list.allSocialAccount",
                     { provider: this.socialProvider.toLowerCase() }, load, error, dojo.hitch( this, function() {

             }) );
         },

         /*
          * Display list of accounts on dom node.
          */
         _printListOfAccounts: function( listAccounts ) {

             //Console.debug("list accounts", listAccounts);
             dojo.empty( this._list );
             dojo.forEach
             ( listAccounts,

                 //Process elements dropped.
                 dojo.hitch( this,
                     function( account ) {

                        //Console.debug("account", account);
                            var widget = this._createTwitterAccount( account );
                            this._list.appendChild( widget.domNode );
                     }) );
              },

              /*
           * Create twitter account row on dom node.
           */
          _createTwitterAccount: function( account ) {
              var widget = new SocialAccountRow(
                      {
                          account: account
                       }
                      );
              return widget;
          }

   });
});
