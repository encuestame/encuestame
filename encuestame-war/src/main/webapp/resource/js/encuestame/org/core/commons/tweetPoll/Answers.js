dojo.provide("encuestame.org.core.commons.tweetPoll.Answers");

dojo.require("dojo.dnd.Source");
dojo.require("dijit.form.Form");
dojo.require("dijit.form.Textarea");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Textarea");
dojo.require("dijit.form.Button");
dojo.require("dijit.Dialog");
dojo.require("dijit.form.TextBox");
dojo.require("dijit._Widget");

dojo.declare(
    "encuestame.org.core.commons.tweetPoll.Answers",
    [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/answer.inc"),

        widgetsInTemplate: true,

        listItems : [],

        buttonWidget : null,

        answerSource : null,

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
        },

        getAnswers : function(){
            var array = [];
             dojo.forEach(this.listItems,
                   dojo.hitch(this,function(item) {
                   array.push(item.answer);
                   }));
            return array;
        },

        onDrop : function(){
             if(dojo.dnd.manager().target !== this.answerSource){
                 return;add
             }
              if(dojo.dnd.manager().target == dojo.dnd.manager().source){
                  var newOrder = [];
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

        addAnswer : function(){
            var text = dijit.byId(this._suggest);
            var items = [];
            var answerWidget = new encuestame.org.core.commons.tweetPoll.AnswerItem({
                answer : { label : text.get('value'), id : 0},
                parentAnswer : this
            });
            items.push(answerWidget.domNode);
            this.listItems.push(answerWidget);
            this.answerSource.insertNodes(false, items);
            text.set('value', "");
            dojo.publish("/encuestame/tweetpoll/updatePreview");
            dojo.publish("/encuestame/tweetpoll/autosave");
        },

        getDialog : function(){
            var dialog = dijit.byId("option_"+this.id);
            return dialog;
        },

        _removeItem : function(event){
            dojo.stopEvent(event);
            var i = dojo.indexOf(this.listItems, this.getDialog().item);
            if(i != -1){
                this.listItems.splice(i, 1);
                dojo.destroy(this.getDialog().item.domNode);
                this.getDialog().hide();
                dojo.publish("/encuestame/tweetpoll/updatePreview");
            } else {
                console.error("Error on remove Item");
            }
        }
    }
);

/**
 * Answer Item.
 */
dojo.declare(
        "encuestame.org.core.commons.tweetPoll.AnswerItem",
        [dijit._Widget, dijit._Templated],{
            templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/answerItem.inc"),

        widgetsInTemplate: true,

        demoShortUrl : "http://en.me/",

        answer : {id: null, label: ''},

        parentAnswer : null,

        postCreate : function(){
            console.debug("answer", this.answer);
        },

        buildDemoUrl : function(){
            var buf = this.answer.label;
            buf +=	" ["
            buf += this.demoShortUrl;
            buf += this.answer.label;
            buf += "] "
            return buf;
        },

        _options : function(event){
            var dialog = this.parentAnswer.getDialog();
            dialog.item = this;
            dialog.show();
        }
});