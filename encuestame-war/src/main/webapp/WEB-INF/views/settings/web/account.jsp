<script type="text/javascript">
    dojo.require("dijit.layout.TabContainer");
    dojo.require("dijit.layout.ContentPane");
    dojo.require("encuestame.org.core.shared.utils.AccountPicture");
    dojo.require("encuestame.org.core.commons.profile.Profile");
    dojo.require("encuestame.org.core.commons.profile.UploadProfilePicture");
</script>
<div class="defaultMarginWrapper">
    <h1>Settings Configuration</h1>
    <div style="width: 900px; height: 500px;">
        <div dojoType="dijit.layout.TabContainer"
            style="width: 100%; height: 100%;">
            <div dojoType="dijit.layout.ContentPane" title="Your Account"
                selected="true">
                <div dojoType="encuestame.org.core.commons.profile.Profile"></div>
            </div>
            <div dojoType="dijit.layout.ContentPane" title="Picture" style="border: 0 none;">
                 <div dojoType="encuestame.org.core.commons.profile.UploadProfilePicture" source="${userAccount.pictureSource}" username="${username}"></div>
           </div>
           <div dojoType="dijit.layout.ContentPane" title="Password" style="border: 0 none;"
                closable="true">
                Change password support.
           </div>
           <div dojoType="dijit.layout.ContentPane" title="Email and Notifications" style="border: 0 none;"
                closable="true">
                Email Notifications.
            </div>
            <div dojoType="dijit.layout.ContentPane" title="Public Profile" style="border: 0 none;"
                closable="true">
                Public Profile Configuration
            </div>
        </div>
    </div>
</div>
