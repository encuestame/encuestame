define([
         "dojo/_base/declare",
         "me/web/widget/options/AbstractOptionSelect",
         "me/core/enme"],
        function(
                declare,
                AbstractOptionSelect,
                _ENME) {
            return declare([ AbstractOptionSelect], {

             /*
              *
              */
             option_value : "results",

             /*
              *
              */
             option_name : "results_",

             /*
              *
              */
             options_label : [
                            _ENME.getMessage('widget_results_options'),
                            _ENME.getMessage('widget_results_only_percents'),
                            _ENME.getMessage('widget_results_all_data')
                            ]
    });
});