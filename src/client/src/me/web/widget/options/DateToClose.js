define( [
 "dojo/_base/declare",
 "dijit/_WidgetBase",
 "dijit/_TemplatedMixin",
 "dijit/_WidgetsInTemplateMixin",
 "dijit/form/CheckBox",
 "dijit/form/TimeTextBox",
 "dijit/form/DateTextBox",
 "dijit/registry",
 "me/third-party/moment",
 "me/core/main_widgets/EnmeMainLayoutWidget",
 "me/core/support/PublishSupport",
 "me/core/support/ContextSupport",
 "me/core/enme",
 "dojo/text!me/web/widget/options/templates/dateToClose.html" ],
function(
    declare,
    _WidgetBase,
    _TemplatedMixin,
    _WidgetsInTemplateMixin,
    CheckBox,
    TimeTextBox,
    DateTextBox,
    registry,
    moment,
    main_widget,
    PublishSupport,
    ContextSupport,
    _ENME,
     template ) {
return declare( [ _WidgetBase, _TemplatedMixin, main_widget, PublishSupport, ContextSupport, _WidgetsInTemplateMixin ], {

	  // Template string.
	  templateString: template,

	 /*
	  * Allow other widgets in the template.
	  */
	 widgetsInTemplate: true,

	 default_time: "T15:00:00",

    default_date: new Date(),

	 scheduleWidget: null,

	 scheduledDateWidget: null,

	 scheduledTimeWidget: null,

	 label: _ENME.getMessage( "widget_date_to_close" ),

	 /*
	 *
	 */
	options: {
		checked: false,
		time: null,
		date: null
	},

    /**
     *  Post create life cycle.
     */
     postCreate: function() {

        // Creating objects
        this.scheduledDateWidget  = new DateTextBox({
            value: this.default_date
        });
        this._scheduledDate_widget.appendChild( this.scheduledDateWidget.domNode );

        //Scheduled
         this.scheduleWidget = registry.byId("schedule_" + this.id );
         this.scheduleWidget.onChange = dojo.hitch( this, function( event ) {
             if ( event ) {
                 dojo.removeClass( this._scheduledTime, "defaultDisplayHide");
                 dojo.removeClass( this._scheduledDate, "defaultDisplayHide");
             } else {
                 dojo.addClass( this._scheduledTime, "defaultDisplayHide");
                 dojo.addClass( this._scheduledDate, "defaultDisplayHide");
             }
             var currentTime = new Date();

             //This.scheduledDateWidget.set("value", currentTime);
             this.scheduledTimeWidget.set("value", currentTime );
             this.options.time = _ENME.getFormatTime( currentTime,  _ENME.timeFormat );
             this.options.time_millis = currentTime.getTime();
             this.options.date = _ENME.getFormatTime( currentTime, _ENME.dateFormat );
             this.options.date_millis = currentTime.getTime();
             this.options.checked = event;
             this.publish({}); ///TODO: ???????

         });

         //This.scheduledDateWidget = registry.byId("scheduledDate_"+this.id);
         this.scheduledDateWidget.onChange = dojo.hitch( this, function( event ) {
             var selectedDate = this.default_date;
             this.options.date = _ENME.getFormatTime( selectedDate, _ENME.dateFormat );
             this.options.date_millis = selectedDate.getTime();
             this.publish({}); ///TODO: ???????

         });

         //Time widget.
         this.scheduledTimeWidget = registry.byId("scheduledTime_" + this.id );
         this.scheduledTimeWidget.onChange = dojo.hitch( this, function( event ) {
             var selectedTime = this.scheduledTimeWidget.get("value");
             this.options.time = _ENME.getFormatTime( selectedTime, _ENME.timeFormat );
             this.options.time_millis = selectedTime.getTime();
             this.publish({}); ///TODO: ???????
         });
     },

    /**
     *
     * @returns {*}
     */
    getOptions: function() {
        if ( this.scheduleWidget.get( "checked" ) ) {
           dojo.mixin( this.options, {
	           checked: true,
	           complete_date: this.options.date_millis + this.options.time_millis
           });
        }
        return this.options;
    },

	/**
	 * Check if the selected date is valid
	 */
	isValid: function() {
		var currentDate = new Date().getTime();
        if ( this._scheduledCheckTime.checked ) {
            return ( this.options.complete_date != null &&
                    currentDate < this.options.complete_date );
        } else {
            return true;
        }
	}

});
});
