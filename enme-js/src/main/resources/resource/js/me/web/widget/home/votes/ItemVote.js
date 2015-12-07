define( [
        "dojo/_base/declare",
        "dijit/_WidgetBase",
        "dijit/_TemplatedMixin",
        "dijit/_WidgetsInTemplateMixin",
        "me/core/main_widgets/EnmeMainLayoutWidget",
        "me/core/enme",
        "dojo/text!me/web/widget/home/votes/templates/item.html" ],
    function( declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin, main_widget, _ENME, template ) {

        return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

            // Template string.
            templateString: template,

            /**
             * Vote message.
             */
            voteMessage: "",

            /**
             * View message.
             */
            viewMessage: "",

            /**
             * Button vote message.
             */
            voteEventMessage: "Vote",

            /**
             * Message if vote is valid.
             */
            voteOkMessage: "Ok !!",

            /**
             * Message if vote is invalid.
             */
            voteFailMessage: "Bad Vote !!",

            /**
             * Current Votes.
             */
            votes: 0,

            /**
             * Current Hits.
             */
            hits: 0,

            /**
             * Poll / Tpoll / Survey Id.
             */
            itemId: 0,

            /**
             * Poll || TPoll || Survey
             */
            itemType: "",

            /**
             * Avoid repated votes
             */
            _block: false,

            /**
             * Vote is allowed or not
             */
            voteUp: true,

            /**
             * I18n Message.
             */
            i18nMessage: {
                vote_error: _ENME.getMessage("button_vote_error_message"),
                button_vote: _ENME.getMessage("button_vote"),
                button_voted: _ENME.getMessage("button_voted")
            },

            /**
             * Post create.
             */
            postCreate: function() {

                // Update the votes
                var isLogged = _ENME.config( "logged" );
                this._updateVotes( this.votes );
                if ( isLogged ) {

                    // Button event
                    dojo.connect( this._button, "onclick", dojo.hitch( this, function() {
                        if ( this.itemId !== null ) {
                            if ( !this._block ) {
                                this._sendVote({
                                    id: this.itemId,
                                    type: this.itemType
                                });
                            }
                        }
                    }) );
                } else {
                    dojo.connect( this._button, "onclick", dojo.hitch( this, function() {
                          if ( alertify ) {
                              alertify.log( this.i18nMessage.vote_error );
                          }
                    }) );
                }
                if ( !this.voteUp ) {
                    this._displayVoteButtonOut();
                } else {
                    this._displayVoteButtonIn();
                }
            },

            /**
             * Update the votes counter
             * @param votes
             * @private
             */
            _updateVotes: function( votes ) {
                this._voteCounter.innerHTML = _ENME.shortAmmount( votes );
                this.votes = votes;
            },

            /**
             * Triggered on mouse over the vote box.
             * @param event
             */
            _displayVoteButtonIn: function( event ) {
                dojo.addClass( this._button, "button-vote-enabled");
                dojo.removeClass( this._button, "button-vote-blocked");
                this._voteText.innerHTML = this.i18nMessage.button_vote;
            },

            /**
             * Triggered on mouse out the vote.
             * @param event
             */
            _displayVoteButtonOut: function() {
                dojo.addClass( this._button, "button-vote-blocked");
                dojo.removeClass( this._button, "button-vote-enabled");
                this._voteText.innerHTML = this.i18nMessage.button_voted;
            },

            /**
             * Call the service to vote.
             * @params {Object} params
             */
            _sendVote: function( params ) {
                this._block = true;
                var param = this;
                var loading = {
                    init: function() {},
                    end: function() {}
                };
                /*
                 * Triggered after 2 seconds the user vote.
                 * Restore to original position the vote button;
                 */
                var afterVote = function() {};

                /*
                 * Function triggered on succesfull response.
                 * @param {Object} data the successfull json service response
                 */
                var load = dojo.hitch( this, function( data ) {
                    if ("success" in data ) {
                        var action = data.success.status;
                        if ( action === "ACTIVE" ) {
                            this._updateVotes( this.votes + 1 );
                            this._displayVoteButtonOut();
                        } else if ( action === "INACTIVE" ) {
                            this._updateVotes( this.votes - 1 );
                            this._displayVoteButtonIn();
                        }
                    }
                    this._block = false;
                });

                /**
                 * Function triggered on failed response
                 * @param {Object} data the failed json service response
                 */
                var error = dojo.hitch( this, function( data ) {
                    this._block = false;
                });

                //Make a POST call to server.
                this.getURLService().put( [ "encuestame.service.list.votes.home", [ this.itemType ]], params, load, error, dojo.hitch( this, function() {

                }) );
            }
        });
    });
