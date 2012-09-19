define([ 
         "dojo/_base/declare",
		 "dijit/_WidgetBase", 
		 "dijit/_TemplatedMixin",
		 "dijit/_WidgetsInTemplateMixin",
		 "me/core/main_widgets/EnmeMainLayoutWidget",
		 "me/core/enme",
		 "dojo/text!me/web/widget/home/votes/templates/item.html" ],
		function(
				declare,
				_WidgetBase, 
				_TemplatedMixin,
				_WidgetsInTemplateMixin,
				main_widget, 
				_ENME, 
				 template) {

			return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

				// template string.
				templateString : template,

				/**
				 * Vote message.
				 */
				voteMessage : "",
				
				/**
				 * View message.
				 */
				viewMessage : "",
				
				/**
				 * Button vote message.
				 */
				voteEventMessage : "Vote",
				
			
				/**
				 * Message if vote is valid.
				 */
				voteOkMessage : "Ok !!",
				
				/**
				 * Message if vote is invalid.
				 */
				voteFailMessage : "Bad Vote !!",
				
				/**
				 * Current Votes.
				 */
				votes : 0,
				
				/**
				 * Current Hits.
				 */
				hits : 0,
				
				 /**
				  * Poll / Tpoll / Survey Id.
				  */
				itemId : 0,
				
				/**
				 * Poll || TPoll || Survey
				 */
				itemType : "",
				
				/**
				 * Post create.
				 */
				postCreate : function() {
					dojo.connect(this.domNode, "onmouseover", dojo.hitch(this, this._displayVoteButtonIn));
					dojo.connect(this.domNode, "onmouseout", dojo.hitch(this, this._displayVoteButtonOut));
					var button = this._createButton();				
					dojo.addClass(this._button, encuestame.utilities.HIDDEN_CLASS);
					dojo.addClass(this._loading, encuestame.utilities.HIDDEN_CLASS);
					this._button.appendChild(button);		
					this._voteCounter.innerHTML = _ENME.shortAmmount(this.votes);
					var view = dojo.create('div');
					view.innerHTML = _ENME.shortAmmount(this.hits) + " " + this.viewMessage;
					this._vote.appendChild(view);
				},
				
				/**
				 * Triggered on mouse over the vote box.
				 * @param event
				 */
				_displayVoteButtonIn : function(event) {
					dojo.stopEvent(event);				
					dojo.removeClass(this._button, encuestame.utilities.HIDDEN_CLASS);
					dojo.addClass(this._vote, encuestame.utilities.HIDDEN_CLASS);
				},
				
				/**
				 * Create a temporal button.
				 * @param {Function} event handler when user push the button.
				 */
				_createButton : function() {
					var button = dojo.create("button");
					button.innerHTML = this.voteEventMessage;
					dojo.addClass(button, encuestame.utilities.GRADINENT_CLASS);
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
				 * Triggered on mouse out the vote.
				 * @param event
				 */
				_displayVoteButtonOut : function(event) {
					dojo.stopEvent(event);
					if (!this.clickVoteButton) {
						dojo.addClass(this._button, encuestame.utilities.HIDDEN_CLASS);
						dojo.removeClass(this._vote, encuestame.utilities.HIDDEN_CLASS);
					}
				},
				
				/**
				 * Call the service to vote.
				 * @params {Object} params
				 */
				_sendVote : function(params) {
					var param = this; 
					var loading = {
				      	init : function() {
							// hide the button and display loadings balls 
							dojo.addClass(param._button, encuestame.utilities.HIDDEN_CLASS);
							dojo.removeClass(param._loading, encuestame.utilities.HIDDEN_CLASS);
				      	}, 
				      	end : function() {
				      		// hide the loadings balls and display the button.
				      		dojo.removeClass(param._button, encuestame.utilities.HIDDEN_CLASS);
							dojo.addClass(param._loading, encuestame.utilities.HIDDEN_CLASS);				
							//display message OK and after that display votes;
				      	}
					}; 
					
					var param = this;
					/*
					 * triggered after 2 seconds the user vote.
					 * Restore to original position the vote button;
					 */
					var afterVote = function() {
						dojo.empty(param._button);
						dojo.removeClass(param._vote, encuestame.utilities.HIDDEN_CLASS);
						dojo.addClass(param._button, encuestame.utilities.HIDDEN_CLASS);					
						var button = param._createButton();
						param._button.appendChild(button); 
					};
					
					/*
					 * function triggered on succesfull response.
					 * @param {Object} data the successfull json service response
					 */
					var load = dojo.hitch(this, function(data) {								
							var r = this.getDefaultResponse(data);				
							delete this.clickVoteButton;
							this.temButton = this._button;											
							if (r) {
								this._button.innerHTML = this.voteOkMessage;
								var currentVote = this._voteCounter.innerHTML;
								if (typeof(currentVote) === "number") {
									var newVoteCounter = parseInt(currentVote) + encuestame.utilities.vote;
									this._voteCounter.innerHTML = _ENME.shortAmmount(newVoteCounter);
								} else {
									this._voteCounter.innerHTML = currentVote;
								}					
							} else {
								this._button.innerHTML = this.voteFailMessage;
							}
							//wait 2 seconds to restore the button.
							setTimeout(afterVote, 2000);
					});
					
					/*
					 * function triggered on failed response
					 * @param {Object} data the failed json service response 
					 */
					var error = dojo.hitch(this, function(data) {
						this._button.innerHTML = this.voteFailMessage;
						setTimeout(afterVote, 2000);
						console.error("data error vote", data);
					});
					
					//make a POST call to server.
					this.callPOST(params, load, this.getURLService().service("encuestame.service.list.votes.home"), loading, error);
				}
			});
		});

//dojo.provide("encuestame.org.core.home.votes.ItemVote");
//
//dojo.require('encuestame.org.core.commons');
//dojo.require("encuestame.org.main.EnmeMainLayoutWidget");
//
///**
// * 
// */
//dojo.declare("encuestame.org.core.home.votes.ItemVote",
//		[ encuestame.org.main.EnmeMainLayoutWidget ], {
//	
//	/**
//	 * Template dijit url.
//	 */
//	templatePath: dojo.moduleUrl("encuestame.org.core.home.votes", "templates/item.html"),
//
//	/**
//	 * Vote message.
//	 */
//	voteMessage : "",
//	
//	/**
//	 * View message.
//	 */
//	viewMessage : "",
//	
//	/**
//	 * Button vote message.
//	 */
//	voteEventMessage : "Vote",
//	
//
//	/**
//	 * Message if vote is valid.
//	 */
//	voteOkMessage : "Ok !!",
//	
//	/**
//	 * Message if vote is invalid.
//	 */
//	voteFailMessage : "Bad Vote !!",
//	
//	/**
//	 * Current Votes.
//	 */
//	votes : 0,
//	
//	/**
//	 * Current Hits.
//	 */
//	hits : 0,
//	
//	 /**
//	  * Poll / Tpoll / Survey Id.
//	  */
//	itemId : 0,
//	
//	/**
//	 * Poll || TPoll || Survey
//	 */
//	itemType : "",
//	
//	/**
//	 * Post create.
//	 */
//	postCreate : function() {
//		dojo.connect(this.domNode, "onmouseover", dojo.hitch(this, this._displayVoteButtonIn));
//		dojo.connect(this.domNode, "onmouseout", dojo.hitch(this, this._displayVoteButtonOut));
//		var button = this._createButton();				
//		dojo.addClass(this._button, encuestame.utilities.HIDDEN_CLASS);
//		dojo.addClass(this._loading, encuestame.utilities.HIDDEN_CLASS);
//		this._button.appendChild(button);		
//		this._voteCounter.innerHTML = ENME.shortAmmount(this.votes);
//		var view = dojo.create('div');
//		view.innerHTML = ENME.shortAmmount(this.hits) + " " + this.viewMessage;
//		this._vote.appendChild(view);
//	},
//	
//	/**
//	 * Triggered on mouse over the vote box.
//	 * @param event
//	 */
//	_displayVoteButtonIn : function(event) {
//		dojo.stopEvent(event);				
//		dojo.removeClass(this._button, encuestame.utilities.HIDDEN_CLASS);
//		dojo.addClass(this._vote, encuestame.utilities.HIDDEN_CLASS);
//	},
//	
//	/**
//	 * Create a temporal button.
//	 * @param {Function} event handler when user push the button.
//	 */
//	_createButton : function() {
//		var button = dojo.create("button");
//		button.innerHTML = this.voteEventMessage;
//		dojo.addClass(button, encuestame.utilities.GRADINENT_CLASS);
//		var param = this;
//		dojo.connect(button, "onclick", dojo.hitch(this, function() {
//			param.clickVoteButton = true;
//			if (this.itemId !== null) {
//				this._sendVote({
//					id : this.itemId,
//					type : this.itemType
//				});
//			}
//		}));		
//		return button;
//	},
//	
//	/**
//	 * Triggered on mouse out the vote.
//	 * @param event
//	 */
//	_displayVoteButtonOut : function(event) {
//		dojo.stopEvent(event);
//		if (!this.clickVoteButton) {
//			dojo.addClass(this._button, encuestame.utilities.HIDDEN_CLASS);
//			dojo.removeClass(this._vote, encuestame.utilities.HIDDEN_CLASS);
//		}
//	},
//	
//	/**
//	 * Call the service to vote.
//	 * @params {Object} params
//	 */
//	_sendVote : function(params) {
//		var param = this; 
//		var loading = {
//	      	init : function() {
//				// hide the button and display loadings balls 
//				dojo.addClass(param._button, encuestame.utilities.HIDDEN_CLASS);
//				dojo.removeClass(param._loading, encuestame.utilities.HIDDEN_CLASS);
//	      	}, 
//	      	end : function() {
//	      		// hide the loadings balls and display the button.
//	      		dojo.removeClass(param._button, encuestame.utilities.HIDDEN_CLASS);
//				dojo.addClass(param._loading, encuestame.utilities.HIDDEN_CLASS);				
//				//display message OK and after that display votes;
//	      	}
//		}; 
//		
//		var param = this;
//		/*
//		 * triggered after 2 seconds the user vote.
//		 * Restore to original position the vote button;
//		 */
//		var afterVote = function() {
//			dojo.empty(param._button);
//			dojo.removeClass(param._vote, encuestame.utilities.HIDDEN_CLASS);
//			dojo.addClass(param._button, encuestame.utilities.HIDDEN_CLASS);					
//			var button = param._createButton();
//			param._button.appendChild(button); 
//		};
//		
//		/*
//		 * function triggered on succesfull response.
//		 * @param {Object} data the successfull json service response
//		 */
//		var load = dojo.hitch(this, function(data) {								
//				var r = this.getDefaultResponse(data);				
//				delete this.clickVoteButton;
//				this.temButton = this._button;											
//				if (r) {
//					this._button.innerHTML = this.voteOkMessage;
//					var currentVote = this._voteCounter.innerHTML;
//					if (typeof(currentVote) === "number") {
//						var newVoteCounter = parseInt(currentVote) + encuestame.utilities.vote;
//						this._voteCounter.innerHTML = ENME.shortAmmount(newVoteCounter);
//					} else {
//						this._voteCounter.innerHTML = currentVote;
//					}					
//				} else {
//					this._button.innerHTML = this.voteFailMessage;
//				}
//				//wait 2 seconds to restore the button.
//				setTimeout(afterVote, 2000);
//		});
//		
//		/*
//		 * function triggered on failed response
//		 * @param {Object} data the failed json service response 
//		 */
//		var error = dojo.hitch(this, function(data) {
//			this._button.innerHTML = this.voteFailMessage;
//			setTimeout(afterVote, 2000);
//			console.error("data error vote", data);
//		});
//		
//		//make a POST call to server.
//		this.callPOST(params, load, encuestame.service.list.votes.home, loading, error);
//	}
//
//});
