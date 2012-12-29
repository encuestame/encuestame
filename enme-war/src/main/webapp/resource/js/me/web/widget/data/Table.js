define([
         "dojo/_base/declare",
         "dojo/dom-construct",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/data/TableRow",
         "me/core/enme",
         "dojo/text!me/web/widget/data/templates/Table.html" ],
        function(
                declare,
                domConstruct,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /** Principal service. **/
            jsonServiceUrl : null,

            limit : 10,

            start : 0,

            total : null,

            showPagination : false,

            postMixInProperties: function(){
            },

            postCreate: function() {
                this.loadItems();
            },

            cleanTable : function(){
                if(this._body){
                    dojo.empty(this._body);
                }
            },

            /**
             * Load Users.
             */
            loadItems : function(){
                var load = dojo.hitch(this, function(data){
                    console.debug("data", data);
                    this.cleanTable();
                    this.iterateResponseItems(data);
                });
                var error = dojo.hitch(this, function(error) {
                    console.debug("error table", error);
                });
                //encuestame.service.xhrGet(this.jsonServiceUrl, {limit: this.limit, start: this.start}, load, error);
                this.getURLService().get(this.jsonServiceUrl, {limit: this.limit, start: this.start}, load, error , dojo.hitch(this, function() {}));
            },

            /**
             * Iterate Response Items.
             */
            iterateResponseItems : function(response){
                console.error('this function should be override');
            },

            /**
             * Error Response.
             */
            errorResponse : function(error){
                console.error('this function should be override');
            },

            /**
             * Build Row.
             */
            buildRow : function(data){
                var widgetRow = new TableRow({data: data });
                this._body.appendChild(widgetRow.domNode);
            },

            /**
             * Next.
             */
            next : function(event){
                dojo.stopEvent(event);
                this.start = this.start + this.limit;
                this.loadItems();
            },

            /**
             * Previous.
             */
            previous : function(event){
                dojo.stopEvent(event);
                this.start = this.start - this.limit;
                if(this.start < 0){
                    this.start = 0;
                }
                this.loadItems();
            },

            /**
             * Last.
             */
            last : function(event){
                dojo.stopEvent(event);
            },

            /**
             * First.
             */
            first : function(event){
              dojo.stopEvent(event);
              this.start = 0;
              this.loadItems();
            }

    });
});



//
// dojo.provide("encuestame.org.core.shared.utils.Table");

// dojo.require("dijit._Templated");
// dojo.require("dijit._Widget");
// dojo.require("dijit.form.CheckBox");
// dojo.require("dijit.form.Form");
// dojo.require("dijit.form.Button");
// dojo.require("dijit.form.ValidationTextBox");
// dojo.require("dijit.form.DateTextBox");
// dojo.require('encuestame.org.core.commons');

// dojo.declare(
//     "encuestame.org.core.shared.utils.Table",
//     [dijit._Widget, dijit._Templated],{
//         templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/Table.html"),

//         /** Allow other widgets in the template. **/
//         widgetsInTemplate: true,

//         }
// );
