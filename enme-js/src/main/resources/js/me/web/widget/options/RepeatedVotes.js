define( [
         "dojo/_base/declare",
         "me/web/widget/options/ConstrainNumberPicker",
         "me/core/enme" ],
        function(
                declare,
                ConstrainNumberPicker,
                _ENME ) {
            return declare( [ ConstrainNumberPicker ], {

           /*
            *
            */
           label: _ENME.getMessage( "widget_repated_votes" ),

           /*
            * To enable publish support, replace null value for publish valid url.
            * eg: /encuestame/tweetpoll/autosave
            */
           publish_url: null,

           /*
            *
            */
           constraints: "{min:2, max:10}"
    });
});
