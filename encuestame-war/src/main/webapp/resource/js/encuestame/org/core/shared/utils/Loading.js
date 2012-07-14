dojo.provide("encuestame.org.core.shared.utils.Loading");

dojo.declare(
    "encuestame.org.core.shared.utils.Loading",
    [encuestame.org.main.EnmeMainLayoutWidget],{
    	
    	/**
    	 * Template.
    	 */
        templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/loading.html"),
        
        i18nMessage : {
        	loading : ENME.getMessage("loading_message", "Loading")
        },
                
        /**
         * Show the loader.
         */
        show : function (message) {
        	dojo.removeClass(this._loading, "hidden");
        	if (message) {
        		this._message.innerHTML = message;
        	} else {
        		this._message.innerHTML = this.i18nMessage.loading;
        	}
        },
        
        /**
         * Hide the loader.
         */
        hide : function () {
        	dojo.addClass(this._loading, "hidden");
        }    
});
