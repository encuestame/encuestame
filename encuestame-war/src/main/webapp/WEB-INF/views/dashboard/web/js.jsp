<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<enme:require>
encuestame.org.core.commons.dashboard.DashboardWrapper
encuestame.org.core.gadget.Activity
encuestame.org.core.gadget.Comments
encuestame.org.core.gadget.TweetPollVotes
</enme:require>
<script type="text/javascript">
    dojo.require("dijit.dijit");
    dojo.require("dojox.widget.Portlet");
    dojo.require("dojox.widget.FeedPortlet");
    dojo.require("dojox.layout.GridContainer");
    dojo.require("dojox.widget.Calendar");
    dojo.require("encuestame.org.core.commons.dialog.ModalBox");
    dojo.require("encuestame.org.core.commons.social.SocialAccountPicker");
</script>