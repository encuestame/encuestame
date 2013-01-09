define([
     "dojo/_base/declare",
     "me/web/widget/questions/patterns/AbstractPattern",
     "me/web/widget/questions/patterns/AbstractSoundResponse",
     "me/web/widget/questions/patterns/AbstractVideoResponse",
     "me/web/widget/questions/patterns/AbstractImageResponse",
     "me/web/widget/questions/patterns/AbstractAddNewAnswer",
     "me/web/widget/questions/patterns/AbstractMultipleSelection",
     "me/core/enme"],
    function(
    declare,
    AbstractPattern,
    AbstractSoundResponse,
    AbstractVideoResponse,
    AbstractImageResponse,
    AbstractAddNewAnswer,
    AbstractMultipleSelection,
    _ENME) {

  return declare([AbstractPattern,
                  AbstractSoundResponse,
                  AbstractVideoResponse,
                  AbstractImageResponse,
                  AbstractAddNewAnswer,
                  AbstractMultipleSelection],{

        // /**
        //  * Post Create.
        //  */
        // postCreate : function() {
        //     this.inherited(arguments);
        // }

  });
});