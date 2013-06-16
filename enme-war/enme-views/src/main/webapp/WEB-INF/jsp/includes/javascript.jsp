<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script>
         var dojoConfig= {
            baseUrl: '<%=request.getContextPath()%>/resources/js/',
            packages: [
                       { name: "dojo", location: "dojo" },
                       { name: "dijit", location: "dijit" },
                       { name: "dojox", location: "dojox" },
                       { name: "me", location: "me" }
            ],
            has: {
                    'dojo-firebug': false,
                    'dojo-debug-messages': false
                },
            // useCommentedJson : false,
            parseOnLoad : false,
            isDebug : 0,
            tlmSiblingOfDojo : false,
            async : true
            };
</script>
<script src="<%=request.getContextPath()%>/resources/js/commons.js"></script>
<script  src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/me/run.js"></script>


<script src="<%=request.getContextPath()%>/resources/js/enme.chart.js"></script>

<script>
var config = {
    contextPath: '${pageContext.request.contextPath}'
};
require([
    "dojo",
    "dojo/_base/declare",
    "dojo/parser",
    "dojo/ready",
    'me/activity/Activity',
    "me/core/enme"
], function(dojo, declare, parser, ready, Activity, _ENME) {
    ready(function(){
        // Call the parser manually so it runs after our widget is defined, and page has finished loading
        _ENME.init({
            contextPath: '<%=request.getContextPath()%>',
            domain : '<%=WidgetUtil.getDomain(request)%>',
            suggest_limit : 10,
            delay : 1800000,
            debug : <%=EnMePlaceHolderConfigurer.getProperty("application.debug.mode")%>,
            message_delay : 5000,
            activity : {
                url : "<%=WidgetUtil.getDomain(request)%>/activity",
                logLevel : "<%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.levelDebug")%>",
                maxConnections : <%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.maxConnections")%>,
                maxNetworkDelay : <%=EnMePlaceHolderConfigurer.getProperty("not.main.activity.maxNetworkDelay")%>,
                delay : <%=EnMePlaceHolderConfigurer.getProperty("not.main.delay")%>,
                limit : <%=EnMePlaceHolderConfigurer.getProperty("not.main.limit")%>
            },
            tp_a : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.allowed")%>,
            tp_hr : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.hr")%>,
            tp_minsoa : <%=EnMePlaceHolderConfigurer.getProperty("tp.min.answer.minsoa")%>
        });
        //parse all widgets.
        parser.parse();
        <c:if test="${!detectedDevice}">
            // initialize the activity support
            var _E = _ENME.config('activity');
            //FUTURE: Modernizr.websockets
            var  activity = new Activity(_E, false);
            activity.connect();
            _ENME.setActivity(activity);
        </c:if>
        // reference, it' not possible add to the build.
        //dojo.require("dojox.fx");
        //dojo.require( "dojo.date.locale" );
        //dojo.require('dojox.timing');
    });
});

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

</script>
<!--
<script src="<%=request.getContextPath()%>/resources/js/default.js"></script>
 -->
<script type="text/javascript">
/**
 * default log.
 */
window.log = function () {
    log.history = log.history || [];
    log.history.push(arguments);
    if (this.console) {
        arguments.callee = arguments.callee.caller;
        var a = [].slice.call(arguments);
        (typeof console.log === "object" ? log.apply.call(console.log, console, a) : console.log.apply(console, a));
    }
};
(function (b) {function c() {}
    for (var d = "assert,count,debug,dir,dirxml,error,exception,group,groupCollapsed,groupEnd,info,log,timeStamp,profile,profileEnd,time,timeEnd,trace,warn".split(","), a; a = d.pop();) {
        b[a] = b[a] || c;
    }
})((function () {
    try {
        return window.console;
    } catch (err) {
        return window.console = {};
    }
})());
</script>
<%-- <script src="<%=request.getContextPath()%>/resources/js/encuestame/encuestame.js"></script> --%>
<%--<%=WidgetUtil.getAnalytics("analytics.inc")%>--%>