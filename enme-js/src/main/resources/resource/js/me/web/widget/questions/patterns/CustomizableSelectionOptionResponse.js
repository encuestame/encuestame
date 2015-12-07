define( [
     "dojo/_base/declare",
     "me/web/widget/questions/patterns/AbstractPattern",
     "me/web/widget/questions/patterns/AbstractSoundResponse",
     "me/web/widget/questions/patterns/AbstractVideoResponse",
     "me/web/widget/questions/patterns/AbstractImageResponse",
     "me/web/widget/questions/patterns/AbstractAddNewAnswer",
     "me/web/widget/questions/patterns/AbstractMultipleSelection",
     "me/core/enme" ],
    function(
    declare,
    AbstractPattern,
    AbstractSoundResponse,
    AbstractVideoResponse,
    AbstractImageResponse,
    AbstractAddNewAnswer,
    AbstractMultipleSelection,
    _ENME ) {

  return declare( [ AbstractPattern,
                  AbstractSoundResponse,
                  AbstractVideoResponse,
                  AbstractImageResponse,
                  AbstractAddNewAnswer,
                  AbstractMultipleSelection ], {

        /**
         * Enable add new response.
         */
        addNewResponse: false,

        /**
         *
         */
        name: "name",

        /**
         *
         */
        value: "",

        /**
         *
         */
        itemId: "",

        /**
         * Define the default of type of answers.
         */
        type: "DEFAULT"

        // /**
        //  * Post Create.
        //  */
        // postCreate : function() {
        //     if (this.multiple) {
        //         this.initializeMultipleSupport(this.name, this._option);
        //     } else {
        //         this._createSimpleButton(this.name, this._option, this.value, this.itemId);
        //     }
        //     if (this.addNewResponse) {
        //         this.initializeAddNewResponseSupport();
        //     }
        //     this.inherited(arguments);
        // }

  });
});
