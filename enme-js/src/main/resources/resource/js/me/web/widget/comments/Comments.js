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
	"dojo/_base/declare",
	"dojo/hash",
	"dojo/dom",
	"dojo/fx",
	"dojo/_base/fx",
	"dojo/on",
	"dojo/dom-style",
	"dijit/_WidgetBase",
	"dijit/_TemplatedMixin",
	"dijit/_WidgetsInTemplateMixin",
	"me/core/main_widgets/EnmeMainLayoutWidget",
	"me/web/widget/comments/Comment",
	"me/core/enme",
	"dojo/text!me/web/widget/comments/templates/comments.html" ],
    function(
    declare,
    hash,
    dom,
    coreFx,
    fx,
    on,
    style,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    Comment,
    _ENME,
     template ) {

  return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

     /**
      * Template string.
      * @property
      */
     templateString: template,

     /**
      *
      * @property
      */
      type: "",

      /**
       *
       * @property
       */
      item_id: null,

        /*
         *
         */
        postCreate: function() {
            if ( this.item_id ) {
                this._loadComments({});
            }
            dojo.subscribe("/encuestame/commons/home/comments/add", this, this._addComment );
        },

        /**
         * Manage the success handler
         * @method successHandler
         */
        successHandler: function( data ) {
            var comments = _ENME.orderByDate( data.success.comments, "created_at", "desc" ).reverse();
            if ( comments.length > 0 ) {
                dojo.forEach( comments, dojo.hitch( this, function( data, index ) {
                    this._addComment( data );
                }) );
            }
        },

        /**
         *
         * @method errorHandler
         */
        errorHandler: function( error ) {
            console.error( "error", error );
        },

        /*
         * Load all comments.
            error: {},
            success: {
              comments: [
                {
                  id: 1,
                  comment: "xxxxxx",
                  created_at: "2011-06-29",
                  likeVote: 432,
                  dislike_vote: 31,
                  item_id: 101,
                  type: "TWEETPOLL",
                  uid: 4,
                  parent_id: null
                }
              ]
            }
         */
        _loadComments: function() {

                // Success handler
                var load = dojo.hitch( this, function( data ) {
                    if ("success" in data ) {
                        this.successHandler( data );
                    }

	                //Read hash {#comment130}
	                var hashValue = hash();
	                if ( hashValue ) {
		                var node = dojo.byId( hashValue );

		                //FIXME: posible confliects with TweetPollList/PollNavigate hash
		                if ( node ) {
			                var node_y = dojo.position( node ).y;
			                window.scrollTo( 0, node_y );
		                }

		                //FUTURE: we need a smooth scroll motion here
	                }
                });

                // Error handler
                var error = dojo.hitch( this, function( error ) {
                    this.errorHandler( error );
                });
                this.getURLService().get( [ "encuestame.service.comments.list", [ this.type ]], { id: this.item_id, max: 10 }, load, error, dojo.hitch( this, function() {

                }) );
        },

      /**
       *
       */
        _printNoCommentsText: function() {
          console.warn("should be implemented into the child widget");
        },

        /**
         * Print a comment.
         * @param data
         * @param top
         * @param is_moderated
         * @method _addComment
         */
        _addComment: function( data, top, is_moderated ) {
            var widget = new Comment({
                data: data,
                is_moderated: is_moderated
            });
            if ( !top ) {
                this._items.appendChild( widget.domNode );
            }

            if ( top ) {
                style.set( widget.domNode, "opacity", "0");
                this._items.insertBefore( widget.domNode, dojo.query( ".comment:first-child" )[ 0 ] );
                var fadeArgs = {
                    node: widget.domNode,
                    onEnd: function() {
                      style.set( widget.domNode, "opacity", "");
                    }
                };
                fx.fadeIn( fadeArgs ).play();
            }
        }
  });
});
