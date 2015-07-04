define([
     "dojo/_base/declare",
     "dojo/query",
     "dojo/dom-class",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/menu/SettingsButton",
     "me/core/enme",
     "dojo/text!me/web/widget/menu/template/settings_switch.html" ],
    function(
            declare,
            query,
            domClass,
            _WidgetBase,
            _TemplatedMixin,
            _WidgetsInTemplateMixin,
            main_widget,
            SettingsButton,
            _ENME,
             template) {

        "use strict";

        return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

        /**
         * template string.
         */
        templateString : template,

        /**
         *
         */
        _widget_references : [],

        /**
         *
         */
        helpSteps : [
            {
                element: '.listSettings',
                intro: _ENME.getMessage('help_settings_accounts_sidebar')
            },
            {
                element: '.settingsListDetail',
                intro: _ENME.getMessage('help_settings_accounts_list_view')
            }
        ],

        /**
         * Post create.
         */
        postCreate : function() {
          query("> [data-dojo-type]", this.srcNodeRef).forEach(
             dojo.hitch(this, function(node) {
                var enabled = node.getAttribute("data-enabled");
                this._createDetail(node.getAttribute('id'), node.getAttribute('data-label'), node, enabled);
                this._cretateButton(node.getAttribute('id'), node.getAttribute('data-label'), node.getAttribute('data-label'), enabled);
             }));
           // hide details
           dojo.subscribe("/encuestame/settings/hide/all", this, dojo.hitch(this, function(type) {
             query("div.setting-detail", this._detail).forEach(
                   dojo.hitch(this, function(node) {
                     domClass.add(node, "hidden");
                   })
               );
           }));
           //help links
           this.initHelpLinks(dojo.hitch(this, function(){
               this.updateHelpPageStatus(_ENME.config('currentPath'), true);
           }));
        },

        /**
         *
         * @param id
         * @param provider
         * @param widget
         * @param enabled
         * @private
         */
        _createDetail : function(id, provider, widget, enabled) {
           //_widget_references.
           if (!_ENME.getBoolean(enabled)) {
             domClass.add(widget, "hidden");
           }
             domClass.add(widget, "setting-detail");
             this._detail.appendChild(widget);
        },

        /**
         * Create a settings button.
         * @param id
         * @param label
         * @param provider
         * @param enabled
         * @private
         */
         _cretateButton : function(id, label, provider, enabled) {
             var widget = new SettingsButton({
                         id : id + "_button",
                         ref_id : id,
                         label : label,
                         provider : provider
                     });
             this._buttons.appendChild(widget.domNode);
             if (_ENME.getBoolean(enabled)) {
                domClass.add(widget.domNode, "selected");
             }
         }

    });
});