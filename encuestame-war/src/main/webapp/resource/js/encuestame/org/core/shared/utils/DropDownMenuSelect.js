dojo.provide("encuestame.org.core.shared.utils.DropDownMenuSelect");

dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
dojo.require('encuestame.org.core.commons');

dojo.declare("encuestame.org.core.shared.utils.DropDownMenuSelect",
        [ encuestame.org.main.EnmeMainLayoutWidget ], {

            templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
                    "template/dropDownMenuSelect.html"),

            /*
             * default text.
             */
            defaultText : "Select the Action...",

            /*
             * post create.
             */
            postCreate : function() {
            	this._createItems();
            },
            
            _createItems : function() {
                // new poll
                var newPoll = new encuestame.org.core.shared.utils.DropDownMenuItem({
                            label : "New Poll",
                            url : "/user/poll/new"
                        });
                this._addItem(newPoll.domNode);
            },

            /*
             * add item on drop down menu.
             */
            _addItem : function(node) {
                dojo.place(node, this._items);
            }

        });

dojo.declare("encuestame.org.core.shared.utils.DropDownMenuItem",
        [ encuestame.org.main.EnmeMainLayoutWidget ], {

    /*
     * template.
     */
     templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
     "template/dropDownMenuItem.html"),

     /*
      *
      */
    label : "",

    /*
     *
     */
    url : "#",

    /*
     * is called after the properties have been initialized.
     */
    postMixInProperties : function() {
        console.info("label", this.url);
         var urlConcat = encuestame.contextDefault;
         urlConcat = urlConcat.concat(this.url);
         this.url = urlConcat;
         console.info("label", urlConcat);
    },

    /*
     *
     */
    postCreate : function() {

    }

});