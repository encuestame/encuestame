dojo.provide("encuestame.org.core.shared.utils.CountDown");

dojo.require("dijit._Templated");
dojo.require("dijit._Widget");
dojo.require('dojox.timing');
dojo.require('encuestame.org.core.commons');

dojo.declare(
    "encuestame.org.core.shared.utils.CountDown",
    [dijit._Widget, dijit._Templated],{

      templatePath: dojo.moduleUrl("encuestame.org.core.shared.utils", "template/countDown.html"),

      montharray : [],

      timer : null,

      limitDate : {
          day : null,
          month : null,
          year : null,
          hour : null,
          minutes : null,
          seconds : null},

      dday : null,

      dmonth : null,

      dhour : null,

      dmin : null,

      dsec : null,

      theyear : null,

      /*
       * post create.
       */
      postCreate : function(){
          //start time
          console.debug("Limit Date", this.limitDate);
          dojo.addOnLoad(dojo.hitch(this, function(){
              this.loadTimer();
          }));
          this.montharray = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"); //TOD: move to common
      },

      loadTimer : function() {
          var father = this;
          this.timer = new dojox.timing.Timer(1);
          this.timer.onTick = function() {
              father.countdown();
          };
          this.timer.onStart = function() {
          };
          this.timer.start();
      },

      /*
       * hide the coutndown.
       */
      hide : function(){
          console.info("hide the countdown.");
          dojo.destroy(this.domNode);
      },

      /*
       *
       */
      countdown : function() {
          this.theyear = this.limitDate.year;
          var themonth = this.limitDate.month;
          var theday =  this.limitDate.day;
          var thehour = this.limitDate.hour;
          var theminute = this.limitDate.minutes;
          var thesecconds = this.limitDate.seconds;

          //right now.
          var today = new Date();
          var todayy = today.getYear();
          if (todayy < 1000) {
              todayy += 1900;
          }
          var todaym  = today.getMonth();
          this.dmonth = todaym;
          var todayd = today.getDate();
          var todayh = today.getHours();
          var todaymin = today.getMinutes();
          var todaysec = today.getSeconds();
          var todaystring = this.montharray[todaym]+" "+todayd+", "+todayy+" "+todayh+":"+todaymin+":"+todaysec;
          var futurestring = this.montharray[themonth-1]+" "+theday+", "+this.theyear+" "+thehour+":"+theminute+":"+thesecconds;
          //console.debug(todaystring, futurestring);
          var dd = Date.parse(futurestring) - Date.parse(todaystring);
          this.dday = Math.floor(dd / (60 * 60 * 1000 * 24) * 1);
          this.dhour = Math.floor((dd % (60 * 60 * 1000 * 24)) / (60 * 60 * 1000) * 1);
          if (this.dhour < 10) {
              this.dhour = "0"+this.dhour;
          }
          this.dmin = Math.floor(((dd%(60*60*1000*24))%(60*60*1000))/(60*1000)*1);

          if (this.dmin < 10) {
              this.dmin = "0"+this.dmin;
          }
          this.dsec = Math.floor((((dd % (60 * 60 * 1000 * 24)) % (60 * 60 * 1000)) % (60 * 1000)) / 1000 * 1);

          if(this.dday == 0 && this.dhour == 0 && this.dmin == 0 && this.dsec == 1 ) {
              this._countdown.innerHTML = "TIME OFF";
              this.timer.stop();
              this.timeOffAction();
          } else {
              var text = this.dday+ " Days | "+this.dhour+":"+this.dmin+":"+this.dsec;
              this._countdown.innerHTML = text;
          }
      },

      /*
       * override.
       */
      timeOffAction: function(){
          // override.
      }
});