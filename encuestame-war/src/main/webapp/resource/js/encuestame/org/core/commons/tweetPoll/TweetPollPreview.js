dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollPreview");

dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.Button");
dojo.require("dijit.form.TextBox");
dojo.require("dijit.form.CheckBox");
dojo.require("dijit.form.TimeTextBox");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.NumberSpinner");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dijit._Widget");
dojo.require("dojo.dnd.Source");
dojo.require("dojo.hash");
dojo.require("dojox.widget.Dialog");

dojo.require("encuestame.org.core.commons.tweetPoll.Answers");
dojo.require("encuestame.org.core.commons.tweetPoll.HashTags");
dojo.require("encuestame.org.core.commons.social.SocialAccountPicker");
dojo.require("encuestame.org.core.commons.dialog.Dialog");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/*
 * A preview of tweetpoll.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollPreview",
        [encuestame.org.main.EnmeMainLayoutWidget],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetpollPreview.html"),
            _questionBox : {node:null,initialize:false},
            _answersBox : {node:null,initialize:false},
            _hashTagsBox : {node:null,initialize:false},
            _completeText : "",
            _isValid : false,
            _isValidMessage : "",
            /*
             * max length text.
             */
            _counterMax : 140,

            /*
             *
             */
            _lastedCounter : 0,

            _answerSize : 0,
            _hashTagsSize : 0,

            _locked : false,

            /*
             * post create.
             */
            postCreate : function() {
                this.initialize();
                this.showEmtpyContent();
            },

            /*
             * show the preview node.
             * @param node
             */
            show : function(/* DOM */ parentNode, y) {
              var yT = (y * -1) -2;
              dojo.style(parentNode, "top", yT+"px");
              if (!this._locked) {
                    this._locked = true;
                    var fadeArgs = {
                            node: parentNode,
                            duration: 200,
                            onEnd: dojo.hitch(this,function() {
                                dojo.addClass(parentNode, "previewFixed");
                                 dojo.removeClass(parentNode, "previewAbsolute");
                                 this._locked = false;
                            })
                    };
                    dojo.fadeIn(fadeArgs).play();
              }
            },

            /*
             * hide the preview node.
             * @param node
             */
            hide : function(/* DOM */ parentNode, y) {
               var yT = (y * -1) -2;
               dojo.style(parentNode, "top", yT+"px");
               if (!this._locked) {
                    this._locked = true;
                    var fadeArgs = {
                            node: parentNode,
                            duration: 200,
                            onEnd: dojo.hitch(this,function() {
                                dojo.removeClass(parentNode, "previewFixed");
                                dojo.addClass(parentNode, "previewAbsolute");
                                this._locked = false;
                            })
                    };
                    dojo.fadeOut(fadeArgs).play();
               }
            },

            /*
             * show empty content.
             */
            showEmtpyContent : function(){
              dojo.empty(this._content);
              var emtpy = dojo.doc.createElement("div");
              //emtpy.innerHTML = "The tweetPoll is emtpy";
              this._content.appendChild(emtpy);
            },

            /*
             * initialize widget.
             */
            initialize : function() {

            },

            /*
             * build question.
             */
            _buildQuestion : function(question){
              dojo.empty(this._content);
                if (this._questionBox.node == null) {
                  this._questionBox.node = dojo.doc.createElement("span");
                  dojo.addClass(this._questionBox.node, "previewQuestion");
                  this._questionBox.innerHTML = "";
                  this._questionBox.initialize = true;
                }
                if (question) {
                  var newPreview = question.get("value");
                  //question
                  this._questionBox.node.innerHTML = newPreview;
                  //console.debug("_buildQuestion newPreview", this._questionBox.node);
                  this._completeText = newPreview;
                }
                if(question.get("value") != "") {
                    this._content.appendChild(this._questionBox.node);
                }
            },

            /*
             *
             */
            _buildAnswers : function(answers){
              if (answers != null) {;
                var arrayItem = [];
                var questionDiv = dojo.doc.createElement("span");
                var wishlist = new dojo.dnd.Source(questionDiv);
                this._answerSize = answers.getAnswers().length;
                dojo.forEach(answers.getAnswers(),
                        dojo.hitch(this,function(item) {
                          var span1 = dojo.doc.createElement("span");
                          span1.innerHTML = item.value;
                          this._completeText = this._completeText + " "+item.value;
                          dojo.addClass(span1, "previewAnswer");
                          arrayItem.push(span1);
                        }));
                wishlist.insertNodes(false, arrayItem);
                //console.info("questionDiv", questionDiv);
                dojo.addClass(questionDiv, "inlineBlock");
                this._content.appendChild(questionDiv);
              } else {
                console.info("no answers widget");
              }
            },

            /*
             * build hashtag items on preview.
             */
            _buildHahsTags : function(hashtags) {
                if (hashtags != null) {
                        var arrayItem = [];
                        var questionDiv = dojo.doc.createElement("span");
                        var wishlist = new dojo.dnd.Source(questionDiv);
                        this._hashTagsSize = hashtags.length;
                        dojo.forEach(hashtags.listItems, dojo.hitch(this, function(item,
                                index) {
                            //TODO: check if blank spaces are counted.
                            var span1 = dojo.doc.createElement("span");
                            span1.innerHTML = "#"+item.data.label;
                            this._completeText = this._completeText + " " + "#"+item.data.label.trim();
                            dojo.addClass(span1, "previewHashTag");
                            arrayItem.push(span1);
                        }));
                        wishlist.insertNodes(false, arrayItem);
                        // console.info("questionDiv", questionDiv);
                        dojo.addClass(questionDiv, "inlineBlock");
                        this._content.appendChild(questionDiv);
                      } else {
                          console.info("no hashtags widget");
                      }
            },

            _getCurrentLengthText : function(){
                return this._lastedCounter;
            },

             /*
              * update counter.
              */
            _updateCounter : function(textTweet) {
              if (this._counter) {
                var counter = this._getCurrentLengthText();
                  //console.debug("counter", counter);
                  if (counter >= 0) {
                    var currentCounter = this._counterMax - textTweet.length;
                    this._counter.innerHTML = currentCounter;
                    this._lastedCounter = currentCounter;
                    dojo.publish("/encuestame/tweetpoll/unblock");
                    this._isValid = true;
                  } else {
                    this._isValid = false;
                    this._isValidMessage = encuestame.constants.errorCodes["020"];
                    dojo.publish("/encuestame/tweetpoll/block");
                    //this._lastedCounter = 0;
                    var currentCounter = this._counterMax - textTweet.length;
                    this._counter.innerHTML = currentCounter;
                    this._lastedCounter = currentCounter;
                  }
              } else {
                  console.error(encuestame.constants.errorCodes["023"]);
              }
            },

            /*
             * check required structure.
             */
            _checkTweetPollStructure : function() {
                if(this._answerSize < config.tp.a) {
                    this._isValid = false;
                    this._isValidMessage = encuestame.constants.errorCodes["021"];
                }
            },

            /*
             * check if tweet text is valid.
             */
            isValid : function() {
                this._checkTweetPollStructure();
                return this._isValid;
            },

            isValidMessage : function() {
                return this._isValidMessage;
            },

           /*
            * return complete tweet.
            */
           getCompleteTweet : function(){
               return this._completeText;
           },

            /*
             * Update preview.
             */
            updatePreview : function(question, answers, hashtags){
                 this._buildQuestion(question);
                 this._buildAnswers(answers);
                 this._buildHahsTags(hashtags);
                 this._updateCounter(this.getCompleteTweet());
            }
});
