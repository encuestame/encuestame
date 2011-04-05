dojo.require("dojox.cometd");
dojo.require("dojox.cometd.timestamp");
dojo.require("dojox.cometd.ack");
dojo.require("dojox.cometd.reload");

dojo.addOnLoad(function() {

        //more info: http://cometd.org/node/49


        function _connectionEstablished() {
            console.info('CometD Connection Established');
        }

        function _connectionBroken() {
            console.info('CometD Connection Broken');
        }

        function _connectionClosed() {
            console.info('CometD Connection Closed');
        }

        // Function that manages the connection status with the Bayeux
        // server
        var _connected = false;
        function _metaConnect(message) {
            if (cometd.isDisconnected()) {
                _connected = false;
                _connectionClosed();
                return;
            }

            var wasConnected = _connected;
            _connected = message.successful === true;
            if (!wasConnected && _connected) {
                //_connectionEstablished();
            } else if (wasConnected && !_connected) {
                _connectionBroken();
            }
        }

        // Function invoked when first contacting the server and
        // when the server has lost the state of this client
        function _metaHandshake(handshake) {
            if (handshake.successful === true) {
                cometd.batch(function() {
                    cometd.subscribe('/service/notification/status', function(message) {
                        console.debug("notification commet message OLD", message);
                        dojo.publish("/encuestame/notifications/update/status",
                                     [message.data.totalNot, message.data.totalNot]);
                    });
                    cometd.subscribe('/service/tweetpoll/autosave', function(message) {
                        console.debug("autosave", message);
                        dojo.publish("/encuestame/tweetpoll/autosave/status",[message]);
                    });
                });
            }
        }

        var cometURL = location.protocol + "//" + location.host
                + config.contextPath + "/cometd";
        cometd.configure({
          url : cometURL,
            logLevel : 'info',
            jsonDebug : true
        });

        cometd.addListener('/meta/handshake', _metaHandshake);
        cometd.addListener('/meta/connect', _metaConnect);

        cometd.handshake();
});


// disconnect when the page unloads
dojo.addOnUnload(function() {
    dojox.cometd.reload();
    cometd.disconnect(true);
});