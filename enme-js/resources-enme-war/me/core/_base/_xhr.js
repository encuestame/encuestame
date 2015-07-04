/**
 * Copyright 2014 encuestame
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
 *  @version 1.147
 *  @module ModuleName
 *  @namespace Widget
 *  @class FileName
 */

define([
    'dojo/has',
    "dojo/_base/lang",
	"dojo/topic",
    'me/core/_base/_log',
    "dojo/_base/array",
    'me/core/_base/_url',
    'me/core/_base/_config',
    "dojo/request",
    "me/core/support/iosOverlay",
    "me/core/support/Spinner"
    ],function(
    has,
    lang,
    topic,
    log,
    _array,
    _url,
    _config,
    request,
    iosOverlay,
    Spinner) {

    // json-comment-optional, json-comment-filtered
    var _handleAs = "json",
        // Indicates whether a request should be allowed to
        _failOk = true,
        _timeout = 20000,
        //_cross_browsing = false,
        _preventCache = true,
        spinner_opts = {
            lines: 13, // The number of lines to draw
            length: 11, // The length of each line
            width: 5, // The line thickness
            radius: 17, // The radius of the inner circle
            corners: 1, // Corner roundness (0..1)
            rotate: 0, // The rotation offset
            color: '#FFF', // #rgb or #rrggbb
            speed: 1, // Rounds per second
            trail: 60, // Afterglow percentage
            shadow: false, // Whether to render a shadow
            hwaccel: false, // Whether to use hardware acceleration
            className: 'spinner', // The CSS class to assign to the spinner
            zIndex: 2e9, // The z-index (defaults to 2000000000)
            top: 'auto', // Top position relative to parent in px
            left: 'auto' // Left position relative to parent in px
        };

    /**
     * Identify
     * @method
     */
    var _service = function(key, params) {
        //log("_service key-->", key);
        //log("_service params-->", params);
        var _service_store = _url,
            url,
            _parent = this;
        if (!params) {
            url = _config.get('contextPath');
            url = url.concat(_service_store[key]);
            return url;
        } else {
            url = _config.get('contextPath');
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
     * Trigger an ajax callback
     * @param url the url of the service.
     * @param params list of parameters are sent in the ajax call
     * @param method the request method (verb)
     * @param response the success method handler
     * @param error the error handler method
     * @param loaderHandler method will be executed after the call
     * @param loadingSpinner enable the loading spinner component
     */
    var _makeCall = function(url , params, method, response, error, loaderHandler, loadingSpinner) {
        var _load = response,
            _error = error;
            loadingSpinner = loadingSpinner || false;
            loaderHandler = loaderHandler || function(){};
            var spinner = null,
            overlay = null,
            make_call_parent = this;

        if (loadingSpinner && has("host-browser")) {
            var target = document.createElement("div");
            document.body.appendChild(target);
            spinner = new Spinner(spinner_opts).spin(target);
        }

        if (loaderHandler !== 'undefined' && typeof loaderHandler === 'function') {
            // load default handler
            _load = function (r) {
               // console.log("XHR LOAD", r.success);
                if (loadingSpinner && has("host-browser")) {
                    overlay.hide();
                }
                try {
                    //TODO: this sentence is repeated
                    if (typeof loaderHandler === 'function') {
                        loaderHandler();
                    }
                    response(r);
                } catch (error) {
                    console.error(error.stack);
                }
            };
            // error default handler
            _error = lang.hitch( this, function (e) {
                //console.log("XHR ERROR", e);
                if (loadingSpinner && has("host-browser")) {
                    overlay.hide();
                }
                    try {
                        // trigger the loader handler
                        if (typeof loaderHandler === 'function') {
                            loaderHandler();
                        }
	                    // custom error handler
	                    if (typeof error === 'function') {
		                    error(e);
	                    }
                    } catch (error) {
                        console.error("crash xhr", error);
                    }
            });

            // default xhr parameters
            var _params = {
                handleAs : _handleAs,
                failOk : _failOk,
                timeout : _timeout,
                method : method,
                preventCache : _preventCache
            };

	        //test environments support only get
	        if (_config.get('forceGET')) {
		        _params.method = 'GET'
	        }

            // on dependes the method, the way to send the data it's different

            if (method === 'POST' || method === 'PUT') {
                _params = lang.mixin(_params, {
                    data: JSON.stringify(params),
                    // this parameter it's not necesary, to be removed
	                //FIXME: we need a way to remove query without broke POST PUT services
                    query: params,
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
            } else if (method === 'POST-FORM') {
                _params = lang.mixin(_params, {
                    data: params,
                    method: "POST",
                    headers: {
                        "Content-Type": "application/x-www-form-urlencoded"
                    }
                });
            } else if (method === 'GET' || method === 'DELETE') {
                _params = lang.mixin(_params, {
                    query : params,
                    headers: {
                        "Content-Type": "application/json"
                    }
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
                    if (loadingSpinner && has("host-browser")) {
                        overlay = iosOverlay({
                            text: "Loading", ///TODO: i18n
                            spinner: spinner
                        });
                    }
                    //console.log("XHR _service_url", _service_url);
                    //console.log("XHR _params", _params);
                    var r = request(_service_url, _params).then(_load, _error, function(evt) {});
                    //console.log("XHR", r);
                    return r;
                }
            } catch (error) {
                console.error("error service ", error.stack, arguments);
            }
        }
    };

    return {
        /*
         *
         */
        get  : function (url, params, load, error, loaderHandler, loadingSpinner) {
            return _makeCall(url, params, 'GET', load, error, loaderHandler, loadingSpinner);
        },

        /*
         *
         */
        post : function (url, params, load, error, loaderHandler, loadingSpinner) {
            return _makeCall(url, params, 'POST', load, error, loaderHandler, loadingSpinner);
        },

        /*
         *
         */
        formU: function (url, params, load, error, loaderHandler, loadingSpinner) {
            return _makeCall(url, params, 'POST-FORM', load, error, loaderHandler, loadingSpinner);
        },
        /*
         *
         */
        del  : function (url, params, load, error, loaderHandler, loadingSpinner) {
            return _makeCall(url, params, 'DELETE', load, error, loaderHandler, loadingSpinner);
        },

        /*
         *
         */
        put  : function (url, params, load, error, loaderHandler, loadingSpinner) {
            return _makeCall(url, params, 'PUT', load, error, loaderHandler, loadingSpinner);
        },

        service: _service
    }

});


