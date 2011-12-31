dojo.provide("encuestame.org.core.shared.stats.RatePosition");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require('encuestame.org.core.commons.stream.HashTagInfo');

dojo.declare(
    "encuestame.org.core.shared.stats.RatePosition",
    [encuestame.org.main.EnmeMainLayoutWidget],{
    /*
     *
     */
    templatePath: dojo.moduleUrl("encuestame.org.core.shared.stats", "templates/position.html"),


    _test : [
             { label : "Nicaragua", last : 4, current : 1},
             { label : "My Hashtag", last : 1, current : 2},
             { label : "Firefox", last : 6, current : 3}
             ],
    /*
     *
     */
    service : encuestame.service.list.rate.profile,

    /*
     *
     */
    postCreate : function(){
        this._printRate(this._test);
    },

    /*
     *
     */
    _printRate : function(data){
        dojo.forEach(data,
                dojo.hitch(this,function(item) {
                    this._createRow(item);
        }));
    },

    /*
     * <tr>
            <td>USA</td>
            <td>4</td>
            <td><img src="/resources/images/icons/enme_down.png" />
            </td>
        </tr>
     */
    _createRow : function(item){
        var tr = dojo.create("tr");
        var row1 = dojo.create("td", null, tr);
        row1.innerHTML = item.label;
        var row2 = dojo.create("td", null, tr);
        row2.innerHTML = item.current;
        dojo.addClass(row2, "web-position-number");
        var row3 = dojo.create("td", null, tr);
        row3.appendChild(this._getPositionRow(parseInt(item.last), parseInt(item.current)));
        this._rows.appendChild(tr);
    },

    /*
     *
     */
    _getPositionRow : function(last, current) {
        var node = dojo.create("span");
        if (last > current) {
            dojo.addClass(node, "web-position-down");
        } else if (last < current) {
            dojo.addClass(node, "web-position-up");
        } else {
            dojo.addClass(node, "web-position-equal");
        }
        return node;
    }
});