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
 *  @module Comment
 *  @namespace Widget
 *  @class Comment
 */
define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/utils/ToggleText",
     "me/core/enme",
     "dojo/text!me/web/widget/comments/templates/comment.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    ToggleText,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

      /**
       * template string.
       * @property
       */
      templateString : template,

     /**
      *
      * @property
      */
     type : "",

     /**
      *
      * @property
      */
     item_id : null,

     /**
      *
      * @property
      */
     data : null,

     /**
      * Define if the comment is moderated
      * @property
      */
     is_moderated: false,


    /**
     *
     * @method postMixInProperties
     */
    postMixInProperties: function() {
        /*
          * in the future the content could be formated by HTML.
          */
         // //if (this._comment_content) {
         //   var p = dojo.create("p");
         //   p.innerHTML = this.data.comment;
         //   this.complete_comment = p;
         // }
    },

       /**
        *
        */
       postCreate : function() {
         if (this.data) {
           try {
             this._fillComment();
           } catch (err) {
              console.error("error on fill comment", err);
           } finally {
            //something could be happend.
           }
          }
          if (this.is_moderated) {
             dojo.addClass(this.domNode, "moderated");
          }
       },

           /*
        *
        */
       _fillComment : function() {
         //set user link
         if (this._commented_by) {
           var a = dojo.create("a");
           var commentedBy = (this.data.commented_by === null ? this.data.commented_username : this.data.commented_by);
           a.innerHTML = commentedBy;
           a.href = _ENME.usernameLink(this.data.commented_username);
           //a.target = "_blank";
           this._commented_by.appendChild(a);
         }

         //set date
         if (this._comment_content_date) {
           var date = dojo.create("a");
           date.innerHTML = _ENME.fromNow(this.data.created_at, "YYYY-MM-DD");
           date.href = "#"; //TODO: future improvements
           this._comment_content_date.appendChild(date);
         }
       }

  });
});