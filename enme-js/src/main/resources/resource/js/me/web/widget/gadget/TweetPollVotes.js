define([
     "dojo/_base/declare",
     "dojo/on",
     "dojo/dom-construct",
     "dojo/dom-class",
     "me/web/widget/dashboard/AbstractGadget",
     "me/core/enme",
     "me/web/widget/support/ButtonEmbebedOption",
     "me/web/widget/chart/EncuestamePieChart",
     "me/web/widget/ui/More",
     "dijit/form/CheckBox",
     "dijit/Tooltip",
     "dijit/registry",
     "dojo/text!me/web/widget/gadget/template/tweetpollsvotes.html"],
    function(
    declare,
    on,
    domConstruct,
    domClass,
    AbstractGadget,
    _ENME,
    ButtonEmbebedOption,
    EncuestamePieChart,
    More,
    CheckBox,
    Tooltip,
    registry,
    template) {


  return declare([AbstractGadget], {

    /**
     * template string.
     * @property templateString
     */
    templateString : template,

    /**
     *
     * @property
     */
    max: 2,

    /**
     *
     * @property
     */
    start : 0,

    /**
     *
     * @property _direction
     */
    _direction: true,

    /**
     *
     * @property widgetChart
     */
    widgetChart : null,

      /**
       * i18n Message.
       */
      i18nMessage : {
          autocomplete : _ENME.getMessage("autocomplete")
      },


   /**
    * PostCreate life cycle.
    */
     initGadget : function() {
          var parent = this,
          interval_ref = null;
          this.load();
          var checkbox = this._auto.onChange = dojo.hitch(this, function(val) {
              //this.stopEvent(event);
              if (this._auto.checked) {
                  // check if hte interval is not null and clear the interval to avoid memory leaks
                  if (interval_ref) {
                    clearInterval(interval_ref);
                  }
              } else {
                //
                interval_ref = setInterval(function() {
                    parent.displayNext();
                }, 10000);
              }
          });
     },

      _generateShareButton : function(id) {
          dojo.empty(this._embed_link);
          var widget = new ButtonEmbebedOption({
              data : {
                  id : id,
                  type: "tweetpoll"
              }
          });
          domConstruct.place(widget.domNode, this._embed_link);
      },

      /***
       * Call Votes.
       * @param type
       */
     _callVotes: function(id, type) {
          var error = dojo.hitch(this, function(dataJson) {
            this.errorMessage("Error on load votes");
          });
          var load = dojo.hitch(this, function(dataJson) {
              var votes = dataJson.success.votesResult,
              results = [];
              dojo.forEach(votes,
                    dojo.hitch(this, function(data, index) {
                        var answer = [data.question_label, (data.votes === null ? 0: data.votes), data.color];
                        results.push(answer);
              }));
              var id = this.id + "_chart";
              dojo.empty(this._chart);

              if (this.widgetChart !== null) {
                  this.widgetChart = null;
              }

              if (type === this.typeChart[0]) {
                  this.widgetChart = new EncuestamePieChart(id, results, 150);
              } else if(type == this.typeChart[1]) {
                  this.widgetChart = new EncuestamePieChart(id, results, 150);
              }

              this.render();
        });
        var params = {
            tweetPollId : id
        };
        this.getURLService().get('encuestame.service.list.VotesTweetPoll', params, load, error , dojo.hitch(this, function() {

        }));
     },

     /**
      *
      * @method displayNext
      */
     displayNext: function() {
        if (this._direction) {
          if (!domClass.contains(this._next, "invisible")) {
             this._next.onclick();
          } else {
              this._direction = !this._direction;
          }
        } else {
          if (!domClass.contains(this._previous, "invisible")) {
             this._previous.onclick();
          } else {
              this._direction = !this._direction;
          }
        }
     },

     /**
      *
      * @method _previous
      */
     _previousVotes: function(e) {
        this.stopEvent(e);
        this.start = this.start - 1;
        this.load();
        this._checkLinks();
     },

     /**
      *
      * @method _checkLinks
      */
     _checkLinks: function() {
       if (this.start >= 1) {
          dojo.removeClass(this._previous, 'invisible');
        } else {
          dojo.addClass(this._previous, 'invisible');
        }
     },

     /**
      *
      * @method _next
      */
     _nextVotes: function(e) {
        this.stopEvent(e);
        this.start = this.start + 1;
        this.load();
        this._checkLinks();
     },

      /***
       * Render.
       */
      render : function() {
          this.widgetChart._buildSeries();
          this.widgetChart.render();
      },

     /**
      *
      * @method
      */
     load: function() {
          var parent = this,
          load = dojo.hitch(this, function(data) {
            if ("success" in data) {
               var data_array = data.success.tweetPolls;
               var tp = data_array[0],
               question = tp.question.question_name;
               this._title.innerHTML = question;
               var id = tp.id;
               this._generateShareButton(id);
               this._callVotes(id, this.typeChart[1]);
               if (data_array.length == 1) {
                  dojo.addClass(this._next, 'invisible');
                  dojo.removeClass(this._previous, 'invisible');
               } else {
                dojo.removeClass(this._next, 'invisible');
               }
            } else {
               this.errorMessage("Error on load votes");
            }
          }),
          params = {
              max : this.max,
              typeSearch: 'ALL',
              start : this.start
          },
          // error handlers
          error = function(error) {
             this.errorMessage("Error on load votes");
          };
          // call the service
          this.getURLService().get('encuestame.service.list.listTweetPoll', params, load, error , dojo.hitch(this, function() {
          }), false);
     },

     /**
      *
      * @method
      */
     printStream: function(data) {
        // dojo.forEach(data, dojo.hitch(this, function(item, index) {
        //   var activityItem = new CommentItem({
        //       item: item
        //   });
        //   this._comments.appendChild(activityItem.domNode);
        // }));
     }
  });
});