define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/stream/HashTagInfo",
         "me/core/enme",
         "dojo/text!me/web/widget/hashtags/template/cloud.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                hashTagInfo,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            /**
           * Done to append hashtag cloud.
           */
           _hashtagCloud : null,

          /**
           * Post create.
           */
          postCreate : function() {
              this._loadCloud();
          },

          /**
           *
           * @param items
           */
          _printCloud : function(items) {
              dojo.forEach(items,
                  dojo.hitch(this,function(item) {
                    var wrapper = dojo.doc.createElement("div");
                    dojo.style(wrapper, "display", "inline-block");
                    dojo.style(wrapper, "margin", "5px");
                    dojo.style(wrapper, "fontSize", "1"+item.size+"%");
                    if (item.hashTagName !== "") { //to avoid null
                        var url = this.contextDefaultPath;
                        url = url.concat("/tag/");
                        url = url.concat(item.hashTagName);
                        url = url.concat("/");
                        var widget = new hashTagInfo(
                                {
                                 hashTagName : item.hashTagName,
                                 url : url,
                                 size : item.size
                                });
                        wrapper.appendChild(widget.domNode);
                        this._cloud.appendChild(wrapper);
                    }
                  }));
          },

          /*
           *
           */
          _loadCloud : function(){
              var load = dojo.hitch(this, function(data) {
                  this.arrayAccounts = data.success.cloud;
                  dojo.empty(this._cloud);
                  //console.debug("social", this._hashtagCloud);
                  this._printCloud(data.success.cloud);
              });
              var error = function(error) {
                  //console.debug("error", error);
              };
              encuestame.service.xhrGet(
                  this.getURLService().service('encuestame.service.list.cloud'), {}, load, error);
          }



            });
        });