dojo.provide("encuestame.org.core.shared.utils.TimeRangeDropDownMenu");

dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.DropDownMenuSelect');

dojo.declare("encuestame.org.core.shared.utils.TimeRangeDropDownMenu",
        [ encuestame.org.core.shared.utils.DropDownMenuSelect ], {

            /*
             * default text.
             */
            defaultText : "Select the Action...",

            /*
             * post create.
             */
            postCreate : function() {
            	this.inherited(arguments);
            },

            /*
             * add item on drop down menu.
             */
            _addItem : function(node) {
                dojo.place(node, this._items);
            }

});
