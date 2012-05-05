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
	

	voteOkMessage : "Ok !!",
	
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
		dojo.addClass(this._button, "hidden");
		dojo.addClass(this._loading, "hidden");
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
	},
	
	/**
	 * Create a temporal button.
	 * @param {Function} event handler when user push the button.
	 */
	_createButton : function() {
		var button = dojo.create("button");
		button.innerHTML = "Vote";
		dojo.addClass(button, "gradient-gray");
		var param = this;
		dojo.connect(button, "onclick", dojo.hitch(this, function() {
			param.clickVoteButton = true;
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
		if (!this.clickVoteButton) {
			dojo.addClass(this._button, "hidden");
			dojo.removeClass(this._vote, "hidden");
		}
	},
	
	/**
	 * Call the service to vote.
	 * @params {Object} params
	 */
	_sendVote : function(params) {
		var param = this; 
		var loading = {
	      	init : function(){
				dojo.addClass(param._button, "hidden");
				dojo.removeClass(param._loading, "hidden");
	      	}, 
	      	end : function(){
	      		dojo.removeClass(param._button, "hidden");
				dojo.addClass(param._loading, "hidden");
				delete param.clickVoteButton;
				param.temButton = param._button;
				param._button.innerHTML = param.voteOkMessage;
				setTimeout(function(){
					dojo.empty(param._button);
					dojo.removeClass(param._vote, "hidden");
					dojo.addClass(param._button, "hidden");					
					var button = param._createButton();
					param._button.appendChild(button); 
				}, 2000);
				//display message OK and after that display votes;
	      	}
		}; 
		var load = dojo.hitch(this, function(data) {
			if ("success" in data) {
				console.info("data", data);
				var currentVote = parseInt(this._voteCounter.innerHTML),
				newVoteCounter = currentVote + encuestame.utilities.vote;
				this._voteCounter.innerHTML = newVoteCounter;
			}			
		});
		var error = dojo.hitch(this, function(data) {
			var temp = this._voteCounter.innerHTML;
			console.error("data error vote", data);
		});
		this.callPOST(params, load, encuestame.service.list.votes.home, loading, error);
	}

});
