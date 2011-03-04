<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<script type="text/javascript">
  dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardPie");
  dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardColumn2D");
  dojo.require("encuestame.org.core.commons.dashboard.chart.DashboardLine");
  dojo.require("encuestame.org.core.commons.dashboard.Dashboard");
  dojo.require("dojox.cometd");
  dojo.require("dojo.io.script");
  dojo.require("dojox.cometd");
  dojo.require("dojox.cometd.callbackPollTransport");
</script>
<script type="text/javascript">
dojo.addOnLoad(function()
{
    var cometd = dojox.cometd;
    console.debug("comentD",cometd);
    var config = {
            contextPath: '/encuestame'
        };

    function _connectionEstablished()
    {
        dojo.byId('body').innerHTML += '<div>CometD Connection Established</div>';
    }

    function _connectionBroken()
    {
        dojo.byId('body').innerHTML += '<div>CometD Connection Broken</div>';
    }

    function _connectionClosed()
    {
        dojo.byId('body').innerHTML += '<div>CometD Connection Closed</div>';
    }

    // Function that manages the connection status with the Bayeux server
    var _connected = false;
    function _metaConnect(message)
    {
        if (cometd.isDisconnected())
        {
            _connected = false;
            _connectionClosed();
            return;
        }

        var wasConnected = _connected;
        _connected = message.successful === true;
        if (!wasConnected && _connected)
        {
            _connectionEstablished();
        }
        else if (wasConnected && !_connected)
        {
            _connectionBroken();
        }
    }

    // Function invoked when first contacting the server and
    // when the server has lost the state of this client
    function _metaHandshake(handshake)
    {
        if (handshake.successful === true)
        {
            cometd.batch(function()
            {
                cometd.subscribe('/hello', function(message)
                {
                    dojo.byId('body').innerHTML += '<div>Server Says: ' + message.data.greeting + '</div>';
                });
                // Publish on a service channel since the message is for the server only
                cometd.publish('/service/hello', { name: 'World' });
            });
        }
    }

    // Disconnect when the page unloads
    dojo.addOnUnload(function()
    {
        cometd.disconnect(true);
    });

    var cometURL = location.protocol + "//" + location.host + config.contextPath + "/cometd";
    cometd.configure({
        url: cometURL,
        logLevel: 'debug'
    });

    cometd.addListener('/meta/handshake', _metaHandshake);
    cometd.addListener('/meta/connect', _metaConnect);

    cometd.handshake();
});

</script>
<div>
  <div id="body"></div>
  <!--
    <div dojoType="encuestame.org.core.commons.dashboard.Dashboard"></div>
    <div id="pie" dojoType="encuestame.org.core.commons.dashboard.chart.DashboardPie"></div>
    <div id="column" dojoType="encuestame.org.core.commons.dashboard.chart.DashboardColumn2D"></div>
    <div id="line" dojoType="encuestame.org.core.commons.dashboard.chart.DashboardLine"></div>
    -->
</div>
