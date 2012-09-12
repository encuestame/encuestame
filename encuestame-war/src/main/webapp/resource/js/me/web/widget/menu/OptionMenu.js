dojo.provide("encuestame.org.core.shared.utils.OptionMenu");

dojo.require('encuestame.org.main.EnmeMainLayoutWidget');
dojo.require('encuestame.org.core.commons.support.ToggleMenu');
dojo.require('encuestame.org.core.commons');

dojo.declare("encuestame.org.core.shared.utils.OptionMenu",
            [ encuestame.org.core.commons.support.ToggleMenu ],{

            /*
             * template.
             */
            templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils", "template/optionMenu.html"),

            /*
             *
             */
            _openBox : true,

            /*
             * Display icon by default.
             */
            enableIcon : true,

            /*
             *
             */
            _classReplace : "",

            /*
             *
             */
            menu_items : [{
                label : "label1",
                action : function() {
                }},
                {label : "label2",
                action : function() {
                }
            }],

            /*
             *
             */
            postCreate : function() {
                if (this.enableIcon) {
                    this.addMenuSupport(this._icon, "click");
                } else {
                    //TODO: enable open context menu on click defined dom node.
                }
                this._buildMenus();
            },

            /*
             * Build a item menu.
             */
            _buildMenus : function() {
                dojo.forEach(this.menu_items,
                    dojo.hitch(this, function(item, action) {
                         console.debug("_buildMenus", item);
                        var widget = new encuestame.org.core.shared.utils.OptionMenuItem(
                                {
                                    label : item.label,
                                    action : item.action
                                });
                        this._menu.appendChild(widget.domNode);
               }));
            }
});

/*
 *
 */
dojo.declare("encuestame.org.core.shared.utils.OptionMenuItem",
        [encuestame.org.core.commons.support.ToggleMenu], {

            templatePath : dojo.moduleUrl("encuestame.org.core.shared.utils",
                    "template/optionMenuItem.html"),

        label : "",

        action : function(){},

        postCreate: function() {
            console.debug("OptionMenuItem", this.label);
            console.debug("OptionMenuItem", this.label.action)
            dojo.connect(this._item, "onclick", this, dojo.hitch(this, this.action));
        }

});
