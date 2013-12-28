<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

try {

    <c:if test="${logged}">
        socket = new Websocket({
          url : '<%=request.getContextPath()%>/enme-ws'
        });

        socket.debug();

        socket.connect({
          notifications_updates : {
            type : 'subscribe',
            suffix : false,
            callback : function(data) {
              dojo.publish('/notifications/service/messages', data);
            },
            channel : '/topic/notification-updates.*'
          }
        });

        _ENME.setActivity(socket);
        socket.subscribe({
             type : 'subscribe',
             suffix : false,
             callback : function(data) {
               console.error("/app/notifications", data);
             },
             channel : '/app/notifications'
        });
    </c:if>

    Offline.options = {checks: {image: {url: 'my-image.gif'}}};
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

} catch(error) {
    console.log('error websocket', error);
}