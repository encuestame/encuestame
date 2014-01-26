<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">    
    <div class="not-available-message">
        <spring:message code="enme.not.available" />
        <a href="<%=request.getContextPath()%>/"> <spring:message
                code="enme.return.home" />
        </a>
    </div>
</div>