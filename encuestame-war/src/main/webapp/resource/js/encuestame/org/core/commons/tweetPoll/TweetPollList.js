dojo.provide("encuestame.org.core.commons.tweetPoll.TweetPollList");


dojo.declare(
        "encuestame.org.core.commons.tweetPoll.TweetPollList",
        [dijit._Widget, dijit._Templated],{
        templatePath: dojo.moduleUrl("encuestame.org.core.commons.tweetPoll", "templates/tweetPollList.inc"),
        widgetsInTemplate: true,
        postCreate : function(){
        }
 });
