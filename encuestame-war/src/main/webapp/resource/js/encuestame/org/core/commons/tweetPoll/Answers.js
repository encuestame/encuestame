dojo.provide("encuestame.org.core.commons.tweetPoll.Answers");

dojo.require("dojo.dnd.Source");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Button");
dojo.require("dijit.Dialog");
dojo.require("dijit.form.TextBox");
dojo.require("encuestame.org.core.shared.utils.OptionMenu");
dojo.require("encuestame.org.main.EnmeMainLayoutWidget");

/**
 * Widget to list of answers.
 */
dojo.declare(
    "encuestame.org.core.commons.tweetPoll.Answers",
    [encuestame.org.main.EnmeMainLayoutWidget],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/answer.html"),

        /*
         * list of items.
         */
        listItems : [],

        /*
         * default short url provider.
         */
        _provider : encuestame.shortUrlProvider,

        /*
         * button widget.
         */
        buttonWidget : null,

        /*
         * default label.
         */
        label : "Answer",

        /*
         * tweetpollId.
         */
        tweetPollId : null,

        /*
         * Answer source.
         */
        answerSource : null,

        /*
         * Flag to control bloked.
         */
        _blocked : false,

        /*
         *
         */
        postCreate: function() {
             this.buttonWidget = new dijit.form.Button({
                 label: "Add",
                 onClick: dojo.hitch(this, function(event) {
                     dojo.stopEvent(event);
                     this.addAnswer();
                 })
             },
             this._addButton);
             this.answerSource  = new dojo.dnd.Source(this._listAnswers, {
                 accept: [],
                 copyOnly: false,
                 selfCopy : false,
                 selfAccept: true,
                 withHandles : false,
                 autoSync : true,
                 isSource : true
              });
             dojo.connect(this.answerSource, "onDrop", this, this.onDrop);
             // on key up.
             dojo.connect(this._suggest, "onKeyUp", dojo.hitch(this, function(e) {
                 if (dojo.keys.ENTER == e.keyCode) {
                     this.addAnswer();
                 }
             }));
        },

        /*
         *
         */
        block : function(){
            //console.debug("blocking answer...");
            this.buttonWidget.disabled = true;
            dijit.byId("answerAddText").disabled = true;
        },

        /*
         *
         */
        unblock : function() {
           // console.debug("unblocking answer...");
            this.buttonWidget.disabled = false;
            dijit.byId("answerAddText").disabled = false;
        },

        /*
         *
         */
        getAnswers : function(){
            var array = [];
             dojo.forEach(this.listItems,
                   dojo.hitch(this,function(item) {
                   array.push({ value : item.getAnswerText()});
                   }));
            //console.debug("getAnswers", array);
            return array;
        },

        /*
         *
         */
        getAnswersId : function() {
            var array = [];
             dojo.forEach(this.listItems,
                   dojo.hitch(this,function(item) {
                   array.push(item.answer.answerId);
                   }));
            console.debug("getAnswers", array);
            return array;
        },

        /*
         *
         */
        onDrop : function() {
                // check out
             if (dojo.dnd.manager().target !== this.answerSource) {
                 return;
             }

             if (dojo.dnd.manager().target == dojo.dnd.manager().source) {
                  //var newOrder = [];
                  this.listItems = [];
                  dojo.forEach
                  (this.answerSource.getAllNodes(),
                      dojo.hitch(this,function(item) {
                          var widget = dijit.byId(item.id);
                          this.listItems.push(widget);
                      }));
                  dojo.publish("/encuestame/tweetpoll/updatePreview");
              }
        },

        /*
         *
         */
        addAnswer : function() {
            //dojo.publish("/encuestame/tweetpoll/autosave");
            var text = dijit.byId(this._suggest);
            var params = {
                    "id" : this.tweetPollId,
                    "answer" : text.get("value"),
                    "shortUrl" : encuestame.shortUrlProvider[1].code
               };
               console.debug("params", params);
               var load = dojo.hitch(this, function(data){
                   console.debug(data);
                   var items = [];
                   var answerWidget = new encuestame.org.core.commons.tweetPoll.AnswerItem({
                       answer :{
                           answerId : data.success.newAnswer.answer.answerId,
                           label: data.success.newAnswer.answer.answers,
                           shortUrl : data.success.newAnswer.short_url,
                           provider: encuestame.shortUrlProvider[1]
                      },
                      parentAnswer : this,
                      tweetPollId : this.tweetPollId
                   });
                   items.push(answerWidget.domNode);
                   this.listItems.push(answerWidget);
                   this.answerSource.insertNodes(false, items);
                   text.set('value', "");
                   dojo.publish("/encuestame/tweetpoll/updatePreview");
               });
               var error = function(error) {
                   console.debug("error", error);
               };
               if(this.tweetPollId != null){
                   encuestame.service.xhrGet(
                           encuestame.service.list.addAnswer, params, load, error);
               } else {
                   console.info("Please, save your tweetpoll first");
               }
        },

        /*
         *
         */
        getDialog : function() {
            var dialog = dijit.byId("option_"+this.id);
            return dialog;
        }
    }
);

/**
 * Widget to represent an answer item.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.AnswerItem",
        [encuestame.org.main.EnmeMainLayoutWidget],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/answerItem.html"),


        /*
         * tweetpoll Id reference.
         */
        tweetPollId : null,

        /*
         * provider list.
         */
        _provider : null,

        /*
         * answer data.
         */
        answer : {},

        /*
         * parent answer.
         */
        parentAnswer : null,

        /*
         * constructor.
         */
        postCreate : function() {
            this._provider = encuestame.shortUrlProvider;
            if (this._item) {
                var answer = dojo.doc.createElement("div");
                answer.innerHTML = this.answer.label;
                dojo.addClass(answer, "answerItemTitle");
                dojo.addClass(answer, "wrap");
                var url = dojo.doc.createElement("div");
                //url
                var urlA = dojo.doc.createElement("a");
                urlA.innerHTML = this.answer.shortUrl;
                urlA.href = "#";
                //urlA.target = "_blank";
                url.appendChild(urlA);
                dojo.addClass(url, "answerItemShortUrl");
                dojo.addClass(url, "wrap");

                var menuWidget = new encuestame.org.core.shared.utils.OptionMenu({
                    _classReplace : "hidden",
                    menu_items : [{
                        label : "Remove",
                        action : dojo.hitch(this, this._removeAnswer)}
                        //{label : "Edit",
                        //action : function() {
                        //    console.debug("Edit");
                        //}}
                ]});
                this._options.appendChild(menuWidget.domNode);
                this._item.appendChild(answer);
                this._item.appendChild(url);
            }
        },

        /*
         * display or short url
         */
        editShortUrl : function(event){
            dojo.stopEvent(event);
            console.debug(event);
        },

        /*
         * remove this answer.
         */
        _removeAnswer : function() {
             var params = {
                     "id" : this.tweetPollId,
                     "answerId" : this.answer.answerId
            };
            var load = dojo.hitch(this, function(data) {
                var i = dojo.indexOf(this.parentAnswer.listItems, this);
                console.debug("removing answer", i);
                this.parentAnswer.listItems.splice(i, 1);
                dojo.publish("/encuestame/tweetpoll/updatePreview");
                dojo.destroy(this.domNode, true);
            });
            var error = function(error) {
                console.debug("error", error);
            };
            encuestame.service.xhrGet(
                    encuestame.service.list.removeAnswer, params, load, error);
        },

        /*
         * answer text.
         */
        getAnswerText: function() {
            var answer = this.answer.label+ " "+this.answer.shortUrl;
            return answer;
        }
});