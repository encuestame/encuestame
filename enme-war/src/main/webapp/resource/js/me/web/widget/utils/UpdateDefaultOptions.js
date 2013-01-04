//dojo.require('dojox.timing');
define([
     "dojo/_base/declare",
     "me/web/widget/options/YesNoWidget",
     "me/core/enme"],
    function(
    declare,
    YesNoWidget,
    _ENME) {

  return declare(null, {

        _node : null,

        /**
         * Append detail node to template.
         * @param node
         */
        addDetail : function(node){
            this._node.appendChild(node);
        },

        /**
         * Set the node to append items.
         * @param node
         */
        setNodeAppend : function(node) {
            this._node = node;
        },

        /**
         * Add new row to template.
         * @param title label
         * @param data initial data
         * @param refFunction function to trigger on change data
         */
        addRow : function(title, data, refFunction, options ) {
            this.addDetail(this.builDetailRow(title, this.addYesNoWidget(data, refFunction, options)));
        },

        /**
         * Build Detail Row.
         */
        builDetailRow : function(labelText, dataContet) {
            var rowDetail = dojo.create('div');
            dojo.addClass(rowDetail, "rownDetail");
            var label = dojo.doc.createElement('div');
            dojo.addClass(label, "label");
            var labelItem = dojo.doc.createElement('label');
            labelItem.innerHTML = labelText;
            label.appendChild(labelItem);
            rowDetail.appendChild(label);
            var data = dojo.doc.createElement('div');
            dojo.addClass(data, "data");
            data.appendChild(dataContet);
            rowDetail.appendChild(data);
            return rowDetail;
        },


        /**
         * Create  Yes / No Widget.
         */
        addYesNoWidget : function(value, onChange, options){
            var widget = new YesNoWidget({data: value, optionalParameters : options});
            if (onChange != null) {
                widget._onChange = onChange;
            }
            return widget.domNode;
        }

  });
});