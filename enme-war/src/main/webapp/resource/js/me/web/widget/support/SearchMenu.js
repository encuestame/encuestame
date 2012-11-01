define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/TextBox",
         "dijit/form/MultiSelect",
         "dijit/form/CheckBox",
         "dijit/form/HorizontalRule",
         "dijit/form/HorizontalRuleLabels",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/support/AbstractFilterSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/support/templates/search-filters.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                TextBox,
                MultiSelect,
                CheckBox,
                HorizontalRule,
                HorizontalRuleLabels,
                main_widget,
                AbstractFilterSupport,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, AbstractFilterSupport, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /*
            * i18n message for this widget.
            */
           i18nMessage : {
             detail_manage_filters_advanced_type_to_search : _ENME.getMessage("detail_manage_filters_advanced_type_to_search"),
             detail_manage_filters_advanced_title : _ENME.getMessage("detail_manage_filters_advanced_title"),
             detail_manage_published : _ENME.getMessage("detail_manage_published"),
             detail_manage_unpublished : _ENME.getMessage("detail_manage_unpublished"),
             detail_manage_filters_advanced_all_results : _ENME.getMessage("detail_manage_filters_advanced_all_results"),
             detail_manage_scheduled : _ENME.getMessage("detail_manage_scheduled"),
             detail_manage_favorites : _ENME.getMessage("detail_manage_favorites"),
             detail_manage_by_account : _ENME.getMessage("detail_manage_by_account"),
             detail_manage_filters_advanced_range_days : _ENME.getMessage("detail_manage_filters_advanced_range_days"),
             detail_manage_search : _ENME.getMessage("detail_manage_search"),
             detail_manage_only_completed : _ENME.getMessage("detail_manage_only_completed"),
           }

    });
});