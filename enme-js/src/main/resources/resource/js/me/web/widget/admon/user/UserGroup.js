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
 *  @module Admon
 *  @namespace Widget
 *  @class UserGroup
 */
define([
         "dojo/_base/declare",
         "dojo/dom-construct",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/TextBox",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/admon/user/UserGroup",
         "me/core/enme",
         "dojo/text!me/web/widget/admon/user/template/UserGroup.html" ],
        function(
                declare,
                domConstruct,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                TextBox,
                main_widget,
                UserGroup,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

            /**
             * template string.
             * @property templateString
             */
            templateString : template,

            /**
             *
             * @property _stateMenu
             */
            _stateMenu : false,

            /**
             *
             * @property _groups
             */
            _groups : [],

            /**
             *
             * @property parentWidget
             */
            parentWidget : null,

            /**
             *
             * @property _groupId
             */
            _groupId : null,

            /**
             *
             * @property dataUser
             */
            dataUser : {},

            /**
             *
             * @property _groupSelectedName
             */
            _groupSelectedName : "",

            /**
             *
             * @property _timer
             */
            _timer : null,

            /**
             *
             * @method
             */
            postCreate : function() {
                if (this.dataUser.groupId) {
                    this._groupId = this.dataUser.groupId;
                    if (this.dataUser.groupBean !== null) {
                        this._groupName.innerHTML = this.dataUser.groupBean.groupName;
                    }
                }
                dojo.subscribe("/encuestame/admon/user/hide", this, "_close");
            },

            /**
             *
             * @method
             */
            _setLabelSelected : function(label) {
                this._groupName.innerHTML = label;
            },

           /**
            * Call Groups.
            * @method
            */
            _callGroups : function() {
                var parent = this;
                var load = dojo.hitch(this, function(response) {
                    this._groups = response.success.groups;
                    this.buildGroups();
                });
                var error = function(error) {
                    parent.errorMessage(error);
                };
                this.getURLService().get('encuestame.service.list.loadGroups', {}, load, error , dojo.hitch(this, function() {

                }));
            },

           /**
            * Build the list of groups
            * @method
            */
            buildGroups : function() {
                dojo.empty(this._items);
                dojo.forEach(this._groups,
                dojo.hitch(this, function(data, index) {
                      this._buildItemMenu(data);
                 }));
                this._items.appendChild(this._buildCreateGroup().domNode);
            },

            /**
             * Build Menu Item.
             * @method
             */
            _buildItemMenu : function(data) {
                 var div = dojo.doc.createElement('div');
                 dojo.addClass(div, "item");
                 if (this._groupId == data.id) {
                    dojo.addClass(div, "selected");
                 }
                 //console.debug(data);
                 div.innerHTML = data.groupName;
                 dojo.connect(div, "onclick", this, dojo.hitch(this, function(event){
                         this._selectItem(data, div);
                         this.dataUser.groupId = data.id;
                         this._groupId = data.id;
                         this._setLabelSelected(data.groupName);
                 }));
                 this._items.appendChild(div);
            },

            /**
             *
             * @method
             */
            _buildCreateGroup : function() {
                var myTextBox = new TextBox({
                    name: "newGroupTextBox",
                    value: "",
                    style: "max-width: 150px;padding: 0px;",
                    maxLength : 49,
                    placeHolder: "enter new group"

                }, "newGroupTextBox");
                dojo.connect(myTextBox, "onKeyDown", this, dojo.hitch(this, function(event){
                    // dojo.stopEvent(event);
                     if (dojo.keys.ENTER == event.keyCode) {
                         this._createGroup(myTextBox.attr("value"));
                     }
                }));
                return myTextBox;
            },


            /**
             * Create Group.
             * @method
             */
            _createGroup : function(data) {
                var load = dojo.hitch(this, function(response) {
                    this._callGroups();
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                this.getURLService().get('encuestame.service.list.groupCreate', {
                    groupName : data
                }, load, error , dojo.hitch(this, function() {

                }));
            },

            /**
             *
             * @method _markAsSelected
             */
            _markAsSelected : function(node){
                dojo.addClass(node, "selected");
             },

            /**
             *
             * @method _selectItem
             */
            _selectItem : function(data, node) {
                var parent = this;
                var load = dojo.hitch(this, function(response){
                    this._markAsSelected(node);
                    dojo.addClass(this._items, "defaultDisplayHide");
                    dojo.removeClass(this._items, "defaultDisplayBlock");
                    dojo.removeClass(this._groupWrapper, "openMenu");
                    this._stateMenu = false;
                });
                var error = function(error) {
                     parent.errorMessage(error);
                };
                var params = {
                    id: data.id,
                    userId: this.parentWidget.data.id
                 };
                 this.getURLService().get('encuestame.service.list.assingGroups', params, load, error , dojo.hitch(this, function() {

                }));
            },

            /**
             *
             * @method _close
             */
            _close : function(widget) {
                 if(widget != this){
                     dojo.addClass(this._items, "defaultDisplayHide");
                     dojo.removeClass(this._items, "defaultDisplayBlock");
                     dojo.removeClass(this._groupWrapper, "openMenu");
                     this._stateMenu = false;
                 }
             },

             /**
              *
              * @method _changeMenu
              */
             _changeMenu : function() {
                 if(this._stateMenu){
                     dojo.addClass(this._items, "defaultDisplayHide");
                     dojo.removeClass(this._items, "defaultDisplayBlock");
                     //dojo.removeClass(this._groupWrapper, "openMenu");
                 } else {
                     this._callGroups();
                     dojo.addClass(this._groupWrapper, "openMenu");
                     dojo.addClass(this._items, "defaultDisplayBlock");
                     dojo.removeClass(this._items, "defaultDisplayHide");
                 }
             },

            /**
             *
             * @method _onOpenMenu
             */
            _onOpenMenu : function(event) {
                dojo.stopEvent(event);
                dojo.publish("/encuestame/admon/user/hide", [this]);
                this._changeMenu();
                this._stateMenu = !this._stateMenu;
            },


            /**
             *
             * @method _onMouseOver
             */
            _onMouseOver: function(event) {
                dojo.stopEvent(event);
                this._timer = setTimeout(dojo.hitch(this, function() {
                    dojo.addClass(this._groupWrapper, "showMenu");
                }), 200);
            },


            /**
             *
             * @method _onMouseOut
             */
            _onMouseOut : function(event) {
                 dojo.stopEvent(event);
                 clearTimeout(this._timer);
                 dojo.removeClass(this._groupWrapper, "showMenu");
            }


    });
});