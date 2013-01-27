define([
     "dojo/_base/declare",
     "dojo/_base/array",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/home/votes/ItemVote",
     "me/web/widget/pictures/AccountPicture",
     "me/web/widget/stream/HashTagInfo",
     "me/core/enme",
     "dojo/text!me/web/widget/stream/templates/frontEndItem.html" ],
    function(
    declare,
    array,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    ItemVote,
    AccountPicture,
    HashTagInfo,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

      // template string.
        templateString : template,
        contextPath : _ENME.config('contextPath'),
        slugName : "",
        questionName : "",
        id : 0,
        item : {},
        votesMessage : "votes",
        viewMessage : "views",
        totalCommentMessage : "home.item.comments",
        addedMessage: "added",
        submiteddByMessage : "submited by",
        owner : "dasd",
        votes : 0,
        views : 0,
        relativeTime: '',
        url : "#",

        /*
         * Post create.
         */
        postCreate : function() {
            var parent = this;
            if (this._tags) {
                // url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                //hashTagName="${h.hashTagName}">
                if ("hashtags" in this.item) {
                     array.forEach(this.item.hashtags, function(entry, i){
                        var widget = new HashTagInfo({
                            url : _ENME.hashtagContext(entry.hashTagName),
                            hashTagName : entry.hashTagName
                        });
                        parent._tags.appendChild(widget.domNode);
                     });
                }
            }
        },

        _geTtotalVotes : function(){

        }

  });
});