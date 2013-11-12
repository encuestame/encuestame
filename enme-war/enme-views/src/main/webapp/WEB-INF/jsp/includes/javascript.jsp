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
// ], function(dojo, declare, parser, ready, Websocket, LoginDialog, _ENME)
	'me/support/Offline',
    "me/web/widget/signup/LoginDialog",
    "me/core/enme"
], function(dojo, declare, parser, ready, Websocket, Offline, LoginDialog, _ENME) {
		ready(function(){
//         Call the parser manually so it runs after our widget is defined, and page has finished loading
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
                 socket = new Websocket({
                  url : '<%=request.getContextPath()%>/enme-ws'
                });

                socket.debug();

                socket.connect({
                  notifications_updates : {
                    type : 'subscribe',
                    suffix : false,
                    callback : function(data) {
                    console.log('updates 2', data);
                    dojo.publish('/notifications/service/messages', data);
                    },
                    channel : '/topic/notification-updates.*'
                  }
                });

                _ENME.setActivity(socket);
                 var offline = new Offline({
                    "up" : function() {
                        socket.reconnect();
                        console.log("1");
                    },
                    "down" : function () {
                        socket.disconnect();
                        console.log("2");
                    }
                });

                _ENME.setOffline(offline);
                 // socket.subscribe({
                //     type : 'subscribe',
                //     suffix : false,
                //     callback : function(data) {
                //       console.error("app/notificationsws", data);
                //     },
                //     channel : '/app/notifications-ws'
                // });


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
