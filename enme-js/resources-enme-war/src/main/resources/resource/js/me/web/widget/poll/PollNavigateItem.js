define([
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "dijit/form/CheckBox",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/support/PanelWipe",
         "me/web/widget/poll/PollNavigateItemDetail",
         "me/core/enme",
         "dojo/text!me/web/widget/poll/templates/pollListItem.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                CheckBox,
                main_widget,
                PanelWipe,
                PollNavigateItemDetail,
                _ENME,
                 template) {
            return declare([ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin], {

          // template string.
           templateString : template,


        /*
         *
         */
        data : null,

        /*
         * i18n message for this widget.
         */
        i18nMessage : {
          poll_admon_poll_edit : _ENME.getMessage("poll_admon_poll_edit"),
          poll_admon_poll_preview : _ENME.getMessage("poll_admon_poll_preview"),
          poll_admon_poll_publish_options : _ENME.getMessage("poll_admon_poll_publish_options"),
          poll_admon_poll_embebed : _ENME.getMessage("poll_admon_poll_embebed"),
          poll_admon_poll_votes : _ENME.getMessage("poll_admon_poll_votes")
        },

        /*
         *
         */
        _standBy : null,

        /**
         * Poll detail widget reference.
         */
        widget_detail : null,

        /**
         * Post create cycle life.
         */
        postCreate : function() {
            var panel = new PanelWipe(this._more, null, null, 390);
            //add event on click edit link
            panel.connect(this._edit, dojo.hitch(this, this._callEditInfo));
            if (this._preview) {
                dojo.connect(this._preview, "onclick", dojo.hitch(this, function(event) {
                    var url = _ENME.pollDetailContext(this.data.id, this.data.question.slug);
                    window.open(url, '_blank');
                    //console.log("this.", this.data);
                }));
            }
            panel.preWipe = dojo.hitch(this, function() {
                dojo.addClass(this.domNode, "selected-row");
            });
            panel.postWipe =  dojo.hitch(this, function() {
                dojo.removeClass(this.domNode, "selected-row");
            });
            //this._standBy = dijit.byId("standby_"+this.id);
            this.widget_detail = new PollNavigateItemDetail(
                {
                  data : this.data ,
                  parentWidget : this,
                  label : _ENME.getMessage('poll_admon_poll_options')
                });
            dojo.addClass(this.widget_detail.domNode, "hidden");
            dojo.place(this.widget_detail.domNode, this._more);
            //set votes
            this._votes.innerHTML = this.data.total_votes == null ? 0 : this.data.total_votes;
            //set date
            this._date.innerHTML = _ENME.fromNow(this.data.creation_date);
        },

        /**
         * Call Edito Info.
         */
        _callEditInfo : function() {
            var load = dojo.hitch(this, function(data) {
                if ("success" in data) {
                    dojo.removeClass(this.widget_detail.domNode, "hidden");
                    this.widget_detail.setResults(data.success.poll);
                } else {
                    this._showErrorMessage(error);
                }
            });
            var error = dojo.hitch(this, function(error) {
                this._showErrorMessage(error);
            });
            var params = {
                    id : this.data.id
            };
            dojo.addClass(this.widget_detail.domNode, "hidden");
            this.getURLService().get('encuestame.service.list.poll.detail', params, load, error , dojo.hitch(this, function() {

            }));
        }
    });
});