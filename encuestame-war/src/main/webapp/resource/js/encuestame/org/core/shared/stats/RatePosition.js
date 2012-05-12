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

    /**
     * Tag name.
     */
    tagName : "",

    /*
     *
     */
    postCreate : function(){
        this._callService();
    },
    
    _callService : function() {
	 	var params = {
	 			tagName : this.tagName,
      	 };
      	 var load = dojo.hitch(this, function(data) {
      		 if ("success" in data) {
      			 this._printRate(data.success.hashTagRankingStats);
      		 }	      		 
      	 });  	 
      	 this.callGET(params, encuestame.service.list.ranking.hashtag, load, null, null);
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

    /**
     * Build a ranking position row.
     * @param {Object} item 
     * <tr>
            <td>USA</td>
            <td>4</td>
            <td><img src="/resources/images/icons/enme_down.png" />
            </td>
        </tr>
     */
    _createRow : function (item) {
        var tr = dojo.create("tr");
        var row1 = dojo.create("td", null, tr);
        row1.innerHTML = item.tagName;
        var row2 = dojo.create("td", null, tr);
        row2.innerHTML = item.position;
        dojo.addClass(row2, "web-position-number");
        var row3 = dojo.create("td", null, tr);
        row3.appendChild(this._getPositionRow(parseInt(item.position), parseInt(item.position)));
        if (item.tagName === this.tagName) {
        	///TODO: highlight the row.
        }
        this._rows.appendChild(tr);
    },

    /*
     *
     */
    _getPositionRow : function (last, current) {
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