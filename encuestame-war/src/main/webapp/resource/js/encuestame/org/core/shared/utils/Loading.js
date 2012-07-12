dojo.provide("encuestame.org.core.shared.utils.Loading");

dojo.declare(
    "encuestame.org.core.shared.utils.Loading",
    [encuestame.org.main.EnmeMainLayoutWidget],{
    	
    	/**
    	 * Template.
    	 */
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/loading.html"),
                
        /**
         * Show the loader.
         */
        show : function () {
        	console.info("SHOW this._loading", this._loading);
        	dojo.removeClass(this._loading, "hidden");
        },
        
        /**
         * Hide the loader.
         */
        hide : function () {
        	console.info("HIDE this._loading", this._loading);
        	dojo.addClass(this._loading, "hidden");
        }    
});
