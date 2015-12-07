define( [
         "dojo/_base/declare",
         "dijit/_WidgetBase",
         "dijit/_TemplatedMixin",
         "dijit/_WidgetsInTemplateMixin",
         "me/core/main_widgets/EnmeMainLayoutWidget",
         "me/web/widget/publish/PublishSupport",
         "me/core/enme",
         "dojo/text!me/web/widget/options/templates/publish_options.html" ],
        function(
                declare,
                _WidgetBase,
                _TemplatedMixin,
                _WidgetsInTemplateMixin,
                main_widget,
                PublishSupport,
                _ENME,
                 template ) {
            return declare( [ _WidgetBase, _TemplatedMixin, main_widget, _WidgetsInTemplateMixin ], {

          /**
           * Template string.
           * @property templateString
           */
          templateString: template,

           /**
            * Create a new publish option layout
            * @method createOptions
            */
           createOptions: function( data, context, dialog ) {

                //Console.log('data', data);
                var publishWidget = new PublishSupport({
                            context: context,
                            item: {
                                id: data.id,
                                name: data.question.question_name,
                                url: data.short_url
                            },
                            dialogContext: dialog
                        });
                publishWidget.removeTitle();
                dojo.empty( this._publish );
                this._publish.appendChild( publishWidget.domNode );
           }

    });
});
