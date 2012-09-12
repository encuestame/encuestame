dojo.provide("encuestame.org.core.commons.results.answers.ResultSupport");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.core.shared.utils.Suggest");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 * Result Support Widget.
 * 
 */
dojo.declare(
    "encuestame.org.core.commons.results.answers.ResultSupport", null,{
     
    /**
     * Represent the unique item id of the result.
     */	
    itemId : null,
    
    /**
     * Represent the label of the result / answer.
     */
    labelResponse : null,
    
    /**
     * Represent the color of the result.
     */
    color : null,
    
    /**
     * The current total of votes.
     */
    votes: null,
    
    /**
     * The question id.
     */
    questionId : null,
    	
    /**
     * Constructor.
     */
	constructor : function() {
		
	}
      
});