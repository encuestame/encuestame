define( [
		"dojo/_base/declare",
		"dojo/on",
		"dojo/_base/lang",
		"dojo/topic",
		"dojo/store/Memory",
		"dojo/dom-class",
		"dijit/_WidgetBase",
		"dijit/_TemplatedMixin",
		"dijit/_WidgetsInTemplateMixin",
		"dijit/form/CheckBox",
		"dijit/Dialog",
		"me/core/main_widgets/EnmeMainLayoutWidget",
		"me/web/widget/home/votes/ItemVote",
		"me/web/widget/support/PanelWipe",
		"me/web/widget/options/PublishOptions",
		"me/web/widget/options/EmbebedOptions",
		"me/web/widget/options/PasswordOptions",
		"me/web/widget/poll/PollNavigateItemDetail",
		"me/core/enme",
		"dojo/text!me/web/widget/poll/templates/pollListItem.html" ],
		function(
			declare,
			on,
			lang,
			topic,
			Memory,
			domClass,
			_WidgetBase,
			_TemplatedMixin,
			_WidgetsInTemplateMixin,
			CheckBox,
			Dialog,
			main_widget,
			ItemVote,
			PanelWipe,
			PublishOptions,
			EmbebedOptions,
			PasswordOptions,
			PollNavigateItemDetail,
			_ENME,
			 template ) {
		return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

			/**
			 *  Template string.
 			 */
			templateString: template,

			/**
			 * All the data object
			 * @property
			 */
			data: null,

			/**
			* Publish dialog reference.
			* @property
			*/
			publish_dialog: null,

			/*
			* I18n message for this widget.
			*/
			i18nMessage: {
				poll_admon_poll_edit: _ENME.getMessage("poll_admon_poll_edit"),
				poll_admon_poll_preview: _ENME.getMessage("poll_admon_poll_preview"),
				poll_admon_poll_publish_options: _ENME.getMessage("poll_admon_poll_publish_options"),
				poll_admon_poll_embebed: _ENME.getMessage("poll_admon_poll_embebed"),
				poll_admon_poll_votes: _ENME.getMessage("poll_admon_poll_votes"),
				poll_password_link:  _ENME.getMessage("poll_password_link")
			},

			/*
			*
			*/
			_standBy: null,

			/**
			* Poll detail widget reference.
			*/
			widget_detail: null,

			/**
			 * Save the folder storage (Dojo Store)
			 */
			folderStore: null,

			/**
			* Post create cycle life.
			*/
			postCreate: function() {
				topic.subscribe("/encuestame/folder/distribute/load", lang.hitch( this, function( foldersData ) {
				  if ( foldersData && "success" in foldersData ) {
				    var folderData = foldersData.success.folders;
				    this.folderStore = new Memory({
				      data: folderData
				    });
				  }
				}) );
			  var panel = new PanelWipe( this._more, null, null, 390 );

			  //Add event on click edit link
			  panel.connect( this._edit, lang.hitch( this, this._callEditInfo ) );
			  if ( this._preview ) {
			      dojo.connect( this._preview, "onclick", lang.hitch( this, function( event ) {
			          var url = _ENME.pollDetailContext( this.data.id, this.data.question.slug );
			          window.open( url, "_blank" );
			      }) );
			  }
			  panel.preWipe = lang.hitch( this, function() {
			      domClass.add( this.domNode, "selected-row");
			  });
			  panel.postWipe =  lang.hitch( this, function() {
			      domClass.remove( this.domNode, "selected-row");
			  });

			  //This._standBy = dijit.byId("standby_"+this.id);
			  this.widget_detail = new PollNavigateItemDetail(
			      {
			        data: this.data,
			        parentWidget: this,
			        label: _ENME.getMessage( "poll_admon_poll_options" )
			      });
			  domClass.add( this.widget_detail.domNode, "hidden");
			  dojo.place( this.widget_detail.domNode, this._more );

			  //Set date
			  this._date.innerHTML = _ENME.fromNow( this.data.creation_date );

				// Enable the password options
				if ( this.data.isPasswordProtected && this.data.password_protected !== "" ) {
					  domClass.remove( this._password, "hidden");
						domClass.remove( this._protected, "hidden");
						domClass.add( this._embebed, "hidden" );
				}

				if ( this.data.is_hidden ) {
						domClass.remove( this._hidden, "hidden");
				}

				topic.subscribe("/encuestame/poll/item/hidden", lang.hitch( this, function( isHidden ) {
						if ( isHidden ) {
							domClass.remove( this._hidden, "hidden");
						} else {
							domClass.add( this._hidden, "hidden");
						}
				}) );

				topic.subscribe("/encuestame/poll/item/password", lang.hitch( this, function( isPassword ) {
					if ( isPassword ) {
						domClass.remove( this._password, "hidden");
						domClass.remove( this._protected, "hidden");
						domClass.add( this._embebed, "hidden" );
					} else {
						domClass.add( this._password, "hidden");
						domClass.add( this._protected, "hidden");
						domClass.remove( this._embebed, "hidden" );
					}
				}) );

			  //
			  on( this._publish, "click", lang.hitch( this, function( e ) {
			    e.preventDefault();
			      this._publish_options.createOptions( this.data, "poll", this._publish_options_dialog );
			      this._publish_options_dialog.show();
			  }) );

			  //
			  on( this._embebed, "click", lang.hitch( this, function( e ) {
			    e.preventDefault();
			      this._embebed_options.initialize();
			      this._embebed_options_dialog.show();
			  }) );

			on( this._password, "click", lang.hitch( this, function( e ) {
			  e.preventDefault();
			  this._passwordoptions_dialog.show();
			}) );

			},

			/**
			*
			* @property
			*/
			_closePublishDialog: function( e ) {
			  this._publish_options_dialog.hide();
			},

			_closeEmbebedDialog: function( e ) {
			  this._embebed_options_dialog.hide();
			},

			/**
			*
			* @private
			*/
			_closeDialog: function() {
			this._passwordoptions_dialog.hide();
			},

			/**
			* Call Edito Info.
			*/
			_callEditInfo: function() {
			  var load = lang.hitch( this, function( data ) {

			    //Console.log("this,folderStore", this.folderStore);
			      if ("success" in data ) {
			          domClass.remove( this.widget_detail.domNode, "hidden");
			          this.widget_detail.setResults( data.success.poll, this.folderStore );
			      } else {
			          this.errorMessage( error );
			      }
			  });
			  var error = lang.hitch( this, function( error ) {
			      this.errorMessage( error );
			  });
			  var params = {
			          id: this.data.id
			  };
			  domClass.add( this.widget_detail.domNode, "hidden");
			  this.getURLService().get( "encuestame.service.list.poll.detail", params, load, error, lang.hitch( this, function() {

			  }), true );
			}
			});
});
