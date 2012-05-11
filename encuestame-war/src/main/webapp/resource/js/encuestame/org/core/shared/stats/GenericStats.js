dojo.provide("encuestame.org.core.shared.stats.GenericStats");

dojo.require('dojox.timing');
dojo.require("dojox.widget.Dialog");
dojo.require("dijit.layout.ContentPane");
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
     _service : encuestame.service.list.generic.stats,

     /*
      * typeGeneric of stats.
      */
     typeGeneric : "",
     
     /**
      * Item id.
      */
     generic : "",


    /*
     *
     */
     postCreate : function() {
    	 this._callGenericStats();
     },
     
     /**
      * 
      */
     _callGenericStats : function() {
	 	var params = {
      	     id : this.generic,
      	     filter  : this.typeGeneric, 
      	 }
      	 var load = dojo.hitch(this, function(data) {
      		 if ("success" in data) {
      			 this._buildStats(data.success.generic);
      		 }	      		 
      	 });  	 
      	 this.callGET(params, this._service, load, null, null);
     },


     /*
      *
      */
     _buildStats : function(stats) {
         for (var i in stats) {
                this._createRow(i, stats[i]);
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