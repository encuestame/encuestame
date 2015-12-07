define( [
 "dojo/_base/declare",
 "dijit/_WidgetBase",
 "dijit/_TemplatedMixin",
 "dijit/_WidgetsInTemplateMixin",
	"me/web/widget/pictures/AccountPicture",
 "me/core/main_widgets/EnmeMainLayoutWidget",
 "me/web/widget/stream/HashTagInfo",
 "me/web/widget/support/ToggleMenu",
 "me/core/enme",
 "dojo/text!me/web/widget/profile/templates/profileMenu.html" ],
function(
        declare,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        AccountPicture,
        main_widget,
        hashTagInfo,
        toggleMenu,
        _ENME,
         template ) {
    return declare( [ _WidgetBase, _TemplatedMixin, main_widget, toggleMenu, _WidgetsInTemplateMixin ], {

  // Template string.
    templateString: template,

    /*
    * The complete username
    */
   completeName: "",

   /*
    * The logged username
    */
   username: "",

   /*
    * Class to replace on toggle event is triggered
    */
   _classReplace: "profileOpenPanel",

   /*
    * Post create life cycle.
    */
  postCreate: function() {

      // Add the menus, find the nodes before render the template
      // to create all menu nodes.
      var append_node = this._nodes;
      dojo.query("div.profile-menu", this.srcNodeRef ).forEach(
              dojo.hitch( this, function( node ) {
                 var li = dojo.create( "li" ),
                 a = dojo.create( "a" ),
                 url = node.getAttribute( "data-url" ),
                 label = node.innerHTML;
                 a.href = url;
                 a.innerHTML = label;
                 a.id = "profile-menu-" + label;
                 li.appendChild( a );
                 append_node.appendChild( li );
              })
       );

    //Set the toggle menu support
      this.addMenuSupport( this._image );
      this.addMenuSupport( this._name );

  }

    });
});
