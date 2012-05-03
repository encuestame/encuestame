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
	  * 
	  */
	itemId : 0,
	
	/**
	 * 
	 */
	itemType : "",
	
	 /**
	  * 
	  */
	_tempNode : null,
	
	/**
	 * Post create.
	 */
	postCreate : function() {
		dojo.connect(this.domNode, "onmouseover", dojo.hitch(this, this._displayVoteButtonIn));
		dojo.connect(this.domNode, "onmouseout", dojo.hitch(this, this._displayVoteButtonOut));
		var button = this._createButton();
		//console.info("button", button);
		button.innerHTML = "Vote";
		dojo.addClass(button, "gradient-gray");
		dojo.addClass(this._button, "hidden");
		this._button.appendChild(button);
	},
	
	/**
	 * 
	 * @param event
	 */
	_displayVoteButtonIn : function(event) {
		this.stopEvent(event);				
		dojo.removeClass(this._button, "hidden");
		dojo.addClass(this._vote, "hidden");
		var x = 1;
	},
	
	/**
	 * Create a temporal button.
	 * @param {Function} event handler when user push the button.
	 */
	_createButton : function() {
		var button = dojo.create("button");
		dojo.connect(button, "onclick", dojo.hitch(this, function(){
			if (this.itemId !== null) {
				this._sendVote({
					id : this.itemId,
					type : this.itemType
				});
			}
		}));		
		return button;
	},
	
	/**
	 * 
	 * @param event
	 */
	_displayVoteButtonOut : function(event) {
		this.stopEvent(event);
		dojo.addClass(this._button, "hidden");
		dojo.removeClass(this._vote, "hidden");
	},
	
	/**
	 * Call the service to vote.
	 * @params {Object} params
	 */
	_sendVote : function(params) {
		var loading = {
	      	init : function(){
				console.debug("init");
	      	}, 
	      	end : function(){
	      		console.debug("end");
	      	}
		};
		console.info("click vote"); 
		var load = dojo.hitch(this, function(data) {
			if ("success" in data) {
				console.info("data", data);
			}			
		});
		this.callPOST(params, load, encuestame.service.list.votes.home, loading);
	}

});
