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
 *  @version 1.5
 *  @module ENME
 *  @namespace Widget
 *  @class enme
 */
define([
        "dojo/_base/lang",
        "dojo/_base/array",
        'me/core/_base/_config',
        'me/core/_base/_browser',
        'me/core/_base/_utils',
        'me/core/_base/_log',
        'me/core/_base/_xhr',
        'me/core/_base/_messages',
        'me/core/_base/_storage',
        'dojo/has',
        "dojo/has!host-browser?dojo/query",
        "dojo/has!host-browser?dojo/dom-attr"
        ], function(
            lang,
            _array,
            _config,
            _browser,
            _utils,
            _log,
            _xhr,
            _messages,
            _storage,
            has,
            query,
            domAttr) {
    //Define if is initialize.
    var isInitialised = false,
    device_info = {
      data_bo : null,
      data_vo : null,
      data_bo_label : '',
      data_so : ''
    },
    activity = null,
    offline = null,
    // expired session flag
    expired_session = false,
    // to store the default configuration
    // spinner default opts
    // channel to publish
    channel = '/encuestame/message/publish',
    // get the configuration default
    duration = 5000,
    // message type
    messageTypes = { //repeated in _messages.js
      MESSAGE: "message",
      WARNING: "warning",
      ERROR: "error",
      FATAL: "fatal"
    },
    // default time / formats
     time = {
        timeFormat : "hh:mm:ss",
        dateFormat : "M/d/yy"
    },
    shortUrlProvider = [
      {
        code:"googl",
        label:"http://googl.com"
      },
      {
        code:"tinyurl",
        label:"http://tinyurl.com"
      },
      {
        code:"bitly",
        label:"http://bit.ly/"
      }
    ],
     _e_parent = this;

    var _service = {};

    var _enme = {

      /**
       *
       * @method
       */
      service : _service,

      /**
       *
       * @property shortUrlProvider
       */
      shortUrlProvider: shortUrlProvider,

      /**
       *
       */
      MESSAGES_TYPE : messageTypes,

      /**
       * default time / formats
       */
      FORMAT_TIME : time,

      /**
       *
       * @method
       */
      expired_session : expired_session,

      /**
       * @deprecated moved to constants.js
       */
      STATUS : ['SUCCESS','FAILED', 'STAND_BY', 'SCHEDULED', 'RE_SEND'],

      /**
       * Default time format.
       */
      timeFormat : "hh:mm:ss",

      /**
       * Default date format.
       */
      dateFormat : "M/d/yy",

      /**
       * @deprecated moved to constants.js
       */
      SURVEYS : ['TWEETPOLL', 'POLl', 'SURVEY', 'HASHTAG'],

      /**
       * @deprecated moved to constants.js
       */
      IMAGES_SIZE : {
          thumbnail : "thumbnail",
          defaultType : "default",
          profile : "profile",
          preview : "preview",
          web : "web"
      },
      //@deprecated
      SUCCESS : "success",
      // - Date Range parameters - //
      // last year
      YEAR : '365',
      // last 24 hours
      DAY : '24',
      // last 7 days
      WEEK : '7',
      // last 30 days
      MONTH : '30',
      // all item
      ALL : 'all',
      // Hashtag rated filter
      HASHTAGRATED : "HASHTAGRATED",
      // messages
      MSG : {
        SUCCESS : 'success',
        ERROR : 'error',
        WARN : 'warn',
        FATAL : 'fatal'
      },

      // type of surveys
      TYPE_SURVEYS : [ 'TWEETPOLL', 'POLL', 'SURVEY', 'HASHTAG' ],

	  GRADINENT_CLASS : "gradient-gray",
	  HIDDEN_CLASS : "hidden",

      /**
       * Store a list of parameters.
       */
      params : {},

      /**
       * A reference of himself.
       */
      _$self : this,

      /**
       * Get a config value.
       * @param value
       */
      config : function (value) {
        return _config.get(value);
      },

    /**
     *
      * @param property
     * @param value
     * @returns {*}
     */
     set : function (property, value) {
         return _config.set(property, value);
     },

      /**
       *
       */
      stopEvent : function(e) {
        e.preventDefault();
        e.stopPropagation();
        return false;
      },


     /**
       * Initialize the core.
       * @param config {Object}
       */
      init : function(config) {
        var ENME = this;
        _config.merge(config);
        // this initialization should be only in the browser
        if (has("host-browser")) {
            this.state = Offline.state;
            query(".header_input_hidden input[type='hidden']").forEach(
                function (item, index) {
                    ENME.params[domAttr.get(item, "name")] = domAttr.get(item, "value");
                }
            );
            // add additional params
            if (config.params) {
                ENME.params = lang.mixin(ENME.params, config.params);
            }
            // get the device info from the DOM
            var html = query("html")[0];
            device_info = {
                data_bo      : html.getAttribute('data-bo'),
                data_vo      : html.getAttribute('data-vo'),
                data_bo_label: html.getAttribute('data-bo-name'),
                data_so      : html.getAttribute('data-so')
            };
        }
        isInitialised = true;
      },


        /**
         * Get message
         * @param value {String} the id message
         * @param default_value {String} if value is undefined, display default
         */
        getMessage : function(value, default_value) {
            var message = typeof this.params[value] === 'undefined' ?
                ((default_value === null || typeof default_value === 'undefined') ?
                    "NOT_DEFINED[" + value + "]" :
                    default_value) : this.params[value];
            return message;
        },


        /**
         * Convert array of string to object, get a i18n message from the context and return object.
         * @method object with i18n message
         */
        getMessages : function(arrayList) {
            var i18n = {},
                parent_enme = this;
            _array.forEach(arrayList, function(message, i) {
                if (typeof(message === 'string')) {
                    var iMessage = parent_enme.getMessage(message);
                    i18n[message] = iMessage;
                }

            });
            return i18n;
        },

    /**
     * Return all i18n messages.
     * @method
     */
    getAllMessages : function () {
        return this.params;
    },

    /**
     *
     * @property
     */
    activityDisconect: function() {},

    /**
     * Check if the webapp is offline.
     * @property state
     */
    state : true,

    /**
     * Save the current activity object
     * @param _activity the activity object
     * @method  setActivity
     */
    setActivity : function(_activity) {
      activity = _activity;
    },

    /**
     * Save the current activity object
     * @param _activity the activity object
     * @method  setActivity
     */
    setOffline : function(_offline) {
      offline = _offline;
    },

    /**
     * Get the current activity object
     * @method  getActivity
     */
    getActivity : function () {
       return activity;
    },

    /**
     * Get the current activity object
     * @method  getActivity
     */
    getOffline : function () {
       return offline;
    }
  };

  //merge submodules
  _enme = lang.mixin(_enme, _utils);
    _enme = lang.mixin(_enme, _utils);
  _enme = lang.mixin(_enme, _storage);
  _enme = lang.mixin(_enme, _browser);
  _enme = lang.mixin(_enme, _messages);
  _enme = lang.mixin(_enme, _log);
  _enme.xhr = _xhr;
  //console.log("_enme==>", _enme);
  return _enme;
});
