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

      dday : null,

      dhour : null,

      dmin : null,

      dsec : null,

      theyear : null,

      /*
       * post create.
       */
      postCreate : function(){
          //start time
          dojo.addOnLoad(dojo.hitch(this, function(){
              this.loadTimer();
          }));
          this.countdown(2012,12,25); //TODO: set real date.
          this.montharray = new Array("Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec");
      },

      loadTimer : function(){
          var father = this;
          this.timer = new dojox.timing.Timer(1);
          this.timer.onTick = function() {
              father.countdown(2011, 7 , 9);
          };
          this.timer.onStart = function() {
          };
          this.timer.start();
      },

      /*
       *
       */
      countdown : function(yr, m, d) {
          this.theyear = yr;
          var themonth = m;
          var theday = d;
          var today = new Date();
          var todayy = today.getYear();
          if (todayy < 1000) {
              todayy += 1900;
          }
          var todaym  =today.getMonth();
          var todayd = today.getDate();
          var todayh = today.getHours();
          var todaymin = today.getMinutes();
          var todaysec = today.getSeconds();
          var todaystring = this.montharray[todaym]+" "+todayd+", "+todayy+" "+todayh+":"+todaymin+":"+todaysec;
          futurestring = this.montharray[m-1]+" "+d+", "+yr;
          dd = Date.parse(futurestring)-Date.parse(todaystring);
          this.dday = Math.floor(dd/(60*60*1000*24)*1);
          this.dhour= Math.floor((dd%(60*60*1000*24))/(60*60*1000)*1);
          if (this.dhour < 10) {
              this.dhour = "0"+this.dhour;
          }
          this.dmin = Math.floor(((dd%(60*60*1000*24))%(60*60*1000))/(60*1000)*1);
          if (this.dmin < 10) {
              this.dmin = "0"+this.dmin;
          }
          this.dsec = Math.floor((((dd%(60*60*1000*24))%(60*60*1000))%(60*1000))/1000*1);
          if(this.dday == 0 && this.dhour == 0 && this.dmin == 0 && this.dsec ==1 ) {
              this._countdown.innerHTML = "TIME OFF";
              this.timer.stop();
          } else {
              var text = this.dday+ " Days | "+this.dhour+":"+this.dmin+":"+this.dsec;
              this._countdown.innerHTML = text;
          }
      }
});