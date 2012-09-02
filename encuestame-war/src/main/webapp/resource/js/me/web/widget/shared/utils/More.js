dojo.provide("encuestame.org.core.shared.utils.More");

dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
dojo.require('encuestame.org.core.commons');

dojo.declare("encuestame.org.core.shared.utils.More",
        [ encuestame.org.main.EnmeMainLayoutWidget ], {

            templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
                    "template/more.html"),

            /*
             *
             */
            pagination : {
                _maxResults : 0,
                _start : 0,
            },

            /*
             *
             */
            postCreate : function() {
                dojo.connect(this._stream, "onclick", dojo.hitch(this, function(event) {
                    if (dojo.isFunction(this.loadItems)) {
                        this.loadItems();
                        this.pagination._start = this.pagination._start + this.pagination._maxResults;
                    }
                }));
            },

            /**
             *
             */
            loadItems : function() {

            },

            /**
             *
             */
            hide : function(){
                dojo.addClass(this.domNode, "hidden");
            },

            /**
             *
             */
            show : function(){
                dojo.removeClass(this.domNode, "hidden");
            }

        });
