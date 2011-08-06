dojo.require("encuestame.org.core.commons");
dojo.require("encuestame.org.core.commons.dashboard.Gadget");
dojo.require("encuestame.org.core.gadget.Gadget");

var ENME = (function(){
    console.debug("dojo test******************");
    var widget = new encuestame.org.core.gadget.Gadget("2345", "encuestame.org.core.commons.dashboard.Gadget");
    //var n = widget.render();
    //console.debug("dojo widget", n);

})();