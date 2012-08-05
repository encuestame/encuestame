/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: open source social survey Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
dojo.provide("encuestame.org.core.shared.options.DateToClose");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require("dijit.form.TimeTextBox");
dojo.require("dijit.form.DateTextBox");
dojo.require("dijit.form.CheckBox");
dojo.require('encuestame.org.core.commons');
dojo.require('encuestame.org.core.shared.utils.ContextSupport');
dojo.require('encuestame.org.core.shared.utils.PublishSupport');

/**
 * Represent a option to set a date to close anything.
 */
dojo.declare(
    "encuestame.org.core.shared.options.DateToClose",
    [dijit._Widget,
     dijit._Templated,
     encuestame.org.core.shared.utils.ContextSupport,
     encuestame.org.core.shared.utils.PublishSupport],{

     /*
      *
      */
     templatePath: dojo.moduleUrl("encuestame.org.core.shared.options", "templates/dateToClose.html"),

     /*
      * Allow other widgets in the template.
      */
     widgetsInTemplate: true,

     default_time : "T15:00:00",

     scheduleWidget : null,

     scheduledDateWidget  : null,

     scheduledTimeWidget  : null,

     label : ENME.getMessage('widget_date_to_close'),

     /*
     *
     */
    options : { checked : false, time : null, date : null},


     postCreate : function(){
        //scheduled
         this.scheduleWidget = dijit.byId("schedule_"+this.id);
         this.scheduleWidget.onChange = dojo.hitch(this, function(event) {
             if (event) {
                 dojo.removeClass(this._scheduledTime, "defaultDisplayHide");
                 dojo.removeClass(this._scheduledDate, "defaultDisplayHide");
             } else {
                 dojo.addClass(this._scheduledTime, "defaultDisplayHide");
                 dojo.addClass(this._scheduledDate, "defaultDisplayHide");
             }
             this.scheduledDateWidget.set("value", new Date());
             this.scheduledTimeWidget.set("value", new Date());
             this.options.time = encuestame.date.getFormatTime(new Date(),  encuestame.date.timeFormat);
             this.options.date = encuestame.date.getFormatTime(new Date(), encuestame.date.dateFormat);
             this.options.checked = event;
             this.publish({});
             //console.debug("this.options", this.getOptions());
         });
         this.scheduledDateWidget = dijit.byId("scheduledDate_"+this.id);
         this.scheduledDateWidget.onChange = dojo.hitch(this, function(event){
             this.options.date = encuestame.date.getFormatTime(this.scheduledDateWidget.get("value"),
                       encuestame.date.dateFormat);
               this.publish({});
               //console.debug("this.options", this.getOptions());
         });
         //time widget.
         this.scheduledTimeWidget = dijit.byId("scheduledTime_"+this.id);
         this.scheduledTimeWidget.onChange = dojo.hitch(this, function(event) {
             this.options.time = encuestame.date.getFormatTime(this.scheduledTimeWidget.get("value"),
                         encuestame.date.timeFormat);
             this.publish({});
             //console.debug("this.options", this.getOptions());
         });
     },

     /*
     *
     */
    getOptions : function() {
        if (this.scheduleWidget.get('checked')){
           dojo.mixin(this.options, { checked : true });
        }
        return this.options;
    }


});
