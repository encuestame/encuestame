define([
         "dojo/parser",
         "dijit/registry",
         "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme",
     "me/web/widget/stream/FrontEndItem",
     "dojo/dom-construct",
     "dojo/dom-class",
     "dojo/dom-attr",
     "dojo/on",
     "dojo/text!me/web/widget/stream/templates/frontEnd.html" ],
    function(parser, registry, declare, _WidgetBase, _TemplatedMixin,
        _WidgetsInTemplateMixin, main_widget, _ENME, FrontEndItem, domConstruct, domClass, domAttr, on, template) {

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
                      var widget = new FrontEndItem(
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
                this.getURLService().get("encuestame.service.stream",
                        {
                            period : "30",
                            maxResults : this._maxResults ,
                            start: this._start
                          }, load, error , dojo.hitch(this, function() {

                }));
            }
  });
});
