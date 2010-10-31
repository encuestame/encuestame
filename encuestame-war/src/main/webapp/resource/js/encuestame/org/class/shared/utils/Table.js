dojo.provide("encuestame.org.class.shared.utils.Table");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.ValidationTextBox");
dojo.require("dijit.form.DateTextBox");
dojo.require('encuestame.org.class.commons');

dojo.declare(
    "encuestame.org.class.shared.utils.Table",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.class.shared.utils", "template/Table.inc"),

        /** Allow other widgets in the template. **/
        widgetsInTemplate: true,
        /** Principal service. **/
        jsonServiceUrl : encuestame.service.list.userList,

        limit : 20,

        showPagination : false,

        postMixInProperties: function(){
        },

        postCreate: function() {
            this.loadItems();
        },

        /**
         * Load Users.
         */
        loadItems : function(){
            var load = dojo.hitch(this, function(data){
                console.debug("data", data);
                dojo.forEach(
                        data.success.items,
                        dojo.hitch(this, function(data, index) {
                            console.debug(data, index);
                            this.buildRow(data);
                        }));
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(this.jsonServiceUrl, {limit:10, start:10}, load, error);
        },

        /**
         * Build Row.
         */
        buildRow : function(data){
            var widgetRow = new encuestame.org.class.shared.utils.TableRow({data: data });
            this._body.appendChild(widgetRow.domNode);
        },

        /**
         * Next.
         */
        next : function(event){

        },

        /**
         * Previous.
         */
        previous : function(event){

        },

        /**
         * Last.
         */
        last : function(event){

        },

        /**
         * First.
         */
        first : function(event){

        },


    }
);

dojo.declare(
        "encuestame.org.class.shared.utils.TableRow",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.class.shared.utils", "template/TableRow.inc"),

            /** Allow other widgets in the template. **/
            widgetsInTemplate: true,

            data: null,

            postMixInProperties: function(){
            },

            /**
             * Post Create.
             */
            postCreate: function() {
                this.buildDefaultRow();
            },

            /**
             * Build Default Row.
             */
            buildDefaultRow : function(){
                var data = this.data;
                this.createInput(data.id)
                this.createColumnDialog(data.name);
                this.createColumn(data.email);
                this.createColumn(data.email);
                this.createColumn(data.email);
                this.buildStatus(data.status);
                this.createColumn(data.id);
            },

            /**
             * Build Options.
             */
            buildOptions : function(id){

            },

            /**
             * Create Column.
             */
            createColumnDialog : function(text){
                 var td = dojo.doc.createElement('td');
                 td.innerHTML = text;
                 this._trbody.appendChild(td);
                 dojo.connect(this.source, "onClick", this, this.onDndDrop);
            },



            /**
             * Create Column.
             */
            createColumn : function(text){
                 var td = dojo.doc.createElement('td');
                 td.innerHTML = text;
                 this._trbody.appendChild(td);
            },

            /**
             * Create Input.
             */
            createInput : function(id){
                var widgetInput = new dijit.form.CheckBox({});
                widgetInput.setValue(id);
                this._trbody.appendChild(widgetInput.domNode);
            },

            buildStatus : function(status){
                var td = dojo.doc.createElement('td');
                td.innerHTML = status;
                this._trbody.appendChild(td);
            },
        }
);
