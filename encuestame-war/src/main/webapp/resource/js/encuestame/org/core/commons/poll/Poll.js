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
dojo.require('encuestame.org.core.shared.options.ResultsOptions');
dojo.require('encuestame.org.core.shared.options.CheckSingleOption');

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

        _default_answers : 4,

        _min_answer_allowed : 2,

        _answer_widget_array : [],

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
            this.enableDndSupport(this._source, true);
            //add default answers.
            for (var i= 0; i <= this._default_answers; i++) {
                 var li = this._newAnswer({ dndEnabled : true});
                 this.addItem(li);
            }
            dojo.connect(this._publish, "onClick", dojo.hitch(this, this._validatePoll));
            dojo.connect(this._addNew, "onclick", dojo.hitch(this, this._addAnswer));
        },

        /**
         *
         * @param event
         */
        _addAnswer : function(event) {
            dojo.stopEvent(event);
            console.info("_addAnswer", this.sourceDndWidget.getSelectedNodes());
            var li = this._newAnswer({ dndEnabled : true});
            this.addItem(li);
        },


        /**
         *
         * @returns {encuestame.org.core.commons.questions.patterns.SingleResponse}
         */
        _newAnswer : function(params){
            params = params == null ? {} : params;
            var li = dojo.create("li");
            dojo.addClass(li, "dojoDndItem");
            var answer = new encuestame.org.core.commons.questions.patterns.SingleResponse(params);
            this._answer_widget_array.push(answer);
            li.appendChild(answer.domNode);
            console.info("_newAnswer", li);
            return li;
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
                   encuestame.service.list.poll.create, params, load, error);
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
        _validatePoll : function(event) {
            dojo.stopEvent(event);
            var valid = false;
            /*
             * options : {
                        multiples : true,
                        allow_add_response : true,
                        repeated_votes : {
                            allow : true,
                            number : 123
                        },
                        limit : {
                            limit : true,
                            number : 231
                        },
                        close : {
                            close : true,
                            time : "",
                            date : ""
                                },
                        comments : "allow",
                        results : "all",
                        folder : 1,
                    }
             */
            var params = {
                    questionName : "",
                    listAnswers : [],
                    };
            //check question.
            if (this._questionWidget.getQuestion() != "" &&
                this._questionWidget.getQuestion() != null) {
                valid = true;
                dojo.mixin(params, { questionName : this._questionWidget.getQuestion()});
            }

            //catching answers.
            dojo.forEach(this._answer_widget_array,
                    dojo.hitch(this,function(item) {
                    if(item != null){
                        var response = item.getResponse();
                        console.debug("_answer_widget_array params", response);
                        if (response != null && response != "") {
                            var newArray = params.listAnswers;
                            newArray.push(response.trim());
                            dojo.mixin(params, { listAnswers : newArray});
                        }
                    }
            }));
            console.debug("required params", params);
            var repeated_votes = dijit.byId("repeated");
            if (repeated_votes.checked){
                dojo.mixin(params, {repeated_votes : repeated_votes.items});
            }
            //ir agregado mas elementos.
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