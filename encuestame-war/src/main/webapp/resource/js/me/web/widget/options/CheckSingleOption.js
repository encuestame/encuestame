define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/CheckBox",
         "dijit/registry",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/options/templates/checkSingleOption.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                CheckBox,
                registry,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /*
            * option value.
            */
           option_value : "value",

           /*
            * label.
            */
           label : "value",

           /*
            *
            */
           option : {checked : false, value : ""},

           /*
            *
            */
           checkWidget: null,

           /*
            *
            */
           postCreate : function() {
               this.checkWidget = registry.byId("check_"+this.id);
               this.option.value = this.checkWidget.get('value');
               this.checkWidget.onChange = dojo.hitch(this, function(event){
                   this.option.checked = event;
               });
           },

           /*
           *
           */
          getValue : function() {
             return this.option;
          }
    });
});

///*
// ************************************************************************************
// * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
// * encuestame Development Team.
// * Licensed under the Apache Software License version 2.0
// * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
// * Unless required by applicable law or agreed to  in writing,  software  distributed
// * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
// * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
// * specific language governing permissions and limitations under the License.
// ************************************************************************************
// */
//dojo.provide("encuestame.org.core.shared.options.CheckSingleOption");
//
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//dojo.require("dijit.form.CheckBox");
//dojo.require('encuestame.org.core.commons');
//dojo.require('encuestame.org.core.shared.utils.ContextSupport');
//dojo.require('encuestame.org.core.shared.utils.PublishSupport');
//
///**
// * Represents a option to repeate votes.
// */
//dojo.declare(
//    "encuestame.org.core.shared.options.CheckSingleOption",
//    [dijit._Widget,
//     dijit._Templated,
//     encuestame.org.core.shared.utils.ContextSupport,
//     encuestame.org.core.shared.utils.PublishSupport],{
//
//     /*
//      *
//      */
//     templatePath: dojo.moduleUrl("encuestame.org.core.shared.options", "templates/checkSingleOption.html"),
//

//});