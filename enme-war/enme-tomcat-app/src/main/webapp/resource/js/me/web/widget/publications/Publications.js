define([
     "dojo/_base/declare",
     "dijit/_WidgetBase",
     "dijit/_TemplatedMixin",
     "dijit/_WidgetsInTemplateMixin",
     "me/core/main_widgets/EnmeMainLayoutWidget",
     "me/web/widget/pictures/AccountPicture",
     "me/web/widget/stream/HashTagInfo",
     "me/core/enme",
     "dojo/text!me/web/widget/publications/templates/publication.html" ],
    function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    main_widget,
    accountPicture,
    hashTagInfo,
    _ENME,
     template) {

  return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

     // template string.
    templateString : template,

    username : "",

      title : "",

      url : "",

      added : "",

      relativeTime : "",

      itemId : "",

      ht : "",

      type : "",

      _tagsCloned : [],

      postMixInProperties : function(){
          if (this.ht != null) {
            //console.info("this.ht.trim().split(",");", this.ht.trim().split(","));
              this._tagsCloned = this.ht.trim().split(",");
          }
      },

      /*
       * Post create.
       */
      postCreate : function() {
          var node = this._tags;
          dojo.forEach(this._tagsCloned, function(entry, i) {
            if (entry !== '') {
                var widget = new hashTagInfo(
                        {
                          hashTagName : entry,
                          autoCreateUrl : true, //create url automatically
                          cssClass : "tag"
                        });
                node.appendChild(widget.domNode);
            }
          });
          //TODO: ????????
          var nodeCustom;
          if (dojo.indexOf(encuestame.surveys, dojo.trim(this.type)) == 0) { //tp

          } else if (dojo.indexOf(encuestame.surveys, dojo.trim(this.type)) == 1) { //pll

          } else if (dojo.indexOf(encuestame.surveys, dojo.trim(this.type)) == 0){ //survey

          } else {
              //error
          }
      }

  });
});