define([
         "dojo/parser",
         "dijit/registry",
         "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme",
     "dojo/dom-construct",
     "dojo/dom-class",
     "dojo/dom-attr",
     "dojo/on",
     "dojo/text!me/web/widget/stream/templates/frontEnd.html" ],
    function(parser, registry, declare, _WidgetBase, _TemplatedMixin,
        _WidgetsInTemplateMixin, main_widget, _ENME, domConstruct, domClass, domAttr, on, template) {

      return declare([ _WidgetBase, _TemplatedMixin, main_widget,
          _WidgetsInTemplateMixin ], {

        // template string.
        templateString : template,

            _maxResults : 10,

            _start : 10,

            /*
             * post create.
             */
            postCreate : function(){
                dojo.connect(this._stream, "onclick", dojo.hitch(this, function(event) {
                    this._loadItems();
                    this._start = this._start + this._maxResults;
                }));
            },

            /*
             * print items.
             */
            _printItems : function(items){
                dojo.forEach(items,
                    dojo.hitch(this,function(item) {
                      var widget = new encuestame.org.core.commons.stream.FrontEndItem(
                              {   questionName : item.question.question_name,
                                  id : item.id,
                                  owner : item.owner_username,
                                  votes : ENME.shortAmmount(item.total_votes),
                                  views : 0,
                                  relativeTime: item.relative_time,
                                  hashtags : item.hastags_string
                               });
                      this._list.appendChild(widget.domNode);
                    }));
            },

            /*
             *
             */
            _fadeOutTrigger : function(){
                dojo.style(this._pagination, "opacity", "1");
                var fadeArgs = {
                    node: this._pagination
                };
                dojo.fadeOut(fadeArgs).play();
            },

            /*
             * load all items
             */
            _loadItems : function() {

              var load = dojo.hitch(this, function(data) {
                    var items = data.success.frontendItems;

                    if(items.length > 0) {
                        this._printItems(items);
                    } else {
                        this._fadeOutTrigger();
                    }
                });

                var error = function(error) {
                    console.debug("error", error);
                };

                encuestame.service.xhrGet(
                        this.getURLService().service('encuestame.service.stream'),
                        {
                          period : "30",
                          maxResults : this._maxResults ,
                          start: this._start
                        }, load, error);
            }


      });
    });

//dojo.provide("encuestame.org.core.commons.stream.FrontEnd");
//
//dojo.require('dojox.timing');
//dojo.require("dojox.widget.Dialog");
//dojo.require("dijit._Templated");
//dojo.require("dijit._Widget");
//dojo.require('encuestame.org.core.commons');
//dojo.require('encuestame.org.core.commons.stream.FrontEndItem');
//
//dojo.declare(
//    "encuestame.org.core.commons.stream.FrontEnd",
//    [dijit._Widget, dijit._Templated],{
//        templatePath: dojo.moduleUrl("encuestame.org.core.commons.stream", "templates/frontEnd.html"),
//
//        widgetsInTemplate: true,
//
//        _maxResults : 10,
//
//        _start : 10,
//
//        /*
//         * post create.
//         */
//        postCreate : function(){
//            dojo.connect(this._stream, "onclick", dojo.hitch(this, function(event) {
//                this._loadItems();
//                this._start = this._start + this._maxResults;
//            }));
//        },
//
//        /*
//         * print items.
//         */
//        _printItems : function(items){
//            dojo.forEach(items,
//                dojo.hitch(this,function(item) {
//                  var widget = new encuestame.org.core.commons.stream.FrontEndItem(
//                          {   questionName : item.question.question_name,
//                              id : item.id,
//                              owner : item.owner_username,
//                              votes : ENME.shortAmmount(item.total_votes),
//                              views : 0,
//                              relativeTime: item.relative_time,
//                              hashtags : item.hastags_string
//                           });
//                  this._list.appendChild(widget.domNode);
//                }));
//        },
//
//        /*
//         *
//         */
//        _fadeOutTrigger : function(){
//            dojo.style(this._pagination, "opacity", "1");
//            var fadeArgs = {
//                node: this._pagination
//            };
//            dojo.fadeOut(fadeArgs).play();
//        },
//
//        /*
//         * load lal items
//         */
//        _loadItems : function(){
//            var load = dojo.hitch(this, function(data) {
//                var items = data.success.frontendItems;
//
//                if(items.length > 0) {
//                    this._printItems(items);
//                } else {
//                    this._fadeOutTrigger();
//                }
//            });
//            var error = function(error) {
//                console.debug("error", error);
//            };
//            encuestame.service.xhrGet(
//                    encuestame.service.stream, {period : "30", maxResults : this._maxResults , start: this._start}, load, error);
//        }
//});
