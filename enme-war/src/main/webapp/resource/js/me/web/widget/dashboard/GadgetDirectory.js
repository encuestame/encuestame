define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/enme",
         "dojo/text!me/web/widget/dashboard/template/directory.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /*
            * dasboard widget.
            */
           dashboardWidget : null,

           /*
            * post create.
            */
           postCreate : function(){
               this._loadDirectory();
           },

           /*
            * load the gadget directory.
            * gadgets: [
               {
                   name: "stream"
                   id: "stream"
                   description: "abc abc abc"
                   image: "/images/gadgets/activity.png"
               }
               -
               {
                   name: "comments"
                   id: "comments"
                   description: "abc abc abc abc"
                   image: "/images/gadgets/comments.png"
               }
               -
               {
                   name: "tweetpoll-votes"
                   id: "tweetpoll-votes"
                   description: "abc abc abc abc"
                   image: "/images/gadgets/tweetpoll-votes.png"
               }
   ]
            */
           _loadDirectory : function(){
               var load = dojo.hitch(this, function(data){
                   if(data.success){
                       var gadgets = data.success.gadgets;
                        dojo.forEach(
                                gadgets,
                                dojo.hitch(this, function(data, index) {
                                    var node = this._createItemDirectory(data);
                                    this._dir.appendChild(node);
                        }));
                   }
               });
               var error = function(error) {
                   console.debug("error", error);
               };
               encuestame.service.xhrGet(this.getURLService().service('encuestame.service.gadget.directory'), {}, load, error);
           },

           /*
            * create directory item.
            * @param gadget
            * @returns
            */
           _createItemDirectory : function(gadget) {
               var item = dojo.create("div");
               item.setAttribute("ga", gadget.id);
               dojo.addClass(item, "web-directory-item");
               var title = dojo.create("div", { innerHTML: "<div>"+gadget.name+"</div>" }, item);
               dojo.addClass(title, "web-directory-item-title");
               var description = dojo.create("div", { innerHTML: "<p>"+gadget.description+"</p>" }, item);
               dojo.addClass(description, "web-directory-item-description");
               var actions = dojo.create("div", null, item);
               dojo.addClass(actions, "web-directory-item-actions");
               var a = dojo.create("a", { innerHTML: "Add" }, actions);
               dojo.connect(a, "onclick", dojo.hitch(this, function() {
                   dojo.publish("/encuestame/dashboard/gadget/add", [this.dashboardWidget._dasboardId, gadget.id]);
               }));
               return item;
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
//dojo.provide("encuestame.org.core.commons.dashboard.GadgetDirectory");
//
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//
//dojo.declare(
//    "encuestame.org.core.commons.dashboard.GadgetDirectory",
//    [dijit._Widget, dijit._Templated],{
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.dashboard", "template/directory.html"),
//

//});