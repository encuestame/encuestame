define( [
         "dojo/_base/declare",
         "me/web/widget/options/AbstractOptionSelect",
         "me/core/enme" ],
        function(
                declare,
                AbstractOptionSelect,
                _ENME ) {
            return declare( [ AbstractOptionSelect ], {

        /*
         *
         */
        _default_selected_item: 1,

        /*
         *
         */
        option_name: "comments_",

        /*
         *
         */
        options_label: [
            {
                label: _ENME.getMessage( "widget_comments_allow" ),
                value: "ALL"
            },
            {
                label: _ENME.getMessage( "widget_comments_moderated" ),
                value: "MODERATE"
            },
            {
                label: _ENME.getMessage( "widget_comments_no_comments" ),
                value: "RESTRICT"
            }]
    });
});
