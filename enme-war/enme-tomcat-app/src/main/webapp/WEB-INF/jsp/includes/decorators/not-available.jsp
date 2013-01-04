<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="vote-page">
    <header class="vote-logo">
        <%@ include file="/WEB-INF/layouts/logo.jsp"%>
    </header>
    <div class="enme-mobile-main-message">
        <spring:message code="enme.not.available" />
        <a href="<%=request.getContextPath()%>/"> <spring:message
                code="enme.return.home" />
        </a>
    </div>
</div>