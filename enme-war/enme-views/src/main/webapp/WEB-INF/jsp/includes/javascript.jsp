<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-config.jsp"%>

<script src="<%=request.getContextPath()%>/resources/js/commons.js"></script>
<script  src="<%=request.getContextPath()%>/resources/js/dojo/dojo.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/run.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/enme.chart.js"></script>

<script>
require([
    "dojo",
    "dojo/_base/declare",
    "dojo/parser",
    "dojo/ready",
    'me/support/Websocket',
    "me/web/widget/signup/LoginDialog",
    "me/core/enme"
], function(dojo, declare, parser, ready, Websocket, LoginDialog, _ENME) {
    ready(function(){
        // Call the parser manually so it runs after our widget is defined, and page has finished loading
        <%@ include file="/WEB-INF/jsp/includes/decorators/enme-init.jsp"%>
        dojo.subscribe('/encuestame/login/show', this, dojo.hitch(this, function(expired_session) {
            var login = new LoginDialog({
                expired_session: expired_session,
                action_url : '<%=request.getContextPath()%>/user/signin/authenticate'
            });
        }));
        //parse all widgets.
        parser.parse();
        console.log("Websocket", Websocket);
        <c:if test="${!detectedDevice}">
            try {
                var sock = new SockJS('<%=request.getContextPath()%>/enme-ws');            
                var stompClient = Stomp.over(sock);
                stompClient.connect('', '', function(frame) {
                      console.warn('Connected ' + frame);
                      var userName = frame.headers['user-name'],
                      queueSuffix = frame.headers['queue-suffix'];                  
                      //self.username("demo10");
                      stompClient.subscribe("/app/notifications-ws", function(message) {
                        //console.error("app/notificationsws", message);
                        //self.portfolio().loadPositions(JSON.parse(message.body));
                      });

                      stompClient.subscribe("/topic/notification-updates.*", function(message) {
                            console.log('updates', JSON.parse(message.body));
                      });

                      stompClient.send("/app/notifications-confirm", {}, {
                        username: "demo10"
                      });
                      // stompClient.subscribe("/topic/price.stock.*", function(message) {
                      //   console.log("ic/price.stock.*", message);
                      //   self.portfolio().processQuote(JSON.parse(message.body));
                      // });
                      // stompClient.subscribe("/queue/position-updates" + queueSuffix, function(message) {
                      //   self.pushNotification("Position update " + message.body);
                      //   self.portfolio().updatePosition(JSON.parse(message.body));
                      // });
                      stompClient.subscribe("/notifications-ws/errors" + "/hola", function(message) {
                        //self.pushNotification("Error " + message.body);
                        console.error("message error", message);
                      });
                    }, function(error) {
                        console.log("STOMP protocol error " + error);
                });
                // stompClient.debug = function(str) {
                //   // append the debug log to a #debug div somewhere in the page using JQuery:
                //   console.log("debug", str);
                // };                
            } catch(error) {
                console.log('error websocket', error);
            }
    
        </c:if>
    });
});
<%@ include file="/WEB-INF/jsp/includes/decorators/dojo-notify.jsp"%>
</script>
<%@ include file="/WEB-INF/jsp/includes/decorators/log-js.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/analytics.jsp"%>
