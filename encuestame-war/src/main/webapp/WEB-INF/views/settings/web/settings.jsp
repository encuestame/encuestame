<%@page import="org.encuestame.mvc.util.WidgetUtil"%>
<script type="text/javascript">
    dojo.require("encuestame.org.core.commons.social.SocialAccounts");
</script>
<div class="defaultMarginWrapper">
    <div dojoType="encuestame.org.core.commons.social.SocialAccounts"
        domain="<%=WidgetUtil.getDomain(request)%>"
     ></div>
</div>
</div>
