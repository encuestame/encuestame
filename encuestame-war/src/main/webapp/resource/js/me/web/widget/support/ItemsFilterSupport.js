define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/support/Wipe",
         "me/web/widget/support/SearchMenu",
         "me/web/widget/support/OrderMenu",
         "me/web/widget/support/SocialFilterMenu",
         "me/web/widget/support/VotesFilterMenu",
         "me/core/enme",
         "dojo/text!me/web/widget/support/templates/filters.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                Wipe,
                SearchMenu,
                OrderMenu,
                SocialFilterMenu,
                VotesFilterMenu,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

           /*
            * template string.
            */
           templateString : template,

            /*
            * widgets.
            */
           optionsWidget : { search : null, filter : null, order : null, social : null, votes : null},

           /*
            * i18n message for this widget.
            */
           i18nMessage : {
               detail_manage_filters_advanced : _ENME.getMessage("detail_manage_filters_advanced"),
               detail_manage_filters_order : _ENME.getMessage("detail_manage_filters_order"),
               detail_manage_filters_social_network : _ENME.getMessage("detail_manage_filters_social_network"),
               detail_manage_filters_votes_options : _ENME.getMessage("detail_manage_filters_votes_options"),
           },

           /*
            * options
            */
           _wipe : { duration : 100},

           /*
            * type support.
            */
           typeItem : "",

           /*
            *
            */
           postCreate : function() {
               dojo.subscribe("/encuestame/filters/selected/remove", this, "_hideAllSelected");
               dojo.connect(this._search, "onclick", dojo.hitch(this, this._openSearch));
               dojo.connect(this._order, "onclick", dojo.hitch(this, this._openOrder));
               dojo.connect(this._social, "onclick", dojo.hitch(this, this._openSocial));
               dojo.connect(this._votes, "onclick", dojo.hitch(this, this._openVotes));
               this.optionsWidget.search = new Wipe(this._search_o, this._wipe.duration, 270, "tp-options", "1");
               //FUTURE: disabled
               //this.optionsWidget.order = new encuestame.org.core.commons.support.Wipe(this._order_o, this._wipe.duration, 140, "tp-options", "3");
               this.optionsWidget.social = new Wipe(this._social_o, this._wipe.duration, 200, "tp-options", "4");
               //FUTURE: disabled
               //this.optionsWidget.votes = new encuestame.org.core.commons.support.Wipe(this._votes_o, this._wipe.duration, 140, "tp-options", "5");
           },

           /*
            *
            */
           _openSearch : function(event) {
               dojo.publish("/encuestame/wipe/close", [this.optionsWidget.search.id, "tp-options"]);
               this._hideAllSelected();
               this.optionsWidget.search.togglePanel(this._search);
               dojo.addClass(this, "selected");
            },

            /*
             *
             */
            _hideAllSelected : function() {
                dojo.query('.web-filters-options nav a').forEach(function(node, index, arr){
                    dojo.removeClass(node, "selected");
                });;
            },

           /*
            *
            */
           _openFilter : function(event) {
               dojo.publish("/encuestame/wipe/close", [this.optionsWidget.filter.id, "tp-options"]);
               this._hideAllSelected();
               this.optionsWidget.search.togglePanel(this._search);
               dojo.addClass(this, "selected");
            },

           /*
            *
            */
           _openOrder : function(event) {
  //             dojo.publish("/encuestame/wipe/close", [this.optionsWidget.order.id, "tp-options"]);
  //             this._hideAllSelected();
  //             this.optionsWidget.order.togglePanel(this._order);
            },

           /*
            *
            */
           _openSocial : function(event) {
               dojo.publish("/encuestame/wipe/close", [this.optionsWidget.social.id, "tp-options"]);
               this._hideAllSelected();
               this.optionsWidget.social.togglePanel(this._social);
            },

           /*
            *
            */
           _openVotes : function(event) {
  //             dojo.publish("/encuestame/wipe/close", [this.optionsWidget.votes.id, "tp-options"]);
  //             this._hideAllSelected();
  //             this.optionsWidget.votes.togglePanel(this._votes);
            }
    });
});