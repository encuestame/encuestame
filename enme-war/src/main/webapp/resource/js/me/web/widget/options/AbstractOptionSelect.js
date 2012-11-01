define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/registry",
         "dijit/form/RadioButton",
         "dijit/form/Form",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/support/PublishSupport",
         "me/core/support/ContextSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/options/templates/options.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                registry,
                RadioButton,
                Form,
                main_widget,
                PublishSupport,
                ContextSupport,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, PublishSupport, ContextSupport, _WidgetsInTemplateMixin], {

         // template string.
         templateString : template,

         options_label : ["option 1", "option 2", "option 3"],

         option_value : "value",

         option_name : "value_comments",

         _default_selected_item : 1,

         _widgets_list : [],

         /*
         *
         */
        postMixInProperties: function() {
            var newName = this.option_name + "_" + this.id;
            this.option_name = newName;
        },

         /*
          *
          */
         postCreate : function() {
             dojo.forEach(this.options_label,
                 dojo.hitch(this,function(label, index) {
                    var selected = false;
                     if (index == this._default_selected_item) {
                         selected = true;
                    }
                    this._createOption(label, selected);
                 }));
         },

         /*
          *
          */
         getResponse : function() {
             var form = registry.byId('options_form_' + this.id);
             if (form) {
                 var value = form.attr('value');
                 return value[this.option_name];
             } else {
                 return null;
             }
         },

         /*
          * create option.
          */
         _createOption : function(title, selected){
             var div = dojo.create("div");
             dojo.addClass(div, "space-option");
             var radioOne = new RadioButton({
                 checked: selected,
                 value: this.option_value,
                 name: this.option_name
             });
             this._widgets_list.push(radioOne);
             div.appendChild(radioOne.domNode);
             /*
              * <label for="name">
                  title
                </label>
              */
             var label = dojo.create("label");
             dojo.addClass(label, "pattern-label");
             label.innerHTML = title;
             label.setAttribute("for", radioOne.id);
             div.appendChild(label);
             this._options.appendChild(div);
         }

  });
});