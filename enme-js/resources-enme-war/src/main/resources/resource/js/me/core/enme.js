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
 *  @module ENME
 *  @namespace Widget
 *  @class enme
 */
define(["dojo",
        "dojo/dom",
        "dojo/request",
        "dojo/_base/lang",
        "dojo/_base/array",
        'dojo/_base/json',
        "dojo/number",
        "dojo/cookie",
        "dojo/on",
        "dojo/query",
        "dojo/dom-attr"], function(
            dojo,
            dom,
            request,
            lang,
            _array,
            json,
            number,
            cookie,
            on,
            query,
            domAttr) {

    //dojo.require( "dojo.date.locale" );

    //Define if is initialize.
    var isInitialised = false,
    activity = null,
    // to store the default configuration
    _config = {},
    // channel to publish
    channel = '/encuestame/message/publish',
    // get the configuration default
    duration = 5000,
    // message type
    messageTypes = {
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
    // json-comment-optional, json-comment-filtered
    _handleAs = "json",
      // Indicates whether a request should be allowed to
    _failOk = true,
    _timeout = 20000,
      //_cross_browsing = false,
     _preventCache = true,
     _e_parent = this;

    /*
     * Append the context to each service @param
     */
    var _appendContext = function(service) {
       var url = this.config['contextPath'];
       this.log("_appendContext-->", url);
       url = url.concat(service);
       return url;
    };

    /**
     * List of services
     * @property SERVICES
     */
    var SERVICES = {
        "encuestame.service.list.userList" : "/api/admon/users.json",
        "encuestame.user.invite" : "api/user/invite.json",
        "encuestame.service.list.getNotifications" : "/api/notifications/list.json",
        "encuestame.service.list.getAllNotifications" : "/api/notifications/all/list.json",
        "encuestame.service.list.changeStatusNotification" : "/api/notifications/readed.json",
        "encuestame.service.list.removeNotification" : "/api/notification/remove.json",
        "encuestame.service.list.userInfo" : "/api/admon/user-info.json",
        "encuestame.poll.publish.social" : "//api/survey/poll/social/publish.json",
        "encuestame.service.list.createUser" : "/api/admon/create-user.json",
        "encuestame.service.list.profile.my" : "/api/settings/profile/my.json",
        "encuestame.service.list.upgradeProfile" : "/api/user/profile/upgrade.json",
        "encuestame.service.list.updateProfile" : "/api/settings/profile/update.json",
        "encuestame.service.list.updateUsername" : "/api/settings/profile/username/update.json",
        "encuestame.service.list.updateEmail" : "/api/settings/profile/email/update.json",
        "encuestame.service.list.updatePicture" : "/api/settings/profile/picture/update.json",
        "encuestame.service.list.listPermissions" : "/api/admon/list-permissions.json",
        "encuestame.service.list.listUserPermissions" : "/api/admon/list-user-permissions.json",
        "encuestame.service.list.addPermission" : "/api/admon/add-permission.json",
        "encuestame.service.list.removePermission" : "/api/admon/remove-permission.json",
        "encuestame.service.list.hashtags" : "/api/common/hashtags.json",
        "encuestame.service.list.ranking.hashtag" : "/api/common/hashtags/stats/ranking.json",
        "encuestame.service.list.range.hashtag" : "/api/common/hashtags/stats/button/range.json",
        "encuestame.service.list.hashtagsAction.getAction" : "/api/survey/hashtag/$0/$1.json",
        "encuestame.service.list.cloud" : "/api/common/hashtags/cloud.json",
        "encuestame.service.list.allSocialAccount" : "/api/common/social/accounts.json",
        "encuestame.service.list.publishTweetPoll" : "//api/survey/tweetpoll/publish.json",
        "encuestame.service.list.listTweetPoll" : "/api/survey/tweetpoll/search.json",
        "encuestame.service.list.changeTweetPollStatus" : "/api/survey/tweetpoll/change-open-status-tweetpoll.json",
        "encuestame.service.list.resumeliveResultsTweetPoll" : "/api/survey/tweetpoll/resumeliveResults-tweetpoll.json",
        "encuestame.service.list.captchaTweetPoll" : "/api/survey/tweetpoll/captcha-tweetpoll.json",
        "encuestame.service.list.favouriteTweetPoll" : "/api/survey/tweetpoll/favourite-tweetpoll.json",
        "encuestame.service.list.liveResultsTweetPoll" : "/api/survey/tweetpoll/liveResults-tweetpoll.json",
        "encuestame.service.list.notificationTweetPoll" : "/api/survey/tweetpoll/notification-tweetpoll.json",
        "encuestame.service.list.repeatedTweetPoll" : "/api/survey/tweetpoll/repeated-tweetpoll.json",
        "encuestame.service.list.VotesTweetPoll" : "/api/chart/tweetpoll/votes.json",
        "encuestame.service.list.tweetpoll.answer.getVotes" : "/api/tweetpoll/$0/answer/$1/votes.json",
        "encuestame.service.list.listPoll" : "/api/survey/poll/search.json",
        "encuestame.service.list.poll.create" : "/api/survey/poll/create.json",
        "encuestame.service.list.poll.publish" : "/api/survey/poll/publish.json",
        "encuestame.service.list.poll.remove" : "//api/survey/poll/remove.json",
        "encuestame.service.list.poll.detail" : "/api/survey/poll/detail.json",
        "encuestame.service.list.poll.setParameter" : "/api/survey/poll/$0-poll.json",
        "encuestame.service.list.poll.getVotes" : "/api/$0/poll/votes.json",
        "encuestame.service.comments.list" : "/api/common/comment/comments/$0.json",
        "encuestame.service.comments.search" : "/api/common/comment/search.json",
        "encuestame.service.comments.like" : "/api/common/comment/like_vote.json",
        "encuestame.service.comments.dislike" : "/api/common/comment/dislike_vote.json",
        "encuestame.service.comments.create" : "/api/common/comment/create.json",
        "encuestame.service.list.rate.comments" : "/api/common/comment/rate/top.json",
        "encuestame.service.list.rate.profile" : "/api/common/frontend/topusers.json",
        "encuestame.service.list.rate.stats" : "/api/common/frontend/$0/stats.json",
        "encuestame.service.list.generic.stats" : "/api/common/stats/generic.json",
        "encuestame.service.list.rate.buttons" : "/api/common/hashtags/stats/button.json",
        "encuestame.service.list.getTweetPollVotes" : "/api/$0/tweetpoll/$1/votes.json",
        "encuestame.service.list.votes.home" : "/api/frontend/home/vote.json",
        "encuestame.service.list.addAnswer" : "/api/survey/tweetpoll/answer/add.json",
        "encuestame.service.list.removeAnswer" : "/api/survey/tweetpoll/answer/remove.json",
        "encuestame.service.list.groupCreate" : "/api/groups/createGroup.json",
        "encuestame.service.list.updateCreate" : "/api/groups/updateGroup.json",
        "encuestame.service.list.removeGroup" : "/api/groups/removeGroup.json",
        "encuestame.service.list.loadGroups" : "/api/groups/groups.json",
        "encuestame.service.list.assingGroups" : "/api/admon/groups/assingToUser.json",
        "encuestame.service.list.checkProfile" : "/api/user/account/validate.json",
        "encuestame.service.publicService.validate.username" : "/api/public/validator/username.json",
        "encuestame.service.publicService.validate.email" : "/api/public/validator/email.json",
        "encuestame.service.publicService.validate.realName" : "/api/public/validator/realName.json",
        "encuestame.service.social.links.loadByType" : "/api/public/social/links/published.json",
        "encuestame.service.social.action.defaultState" : "/api/social/actions/account/default.json",
        "encuestame.service.social.action.remove" : "/api/social/actions/account/remove.json",
        "encuestame.service.search.suggest" : "/api/search/quick-suggest.json",
        "encuestame.service.stream" : "/api/common/frontend/stream.json",
        "encuestame.service.folder.create" : "/api/survey/folder/$0/create.json",
        "encuestame.service.folder.update" : "/api/survey/folder/$0/update.json",
        "encuestame.service.folder.remove" : "/api/survey/folder/$0/remove.json",
        "encuestame.service.folder.move" : "/api/survey/folder/$0/move.json",
        "encuestame.service.folder.list" : "/api/survey/folder/$0/list.json",
        "encuestame.service.dashboard.create" : "/api/common/dashboard/create-dashboard.json",
        "encuestame.service.dashboard.list" : "/api/common/dashboard/list.json",
        "encuestame.service.dashboard.select" : "/api/common/dashboard/select.json",
        "encuestame.service.gadget.directory" : "/api/common/gadgets/directory.json",
        "encuestame.service.gadget.list" : "/api/common/gadgets/list.json",
        "encuestame.service.gadget.move" : "/api/common/dashboard/move-gadget.json",
        "encuestame.service.gadget.add" : "/api/common/gadgets/add.json",
        "encuestame.service.gadget.load" : "/api/common/dashboard/gadget/load.json",
        "encuestame.service.gadget.remove" : "/api/common/dashboard/gadget/remove.json",
        "encuestame.service.tweetpoll.autosave" : "/api/survey/tweetpoll/autosave.json"
    };

    /**
     * Identify
     * @method
     */
    var _service = function(key, params) {
        this.log("_service key-->", key);
        this.log("_service params-->", params);
        var _service_store = SERVICES,
        _parent = this;
          if (!params) {
             var url = _config['contextPath'];
             url = url.concat(_service_store[key]);
            return url;
          } else {
            var url = _config['contextPath'];
            url = url.concat(_service_store[key]);
            _array.forEach(params, function(entry, i) {
              if (entry !== "undefined" && entry !== '') {
                url = url.replace("$" + i, entry);
              }
            });
            return url;
          }
     };

     /**
       *
       */
      var _makeCall = function(url , params, method, response, error, loaderHandler) {
          var _load = response,
          _error = error,
          make_call_parent = this;
          if (loaderHandler != 'undefined' && typeof loaderHandler === 'function') {
              // load default handler
              _load = function(r) {
                  try {
                    //TODO: this sentence is repeated
                    if (typeof loaderHandler === 'function') {
                       loaderHandler();
                    }
                    response(r);
                  } catch(error) {
                      make_call_parent.log(error);
                  }
              };
              // error default handler
              _error = function(e) {
                  try{
                      if (typeof loaderHandler === 'function') {
                         loaderHandler();
                      }
                      error(e);
                  } catch(error) {
                      make_call_parent.log(error);
                  }
              };
          }

          // default xhr parameters
          var _params = {
             handleAs : _handleAs,
               failOk : _failOk,
               timeout : _timeout,
               method : method,
               preventCache : _preventCache
           };

           // on dependes the method, the way to send the data it's different

           if (method === 'POST' || method === 'PUT') {
                _params = lang.mixin(_params, {
                    data : params
                });
           } else if (method === 'GET' || method === 'DELETE') {
                _params = lang.mixin(_params, {
                    query : params,
                    headers: { "Content-Type": "application/json"}
                });
           }

          // resolve complex url
          try {
             var _service_url = null;
             if (typeof url === 'string') {
                 _service_url = _service(url);
             } else if (lang.isArray(url) && url.length === 2) {
                 _service_url = _service(url[0], url[1]);
             } else if (lang.isArray(url) && url.length === 1) {
                 _service_url = _service(url[0]);
             }
             if (_service_url !== null ) {
                  // make the request
                  this.log("url to call -->", _service_url);
                  this.log("URL ---->", _service_url);
                  request(_service_url, _params).then(_load, _error,
                           function(evt) {});
             }
          } catch (error) {
              this.log("error service ", error, arguments);
          }
    };

    /**
     *
     */
    var _publish = function(message, description, type) {
      description = description === null ? '' : description;
      if (typeof(message === 'string')) {
          dojo.publish(channel, [{
            message: message,
            type: type,
            duration: duration,
            description : description
          }]);
      }
    };

    return {

      service : _service,

      /**
       *
       */
      MESSAGES_TYPE : messageTypes,

      /**
       * default time / formats
       */
      TIME : time,

      /**
       * @deprecated moved to constants.js
       */
      STATUS : ['SUCCESS','FAILED', 'STAND_BY', 'RE_SCHEDULED', 'RE_SEND'],

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
      // default status
      STATUS : [ 'SUCCESS', 'FAILED', 'STAND_BY', 'RE_SCHEDULED',
          'RE_SEND' ],
          // messages
      MSG : {
        SUCCESS : 'success',
        ERROR : 'error',
        WARN : 'warn',
        FATAL : 'fatal'
      },

      // type of surveys
      TYPE_SURVEYS : [ 'TWEETPOLL', 'POLL', 'SURVEY', 'HASHTAG' ],

      success : function (message, description) {
          _publish(message, description, messageTypes.MESSAGE);
        },

        warning : function (message, description) {
          _publish(message, description, messageTypes.WARNING);
        },

        error : function (message, description) {
          _publish(message, description, messageTypes.ERROR);
        },

        fatal : function (message, description) {
          _publish(message, description, messageTypes.FATAL);
        },

      /**
       * Store a list of parameters.
       */
      params : {},

      /**
       * Returns an HTMLElement reference.
       *
       * @method $
       * @param {String |
       *            HTMLElement |Array} el Accepts a string to use as an
       *            ID for getting a DOM reference, an actual DOM
       *            reference, or an Array of IDs and/or HTMLElements.
       * @return {HTMLElement | Array} A DOM reference to an HTML element
       *         or an array of HTMLElements.
       */
      $ : dojo,

      /**
       * A reference of himself.
       */
      _$self : this,

      /**
       *
       */
      log : function(obj) {
        if (typeof console != "undefined" && console.log && this.config('debug')) { //TODO: Add verbose condition.
          log(obj);
        }
      },

      /**
       * Get a config value.
       * @param value
       */
      config : function (value) {
        return _config[value];
      },

      /**
       * Build a user profile url.
       * @param username the user name
       * @method
       */
     usernameLink :function(username) {
          var url = this.config('contextPath');
          if (username) {
              url = url.concat("/profile/");
              url = url.concat(username);
              return url;
          } else {
              url = url.concat("/404");
              return url;
          }
      },

      /**
       *
       */
      stopEvent : function(e) {
//        on.emit(target, "event", {
//            bubbles: true,
//            cancelable: true
//          });
        e.stopPropagation();
        return false;
      },

      /**
       * Return the url query as a object.
       * @method getQueryAsObject
       */
      getURLParametersAsObject : function () {
        var query_string = {};
            var query = window.location.search.substring(1);
            var vars = query.split("&");
            for (var i=0;i<vars.length;i++) {
              var pair = vars[i].split("=");
                // If first entry with this name
              if (typeof query_string[pair[0]] === "undefined") {
                query_string[pair[0]] = pair[1];
                // If second entry with this name
              } else if (typeof query_string[pair[0]] === "string") {
                var arr = [ query_string[pair[0]], pair[1] ];
                query_string[pair[0]] = arr;
                // If third or later entry with this name
              } else {
                query_string[pair[0]].push(pair[1]);
              }
            }
        return query_string;
      },

      /**
       *
       */
      // include : function(url) {
      //   if (!this.contains(included, url)) {
      //     included.push(url);
      //     var s = document.createElement("script");
      //     s.src = url;
      //     query("body").append(s);
      //   }
      // },


      toggleClassName : function(element, className) {
        if (!(element = query(element))) {
          return;
        }
        element.toggleClass(className);
      },


      setVisible : function(element, show) {
        if (!(element = this.$(element))) {
          return;
        }
        var $ = query;
        $(element).each(function() {
          var isHidden = $(this).hasClass("hidden");
          if (isHidden && show) {
            $(this).removeClass("hidden");
          } else if (!isHidden && !show) {
            $(this).addClass("hidden");
          }
        });
      },

      getBoolean : function(value) {
        if (value != null) {
          if (typeof value == "boolean") {
            return value;
          } else {
            return (value === "true" ? true : false);
          }
        }
        return false;
      },

      isVisible : function(element) {
        return !query(element).hasClass("hidden");
      },

      /**
       * Initialize the core.
       * @param config {Object}
       */
      init : function(config) {
        var ENME = this;
        _config = config || {};
        query(".header_input_hidden input[type='hidden']").forEach(
          function(item, index) {
            ENME.params[domAttr.get(item, "name")] = domAttr.get(item, "value");
          });
        isInitialised = true;
      },

      /**
       * Get message
       * @param value {String} the id message
       * @param default_value {String} if value is undefined, display default
       */
      getMessage : function(value, default_value) {
        var ENME = this;
        return this.params[value] == undefined
             ? (default_value == null
                 ? "NOT_DEFINED[" + value + "]"
                 : default_value) : ENME.params[value];
      },

      /**
       * Finds the index of an element in the array.
       */
      indexOf : function(array, item, fromIndex) {
        var length = array.length;
        if (fromIndex == null) {
          fromIndex = 0;
        } else {
          if (fromIndex < 0) {
            fromIndex = Math.max(0, length + fromIndex);
          }
        }
        for ( var i = fromIndex; i < length; i++) {
          if (array[i] === item)
            return i;
        }
        return -1;
      },

      /**
       * Looks for an element inside the array.
       */
      contains : function(array, item) {
        return this.indexOf(array, item) > -1;
      },

      /**
       * Includes firebug lite for debugging in IE. Especially in IE.
       * @method firebug
       * @usage Type in addressbar "javascript:alert(ENME.firebug());"
       */
      // firebug : function() {
      //   var script = this.$(document.createElement("script"));
      //   script.attr("src", "http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js");
      //   this.$("head").append(script);
      //   (function() {
      //     if (window.firebug) {
      //       firebug.init();
      //     } else {
      //       setTimeout(arguments.callee, 0);
      //     }
      //   })();
      // },

      /**
       * Check if the url is valid.
       * @returns {Boolean}
       */
      validURL : function (str) {
        var expression = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
        //remove posible parameters
//        var new_url = str.substring(0, str.indexOf('&')),
//        new_url_2 = new_url.substring(0, str.indexOf('?'));
        var regex = new RegExp(expression);
         if (str.match(regex) ) {
            return true;
         } else {
            return false;
         }
      },

      /**
       * Set a fake image if the flag is false
       * @param flag define if is set the fake image
       * @param size define the size of fake image.
       * @param original {String} original url path
       */
      fakeImage : function (size, original) {
        var domain = this.config('domain'),
        url = "";
        if (!this.validURL(original)) {
            switch(size) {
            case "24":
              url = domain  + "/resources/images/social/fake_24_24.png";
              break;
            case "32":
              url = domain  + "/resources/images/social/fake_32_32.png";
                break;
            case "64":
              url = domain  + "/resources/images/social/fake_64_64.png";
                break;
            case "128":
              url = domain  + "/resources/images/social/fake_128_128.png";
              break;
            default:
              url = domain  + "/resources/images/social/fake_24_24.png";
            }
            return url;
        } else {
          return original;
        }
      },

      /**
       * Clones the element specified by the selector and removes the id
       * attribute.
       * @param selector a jQuery selector
       */
      clone : function(selector) {
        var x = this.$.query(selector),
        c = x.clone();
        c.removeAttr("id");
        return x;
      },

      /**
       * Convert a normal number value and return a format a number like 10,332.
       * @param value
       * @returns
       */
      numberFormat : function (value) {
        return number.format(value, {places: 0});
      },

      isEmtpy : function() {
         return (!str || 0 === str.length);
      },

      /**
       *
       * @param hashtagName
       * @returns
       */
      hashtagContext : function(hashtagName) {
         if (hashtagName) {
                // http://jsperf.com/concat-test-jc
                var url = this.config("contextPath");
                url = url + "/tag/";
                url = url + hashtagName;
                url = url + "/";
                return url;
            } else {
                throw new Error("hashtag name is required");
           }
      },

      /**
       *
       * @method
       */
      pollDetailContext : function(id, slug) {
         if (id != null && slug != null) {
                // http://jsperf.com/concat-test-jc
                var url = this.config("contextPath");
                url +="/poll/";
                url += id;
                url += "/";
                url += slug;
                return url;
            } else {
                throw new Error("poll id is required");
           }
      },

      /**
       *
       * @method
       */
      shortAmmount : function(quantity){
        if (typeof(quantity) === "number") {
              quantity = ( quantity < 0 ? 0  : quantity);
              var text = quantity.toString();
              // 5634 --> 5634k
              if (quantity > 1000) {
                  var quantityReduced = Math.round(quantity / 100);
                  text = quantityReduced.toString();
                  text = text + "K";
              }
          return text;
          } else {
              throw new Error("invalid number");
          }
      },

      /**
       * Encuestane namespace declaration.
       * @param ns_string
       * @returns
       */
      namespace : function(ns_string) {
          var parts = ns_string.split('.'), parent = ENME, i;
          // strip redundant leading global
          if (parts[0] === "ENME") {
              parts = parts.slice(1);
          }
          for (i = 0; i < parts.length; i += 1) {
              // create a property if it doesn't exist
              if (typeof parent[parts[i]] === "undefined") {
                  parent[parts[i]] = {};
              }
              parent = parent[parts[i]];
          }
          return parent;
      },

      /**
       * Return the session id saved in cookie by spring security.
       */
      getSession : function() {
          //JSESSIONID=dh3u2xvj7fwd1llbddl33dhcq; path=/encuestame; domain=demo2.encuestame.org
          var sessionCookie = cookie("JSESSIONID");
          if (sessionCookie == undefined) {
              //encuestame.error.session(encuestame.error.messages.denied);
          } else {
              log("session is valid");
          }
      },

      /*
       * Store a item into session storage
       * @method storeItem
       * @param key
       * @param value
       * @param local
       */
      storeItem : function (key, value, local) {
          local = local || false;
          if (typeof Modernizr != 'undefined' && Modernizr.sessionstorage) {
              sessionStorage.setItem(key, json.toJson(value));
          } else {
              //TODO: save on COOKIE
          }
      },


      /*
       * Remove a item from session storage
       * @method removeItem
       * @param key the key item
       * @param local define if the source is local
       */
      removeItem : function (key, local) {
          local = local || false;
          if (typeof Modernizr != 'undefined' && Modernizr.sessionstorage) {
              sessionStorage.removeItem(key);
          } else {
              //TODO: remove on COOKIE
          }
      },

      /**
       * @method restoreItem
       * @param key
       * @param local
       */
      restoreItem : function (key, local) {
          local = local || false;
          if (typeof Modernizr != 'undefined' && Modernizr.sessionstorage) {
              return sessionStorage.getItem(key);
          } else {
            //TODO: get on COOKIE
          }
      },

      /**
       *
       * @param provider
       * @returns {String}
       */
      shortPicture : function(provider) {
           var url = this.config('contextPath') + "/resources/images/social/" + provider.toLowerCase()
                 +"/enme_icon_" + provider.toLowerCase() + ".png";
           return url;
      },

      /**
       * Convert a format date to relative time.
       * @param date date on string format {String}
       * @param format format of date {String}
       */
      fromNow : function(date, format) {
        try {
          format = format || "YYYY-MM-DD";
          if (moment != "undefined") {
            return moment(date, format).fromNow();
          } else {
            return date;
          }
        } catch (error) {
          return date;
        }
      },

      /**
       * Convert huge number to relative quantities
       * @method a number to evaluate
       */
      relativeQuantity : function (quantity) {
         if (typeof quantity === 'number') {
              if (quantity > 9999) {
                  var q = "" + quantity;
                  return ">1K";
              } else if (quantity < 9999) {
                  return quantity;
              }
         }
      },

    /**
     * Get format time based on format string.
     * @param date
     * @param fmt
     * @returns
     */
     getFormatTime : function(date, fmt) {
          return dojo.date.locale.format(date, {
              selector: "date",
              datePattern: fmt
          });
    },

    /**
     * Save the current activity object
     * @param _activity the activity object
     * @method  setActivity
     */
    setActivity : function(_activity) {
      activity = _activity;
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
        return this.params
    },

    /**
     * Get the current activity object
     * @method  getActivity
     */
    getActivity : function () {
       return activity;
    },

    /**
     * Services layer.
     * @property
     */
     xhr : {

          /*
           *
           */
          get : function(url, params, load, error, loaderHandler) {
                _makeCall(url, params, 'GET', load, error, loaderHandler);
          },

          /*
           *
           */
          post : function(url, params, load, error, loaderHandler) {
                _makeCall(url, params, 'POST', load, error, loaderHandler);
          },

          /*
           *
           */
          del : function(url, params, load, error, loaderHandler) {
               _makeCall(url, params, 'DELETE', load, error, loaderHandler);
          },

          /*
           *
           */
          put : function(url, params, load, error, loaderHandler) {
               _makeCall(url, params, 'PUT', load, error, loaderHandler);
          }
      }
  };
});