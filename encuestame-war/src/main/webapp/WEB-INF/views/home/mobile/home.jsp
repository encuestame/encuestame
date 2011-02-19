<div id="main" dojoType="dojox.mobile.View" selected="true">
    <h1 dojoType="dojox.mobile.Heading">
        Settings
    </h1>
    <ul dojoType="dojox.mobile.EdgeToEdgeList">
        <li dojoType="dojox.mobile.ListItem" icon="http://docs.dojocampus.org/moin_static185/js/dojo/trunk/dojox/mobile/tests/images/i-icon-1.png">
            Coolness Mode
            <div class="mblItemSwitch" dojoType="dojox.mobile.Switch">
            </div>
        </li>
        <li dojoType="dojox.mobile.ListItem" icon="http://docs.dojocampus.org/moin_static185/js/dojo/trunk/dojox/mobile/tests/images/i-icon-2.png"
        rightText="mac" moveTo="disco">
            Disco Room
        </li>
        <li dojoType="dojox.mobile.ListItem" icon="http://docs.dojocampus.org/moin_static185/js/dojo/trunk/dojox/mobile/tests/images/i-icon-3.png"
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
