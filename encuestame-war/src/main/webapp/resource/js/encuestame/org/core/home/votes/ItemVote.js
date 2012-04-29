dojo.provide("encuestame.org.core.home.votes.ItemVote");

dojo.require('encuestame.org.core.commons');
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 * 
 */
dojo.declare("encuestame.org.core.home.votes.ItemVote",
		[ encuestame.org.main.EnmeMainLayoutWidget ], {
	
	 templatePath: dojo.moduleUrl("encuestame.org.core.home.votes", "templates/item.html"),

	/**
	 * Vote message.
	 */
	voteMessage : "",
	
	/**
	 * View message.
	 */
	viewMessage : "",
	
	
	voteEventMessage : "Vote",
	
	/**
	 * Votes.
	 */
	votes : 0,
	
	/**
	 * Hits.
	 */
	hits : 0,
	
	/**
	 * Default vote source.
	 */
	voteSource : "anonymous",
	
	/**
	 * Post create.
	 */
	postCreate : function() {
		dojo.connect(this._vote, "onmouseover", dojo.hitch(this, this._displayVoteButtonIn));
		dojo.connect(this._vote, "onmouseout", dojo.hitch(this, this._displayVoteButtonOut));
	},
	
	/**
	 * 
	 * @param event
	 */
	_displayVoteButtonIn : function(event){
		this.stopEvent(event);
		
	},
	
	_createButton : function(onClick){
		var button = dojo.create("button");
		dojo.connect(button, "onClick", dojo.hitch(this, onClick));
		
		return button;
	},
	
	/**
	 * 
	 * @param event
	 */
	_displayVoteButtonOut : function(event){
		this.stopEvent(event);
		
	},

	/**
	 * Call the service to vote.
	 */
	_vote : function() {
		var load = dojo.hitch(this, function(data){
			if ("success" in data) {
				
			}			
		});
		var params = {
				
		};
		this.callPOST(params, load, encuestame.service.list.votes.home(this.voteSource));
	}

});
