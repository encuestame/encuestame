define([
    'me/core/enme',
    'me/core/_base/_url',
    'me/core/ui/Loading',
    'dojo/dom',
    "dojo/dom-class",
    "dojo/_base/lang",
    'dojo/query',
    'dojo/dom-construct',
    'dojo/store/Memory',
    "dojo/json",
    "dojo/_base/window",
    'dojo/dom-attr'
    //'sinon/sinon',
], function (
    _ENME,
    _url,
     Loading,
     dom,
     domClass,
     lang,
     query,
     domConstruct,
     Memory,
     _JSON,
     win,
     domAttr) {

    function createFakeServer(address) {
        var server = sinon.fakeServer.create();
        server.autoRespond = true;
        sinon.FakeXMLHttpRequest.useFilters = true;
        sinon.FakeXMLHttpRequest.addFilter(function (method, url) {
            return url !== address;
        });
        return server;
    }

    return {
        cssStore : null,
        initialized : false,
        cssId : 0,
        enme : function() {
            return _ENME;
        },
        /**
         * Create a fake server
         * @param dataUrl
         * @param results
         */
        createFakeServer: function(dataUrl, results) {
            var server = createFakeServer(dataUrl);
            server.respondWith(function (request) {
                request.respond(200, { 'Content-Type': 'application/json' }, JSON.stringify(results));
            });
        },
        url : function(url){
          return _url[url];
        },
        init : function(_options) {
            //workaround Offline is missing
            Offline = {
                state : true
            };
            //workaround Chart is missing
            window.Chart = function(){
                return {
                    Pie : function(){},
                    Line : function(){},
                    Bar : function(){}
                };
            };

            window.config = {
                contextPath : '/js/me/tests/resources'
            };
            this.createElement();
            // fake Enme initialzation
            var _default = {
                contextPath: '/js/me/tests/resources',
                domain : '/js/me/tests/resources',
                suggest_limit : 10,
                delay : 1800000,
                isMobile: false,
                forceGET: true,
                locale: 'en',
                //logged: true,
                debug : true,
                message_delay : 5000,
                tp_a : 2,
                tp_hr : 2,
                tp_minsoa : 2
            };

            var options = lang.mixin(_default, _options);
            _ENME.init(options);
            // store css elements
            this.cssStore = new Memory();
            if (!this.initialized) {
                this.initResources();
            }
        },
        createEvent: function(){
            var event = document.createEvent("HTMLEvents");
            return event;
        },
        initResources : function() {
            window.config = '/';
            //this.addCss(1, "../../tests/resources/resources/css/font-awesome.min.css");
            //this.addCss(2, "../../tests/resources/resources/css/dbootstrap.css");
            //this.addCss(3, "../../tests/resources/resources/css/dijit.css");
            this.createElement('loading');
            this.initialized = true;
            var _dom = dom.byId('loading');
            this._loading_widget = new Loading({
                id : 'loading'
            });
            if (this._loading_widget && this._loading_widget.domNode) {
                domConstruct.place(this._loading_widget.domNode, _dom);
            }
        },
        createElement : function(id) {
            if (id) {
                var n = domConstruct.create("div", {
                    id : id
                });
                domConstruct.place(n, win.body());
                return n;
            }
        },
        addClass : function(node, clazz) {
            if (node) {
                domClass.add(node, clazz);
            }
        },
        hasClass : function(node, clazz) {
            return domClass.contains(node, clazz);
        },
        addCss : function(id, css) {
            //var cssInject = function(css) {
            //    var link = document.createElement("link");
            //    link.href = css;
            //    link.type = "text/css";
            //    link.rel = "stylesheet";
            //    document.getElementsByTagName("head")[0].appendChild(link);
            //    return link;
            //};
            //var link = cssInject(css);
            //this.cssId++;
            //this.cssStore.put({
            //    id : this.cssId,
            //    elementDom : link
            //});
        },
        removeCss : function(id) {
            //var element = this.cssStore.get(id);
            //domConstruct.destroy(element.elementDom);
            //this.cssStore.remove(id);
        },
        removeElement : function(node) {
            if (node) {
                domConstruct.destroy(node);
            }
        },
        json : function(data) {
            return _JSON.parse(data);
        },
        place : function(nodeElement, targetElement) {
            domConstruct.place(nodeElement, this.getbyId(targetElement));
        },
        query : function(id) {
            return query(id);
        },
        clean : function(element) {
            domConstruct.empty(element);
        },
        getbyId : function(id) {
            return dom.byId(id);
        },
        /**
         * Build a success response
         * @param data
         * @returns {{error: {}, success: *}}
         */
        buildSuccessResponse : function(data) {
            var response = {
                error  : {},
                success: data
            };
            return response;
        },

        /**
         * Build a error response
         * @param data
         * @returns {{error: {}, success: *}}
         */
        buildErrorResponse : function(data) {
            var response = {
                error  : data,
                success: {}
            };
            return response;
        }
    };
});
