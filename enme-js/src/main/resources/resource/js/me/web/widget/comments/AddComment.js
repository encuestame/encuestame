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
 *  @class Comments
 */
define( [
     "dojo/string",
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "dijit/form/Button",
     "dijit/form/CheckBox",
     "dijit/form/Select",
     "dijit/form/Textarea",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/comments/Comment",
     "me/web/widget/pictures/AccountPicture",
     "me/core/enme",
     "dojo/text!me/web/widget/comments/templates/commentForm.html" ],
    function(
    string,
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    Button,
    CheckBox,
    Select,
    Textarea,
    main_widget,
    comment,
    AccountPicture,
    _ENME,
     template ) {

  "use strict";

  return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

     /**
      * Template string.
      * @property  templateString
      */
    templateString: template,

    /**
     * Save the
     * @property type
     */
    type: "",

    /**
     * Save the item id.
     * @property
     */
    item_id: null,

    /**
     *
     * @property
     */
    username: "",

    /**
     * Define if the comment moderation is enabled
     * @property
     */
    isModerated: false,

    /**
     * Limit the text content
     * @property comment_limit
     */
    comment_limit: 2000,

    /*
     *
     */
    postCreate: function() {
        if ( this.item_id ) {
            this.initializeForm({});
        }
    },

    /**
     *
     * @method
     */
    initializeForm: function() {

        if ( this._new_comment.get( "value" ).length === 0 ) {
            this._post.attr( "disabled", true );
        }

        this._new_comment.onChange = dojo.hitch( this, function( e ) {
            if ( this._new_comment.get( "value" ).length > 0 ) {
                this._post.attr( "disabled", false );
            } else {
                this._post.attr( "disabled", true );
            }
            return false;
        });

        // Event to control the post button
        this._post.onClick = dojo.hitch( this, function( e ) {
            this._post.setLabel("Posting ....");
            this._post.attr( "disabled", true );
            this._saveComment();
        });
    },

    /**
     * Save comment.
     * @method _saveComment
     */
    _saveComment: function() {
        var params = {
            comment: string.trim( this._new_comment.get( "value" ) ),
            tweetPollId: this.item_id
        };

        var restoreButton = dojo.hitch( this, function() {
            this._post.setLabel("Post");
            this._post.attr( "disabled", false );
        });

        if ( params.comment !== "" ) {

            // Success handler
            var load = dojo.hitch( this, function( data ) {
                if ("success" in data ) {
                    dojo.publish( "/encuestame/commons/home/comments/add", [ data.success.comment, true, this.isModerated ] );
                    restoreButton();
                    this._new_comment.set( "value", "" );
                    this._post.attr( "disabled", true );
                }
            });

            // Error handler
            var error = dojo.hitch( this, function( error ) {
                this.errorMessage( _ENME.getMessage("comment_post_error", "There was a problem saving your comment. Please try again.") );
                restoreButton();
            });
            this.getURLService().formU( [ "encuestame.service.comments.create", [ this.type ]], params, load, error, dojo.hitch( this, function() {

            }) );
        }
    }
  });
});
