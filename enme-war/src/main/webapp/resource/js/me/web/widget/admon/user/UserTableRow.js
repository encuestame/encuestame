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
 *  @class UserTableRow
 */
define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/admon/user/UserGroup",
         "me/core/enme",
         "dojo/text!me/web/widget/admon/user/template/UserTableRow.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                UserGroup,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            data: null,

            postMixInProperties: function(){},

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
                //this.createInput(data.id)
                this.createColumnDialog(data.username);
                this.createColumn(data.name);
                this.createGroupWidget(data);
                this.createColumn(data.relateTimeEnjoy);
                this.buildStatus(data.status);
                this.createColumn(data.tweetPoll, true);
                this.createColumn(data.poll, true);
                this.createColumn(data.survey, true);
                this.createColumn(
                        dojo.date.locale.format(new Date(), {datePattern: "MM/dd/yy" , selector:'date'}), true);
                this.createColumn(data.followers == null ? 0 : data.followers, true);
            },

            /**
             * Build Options.
             */
            buildOptions : function(id){

            },

            /**
             * Create Column.
             */
            createColumnDialog : function(text, centered){
                 var td = dojo.doc.createElement('td');
                 var a = dojo.doc.createElement('a');
                 dojo.addClass(a, "link");
                 a.innerHTML = text;
                 a.href = "#";
                 dojo.connect(a, "onclick", this, this.editUSer);
                 td.appendChild(a);
                 this._trbody.appendChild(td);
            },

            /*
             *
             */
            createGroupWidget : function(data){
                var td = dojo.doc.createElement('td');
                var groupWidget = new UserGroup(
                        {
                         dataUser: data,
                         parentWidget: this
                         });
                td.appendChild(groupWidget.domNode);
                this._trbody.appendChild(td);
            },

            /**
             * Edit User.
             */
            editUSer : function(){
                var userEdit = dijit.byId("userEdit");
                userEdit.data = this.data;
                if(userEdit != null){
                    this.getUserInfo(this.data.id);
                }
            },

            /**
             * Get User.
             */
            getUserInfo : function(id){
                var load = dojo.hitch(this, function(response){
                     dijit.byId("userEdit").show();
                    var data = response.success.user;
                    dijit.byId("userEdit").title = data.username;
                    var name = dijit.byId("name");
                    name.setValue(data.username);
                    var email = dijit.byId("email");
                    email.setValue(data.email);
                    var realName = dijit.byId("realName");
                    realName.setValue(data.name);
                    //set widgets
                    if(dijit.byId("widgetPermission")){
                        dijit.byId("widgetPermission").user = data;
                        dijit.byId("widgetPermission").initialize();
                    } else {
                        console.info("Permission Widget not found");
                    }
                });
                var error = function(error) {
                    console.debug("error", error);
                };
                //encuestame.service.xhrGet('encuestame.service.list.userInfo', {id: id}, load, error);
                this.getURLService().get('encuestame.service.list.userInfo', {id: id}, load, error , dojo.hitch(this, function() {}));
            },

            /**
             * Create Column.
             */
            createColumn : function(text, centered){
                var td = dojo.doc.createElement('td');
                if(centered){
                    td.setAttribute("align", "center");
                }
                td.innerHTML = text;
                this._trbody.appendChild(td);
            },

            /**
             * Create Input.
             */
            createInput : function(id){
                var td = dojo.doc.createElement('td');
                var widgetInput = new dijit.form.CheckBox({});
                widgetInput.setValue(id);
                td.appendChild(widgetInput.domNode);
                this._trbody.appendChild(td);
            },

            buildStatus : function(status){
                var td = dojo.doc.createElement('td');
                td.innerHTML = status;
                this._trbody.appendChild(td);
            }


    });
});