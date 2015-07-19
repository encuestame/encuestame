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
 *  @module Poll
 *  @namespace Widget
 *  @class Poll
 */
define([
 "dojo/_base/declare",
 "dojo/_base/lang",
 "dojo/_base/array",
 "dojo/dom-attr",
 "dojo/query",
 "dojo/dom-class",
 "dojo/on",
 "dijit/_WidgetBase",
 "dijit/_TemplatedMixin",
 "dijit/_WidgetsInTemplateMixin",
 "dijit/registry",
 "dijit/form/Button",
 "me/third-party/moment",
 "me/core/main_widgets/EnmeMainLayoutWidget",
 "me/web/widget/folder/FolderSelect",
 "me/web/widget/questions/Question",
 "me/web/widget/questions/patterns/SingleResponse",
 "me/web/widget/options/RepeatedVotes",
 "me/web/widget/options/LimitVotes",
 "me/web/widget/options/DateToClose",
 "me/web/widget/tweetpoll/HashTags",
 "me/web/widget/dialog/Dialog",
 "me/web/widget/publish/PublishSupport",
 "me/web/widget/support/ActionDialogHandler",
 "me/web/widget/options/CommentsOptions",
 "me/web/widget/support/DnD",
 "me/web/widget/options/CheckSingleOption",
 "me/web/widget/options/ResultsOptions",
 "me/core/enme",
 "dojo/text!me/web/widget/poll/templates/poll.html" ],
function(
  declare,
  lang,
  array,
  domAttr,
  query,
  domClass,
	on,
  _WidgetBase,
  _TemplatedMixin,
  _WidgetsInTemplateMixin,
  registry,
  Button,
  moment,
  main_widget,
  FolderSelect,
  Question,
  SingleResponse,
  RepeatedVotes,
  LimitVotes,
  DateToClose,
  HashTags,
  Dialog,
  PublishSupport,
  ActionDialogHandler,
  CommentsOptions,
  DnD,
  CheckSingleOption,
  ResultsOptions,
  _ENME,
   template) {
return declare([ _WidgetBase,
 _TemplatedMixin,
 main_widget,
 ActionDialogHandler,
 DnD,
 _WidgetsInTemplateMixin], {

  /*
   * template string.
   */
   templateString : template,

   /*
   *
   */
  _folderWidget : null,

  /*
   * i18n message for this widget.
   */
  i18nMessage : {
    poll_create_question_title : _ENME.getMessage("poll_create_question_title"),
    poll_create_build_answers : _ENME.getMessage("poll_create_build_answers"),
    poll_create_add_new_answer : _ENME.getMessage("poll_create_add_new_answer"),
    poll_create_allow_multiple_selection : _ENME.getMessage("poll_create_allow_multiple_selection"),
    poll_create_allow_new_responses : _ENME.getMessage("poll_create_allow_new_responses"),
    poll_create_limits : _ENME.getMessage("poll_create_limits"),
    hashtag : _ENME.getMessage("tp_add_hashtag"),
    poll_create_poll_options : _ENME.getMessage("poll_create_poll_options"),
    poll_create_comments : _ENME.getMessage("poll_create_comments"),
    poll_create_results : _ENME.getMessage("poll_create_results"),
    poll_create_button_create : _ENME.getMessage("poll_create_button_create"),
    poll_is_hidden : _ENME.getMessage("poll_is_hidden"),
    poll_password : _ENME.getMessage("poll_password"),
    commons_cancel : _ENME.getMessage("commons_cancel"),
    leave_mesage : _ENME.getMessage("leave_mesage")
  },

  helpSteps: [
    {
        element: '.help-q1',
        intro: _ENME.getMessage('help_poll_question_text')
    },
    {
        element: '.help-q2',
        intro: _ENME.getMessage('help_poll_answers_text')
    },
    {
        element: '.help-q3',
        intro: _ENME.getMessage('help_poll_hashtags')
    },
    {
        element: '.help-q4',
        intro: _ENME.getMessage('help_poll_options')
    },
    {
        element: '.help-q5',
        intro: _ENME.getMessage('help_poll_limit_options')
    },
    {
        element: '.help-q6',
        intro: _ENME.getMessage('help_poll_comments_options')
    },
    {
        element: '.help-q7',
        intro: _ENME.getMessage('help_poll_comments_results')
    },
    {
        element : '.botton-button-publish',
        intro: _ENME.getMessage('help_poll_cancel_button')
    }
],

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
  _default_answers : 4,

  /*
   *
   */
  _min_answer_allowed : 2,

  /**
   * Store all hashtag
   * @property _hashtags
   */
  _hashtags : [],

  /*
   *
   */
  _answer_widget_array : [],

/**
 * Disable unload on close window
 */
 forceClose : false,

	/**
	 *
	 */
 dnd_sources : [],

   /**
    * Post Create clycle.
    */
  postCreate : function() {
      var parent = this;
      //this._folderWidget = new FolderSelect({folderContext : "poll"});
      this._questionWidget = new Question({
                  maxSize : 200,
                  maxLength : 900
      });
      this._question.appendChild(this._questionWidget.domNode);
      //if (this._folder) {
      //    this._folder.appendChild(this._folderWidget.domNode);
      //}
      //add default answers.

      for (var i= 0; i <= this._default_answers; i++) {
           var li = this._newAnswer(
             {
               dndEnabled : false
             });
           this.addItem(li);
      }

      if (parent.isDnD && !this.isMobile) {
          this.addDnDSupport();
      } else {
        //FIXME: remove icons to drag if dnd is disabled
      }

      // trigger the validate poll or publish and create
      on(this._publish, "click", lang.hitch(this, this._validatePoll));
      // trigger the add new answer
      on(this._addNew, "click", lang.hitch(this, this._addAnswer));
      // cancel button
      on(this._cancel, "click", lang.hitch(this, function() {
        window.location.href = _ENME.config('contextPath') + "/user/poll/list";
      }));
      // leave message
      this.unLoadSupport(this.i18nMessage.leave_mesage);

      //hashtag support ENCUESTAME-435
      this.hashTagWidget = this._hashtag_widget;

      this.hashTagWidget._addHastahToItem = lang.hitch(this, function(data) {
          //console.log("click ", data);
          this._hashtags.push(data.label);
          data.hashTagName = data.label;
          this.hashTagWidget.newHashTag(data);
      });

       // set new date
       var f = moment().add(1,'day')
       var _date = new Date(f.format());
       this.closeWidget = new DateToClose({
           default_date : _date
       });
       this._date_to_close.appendChild(this.closeWidget.domNode);
       //close.scheduledDateWidget.dropDownDefaultValue = _date;
       //close.scheduledDateWidget.set('value', _date);
       //help links
       this.initHelpLinks(lang.hitch(this, function(){
           this.updateHelpPageStatus(_ENME.config('currentPath'), true);
       }));
       // leave message
       if (!this.forceClose) {
           this.unLoadSupport(this.i18nMessage.leave_mesage);
       }
  },

  /**
   * Add dnd support to a list of nodes.
   * @method addDnDSupport
   */
  addDnDSupport : function () {
    var parent = this;
    var dragSrcEl = null;
    this.enableDnDSupport(this.dnd_sources,
            {
              dragstart : function (e) {
                var node = this;
                domClass.add(this, "me_opa");
                dragSrcEl = this;
                e.dataTransfer.effectAllowed = 'move';
                e.dataTransfer.setData('poll-answer', domAttr.get(node, 'd-id'));
              },
              dragenter : function (e) {
                // this / e.target is the current hover target.
                var node = this;
                domClass.add(node, 'over');
              },
              dragover : function (e) {
                if (e.preventDefault) {
                    e.preventDefault(); // Necessary. Allows us to drop.
                }
                e.dataTransfer.dropEffect = 'move';  // See the section on the DataTransfer object.
                domClass.remove(this, 'over');
                return false;
              },
              dragleave : function (e) {
                var node = this;
                domClass.remove(node, 'over');
                domClass.remove(e.target, 'over');
              },
              drop : function (e) {
                var node = this;
                var origin_id  = e.dataTransfer.getData('poll-answer');
                var target_id = domAttr.get(node, 'd-id');
                var _w = registry.byId(origin_id);
                var _w_t = registry.byId(target_id);
                node.appendChild(_w.domNode);
                domAttr.set(node, 'd-id', _w.id);
                dragSrcEl.appendChild(_w_t.domNode);
                domAttr.set(dragSrcEl, 'd-id', _w_t.id);
                // return false so the event will not be propagated to the browser
                // this / e.target is current target element.
                if (e.stopPropagation) {
                  e.stopPropagation(); // stops the browser from redirecting.
                }
                e.dataTransfer.clearData("poll-answer");
                // See the section on the DataTransfer object.
                 [].forEach.call(parent.dnd_sources, function (col) {
                    //col.classList.remove('over');
                    domClass.remove(col, "me_opa");
                    //this.style.opacity = '1';
                 });
                return false;
              },
              dragend : function (e) {
                domClass.remove(this, "me_opa");
              }
            });
  },

  /**
   *
   * @method
   */
  addItem : function (node) {
    this._source.appendChild(node);
  },

  /**
   *
   * @param event
   */
  _addAnswer : function(event) {
      event.preventDefault();
      var li = this._newAnswer({ dndEnabled : true});
      this.addItem(li);
      this.addDnDSupport();
  },


  /**
   *
   * @returns {encuestame.org.core.commons.questions.patterns.SingleResponse}
   */
  _newAnswer : function(params) {
      params = params === null ? {} : params;
      var answer = new SingleResponse(params);
      var li = dojo.create("li");
      domAttr.set(li, 'draggable', true);
      domAttr.set(li, 'd-id', answer.id);
      li.appendChild(answer.domNode);
      if (this.isDnD && !this.isMobile) {
            this.dnd_sources.push(li);
      }
      return li;
  },


  /**
   *
   * @param params
   * @private
   */
  _createPoll : function(params) {
    var parent = this,
    load = lang.hitch(this, function(data) {
         parent.loading_hide();
         if ("success" in data) {
         parent.cancelUnLoadSupport();
         var pollBean = data.success.pollBean;
             if (pollBean !== null) {
                 parent._createDialogSupport();
                 parent._openSuccessMessage(pollBean);
             }
         }
     }),
     error = lang.hitch(this, function(error) {
         parent.errorMessage(error);
     });
     this.loading_show();
     this.getURLService().post('encuestame.service.list.poll.create', params, load, error , lang.hitch(this, function() {}));
  },

  /**
   *
   * @param params
   */
  createPoll : function(params){
      this._createPoll(params);
  },

/**
 * Validate the poll.
 * @param e Event
 * @method
 */
_validatePoll : function(e) {
    e.preventDefault();
    var valid = this._checkValidations();
    if (valid) {
        this.createPoll(valid);
    }
},

  /**
   *
   * @returns {*}
   * @private
   */
_checkValidations : function() {
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
         allow_repeated_votes : 0,
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
            questionName : '',
            listAnswers : []
         };

      // validate the question
      if (this._questionWidget.getQuestion() !== '' && this._questionWidget.getQuestion() !== null) {
          lang.mixin(params, {
              questionName : this._questionWidget.getQuestion()
          });
      } else {
          this.warningMesage(_ENME.getMessage("Question length not valid"));
          return false;
      }

      var c = 0,
      parent = this;
      // search the draggable nodes in order
      this._answer_widget_array = [];
      query("[draggable]").forEach(function(node) {
          var target_id = domAttr.get(node, 'd-id');
          var _w = registry.byId(target_id);
          parent._answer_widget_array.push(_w);
      });
      var _invalid_answers = false;
      // iterate all answers
      array.forEach(this._answer_widget_array, lang.hitch(this,function(item) {
        if (item !== null) {
          var response = item.getResponse();
          if (!item.isValid()) {
            _invalid_answers = true;
          }
          if (response !== null && response !== '') {
              var newArray = params.listAnswers;
              newArray.push(response.trim());
              lang.mixin(params, {
                listAnswers : newArray
              });
              c++;
          }
        }
      }));
      if (_invalid_answers) {
        this.warningMesage(_ENME.getMessage("Question length not valid"));
        return false;
      }

      // check if answer are valid
      if (c < this._min_answer_allowed) {
          this.warningMesage(_ENME.getMessage("m_025"));
          c = 0;
          return false;
      }

      var repeated_votes = registry.byId("repeated");
      if (repeated_votes.getOptions().checked) {
          lang.mixin(params, {
              allow_repeated_votes : repeated_votes.getOptions().items
          });
      }

      var limit_votes = registry.byId("limit");
      if (limit_votes.getOptions().checked) {
          lang.mixin(params, {
            limit_votes : limit_votes.getOptions().items
          });
      }

     // is hidden
    lang.mixin(params, {
        is_hidden : registry.byId('isHidden').getValue().checked
    });

    //password_protection
    lang.mixin(params, {
        is_password_protected : registry.byId('passwordProtection').getValue().checked
    });

    var close = this.closeWidget;
      if (close.getOptions().checked){
          lang.mixin(params, {
              close_date : close.getOptions().complete_date
          });
      }

    if(!close.isValid()) {
      this.warningMesage(_ENME.getMessage("m_026"));
          return false;
    }

      var comments = registry.byId("comments");
      if (comments.getResponse() !== null) {
          lang.mixin(params, {
            showComments : comments.getResponse()
          });
      }

      var results = registry.byId("results");
      if (results.getResponse() !== null) {
          lang.mixin(params, {
              results : results.getResponse()
          });
          //FIXME: this list is not implemented in the json service
      }

      lang.mixin(params, {
          multiple : registry.byId('multiple').getValue().checked
      });

      //temp: folder select and allow-add disabled by default
      //lang.mixin(params, {allow_add : registry.byId('allow-add').getValue().checked});
      //lang.mixin(params, {folder_name : this._folderWidget.getSelected()});
      // recover hashtags

      params.hashtags = this.hashTagWidget.getHashTags("hashTagName");

      return params;
  },

  /**
   *
   * @method
   */
  _addHashTags : function(){},

  /**
   *
   * @method
   */
  _publishPoll : function(){},


  /**
   *
   * @param pollBean
   * @private
   */
  _openSuccessMessage : function(pollBean) {
      //social widget.
      var publishWidget = new PublishSupport(
      {
        context: this.context,
        item : {
            id: pollBean.id,
            name : pollBean.question.question_name ,
            url : pollBean.shortUrl
        },
        done_button : true,
        dialogContext : this._dialogPublish
      });
      this._dialogPublish.set('title', publishWidget.text_message);
      this._dialogPublish.containerNode.appendChild(publishWidget.domNode);
      this._dialogPublish.show();
  },

  /**
   *
   * @param errorMessage
   * @private
   */
  _openFailureMessage : function(errorMessage) {
       this.infoMesage(errorMessage);
  },

  /**
   *
   * @private
   */
  _createDialogSupport : function() {
    this._dialogPublish = new Dialog({
      style :"width: 850px; heigth:400px;"
    });
  }

});
});
