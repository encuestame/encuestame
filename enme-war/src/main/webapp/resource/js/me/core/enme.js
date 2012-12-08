//require(["dojo/parser", "ready!"], function(AuthoredDialog, parser) {
//    // Parse the page
//    parser.parse();
//
//});
define(["dojo",
        "dojo/dom",
        'dojo/_base/json',
        "dojo/number",
        "dojo/cookie",
        "dojo/on",
        "dojo/query",
        "dojo/dom-attr",
        "moment"], function(
            dojo,
            dom,
            json,
            number,
            cookie,
            on,
            query,
            domAttr,
            moment) {

    dojo.require( "dojo.date.locale" );

    //Define if is initialize.
    var isInitialised = false,

    // to store the default configuration
    _config = {};

    // channel to publish
    var channel = '/encuestame/message/publish';
    // get the configuration default
    var duration = 5000;
    // message type
    var	messageTypes = {
      MESSAGE: "message",
      WARNING: "warning",
      ERROR: "error",
      FATAL: "fatal"
    };

    // default time / formats
    var time = {
        timeFormat : "hh:mm:ss",
        dateFormat : "M/d/yy"
    };

    /**
     *
     */
    var _publish = function(message, description, type) {
      description === null ? "" : description;
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
        if (typeof console != "undefined" && console.log ) { //TODO: Add verbose condition.
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
       *
       */
      stopEvent : function(e) {
//				on.emit(target, "event", {
//				    bubbles: true,
//				    cancelable: true
//				  });
        e.stopPropagation();
        return false;
      },

      /**
       *
       */
      include : function(url) {
        if (!this.contains(included, url)) {
          included.push(url);
          var s = document.createElement("script");
          s.src = url;
          query("body").append(s);
        }
      },


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
        query("#header input[type='hidden']").forEach(
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
      firebug : function() {
        var script = this.$(document.createElement("script"));
        script.attr("src", "http://getfirebug.com/releases/lite/1.2/firebug-lite-compressed.js");
        this.$("head").append(script);
        (function() {
          if (window.firebug) {
            firebug.init();
          } else {
            setTimeout(arguments.callee, 0);
          }
        })();
      },

      /**
       * Check if the url is valid.
       * @returns {Boolean}
       */
      validURL : function (str) {
        var expression = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?/;
        //remove posible parameters
//				var new_url = str.substring(0, str.indexOf('&')),
//				new_url_2 = new_url.substring(0, str.indexOf('?'));
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
       * @param key
       * @param value
       */
      storeItem : function (key, value) {
          if (typeof Modernizr != 'undefined' && Modernizr.sessionstorage) {
              sessionStorage.setItem(key, json.toJson(value));
          } else {
              //TODO: save on COOKIE
          }
      },


      /*
       * Remove a item from session storage
       * @param key the key item
       */
      removeItem : function (key) {
          if (typeof Modernizr != 'undefined' && Modernizr.sessionstorage) {
              sessionStorage.removeItem(key);
          } else {
              //TODO: remove on COOKIE
          }
      },

      /*
       * @param key
       * @param value
       */
      restoreItem : function (key) {
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
    }
    };
});

//	var ENME = (function() {
//		var fn = {};
//
//		return fn;
//
//	})();
//});

/**
 * default log.
 */
window.log = function () {
    log.history = log.history || [];
    log.history.push(arguments);
    if (this.console) {
        arguments.callee = arguments.callee.caller;
        var a = [].slice.call(arguments);
        (typeof console.log === "object" ? log.apply.call(console.log, console, a) : console.log.apply(console, a));
    }
};
(function (b) {function c() {}
    for (var d = "assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,timeStamp,profile,profileEnd,time,timeEnd,trace,warn".split(","), a; a = d.pop();) {
        b[a] = b[a] || c;
    }
})((function () {
    try {
        return window.console;
    } catch (err) {
        return window.console = {};
    }
})());