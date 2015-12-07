/**
 * Panel Wipe Support.
 * @author Picado, Juan juanATencuestame.org
 */

//Dojo.require("dojox.fx");
define( [
     "dojo/_base/declare",
     "dojo/fx",
     "me/core/enme" ],
    function(
    declare,
    coreFx,
    _ENME ) {

  return declare( null, {

    /*
    *
    */
   content: null,

   /*
    *
    */
   duration: 200,

   /*
    *
    */
   height: 300,

   /*
    *
    */
   selected: false,

   /*
    *
    */
   constructor: function( /* node */ content, /** title */ title, /* selected by default */ selected, heightOp ) {
       this.height = heightOp === null ? this.height :  heightOp;
       if ( content ) {
           this.content = content;
       } else {
           throw new Error("content is required");
       }
   },

  /**
   *
   */
  wipeInOne: function() {

      //Console.info("connect wipeInOne", this.content);
      this.preWipe();
      coreFx.wipeIn({
           node: this.content,
          duration: this.duration,
          height: this.height
      }).play();
  },

  /*
   *
   */
  wipeOutOne: function() {

      //Console.info("connect wipeOutOne", this.content);
      if ( this.content ) {
          this.postWipe();
          coreFx.wipeOut({
              node: this.content,
              duration: this.duration
          }).play();
      }
  },

  /**
   * Event called after wipe.
   */
  preWipe: function() {

  },

  /**
   * Event called before wipe.
   */
  postWipe: function() {

  },

   // Connect the node with wipe effect
   connect: function( node, functionCall ) {

       //Console.info("connect with", node);
       if ( node ) {
           dojo.connect( node, "onclick", dojo.hitch( this, function( event ) {

               //Console.info("connect click", node);
               if ( this.selected ) {
                   this.wipeOutOne();
               } else {
                    this.wipeInOne();
                    functionCall();
               }
               this.selected = !this.selected;

               //Console.info("connect click", this.selected);
           }) );
       }
   }

  });
});
