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
 *  @author juanpicado19D0Tgmail.com
 *  @version 1.146
 *  @module TweetpollListDetail
 *  @namespace Widgets
 *  @class TweetpollListDetail
 */

define( [
    "dojo/_base/declare",
	"dojo/_base/window",
    "dojo/ready",
    "dojo/_base/lang",
	"dojo/store/Memory",
	"dojo/_base/array",
	"dojo/dom-class",
	"dojo/on",
	"dojo/dom-construct",
	"dojo/topic",
    "dijit/_WidgetBase",
    "dijit/_TemplatedMixin",
    "dijit/_WidgetsInTemplateMixin",
    "dijit/layout/TabContainer",
    "dijit/layout/ContentPane",
	"dijit/form/FilteringSelect",
    "me/core/main_widgets/EnmeMainLayoutWidget",
    "me/web/widget/tweetpoll/detail/TweetPollAnswer",
    "me/web/widget/options/YesNoWidget",
    "me/web/widget/support/CommentsModeration",
    "me/web/widget/support/SocialLinkResume",
    "me/web/widget/chart/EncuestamePieChart",
    "me/core/enme",
    "dojo/text!me/web/widget/tweetpoll/templates/tweetPollListDetail.html" ],
    function(
        declare,
        win,
        ready,
        lang,
        Memory,
        array,
        domClass,
        on,
        domConstruct,
        topic,
        _WidgetBase,
        _TemplatedMixin,
        _WidgetsInTemplateMixin,
        TabContainer,
        ContentPane,
        FilteringSelect,
        main_widget,
        TweetPollAnswer,
        YesNoWidget,
        CommentsModeration,
        SocialLinkResume,
        EncuestamePieChart,
        _ENME,
         template ) {
    return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

        /**
         * Template string.
         * @property templateString
         */
        templateString: template,

        /**
        *
        * @property data
        */
        data: null,

        /**
        *
        */
        type_filter: null,

        /**
        *
        * @property widgetChart
        */
        widgetChart: null,

        /**
        * Reference parent widget.
        * @property
        */
        parentWidget: null,

	    // Store folder data
	    _folderData: [],

        /**
        * I18n message for this widget.
        */
        i18nMessage: {
          tweetpoo_detail_tab_detail: _ENME.getMessage("tweetpoo_detail_tab_detail"),
          tweetpoo_detail_tab_stats: _ENME.getMessage("tweetpoo_detail_tab_stats"),
          tweetpoo_detail_tab_comments: _ENME.getMessage("tweetpoo_detail_tab_comments"),
          tweetpoo_detail_tab_social: _ENME.getMessage("tweetpoo_detail_tab_social"),
          tweetpoo_detail_tab_delete: _ENME.getMessage("tweetpoo_detail_tab_delete"),
          tweetpoo_detail_answers_title_link: _ENME.getMessage("tweetpoo_detail_answers_title_link"),
          tweetpoo_detail_answers_title_count: _ENME.getMessage("tweetpoo_detail_answers_title_count"),
          tweetpoo_detail_answers_title_percent: _ENME.getMessage("tweetpoo_detail_answers_title_percent")
        },

        /**
        * Post create
        * @method postCreate
        */
        postCreate: function() {
	        topic.subscribe("/encuestame/tweetpoll/detail/update", lang.hitch( this, this.updateDetail ) );
		    topic.subscribe("/encuestame/folder/distribute/load", lang.hitch( this, function( foldersData ) {
			    if ( foldersData && "success" in foldersData ) {
				    var folderData = foldersData.success.folders;
				    store = new Memory({ data: folderData });

				    //ENCUESTAME-264
				    this.addDetail( this.builDetailRow("Folder", this.createFolderCombo( store, lang.hitch( this, function( item ) {
					    this.data.folder_id = item;
					    topic.publish("/encuestame/folder/item/add", item, this.data.id );
				    }) ) ) );
			    }
		    }) );
	        topic.subscribe("/encuestame/tweetpoll/detail/chart/render", lang.hitch( this, this.render ) );
	          if ( this._extra ) {
	              on( this._extra, "click", lang.hitch( this, function( event ) {
		              event.preventDefault();
	              }) );
	          }
        },

        /**
        * Executec after postCreate
        * @method
        */
        startup: function() {
            this.inherited( arguments );
            this.contentPane = new TabContainer({
                style: "height: 100%; width: 100%;"
            }, this._tab );

            var detail = new ContentPane({
                 title: this.i18nMessage.tweetpoo_detail_tab_detail,
                 content: this._detailItems
            });

            var stats = new ContentPane({
                 title: this.i18nMessage.tweetpoo_detail_tab_stats,
                 content: this._extra_wrapper
            });

            // Load the chart data after user click on the tab
            stats.onShow = lang.hitch( this, function() {
              this.displayChart( this.typeChart[ 1 ] );
            });

            var comments = new ContentPane({
                 title: this.i18nMessage.tweetpoo_detail_tab_comments,
                 content: this._comments
            });

            comments.onShow = lang.hitch( this, function() {
                var commentsModeration = new CommentsModeration({
                      type: "tweetpoll",
                      item_id: this.data.id
                });
                domConstruct.empty( this._comments );
                this._comments.appendChild( commentsModeration.domNode );
            });

            var social = new ContentPane({
                 title: this.i18nMessage.tweetpoo_detail_tab_social,
                 content: this._social_links
            });

            social.onShow = lang.hitch( this, function() {
                domConstruct.empty( this._social_links );
                var socialLinkResume = new SocialLinkResume({
                      type: "tweetpoll",
                      item_id: this.data.id
                });
                domConstruct.empty( this._comments );
                this._social_links.appendChild( socialLinkResume.domNode );
            });

            var _delete = new ContentPane({
                 title: this.i18nMessage.tweetpoo_detail_tab_delete,
                 content: this._delete
            });

            this.contentPane.addChild( detail );

            // In scheduled mode this tab should be hidden
            if ( this.type_filter !== this._type_filter.SCHEDULED ) {
                this.contentPane.addChild( stats );
                this.contentPane.addChild( comments );
                this.contentPane.addChild( social );
                this.enableScheduledUI();
            }

            this.contentPane.addChild( _delete );

            this.contentPane.startup();
            this.updateDetail( this.data );
        },

        /**
         * The schedulded UI enables a special list of features
         */
        enableScheduledUI: function() {

            //TODO: execute background service to publish
        },

        /**
        * Delete a tweetpoll
        * @method _deleteTweetPoll
        */
        _deleteTweetPoll: function( e ) {
           this.stopEvent( e );
           var load = lang.hitch( this, function( data ) {
                this.publishMessage( _ENME.getMessage( "commons_success" ), _ENME.MSG.SUCCESS );
                this.destroy();
                domConstruct.destroy( this.domNode );
                this.parentWidget.destroy();
                domConstruct.destroy( this.parentWidget.domNode );
           }),
           error = lang.hitch( this, function( error ) {
             this.errorMessage( error );
           }),
           parent = this,
           div = this.createAlert("This message will be removed", "warn");
           this.createConfirmDialog("Do you want remove this Tweetpoll?", div.domNode, function() {
              parent.getURLService().del( [ "encuestame.service.list.tweetpoll.delete", [ parent.data.id ]], {}, load, error, lang.hitch( this, function() {

              }) );
          });
        },

        /***
        * Update Detail.
        */
        updateDetail: function( data ) {
          if ( data !== null ) {
              this.loadContent( data );
          } else {
              this.error();
          }
        },

        /***
        * Call Service.
        */
        _callService: function( /** function after response */ load, url ) {
	         var error = lang.hitch( this, function( error ) {
	             this.errorMessage( error );
	         });
	         var params = {
	                 tweetPollId: this.data.id
	         };
	         this.getURLService().get( url, params, load, error, lang.hitch( this, function() {

	         }) );
        },

        /***
        *
        */
        successDetailUpdateMessage: function() {
            this.publishMessage( _ENME.getMessage( "commons_success" ), _ENME.MSG.SUCCESS );
        },

        /***
        *
        */
        _setAllowLiveResults: function() {
         var load = lang.hitch( this, function( data ) {
             this.data.allowLiveResults = !this.data.allowLiveResults;
             this.successDetailUpdateMessage();
         });
         lang.hitch( this, this._callService( load, "encuestame.service.list.liveResultsTweetPoll" ) );
        },

        /***
        *
        */
        _setResumeLiveResults: function() {
         var load = lang.hitch( this, function( data ) {
             this.data.resumeLiveResults = !this.data.resumeLiveResults;
             this.successDetailUpdateMessage();
         });
         lang.hitch( this, this._callService( load, "encuestame.service.list.resumeliveResultsTweetPoll" ) );
        },

        /***
        *
        */
        _setCaptcha: function() {
         var load = lang.hitch( this, function( data ) {
             this.data.captcha = !this.data.captcha;
             this.successDetailUpdateMessage();
         });
         lang.hitch( this, this._callService( load, "encuestame.service.list.captchaTweetPoll" ) );
        },

        /***
        *
        */
        _setNotification: function() {
         var load = lang.hitch( this, function( data ) {
             this.data.closeNotification = !this.data.closeNotification;
             this.successDetailUpdateMessage();
         });
         lang.hitch( this, this._callService( load, "encuestame.service.list.notificationTweetPoll" ) );
        },

        /***
        * Set as repeated
        */
        _setRepeated: function() {
         var load = lang.hitch( this, function( data ) {
             this.data.allowRepeatedVotes = !this.data.allowRepeatedVotes;
             this.successDetailUpdateMessage();
         });
         lang.hitch( this, this._callService( load, "encuestame.service.list.repeatedTweetPoll" ) );
        },

        /**
        * Set a comment moderation
        * @method
        */
        _setCommentModeration: function() {
         var load = lang.hitch( this, function( data ) {
             this.data.moderated_comments = !this.data.moderated_comments;
             this.successDetailUpdateMessage();
         });
         lang.hitch( this, this._callService( load, "encuestame.service.list.commentTweetPoll" ) );
        },

        /***
        * Error messages.
        */
        error: function() {

          //This.errorMesage();
          //this.publishMessage(_ENME.getMessage('e_023'), _ENME.MSG.ERROR);
          this.errorMessage( _ENME.getMessage( "e_023" ) );
        },

        /***
        * Load Content.
        * @param data
        */
        loadContent: function( data ) {
          this.data = data;

          //Build Detail.
          domConstruct.empty( this._detailItems );

          //this.addDetail(this.builDetailRow("Public Link", this.createTextContent("http://www.google.es")));
          this.addDetail( this.builDetailRow( _ENME.getMessage("commons_created_date"),
              this.createTextContent( _ENME.fromNow( this.data.createdDateAt ) ) ) );
          this.addDetail( this.builDetailRow( _ENME.getMessage("commons_captcha"), this.addYesNoWidget( this.data.captcha,
                   lang.hitch( this, this._setCaptcha ) ) ) );
          this.addDetail( this.builDetailRow( _ENME.getMessage("tp_options_allow_results"),
                           this.addYesNoWidget( this.data.allowLiveResults,
                               lang.hitch( this, this._setAllowLiveResults ) ) ) );
          this.addDetail( this.builDetailRow( _ENME.getMessage("tp_options_follow_dashboard"), this.addYesNoWidget( this.data.resumeLiveResults,
              lang.hitch( this, this._setResumeLiveResults ) ) ) );
          this.addDetail( this.builDetailRow( _ENME.getMessage("tp_options_allow_repeated_votes"), this.addYesNoWidget(
                  this.data.allowRepeatedVotes,
              lang.hitch( this, this._setRepeated ) ) ) );
          this.addDetail( this.builDetailRow( _ENME.getMessage("tp_options_notifications"), this.addYesNoWidget(
                  this.data.closeNotification,
              lang.hitch( this, this._setNotification ) ) ) );
          this.addDetail( this.builDetailRow("Comments Moderated", this.addYesNoWidget(
                  this.data.moderated_comments,
                  lang.hitch( this, this._setCommentModeration ) ) ) );

	      //ENCUESTAME-264
          //this.addDetail(this.builDetailRow("Folder", this.createFolderCombo(this.data.closeNotification, lang.hitch(this, this._setNotification))));
          if ( this._extra ) {
              array.forEach(
                  this.data.tweetpoll_answers,
                  lang.hitch( this, function( data, index ) {
                  var param = {
                          aId: data.id,
                          color: data.results.color,
                          label: data.results.question_label,
                          owner: this.data.ownerUsername,
                          completed: this.data.completed,
                          url: data.short_url || data.relative_url
                      };
                  var row = new TweetPollAnswer( param, "tr");
                  this._extra.appendChild( row.domNode );
                  topic.publish("/encuestame/tweetpoll/detail/answer/reload", [ data.id, [ data.results.votes, data.results.percent ]] );
           }) );
          }
        },

        /***
        *  Add a detail
        *  @param the node
        */
        addDetail: function( node ) {
          if ( this._detailItems ) {
	          this._detailItems.appendChild( node );
          }
        },

	    /**
	     * Create a folder combo.
	     * @param storage dojo/Memory object
	     * @returns {registerForNodeList.domNode|*|tests.parser.Button.domNode}
	     */
	    createFolderCombo: function( storage, cb ) {
		    var filteringSelect = new FilteringSelect({
			    autoComplete: true,
			    required: true,
			    store: storage,
			    value: this.data.folder_id,
			    onChange: cb,
			    searchAttr: "name"
		    });
		    filteringSelect.startup();
		    return filteringSelect.domNode;
	    },

        /***
        * Yes / No.
        * @param value
        * @param onChange
        */
        addYesNoWidget: function( value, onChange ) {
          var widget = new YesNoWidget({ data: value });
          if ( onChange !== null ) {
              widget._onChange = onChange;
          }
          return widget.domNode;
        },

        /***
        * Create a text content.
        * //TODO move to WidgetServices
        */
        createTextContent: function( text ) {
          var textData = win.doc.createElement( "div" );
          textData.innerHTML = text;
          return textData;
        },

        /***
        * Build Detail Row.
        * @param labelText
        * @param dataContent
        */
        builDetailRow: function( labelText, dataContet ) {
          var rowDetail = win.doc.createElement( "div" );
          domClass.add( rowDetail, "rownDetail");
          var label = win.doc.createElement( "div" );
          domClass.add( label, "span");
          var labelItem = win.doc.createElement( "span" );
          labelItem.innerHTML = labelText;
          label.appendChild( labelItem );
          rowDetail.appendChild( label );
          var data = win.doc.createElement( "div" );
          domClass.add( data, "data");
          data.appendChild( dataContet );
          rowDetail.appendChild( data );
          rowDetail.id = labelText.toLowerCase();
          return rowDetail;
        },

        /***
        * Display Data.
        * @type
        */
        displayChart: function( type ) {

          //Var widget;
          this._callVotes( type );
        },

        /***
        * Call Votes.
        * @param type
        */
        _callVotes: function( type ) {
          var response = lang.hitch( this, function( dataJson ) {
              var votes = dataJson.success.votesResult;
              var results = [];
              array.forEach(
                      votes,
                      lang.hitch( this, function( data, index ) {
                          var answer = [ data.question_label, ( data.votes === null ? 0 : data.votes ), data.color ];

                          //Console.debug("Re answer", answer);
                          results.push( answer );
                          topic.publish("/encuestame/tweetpoll/detail/answer/reload", [ data.id, [ data.votes, data.percent ]] );
              }) );

              //Var id = this.id+"_chart";
              domConstruct.empty( this._chart );
              if ( this.widgetChart !== null ) {
                  this.widgetChart = null;
              }
              if ( type === this.typeChart[ 0 ] ) {
                  this.widgetChart = new EncuestamePieChart( this._chart, results, 200 );
              } else if ( type === this.typeChart[ 1 ] ) {
                  this.widgetChart = new EncuestamePieChart( this._chart, results, 200 );
              }
              this.render();
          });
          this._callService( response, "encuestame.service.list.VotesTweetPoll" );
        },

        /***
        * Render.
        */
        render: function() {
          this.widgetChart._buildSeries();
          this.widgetChart.render();
        }
    });
});
