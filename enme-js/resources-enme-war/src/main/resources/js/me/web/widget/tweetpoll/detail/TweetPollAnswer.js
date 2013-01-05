define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/core/enme",
     "dojo/text!me/web/widget/tweetpoll/detail/templates/tweetPollAnswer.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    _ENME,
     template) {

  return declare([ _WidgetBase,
                   _TemplatedMixin,
                   main_widget,
                   _WidgetsInTemplateMixin], {

     /**
      * template string.
      */
     templateString : template,

    // answer id.
    aId : null,

    // label
    label : "",

    // is completed?
    completed : false,

    //url
    url : "",

    // tweetpoll owner
    owner: "",

    // default color
    color : "#000",

    /**
     * post create.
     */
    postCreate : function() {
        dojo.subscribe("/encuestame/tweetpoll/detail/answer/reload", this, this._reloadAnswerInfo);
           // dojo.connect(this._url, "onclick", dojo.hitch(this, function(event){
           //     dojo.stopEvent(event);
           //     //location.target = "_blank";
           //     window.location.href = this.url;
           // }));
    },

    /**
     * reload answer info.
     */
    _reloadAnswerInfo : function(id, data /*[votes, percent]*/) {
        if (this.aId == id) {
            this._reloadValues(data[0], data[1]);
        }
    },

    /*
     * reload percent and votes values.
     */
    _reloadValues : function(votes, percent){
        if (this._votes) {
            this._votes.innerHTML = votes;
        }
        if (this._percent) {
            this._percent.innerHTML = percent;
        }
    }

  });
});