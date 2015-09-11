/**
 * Represents a option to limit votes.
 */
define([
         "dojo/_base/declare",
         "me/web/widget/options/ConstrainNumberPicker",
         "me/core/enme"],
        function(
                declare,
                ConstrainNumberPicker,
                _ENME) {
            return declare([ ConstrainNumberPicker], {

           /*
            *
            */
           label : _ENME.getMessage('widget_limit_votes'),

           /*
            * to enable publish support, replace null value for publish valid url.
            * eg: /encuestame/tweetpoll/autosave
            */
           publish_url : null,

           /*
            *
            */
            constraints_custom : {min:1, max:4000},

            /**
             *
             */
            postCreate: function(){
                this.inherited(arguments);
            }
    });
});