<%@page import="org.encuestame.mvc.util.WidgetUtil"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<script type="text/javascript">
    dojo.require("encuestame.org.core.commons.social.SocialAccounts");
</script>
<c:if test="${not empty message}">
      <div class="${message.infoType.css}">${message.message}</div>
</c:if>
<div class="defaultMarginWrapper">
    <div dojoType="encuestame.org.core.commons.social.SocialAccounts"
        domain="<%=WidgetUtil.getDomain(request)%>"
     ></div>
</div>
</div>
