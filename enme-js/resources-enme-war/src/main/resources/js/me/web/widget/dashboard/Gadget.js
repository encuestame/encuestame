/*
 * Copyright 2013 encuestame
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/***
 *  @author juanpicado19D0Tgm@ilDOTcom
 *  @version 1.146
 *  @module Dashboard
 *  @namespace Widget
 *  @class Gadget
 */
define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/dashboard/template/gadget.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

            /**
              * template string.
              */
            templateString : template,

            /*
             * gadget info.
             */
            data : null,

            /*
             *
             */
            dashboardId  : null,

            /*
             *
             */
            _widgetInside : null,

            /*
             * Post create.
             */
            postCreate: function() {
                dojo.subscribe("/encuestame/dashboard/gadget/remove", this, "_removeGadget");
                if (this.data) {
                    //console.debug("gadget data",  this.data);
                    if (this.data.id) {
                        this.domNode.setAttribute("gid",  this.data.id);
                        this._initialize();
                    }
                }
            },

            /*
             * set gadget title.
             */
            _setTitle : function(title) {
                if (title) {
                    this._title.innerHTML = title;
                }
            },

            /*
             *
             */
            _initialize : function() {
                 var load = dojo.hitch(this, function(data) {
                     if (data.success) {
                         this._widgetInside = this._loadGadget(this.data.gadget_name, {gadgetId : this.data.id});
                         this._widgetInside.placeAt(this._content);
                         this._widgetInside.startup();
                     }
                 });
                 var error = function(error) {
                     console.debug("error", error);
                 };
                 encuestame.service.xhrGet(encuestame.service.gadget.load, {gadgetId: this.data.gadget_name}, load, error);
            },

            /*
             * remove the gadget.
             */
            _removeGadget : function(controlId) {
                //console.info("REMOVING controlId", controlId);
                if (controlId == this.data.id) {
                    console.info("REMOVING gadgetId", this.data.id);
                    var params = { gadgetId: this.data.id, dashboardId: this.dashboardId};
                    var load = dojo.hitch(this, function(data) {
                        if ("success" in data) {
                           dojo.publish("/encuestame/dashboard/gadget/unsubscribe", [this.data.id]);
                           this._widgetInside.destroyRecursive(true);
                           this.destroyRecursive(true);
                        }
                    });
                    var error = function(error) {
                        console.debug("error", error);
                    };
                    encuestame.service.xhrGet(encuestame.service.gadget.remove, params, load, error);
                }
           },

             /*
              *
              * @param type
              * @param params
              * @returns
              */
            _loadGadget : function(type, params) {
                // //console.debug("load gadget", type);
                // if (type == "stream") {
                //     return new Activity(params);
                // } else if (type == "comments") {
                //     return new Comments(params);
                // }  else {
                //    throw new Error("gadget not valid");
                // }
            },

            _editConfiguration : function(){}

    });
});