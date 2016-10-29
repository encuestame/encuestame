define( [
         "dojo/_base/declare",
         "dojo/query",
		 "dojo/topic",
		"dojo/_base/lang",
		"dojo/store/Memory",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/Button",
		"dijit/form/FilteringSelect",
         "me/web/widget/chart/ChartLayerSupport",
         "me/web/widget/utils/UpdateDefaultOptions",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/core/URLServices",
         "me/web/widget/dialog/Confirm",
         "me/core/enme",
         "dojo/text!me/web/widget/poll/templates/pollListItemDetail.html" ],
        function(
                declare,
                query,
                topic,
                lang,
                Memory,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                Button,
                FilteringSelect,
                ChartLayerSupport,
                UpdateDefaultOptions,
                main_widget,
                URLServices,
                Confirm,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase,
                             _TemplatedMixin,
                             main_widget,
                             ChartLayerSupport,
                             UpdateDefaultOptions,
                             _WidgetsInTemplateMixin ], {

          // Template string.
           templateString: template,

           /**
           * H2 title.
           */
          label: "",

          /**
           *
           * @property
           */
          parentWidget: null,

          /**
           * The information of poll detail.
           */
          data: {},

          /*
           * I18n message for this widget.
           */
          i18nMessage: {
            poll_admon_poll_answers: _ENME.getMessage("poll_admon_poll_answers"),
            commons_remove: _ENME.getMessage("commons_remove"),
            poll_options_close: _ENME.getMessage("poll_options_close"),
            poll_options_quota: _ENME.getMessage("poll_options_quota"),
            poll_options_ip: _ENME.getMessage("poll_options_ip"),
            poll_options_password: _ENME.getMessage("poll_options_password"),
            poll_options_info: _ENME.getMessage("poll_options_info"),
            poll_options_public: _ENME.getMessage("poll_options_public"),
            poll_options_notifications: _ENME.getMessage("poll_options_notifications"),
            commons_confirm: _ENME.getMessage("commons_confirm"),
            commons_yes: _ENME.getMessage("commons_yes"),
            poll_is_hidden: _ENME.getMessage("poll_is_hidden"),
            commons_no: _ENME.getMessage("commons_no")
          },

          /**
           * Post create.
           */
          postCreate: function() {
              this._remove.onClick = lang.hitch( this, function() {
                var div = this.createAlert("You are removing this poll, you can recover it in the gadget directory.", "warn"),
                parent = this;
                this.createConfirmDialog("Are you sure?", div.domNode, function() {
                      var params = {
                        pollId: parent.data.id
                      };
                      var load = lang.hitch( parent, function() {
                           parent.successMesage("Poll Removed");
                           dojo.destroy( parent.parentWidget.domNode );
                      });

                      // Error handler
                      var error = lang.hitch( parent, function( error ) {
                          parent.errorMesage( error.message );
                      });

                      parent.getURLService().del("encuestame.service.list.poll.remove", params, load, error, lang.hitch( this, function() {

                      }), true );
                });
            });
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

          /**
           *
           * @param property
           */
          _updatePollParameters: function( property ) {
              var params = {
                   "pollId": this.data.id
              };
              var load = lang.hitch( this, function( data ) {
                  if ("success" in data ) {
                      console.info("success", data );
                      this.successMesage( _ENME.getMessage("commons_success") );
                  }
              });

              var error = lang.hitch( this, function( error ) {
                  this.errorMessage( error.message );
              });
              if ( property ) {
                  this.getURLService().post( [ "encuestame.service.list.poll.setParameter", [ property ]], params, load, error, lang.hitch( this, function() {
                  }) );
              } else {
                  this.errorMessage("error on update parameter");
              }
          },

          /**
           * Render the answer in table format.
           * @param data a object with answer data
           */
          reRenderResults: function( data ) {
              dojo.empty( this._detailAnswers );
              if ( data.length > 0 ) {
                  dojo.forEach( data, lang.hitch( this, function( answer ) {
                  var rowDetail = dojo.create( "div" );
                      dojo.addClass( rowDetail, "web-poll-answer-row");

                      //Color
                      var color = dojo.create( "div" );
                      var span_color = dojo.create( "span" );

                      dojo.style( span_color, "display", "inline-block");
                      dojo.style( span_color, "width", "30px");
                      dojo.place( span_color, color );
                      dojo.addClass( color, "web-poll-answer-row-color");
                      dojo.style( color, "backgroundColor", answer.color );

                      //Color.innerHTML = answer.color;
                      dojo.place( color, rowDetail );

                      //Label
                      var label = dojo.create( "div" );
                      dojo.addClass( label, "web-poll-answer-row-label");
                      label.innerHTML = answer.answers;
                      dojo.place( label, rowDetail );

                      //Percent
                      var percent = dojo.create( "div" );
                      dojo.addClass( percent, "web-poll-answer-row-percent");
                      percent.innerHTML = typeof answer.votes == "undefined" ? 0 : answer.votes;
                      dojo.place( percent, rowDetail );

                      //Append to root
                      dojo.place( rowDetail, this._detailAnswers );
                  }) );
              } else {
                  dojo.place( encuestame.utilities.noResults("web-poll-answer-no-results"), this._detailAnswers );
              }
          },

          /**
           *
           * @method _displayAnswers
           */
          _displayAnswers: function( e ) {
              dojo.addClass( this._detailWrapperItems, "hidden" );
              dojo.removeClass( this._detailWrapperAnswers, "hidden" );
              query(".selected", this.domNode ).forEach( function( node ) {
                    dojo.removeClass( node, "selected" );
              });
              dojo.addClass( e.target, "selected" );
          },

          /**
           *
           * @method _displayOptions
           */
          _displayOptions: function( e ) {
              dojo.addClass( this._detailWrapperAnswers, "hidden" );
              dojo.removeClass( this._detailWrapperItems, "hidden" );
              query(".selected", this.domNode ).forEach( function( node ) {
                    dojo.removeClass( node, "selected" );
              });
              dojo.addClass( e.target, "selected" );
          },

          /**
           * Set results.
           * @param data a object with answer data
           */
          setResults: function( data, store ) {
              dojo.empty( this._detailItems );
              this.setNodeAppend( this._detailItems );
              this.addRow( this.i18nMessage.poll_options_close, data.poll_bean.is_close_after_date, lang.hitch( this, this._updatePollParameters ), "change-open-status");
              this.addRow( this.i18nMessage.poll_options_quota, data.poll_bean.is_close_after_quota, lang.hitch( this, this._updatePollParameters ), "close-after-quota");
              this.addRow( this.i18nMessage.poll_options_ip, data.poll_bean.is_ip_restricted, lang.hitch( this, this._updatePollParameters ), "ip-protection");
              this.addRow( this.i18nMessage.poll_options_notifications, data.poll_bean.close_notification, lang.hitch( this, this._updatePollParameters ), "notifications");
              this.addRow( this.i18nMessage.poll_options_password, data.poll_bean.is_password_restriction, lang.hitch( this, function( param ) {
	              var isHidden = !data.poll_bean.is_password_restriction;
	              data.poll_bean.is_password_restriction = !data.poll_bean.is_password_restriction;
	              if ( isHidden ) {
		              topic.publish("/encuestame/poll/item/password", true );
	              } else {
		              topic.publish("/encuestame/poll/item/password", false );
	              }
	              this._updatePollParameters( param );
              }), "password-restrictions");
              this.addRow( this.i18nMessage.poll_options_info, data.poll_bean.is_show_additional_info, lang.hitch( this, this._updatePollParameters ), "additional-info");
              this.addRow( this.i18nMessage.poll_options_public, data.poll_bean.show_resultsPoll, lang.hitch( this, this._updatePollParameters ), "change-display-results");
              this.addRow( this.i18nMessage.poll_is_hidden, data.poll_bean.is_hidden, lang.hitch( this, function( param ) {
	              var isHidden = !data.poll_bean.is_hidden;
	              data.poll_bean.is_hidden = !data.poll_bean.is_hidden;
	              if ( isHidden ) {
		              topic.publish("/encuestame/poll/item/hidden", true );
	              } else {
		              topic.publish("/encuestame/poll/item/hidden", false );
	              }
	              this._updatePollParameters( param );
              }), "is-hidden");

	          //ENCUESTAME-264
	          dojo.empty( this._folder_detail );
	          var node_folder = this.createFolderCombo( store, lang.hitch( this, function( item ) {
	             this.data.folder_id = item;
	             topic.publish("/encuestame/folder/item/add", item, this.data.id );
	          }) );
	          this._folder_detail.appendChild( node_folder );
              var nodeId = this.id + "_chart";
              dojo.empty( dojo.byId( nodeId ) );

              //If results are empty it's needed display a "no results" option
              if ( data.poll_results.length > 0 ) {
                 this.widgetChart = this.buildChart({
                 id: nodeId,
                 results: this._convertToChartAnswer(
                       this._mergeResultsAnswers(
                           data.poll_list_answers,
                           data.poll_results ) )
                 });
                 this.renderChart( this.widgetChart );
              } else {
                var node = dojo.byId( nodeId ),
                no_results = dojo.create( "div" );
                no_results.innerHTML = _ENME.getMessage( "commons_no_results" );
                dojo.addClass( no_results, "no_results");
                node.appendChild( no_results );
              }

              //Display the list of answer on a table.
              this.reRenderResults( this._mergeResultsAnswers( data.poll_list_answers, data.poll_results ) );

              var comments = dojo.create("div");
              dojo.addClass( comments, "ui-comments ui-icon");
              comments.innerHTML = data.poll_bean.total_comments;

              var hits = dojo.create("div");
              dojo.addClass( hits, "ui-hits ui-icon");
              hits.innerHTML = data.poll_bean.hits;

              var likes = dojo.create("div");
              dojo.addClass( likes, "ui-likes ui-icon");
              likes.innerHTML = data.poll_bean.like_votes;

              var dislike = dojo.create("div");
              dojo.addClass( dislike, "ui-dislike ui-icon");
              dislike.innerHTML = data.poll_bean.dislike_votes;

              //Empty
              dojo.empty( this._detail_info );

              //Append the info
              dojo.place( comments, this._detail_info );
              dojo.place( hits, this._detail_info );
              dojo.place( likes, this._detail_info );
              dojo.place( dislike, this._detail_info );
          },

          /**
           * Merge the answer with votes, if results exist is merged with the vote.
           * Added new property, answer.vote = x;
           * @param list_answers
           * @param list_results
           */
          _mergeResultsAnswers: function( list_answers, list_results ) {
              dojo.forEach( list_answers,
                      lang.hitch( this, function( data, index ) {
                          dojo.forEach( list_results, function( data2, index ) {
                              if ( data2.answer.answer_id === data.answer_id ) {
                                  data.votes = data2.answer_votes;
                                  data.percent = 0;
                                  return false;
                              }
                          });
                 }) );
              return list_answers;
          },

          /**
           * Convert to answer to chat.
           * @param data a array with answers object
           */
          _convertToChartAnswer: function( answers ) {
              var array = [];
              dojo.forEach( answers, function( answer ) {
                 array.push( [ answer.answers.substring( 0, 8 ), typeof answer.votes == "undefined" ? 0 : answer.votes, answer.color ] );
              });
              return array;
          }
    });
});
