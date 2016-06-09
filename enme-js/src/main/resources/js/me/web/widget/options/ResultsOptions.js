define( [
     "dojo/_base/declare",
     "me/web/widget/options/AbstractOptionSelect",
     "me/core/enme" ],
function(
        declare,
        AbstractOptionSelect,
        _ENME ) {
        return declare( [ AbstractOptionSelect ], {

        /**
         *
         */
        option_name: "results_",

        /**
         *
         */
        options_label: [
            {
                label: _ENME.getMessage( "widget_results_options" ),
                value: "RESTRICTED"
            },
            {
                label: _ENME.getMessage( "widget_results_only_percents" ),
                value: "PERCENT"
            },
            {
                label: _ENME.getMessage( "widget_results_all_data" ),
                value: "ALL"
            }]
        });
});
