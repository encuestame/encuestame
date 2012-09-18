define([ 
         "dojo/_base/declare",
		 "dijit/_WidgetBase", 
		 "dijit/_TemplatedMixin",
		 "dijit/_WidgetsInTemplateMixin",
		 "me/core/main_widgets/EnmeMainLayoutWidget",
		 "dojo/text!me/web/widget/menu/template/dashboardMenu.html" ],
		function(declare, _WidgetBase, _TemplatedMixin,
				_WidgetsInTemplateMixin, _main, template) {

			return declare([ _WidgetBase, _TemplatedMixin,
					_WidgetsInTemplateMixin ], {
				
				contextPath : "/",

				// template string.
				templateString : template,

			});
		});
