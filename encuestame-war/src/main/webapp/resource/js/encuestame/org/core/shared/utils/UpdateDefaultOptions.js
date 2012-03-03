/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.shared.utils.UpdateDefaultOptions");

dojo.require("encuestame.org.core.shared.utils.YesNoWidget");

dojo.declare("encuestame.org.core.shared.utils.UpdateDefaultOptions", null, {

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
        var widget = new encuestame.org.core.shared.utils.YesNoWidget({data: value, optionalParameters : options});
        if (onChange != null) {
            widget._onChange = onChange;
        }
        return widget.domNode;
    }
});