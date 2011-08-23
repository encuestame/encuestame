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
dojo.provide("encuestame.org.core.shared.options.AbstractOptionSelect");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.RadioButton");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.ContextSupport');
dojo.require('encuestame.org.core.shared.utils.PublishSupport');

/**
 * Represents a option to repeate votes.
 */
dojo.declare(
    "encuestame.org.core.shared.options.AbstractOptionSelect",
    [dijit._Widget,
     dijit._Templated,
     encuestame.org.core.shared.utils.ContextSupport,
     encuestame.org.core.shared.utils.PublishSupport],{

     /*
      *
      */
     templatePath: dojo.moduleUrl("encuestame.org.core.shared.options", "templates/options.html"),

     options_label : ["option 1", "option 2", "option 3"],

     option_value : "value",

     option_name : "value_"+this.id,

     _widgets_list : [],

     /*
      * Allow other widgets in the template.
      */
     widgetsInTemplate: true,

     /*
      *
      */
     postCreate : function() {
         dojo.forEach(this.options_label,
             dojo.hitch(this,function(item) {
             console.info(item);
                   this._createOption(item);
             }));
     },

     /*
      * create option.
      */
     _createOption : function(title){
         var div = dojo.create("div");
         var radioOne = new dijit.form.RadioButton({
             checked: false,
             value: this.option_value,
             name: this.option_name,
         });
         this._widgets_list.push(radioOne);
         div.appendChild(radioOne.domNode);
         /*
          * <label for="name">
              title
            </label>
          */
         var label = dojo.create("label");
         label.innerHTML = title;
         label.setAttribute("for", radioOne.id);
         div.appendChild(label);
         this._options.appendChild(div);
     },
});