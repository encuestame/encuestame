define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/social/SocialAccountsSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/support/templates/social-item-filters.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                SocialAccountsSupport,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, SocialAccountsSupport, _WidgetsInTemplateMixin ], {

        /*
         * Template
         */
        templateString: template,

          /*
          * Item data.
          */
           data: null,

           /*
            * Selected flag (false by default)
            */
           selected: false,
            /*
             * Is triggered before render
             */
           postMixInProperties: function() {

              // If the social link is not valid, reset a fake social image.
             this.data.picture_url = _ENME.fakeImage("24", this.data.picture_url || "");
             this.data.image_social = _ENME.shortPicture( this.data.type_account );
           },

            /*
             *
             */
            postCreate: function() {
                dojo.connect( this.domNode, "onclick", this, dojo.hitch( this, function() {
                    this.selected = !this.selected;
                    if ( this.selected ) {
                        this.markAsSelected();
                    } else {
                        this.unSelected();
                    }
                    dojo.publish("/encuestame/social/picker/counter/reload");
                }) );
            },

            /*
             * Set as selected.
             */
            markAsSelected: function() {
                dojo.addClass( this.domNode, "selected");
                this.selected = true;
            },

            /*
             * Set at un selected.
             */
            unSelected: function() {
                dojo.removeClass( this.domNode, "selected");
                this.selected = false;
            }

    });
});
