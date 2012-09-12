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

    /**
     * Post create life cycle.
     */
    postCreate : function(){
        this._callService();
    },
    
    /**
     * Call to ranking service.
     */
    _callService : function () {
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

    /**
     * Display the ranking.
     */
    _printRate : function(data) { 
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
        var tr = dojo.create("div");
        dojo.addClass(tr, 'position-row');
        var row1 = dojo.create("span", null, tr);
        row1.innerHTML = item.tagName;
        dojo.addClass(row1, 'position-label');
        dojo.addClass(row1, 'ellipsis');
        var row2 = dojo.create("span", null, tr);
        dojo.addClass(row2, 'position');
        row2.innerHTML = item.position;
        dojo.addClass(row2, "web-position-number");
        var row3 = dojo.create("span", null, tr);
        dojo.addClass(row3, 'graph');
        row3.appendChild(this._getPositionRow(parseInt(item.position), parseInt(item.lastPosition)));
        if (item.tagName === this.tagName) {
        	dojo.addClass(tr, 'highlight');
        } else {
        	var url_hashtag = encuestame.utilities.url.hashtag(item.tagName);
        	var a = dojo.create("a");
        	a.innerHTML = item.tagName;
        	a.setAttribute('href', url_hashtag);   	
        	dojo.empty(row1);
        	row1.appendChild(a);
        }
        this._rows.appendChild(tr);
    },

    /**
     * Get the position row.
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