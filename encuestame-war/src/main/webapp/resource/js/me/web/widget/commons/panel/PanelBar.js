dojo.provide("encuestame.org.core.commons.panel.PanelBar");

dojo.require("dojo.io.iframe");
dojo.require("dojox.fx");

dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.ComboButton");
dojo.require("dijit.MenuItem");
dojo.require("dijit.Menu");

dojo.declare("encuestame.org.core.commons.panel.PanelBar",
        [dijit._Widget, dijit._Templated ], {
    /*
     * template.
     */
    templatePath: dojo.moduleUrl("encuestame.org.core.commons.panel", "templates/panelBar.html"),

    /*
     * widgets enabled.
     */
    widgetsInTemplate : true,

    /*
     *
     */
    postCreate : function(){
         console.log(dojo.query(".section-signup"));
         dojo.query(".section-signup").forEach(dojo.hitch(this, function(node, index, arr){
             console.log("NODE", node);
             this._menu.appendChild(this._createItem(node).domNode);
         }));
    },

    /*
     *
     */
    _createItem : function(node) {
         console.log("NODE collapsed", node.collapsed);
        var collapsed = (node.collapsed == undefined ? false : node.collapsed);
        var item = new encuestame.org.core.commons.panel.PanelBarItem({collapsed : collapsed});
        item.append(node);
        item.setTitle(node.title);
        item._change();
        return item;
    }

});

/**
 *
 */
dojo.declare("encuestame.org.core.commons.panel.PanelBarItem",
        [dijit._Widget, dijit._Templated ], {

     /*
     * template.
     */
    templatePath: dojo.moduleUrl("encuestame.org.core.commons.panel", "templates/panelBarItem.html"),

    /*
     * widgets enabled.
     */
    widgetsInTemplate : true,

    collapsed : true,

    /*
     *
     */
    postCreate : function() {
        //console.debug("item collapsed", this.collapsed);
         dojo.connect(this._title, "onclick", dojo.hitch(this, function(event) {
             dojo.stopEvent(event);
             this._change();
             dojo.publish("/encuestame/panel/close", [this.id]);
         }));
         dojo.subscribe("/encuestame/panel/close", this, this._close);
    },

    /*
     *
     */
    _close : function(id) {
        if(id != this.id){
            this.collapsed = false;
            this.wipeOutOne();
        }
    },

    /*
     * TODO: migrate to WIPE class.
     */
    _change : function(){
        if(this.collapsed){
            this.wipeOutOne(); //this.panelWidget = new encuestame.org.core.commons.support.Wipe(this._panel);
        } else {
            this.wipeInOne(); //this.panelWidget = new encuestame.org.core.commons.support.Wipe(this._panel);
        }
        this.collapsed = !this.collapsed;
    },

    /*
     * @deprecated
     */
    wipeInOne: function() {
        if (this._item) {
            dojox.fx.wipeTo({
                 node: this._item,
                duration: 200,
                height: 300
            }).play();
        }
    },

    /*
     * @deprecated
     */
    wipeOutOne : function() {
        if (this._item) {
            dojox.fx.wipeOut({
                node: this._item,
               duration: 200
            }).play();
        }
    },

    /*
     *
     */
    append : function(node){
        if(node != undefined && this._item != undefined){
            this._item.appendChild(node);
        }
    },

    /*
     *
     */
    setTitle : function(title) {
        if (title) {
            this._title.innerHTML = title;
        }
    }
});