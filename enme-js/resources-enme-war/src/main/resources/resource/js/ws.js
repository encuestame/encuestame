
/**
 * Created by jpicado on 02/08/14.
 */

var socket = new SockJS("/encuestame/portfolio");
var stompClient = Stomp.over(socket);
stompClient.connect({}, function(frame) {

});