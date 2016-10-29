/**
 * Wipe Support.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */

//Dojo.require("dojox.fx");
define( [
     "dojo/_base/declare",
     "dojo/topic",
     "dojo/dom-class",
     "dojo/fx",
     "me/core/enme" ],
    function(
    declare,
    topic,
    domClass,
    coreFx,
    _ENME ) {

  return declare( null, {

      /*
      * Node.
      */
     node: null,
     /*
      * Duration.
      */
     duration: 200,
     /*
      * Height.
      */
     height: 300,

     /*
      * Default status.
      */
     _open: false,

     /*
      * Default group.
      */
     group: "default",

     /*
      * Id.
      */
     id: "",

     /*
      * Constructor of wipe.
      * @param {String} node
      * @param {Number} duration
      * @param {Number} height
      * @param {String} group
      * @param {Number} id
      */
     constructor: function( node, duration, height, group, id ) {
         dojo.subscribe("/encuestame/wipe/close", this, dojo.hitch( this, function( id, group ) {
           this._close( id, group );
         }) );
         dojo.subscribe("/encuestame/wipe/close/group", this, dojo.hitch( this, function( id, group ) {
           this._group( id, group );
         }) );
         this.node = node;
         this.duration = ( duration === null ) ? this.duration : duration;
         this.height = ( height === null ) ? this.height : height;
         this.group = ( group === null ) ? this.group : group;
         this.id = ( id === null ) ? this.id : id;
     },

    /*
     * On wite in.
     */
    wipeInOne: function() {
        coreFx.wipeIn({
            node: this.node,
            duration: this.duration,
            height: this.height
        }).play();
    },

    /*
     * Close the wipe.
     */
    _group: function( group ) {
        if ( group === this.group ) {
            this.wipeOutOne();
            this._open = false;
        }
    },

    /*
     * Close the wipe.
     */
    _close: function( id, group ) {
        if ( id !== this.id && group === this.group ) {
            this.wipeOutOne();
            this._open = false;
        }
    },

    /*
     * On wipe out.
     */
    wipeOutOne: function() {
        if ( this.node ) {
            coreFx.wipeOut({
                node: this.node,
                duration: this.duration
            }).play();
        }
    },

    /*
     * Provide toggle suport to wipe panel.
     */
    togglePanel: function( node ) {
        if ( this._open ) {
            this.wipeOutOne();
         } else {
            this.wipeInOne();
            if ( node !== null ) {
                domClass.add( node, "selected");
            }
         }
         this._open = !this._open;
    }

  });
});
