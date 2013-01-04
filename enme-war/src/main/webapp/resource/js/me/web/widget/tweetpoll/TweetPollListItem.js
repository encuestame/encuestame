define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/tweetpoll/TweetPollListDetail",
         "me/web/widget/stream/HashTagInfo",
         "me/web/widget/support/Wipe",
         "me/core/enme",
         "dojo/topic",
         "dojo/on",
         "dojo/dom-class",
         "dojo/text!me/web/widget/tweetpoll/templates/tweetPollListItem.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                TweetPollListDetail,
                HashTagInfo,
                Wipe,
                _ENME,
                topic,
                on,
                domClass,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
            templateString : template,

            //data
            data: null,
            //seleted
            selected : false,

            panelWidget : null,

            // wipe parameters.
            _wipe : {
                       height : 255,
                       duration : 200
                    },

            /*
             * i18n message for this widget.
             */
            i18nMessage : {
              related_terms : _ENME.getMessage("related_terms")
            },

            //post create
            postCreate : function() {
                dojo.subscribe("/encuestame/tweetpoll/item/unselect", this, "unselect");
                on(this._title, "click", dojo.hitch(this, this._onClickItem));

                if (!this.data.favourites) {
                    domClass.add(this._favourite, "emptyFavourite");
                    domClass.remove(this._favourite, "selectedFavourite");
                } else {
                    domClass.add(this._favourite, "selectedFavourite");
                    domClass.remove(this._favourite, "emptyFavourite");
                }

                this.panelWidget = new Wipe(this._panel, this._wipe.duration, this._wipe.height);
                if (this.data.hashtags) {
                    if (this.data.hashtags.length > 0) {
                        dojo.forEach(this.data.hashtags,
                            dojo.hitch(this,function(item) {
                                if (item.hashTagName !== "") {
                                    var hashtag = new HashTagInfo(
                                            {
                                             hashTagName: item.hashTagName,
                                             url: _ENME.hashtagContext(item.hashTagName)
                                            }
                                            );
                                    this._hashtags.appendChild(hashtag.domNode);
                                }
                        }));
                    } else {
                        dojo.empty(this._hashtags);
                    }
                }
            },

            /*
             * Call Service.
             */
            _callService : function(/* function after response */ load, url) {
                var error = function(error) {
                    this.publishMessage(error.message, _ENME.MSG.ERROR);
                };
                var params = {
                        tweetPollId : this.data.id
                };
                //encuestame.service.xhrGet(url, params, load, error);
                //this._loading.show('Cargando TweetPolls', _ENME.MESSAGES_TYPE.WARNING);
                dojo.publish('encuestame/search/loading/display/on', ['dsdsdsdsds', _ENME.MESSAGES_TYPE.WARNING]);
                this.getURLService().get(url, params, load, error , dojo.hitch(this, function() {
                    dojo.publish('encuestame/search/loading/display/off');
                }));
            },

            /*
             * set favourite this item.
             */
            _setFavourite : function(event) {
                dojo.stopEvent(event);
                var load = dojo.hitch(this, function(data) {
                    this.data.favourites = !this.data.favourites;
                    if (this.data.favourites) {
                        domClass.add(this._favourite, "selectedFavourite");
                        domClass.remove(this._favourite, "emptyFavourite");
                        this.publishMessage(_ENME.getMessage('commons_favourite'), _ENME.MSG.SUCCESS);
                    } else {
                        domClass.add(this._favourite, "emptyFavourite");
                        domClass.remove(this._favourite, "selectedFavourite");
                        this.publishMessage(_ENME.getMessage('commons_unfavourite'), _ENME.MSG.SUCCESS);
                    }

                });
                this._callService(load, 'encuestame.service.list.favouriteTweetPoll');
            },

            /*
             *
             */
            _setCloseTweetPoll : function(event) {
                dojo.stopEvent(event);
                var load = dojo.hitch(this, function(data) {
                    //console.debug(data);
                });
                this._callService(load, 'encuestame.service.list.changeTweetPollStatus');
            },

            /*
             * change background color on if selected.
             */
            _changeBackGroundSelected : function() {
                if (this.selected) {
                   this._setUnselected();
                   this.panelWidget.wipeOutOne();
                   dojo.empty(this._panel);
                   domClass.add(this._panel, "tweet-poll-item-panel");
                } else {
                   domClass.add(this.domNode, "listItemTweetSeleted");
                   domClass.remove(this._hashtags, "defaultDisplayHide");
                   domClass.remove(this._panel, "tweet-poll-item-panel");
                   this._createDetail(this.data);
                   this.panelWidget.wipeInOne();
                }
                this.selected =!this.selected;
            },

            /*
             * un selected.
             */
            _setUnselected : function() {
                domClass.remove(this.domNode, "listItemTweetSeleted");
                domClass.add(this._hashtags, "defaultDisplayHide");
            },

            /*
             * unselect this item.
             */
            unselect : function(id) {
                if (this.selected && this != id) {
                    this._changeBackGroundSelected();
                }
            },

            /*
             * create detail.
             */
            _createDetail : function(data) {
                var detail = new TweetPollListDetail({data: data});
                this._panel.appendChild(detail.domNode);
            },

            /*
             * on click on the widget dom node.
             */
            _onClickItem : function(event) {
                 dojo.stopEvent(event);
                 this._changeBackGroundSelected();
                 dojo.publish("/encuestame/tweetpoll/item/unselect", [this]);
            }
    });
});