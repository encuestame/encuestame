define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollCore",
         "dojo/dnd/Source",
         "me/core/enme",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetpollPreview.html"],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TweetPollCore,
                Source,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, TweetPollCore, _WidgetsInTemplateMixin], {

            /*
             * template string.
             */
            templateString : template,

            /*
            * question widget.
            */
           _questionBox : {node : null, initialize : false},

           /*
            * answer widgets.
            */
           _answersBox : {node:null,initialize : false},

           /*
            * hashtag widget.
            */
           _hashTagsBox : {node:null,initialize : false},

           /*
            * widget to buil preview reference.
            */
           _widgetReferences : { question: null, answer: null, hashtag: null},

           /*
            * completed text.
            */
           _completeText : "",

           /*
            *
            */
           _isValid : false,

           /*
            *
            */
           _isValidMessage : "",

           /*
            * max length text.
            */
           _counterMax : 140,


           _current_counter : 0,

           /*
            *
            */
           _lastedCounter : 0,

           /*
            *
            */
           _answerSize : 0,

           /*
            *
            */
           _hashTagsSize : 0,

           /*
            *
            */
           _locked : false,

           /*
            * post create.
            */
           postCreate : function() {
               this.initialize();
               this.showEmtpyContent();
               this.enableBlockTweetPollOnProcess();
           },

           /*
            * show the preview node.
            * @param node
            */
           show : function(/* DOM */ parentNode) {
             if (!this._locked) {
                   this._locked = true;
                   var fadeArgs = {
                           node: parentNode,
                           duration: 100,
                           onEnd: dojo.hitch(this,function() {
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
           hide : function(/* DOM */ parentNode) {
              if (!this._locked) {
                   this._locked = true;
                   var fadeArgs = {
                           node: parentNode,
                           duration: 100,
                           onEnd: dojo.hitch(this,function() {
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
               dojo.subscribe("/encuestame/tweetpoll/updatePreview", this, "updatePreview");
           },

           /*
            * build question.
            * @param question widget.
            */
           _buildQuestion : function(/* widget */ question) {
               //remove old question
             dojo.empty(this._content);
             var newQuestionValue = question.get("value");
             // by first time it's necessary save the node.
               if (this._questionBox.node == null) {
                 this._questionBox.node = dojo.create("span");
                 dojo.addClass(this._questionBox.node, "previewQuestion");
                 this._questionBox.innerHTML = "";
                 this._questionBox.initialize = true;
               }
               if (question) {
                 var newPreview = newQuestionValue;
                 //question
                 this._questionBox.node.innerHTML = newPreview;
                 this._completeText = newPreview || "";
               }
               if (newQuestionValue != "") {
                   this._content.appendChild(dojo.clone(this._questionBox.node));
               }
           },

           /*
            *
            */
           _buildAnswers : function(answers) {
             if (answers != null) {
               var arrayItem = [];
               var questionDiv = dojo.doc.createElement("span");
               var wishlist = new Source(questionDiv);
               this._answerSize = answers.getAnswers().length;
               dojo.forEach(answers.getAnswers(),
                       dojo.hitch(this,function(item) {
                         var span1 = dojo.doc.createElement("span");
                         span1.innerHTML = item.value;
                         this._completeText = this._completeText + " " + item.value;
                         dojo.addClass(span1, "previewAnswer");
                         arrayItem.push(span1);
                       }));
               wishlist.insertNodes(false, arrayItem);
               //console.info("questionDiv", questionDiv);
               dojo.addClass(questionDiv, "inlineBlock");
               this._content.appendChild(questionDiv);
             }
           },

           /*
            * build hashtag items on preview.
            */
           _buildHahsTags : function(hashtags) {
               if (hashtags != null) {
                       var arrayItem = [];
                       var questionDiv = dojo.doc.createElement("span");
                       var wishlist = new Source(questionDiv);
                       this._hashTagsSize = hashtags.length;
                       dojo.forEach(hashtags.listItems, dojo.hitch(this, function(item,
                               index) {
                           //TODO: check if blank spaces are counted.
                           var span1 = dojo.doc.createElement("span");
                           span1.innerHTML = "#" + item.data.hashTagName;
                           /*
                            * TODO: improve the concatenation.
                            */
                           this._completeText = this._completeText + " " + "#"+item.data.hashTagName.trim();
                           dojo.addClass(span1, "previewHashTag");
                           arrayItem.push(span1);
                       }));
                       wishlist.insertNodes(false, arrayItem);
                       // console.info("questionDiv", questionDiv);
                       dojo.addClass(questionDiv, "inlineBlock");
                       this._content.appendChild(questionDiv);
                }
           },

           /*
            * Return the current lenght text.
            */
           _getCurrentLengthText : function() {
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
                   this._current_counter = currentCounter;
                   dojo.publish("/encuestame/tweetpoll/unblock");
                   this._isValid = true;
                 } else {
                   this._isValid = false;
                   this._isValidMessage = _ENME.getMessage("e_020");
                   dojo.publish("/encuestame/tweetpoll/block");
                   //this._lastedCounter = 0;
                   var currentCounter = this._counterMax - textTweet.length;
                   this._current_counter = currentCounter;
                   this._counter.innerHTML = currentCounter;
                   this._lastedCounter = currentCounter;
                 }
             } else {
               _ENME.log(_ENME.getMessage("e_023"));
             }
           },

           /**
            * Check if the length of the tweetpol is valid.
            * @method _checkTweetPollStructure
            */
           _checkTweetPollStructure : function() {
                // it's necesary check if the required ans
               if (this._answerSize < _ENME.config('tp_a') || this._current_counter < 0) {
                   this._isValid = false;
                   this._isValidMessage =  _ENME.getMessage("e_021");
               }
           },

           /*
            * check if tweet text is valid.
            */
           isValid : function() {
               this._checkTweetPollStructure();
               return this._isValid;
           },

           /*
            * check if the message is valid.
            */
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
           updatePreview : function() {
                this._buildQuestion(this._widgetReferences.question);
                this._buildAnswers(this._widgetReferences.answer);
                this._buildHahsTags(this._widgetReferences.hashtag);
                this._updateCounter(this.getCompleteTweet());
           }

    });
});