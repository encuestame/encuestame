<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="mobile-setup-header">
    <div class="mobile-title">
        <spring:message code="setup.header"></spring:message>
    </div>
</div>
<div class="mobile-header-description">
    <spring:message code="setup.header.des"></spring:message>
    <%=EnMePlaceHolderConfigurer.getProperty("app.version")%>
</div>

