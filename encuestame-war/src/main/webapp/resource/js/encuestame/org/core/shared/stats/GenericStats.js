dojo.provide("encuestame.org.core.shared.stats.GenericStats");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.layout.ContentPane");
dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
dojo.require('encuestame.org.core.commons.stream.HashTagInfo');

dojo.declare(
    "encuestame.org.core.shared.stats.GenericStats",
    [encuestame.org.main.EnmeMainLayoutWidget],{

    /*
     *
     */
     templatePath: dojo.moduleUrl("encuestame.org.core.shared.stats", "templates/stats.html"),

     /*
      * service json string.
      */
     _service : null,

     /*
      * type of stats.
      */
     type : null,


     _test : {
         "stats" : {
            "Likes / Dislike Rate" : "34",
            "Hits" : "1,500",
            "Created" : "4 years ago",
            "Average" : "1.4",
            "Created By" : "admin"
        }
    },


    /*
     *
     */
     postCreate : function() {
         console.info("postCreate", this.type);
         if (this.type) {
             this._service = encuestame.service.list.rate.stats(this.type);
         }
         console.info("stats", this._test);
         this._buildStats();
     },


     /*
      *
      */
     _buildStats : function() {
         for (var i in this._test.stats){
                this._createRow(i, this._test.stats[i]);
          }
     },

     /*
      *
      */
     _createRow : function(header, value) {
         var tr = dojo.create("tr");
         var label = dojo.create("td", null, tr);
         label.innerHTML = header;
         var label = dojo.create("td", null, tr);
         label.innerHTML = value;
         this._rows.appendChild(tr);
     }
});