define([
     "dojo/_base/declare",
     "me/core/enme"],
    function(
    declare,
    _ENME) {

  return declare(null, {

     /**
      * Represent the unique item id of the result.
      */
     itemId : null,

     /**
      * Represent the label of the result / answer.
      */
     labelResponse : null,

     /**
      * Represent the color of the result.
      */
     color : null,

     /**
      * The current total of votes.
      */
     votes: null,

     /**
      * The question id.
      */
     questionId : null,

     /**
      * Constructor.
      */
    constructor : function() {}

  });
});