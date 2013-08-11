<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
require(["dojo", "dojo/request/notify", "me/core/enme"],
    function(dojo, notify, _ENME) {

    notify("start", function(){
    // Do something when the request queue has started
    // This event won't fire again until "stop" has fired
      //console.log("NOTIFYYYY start", arguments);
      dojo.subscribe("/encuestame/status/start", this, dojo.hitch(this, function(_f) {
          _f();
      }));
    });

    notify("send", function(response, cancel) {
    // Do something before a request has been sent
    // Calling cancel() will prevent the request from
    // being sent
      //console.log("NOTIFYYYY send", arguments);
      dojo.subscribe("/encuestame/status/sent", this, dojo.hitch(this, function(_f) {
          _f();
      }));
    });

    notify("load", function(response) {
    // Do something when a request has succeeded
      //console.log("NOTIFYYYY load", arguments);
      dojo.subscribe("/encuestame/status/load", this, dojo.hitch(this, function(_f) {
          _f();
      }));
    });

    notify("error", function(error){
    // Do something when a request has failed
      //console.log("NOTIFYYYY error", arguments);
      dojo.subscribe("/encuestame/status/error", this, dojo.hitch(this, function(_f) {
          _f();
      }));
    });

    notify("done", function(responseOrError) {
    // Do something whether a request has succeeded or failed
      //console.log("NOTIFYYYY done", arguments);
      dojo.subscribe("/encuestame/status/done", this, dojo.hitch(this, function(_f) {
          _f();
      }));
    // if (responseOrError instanceof Error) {
    //   // Do something when a request has failed
    // } else {
    //   // Do something when a request has succeeded
    // }
    });

    notify("stop", function() {
     // console.log("NOTIFYYYY stop", arguments);
      dojo.subscribe("/encuestame/status/stop", this, dojo.hitch(this, function(_f) {
          _f();
      }));
    // Do something when all in-flight requests have finished
    });
});