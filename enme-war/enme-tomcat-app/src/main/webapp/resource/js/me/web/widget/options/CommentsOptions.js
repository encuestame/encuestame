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
            option_value : "comments",

            /*
             *
             */
            _default_selected_item : 1,

            /*
             *
             */
            option_name : "comments_",

            /*
             *
             */
            options_label : [
                            _ENME.getMessage('widget_comments_allow'),
                            _ENME.getMessage('widget_comments_moderated'),
                            _ENME.getMessage('widget_comments_no_comments')
                            ]
    });
});