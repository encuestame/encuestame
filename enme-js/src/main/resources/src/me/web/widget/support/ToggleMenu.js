
/**
 * Toggle Menu Support.
 * @author Picado, Juan juanATencuestame.org
 * @since 14/01/12
 */
define( [
     "dojo/_base/declare",
     "me/core/enme" ],
    function(
    declare,
    _ENME ) {

  return declare( null, {

   /*
    *
    */
   _openBox: false,

   /*
    *
    */
   _classReplace: "",

   /*
    * Add switch menu support to node based on a defined event.
    * @param event
    * @param node
    */
   addMenuSupport: function( node ) {
       dojo.connect( node, "onclick", this, this._switchMenu );
   },

   /*
    * Switch the menu based on boolean value.
    * @event set by dojo.
    */
   _switchMenu: function( event ) {

       //Console.debug("_switchMenu", this._classReplace);
       //console.debug("_switchMenu", this._menu);
       //console.debug("_switchMenu", this._openBox);
       dojo.stopEvent( event );
       if ( this._openBox ) {
           dojo.removeClass( this._menu, this._classReplace );
           this._openBox = false;
       } else {
           dojo.addClass( this._menu, this._classReplace );
           this._openBox = true;
       }
   }

  });
});
