dojo.addOnLoad(function() {
        // More Info: http://cometd.org/node/49

        function _connectionEstablished() {
            dojo.byId('body').innerHTML += '<div>CometD Connection Established</div>';
        }

        function _connectionBroken() {
            dojo.byId('body').innerHTML += '<div>CometD Connection Broken</div>';
        }

        function _connectionClosed() {
            dojo.byId('body').innerHTML += '<div>CometD Connection Closed</div>';
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
                    cometd.subscribe('/not', function(message) {
                        //console.debug("notification commet message", message);
                        dojo.publish("/encuestame/notifications/update/status",
                                     [message.data.totalNot, message.data.totalNot]);
                    });
                });
            }
        }

        // Disconnect when the page unloads
        dojo.addOnUnload(function() {
            cometd.disconnect(true);
        });

        var cometURL = location.protocol + "//" + location.host
                + config.contextPath + "/cometd";
        cometd.configure({
            url : cometURL,
            logLevel : 'warn'
        });

        cometd.addListener('/meta/handshake', _metaHandshake);
        cometd.addListener('/meta/connect', _metaConnect);

        cometd.handshake();
});
