<%@page import="org.encuestame.core.security.util.WidgetUtil"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<c:if test="${not empty message}">
      <div class="${message.infoType.css}">${message.message}</div>
</c:if>
<div class="defaultMarginWrapper">
    <div dojoType="encuestame.org.core.commons.social.SocialAccounts"
         domain="<%=WidgetUtil.getDomain(request)%>"
     ></div>
</div>
