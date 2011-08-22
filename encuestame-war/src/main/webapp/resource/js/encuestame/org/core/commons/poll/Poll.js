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
dojo.provide("encuestame.org.core.commons.poll.Poll");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('dijit.form.Button');
dojo.require("dijit.form.CheckBox");
dojo.require('dijit.form.TimeTextBox');
dojo.require('dijit.form.DateTextBox');
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.FolderSelect');
dojo.require("encuestame.org.core.commons.social.SocialAccountPicker");
dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require('encuestame.org.core.commons.questions.patterns.SingleResponse');
dojo.require('encuestame.org.core.commons.questions.Question');
dojo.require('encuestame.org.core.commons.support.DnD');
dojo.require('encuestame.org.core.shared.options.RepeatedVotes');
dojo.require('encuestame.org.core.shared.options.LimitVotes');
dojo.require('encuestame.org.core.shared.publish.PublishSupport');
dojo.require('encuestame.org.core.shared.options.CommentsOptions');
dojo.require('encuestame.org.core.shared.options.DateToClose');

/**
 *
 */
dojo.declare(
    "encuestame.org.core.commons.poll.Poll",
    [dijit._Widget, dijit._Templated,
        encuestame.org.core.commons.support.DnD],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.poll", "templates/poll.html"),

        widgetsInTemplate: true,

        /*
         *
         */
        _folderWidget : null,

        /*
         *
         */
        context : "poll",

        /*
         *
         */
        _dialogPublish : null,

        /*
         * question widget.
         */
        _questionWidget : null,

        /*
         *
         */
        postCreate : function() {
            this._folderWidget = new encuestame.org.core.shared.utils.FolderSelect({folderContext : "poll"});
            this._questionWidget = new encuestame.org.core.commons.questions.Question(
                    {
                        maxSize : 200,
                        maxLength : 900
                    }
                    );
            this._question.appendChild(this._questionWidget.domNode);
            if (this._folder) {
                this._folder.appendChild(this._folderWidget.domNode);
            }
            dojo.connect(this._publish, "onClick", dojo.hitch(this, this._validatePoll));
            this.enableDndSupport(this._source, true);
        },


        /*
         *
         */
        _createPoll : function(params) {
           console.debug("params", params);
           var load = dojo.hitch(this, function(data) {
               this._createDialogSupport();
               this._openSuccessMessage();
           });
           var error = dojo.hitch(this, function(error) {
               this._openFailureMessage(error);
           });
           console.debug("_createPoll", params);
           encuestame.service.xhrPostParam(
                   encuestame.service.list.create, params, load, error);
        },

        /*
         *
         */
        createPoll : function(params){
            this._createPoll(params);
        },

        /*
         *
         */
        _validatePoll : function(event){
            dojo.stopEvent(event);
            var valid = false;
            var params = {};
            valid = true;
            if (valid) {
                this.createPoll(params);
            }
        },

        _addHashTags : function(){},
        _publishPoll : function(){},


        /*
         *
         */
        _openSuccessMessage : function(){
            var div = dojo.create('div');
            //social widget.
            var publishWidget = new encuestame.org.core.shared.publish.PublishSupport(
                    {
                        context:this.context,
                        dialogContext : this._dialogPublish
                    });
            this._dialogPublish.containerNode.appendChild(publishWidget.domNode);
            this._dialogPublish.show();
        },

        /*
         *
         */
        _openFailureMessage : function(error){
            console.error(error);
        },

        /*
         *
         */
        _createDialogSupport : function(){
            this._dialogPublish = new encuestame.org.core.commons.dialog.Dialog({ style :"width: 850px; heigth:400px;"});
        }
});