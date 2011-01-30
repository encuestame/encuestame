<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
  <style type="text/css">
      @import "<%=request.getContextPath()%>/resource/js/dojox/mobile/themes/iphone/iphone.css";
  </style>
  <div>
    <script>
        // Load the basic mobile widgetry and support code.
        dojo.require("dojox.mobile");

        // Load the lightweight parser.  dojo.parser can also be used, but it requires much more code to be loaded.
        dojo.require("dojox.mobile.parser");

        // Load the compat layer if the incoming browser isn't webkit based
        dojo.requireIf(!dojo.isWebKit, "dojox.mobile.compat");
    </script>

    <div id="main" dojoType="dojox.mobile.View" selected="true">
        <h1 dojoType="dojox.mobile.Heading">
            Settings
        </h1>
        <ul dojoType="dojox.mobile.EdgeToEdgeList">
            <li dojoType="dojox.mobile.ListItem" icon="/moin_static185/js/dojo/trunk/dojo/../dojox/mobile/tests/images/i-icon-1.png">
                Coolness Mode
                <div class="mblItemSwitch" dojoType="dojox.mobile.Switch">
                </div>
            </li>
            <li dojoType="dojox.mobile.ListItem" icon="/moin_static185/js/dojo/trunk/dojo/../dojox/mobile/tests/images/i-icon-2.png"
            rightText="mac" moveTo="disco">
                Disco Room
            </li>
            <li dojoType="dojox.mobile.ListItem" icon="/moin_static185/js/dojo/trunk/dojo/../dojox/mobile/tests/images/i-icon-3.png"
            rightText="AcmePhone" moveTo="disco">
                Carrier
            </li>
        </ul>
    </div>
    <div id="disco" dojoType="dojox.mobile.View">
        <h1 dojoType="dojox.mobile.Heading">
            Hello
        </h1>
        <ul dojoType="dojox.mobile.EdgeToEdgeList">
            <ul dojoType="dojox.mobile.EdgeToEdgeList">
                <li dojoType="dojox.mobile.ListItem" moveTo="main">
                    I'm a square, man.
                </li>
                <li dojoType="dojox.mobile.ListItem" moveTo="main">
                    Leave Disco Room
                </li>
            </ul>
    </div>

  </div>
<%@ include file="/WEB-INF/jsp/includes/endBody.jsp" %>