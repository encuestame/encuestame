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
 *  @module Services
 *  @namespace Widget
 *  @class EnmeMainLayoutWidget
 *  @requires WidgetServices
 */

define( [ "dojo/parser",
         "dojo/_base/declare",
     "me/core/URLServices",
     "me/web/widget/dialog/Confirm",
     "me/web/widget/ui/Alert",
     "me/web/widget/support/IntroJSupport",
     "me/core/main_widgets/WidgetServices",
     "me/core/enme" ], function(
    parser,
    declare,
    _URL,
    Confirm,
    Alert,
    IntroJSupport,
    _WIDGET,
    _ENME ) {

  return declare( [ _WIDGET, IntroJSupport ], {

      /**
       *
       * @property defaultNoResults
       */
       defaultNoResults: "Nothing find with ",

      /**
       *
       * @property
       */
      typeChart: [ "Bars", "Pie", "Lines" ],

       /**
        * Return if the device is mobile or not
        */
       isMobile: _ENME.config( "isMobile" ),

      /**
       * I18n Message.
       */
      i18nMessage: {
          commons_confirm: _ENME.getMessage("commons_confirm"),
          commons_yes: _ENME.getMessage("commons_yes"),
          commons_no: _ENME.getMessage("commons_no")
       },

	  /*
	   * Tist of filters, this content should coincide with api enum.
	   */
	  _type_filter: {
		  BYOWNER: "BYOWNER",
		  LASTDAY: "LASTDAY",
		  LASTWEEK: "LASTWEEK",
		  FAVOURITES: "FAVOURITES",
		  SCHEDULED: "SCHEDULED",
		  ALL: "ALL"
	  },

       /**
        * Default context path.
        * @property contextDefaultPath
        */
       contextDefaultPath: _ENME.config( "contextPath" ),

       /**
        * Contains all 18n messages available in the context
        * @property
        */
       i18n: {},

      /**
       * Add item on drop down menu.
       * @property append
       */
       append: function( node, place ) {
          dojo.place( node, place );
       },

       /**
        *
        * @property
        */
       addOfflineExecution: function( executions ) {
          if ( _ENME.getOffline() ) {
            _ENME.getOffline().bindExecutions( executions );
          }
       },

       /**
        *
        * @method
        */
       getErrorMessage: function( error ) {
          return error.response.data.error.message;
       },

       /**
        * Create an alert message.
        * @method createAlert
        */
       createAlert: function( message, type ) {
          var widget = new Alert({
              message: message,
              type_message: type
            });
          return widget;
       },

      /**
       * Create a confirm dialog
       * @param title
       * @param content
       */
       createConfirmDialog: function( title, content, yesHandler ) {
            var myDialog = new Confirm({
                title: title,
                content_widget: content,
                style: "width: 650px",
                label: {
                    yes: this.i18nMessage.commons_yes || "Yes",
                    no: this.i18nMessage.commons_no || "No"
                }
              });
              myDialog.functionYes = dojo.hitch( this, function() {
                yesHandler();
                myDialog.hide();
              });
              myDialog.show();
       },

	  /**
	   * Validate the username.
	   * @param username
	   * @returns {*}
	   */
	  validateUsername: function( username ) {
		  return _ENME.validateCharacterDigits( username );
	  },

       /**
        * Constructor
        * @method constructor
        */
       constructor: function() {
           this.i18n = _ENME.getAllMessages();
       },

      /**
       * Validate
        */

//       SurveyValidation : {
//
//       },

       /**
        * Range Actions
        * @property range_actions
        */
       range_actions: [ {
         period: "All",
         value: "all",
         action: dojo.hitch( this, function( channel ) {
           dojo.publish( channel, [ "all" ] );
         })
       }, {
         period: "Last Year",
         value:  "365",
         action: dojo.hitch( this, function( channel ) {
           dojo.publish( channel, [ "365" ] );
         })
       }

//       {
//         period : "Last Month",
//         value  : "30",
//         action : dojo.hitch(this, function(channel) {
//           dojo.publish(channel, [ "30" ]);
//         })
//       }, {
//         period : "Last Week",
//         value  : "7",
//         action : dojo.hitch(this, function(channel) {
//           dojo.publish(channel, [ "7" ]);
//         })
//       }
//       {
//         period : "Last Day",
//         value  : "24",
//         action : dojo.hitch(this, function(channel) {
//           dojo.publish(channel, [ "24" ]);
//         })
//       }
       ]
  });
});
