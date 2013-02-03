define([
         "dojo/_base/declare",
         'dojo/_base/json',
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
                json,
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
            * save the status of all components.
            */
           _status : {
               keyword : null,
                _published : true,
               social_networks : []
           },

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
             detail_manage_only_completed : _ENME.getMessage("detail_manage_only_completed")
           },

           /*
            * the key to restore / save the status of the widget in the browser
            */
           _key_save : 'filter-search',


           /*
            * Customized method to restore the status.
            */
           _buildStatusObject : function (status) {
              this._setValues(status);
           },


           /*
            * Clean the search menu widget.
            */
           clean : function () {
              // clean the keyword field
             this._setValues({
                  keyword : null,
                  social_networks : [],
                  _published : true,
                  _complete : null,
                  _unpublished : null
              });
           },

           /*
            *
            */
           _setValues : function (status) {
                this._keyword.set('value', status.keyword);
                this._social.set('value', status.social_networks);
                this._published.set('checked', status._published);
                this._unpublished.set('checked', status._unpublished);
                this._complete.set('checked', status._complete);
           },

           /*
            * post create life cycle.
            */
           postCreate : function () {
               // restore
               this._restoreStatus();

               this._keyword.onChange = dojo.hitch(this, function(e) {
                   this._status.keyword = this._keyword.get('value');
                   this._saveStatus(this._status);
               });

               this._social.onChange = dojo.hitch(this, function(e) {
                  this._status.social_networks = this._social.get('value');
                  this._saveStatus(this._status);
               });

               this._published.onChange = dojo.hitch(this, function(e) {
                   this._status._published = this._published.get('checked');
                   this._saveStatus(this._status);
               });

               this._unpublished.onChange = dojo.hitch(this, function(e) {
                   this._status._unpublished = this._unpublished.get('checked');
                   this._saveStatus(this._status);
               });

               this._complete.onChange = dojo.hitch(this, function(e) {
                   this._status._complete = this._complete.get('checked');
                   this._saveStatus(this._status);
               });
           }
    });
});