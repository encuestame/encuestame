define( [
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/pictures/AccountPicture",
     "me/web/widget/stream/HashTagInfo",
     "me/core/enme",
     "dojo/text!me/web/widget/publications/templates/publication.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    accountPicture,
    hashTagInfo,
    _ENME,
     template ) {

  return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

     // Template string.
    templateString: template,

    username: "",

      title: "",

      url: "",

      added: "",

      relativeTime: "",

      displayImage: true,

      itemId: "",

      ht: "",

      type: "",

      _tagsCloned: [],

      postMixInProperties: function() {
          if ( this.ht !== null ) {

            //Console.info("this.ht.trim().split(",");", this.ht.trim().split(","));
              this._tagsCloned = this.ht.trim().split(",");
          }
      },

      /*
       * Post create.
       */
      postCreate: function() {
          var node = this._tags;

          if ( !this.displayImage && this._picture ) {
              dojo.destroy( this._picture );
          }

          dojo.forEach( this._tagsCloned, function( entry, i ) {
            if ( entry !== "" ) {
                var widget = new hashTagInfo(
                        {
                          hashTagName: entry,
                          autoCreateUrl: true, //Create url automatically
                          cssClass: "tag"
                        });
                node.appendChild( widget.domNode );
            }
          });

          //TODO: ????????
          var nodeCustom;
          if ( dojo.indexOf( _ENME.SURVEYS, dojo.trim( this.type ) ) === 0 ) { //Tp

          } else if ( dojo.indexOf( _ENME.SURVEYS, dojo.trim( this.type ) ) == 1 ) { //Pll

          } else if ( dojo.indexOf( _ENME.SURVEYS, dojo.trim( this.type ) ) === 0 ) { //Survey

          } else {

              //Error
          }
      }

  });
});
