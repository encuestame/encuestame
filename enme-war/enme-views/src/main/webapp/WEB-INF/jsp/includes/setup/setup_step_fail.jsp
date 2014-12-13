<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<h3>
    <spring:message code="setup.fail.title"></spring:message>
</h3>
<hr/>
<p>
    <spring:message code="setup.fail.info"></spring:message>
</p>
<div class="alert alert-info">
    <a target="_blank" href="http://encuestame.org/wiki/display/DOC/Installation+commons+errors">
        http://encuestame.org/wiki/display/DOC/Installation+commons+errors
    </a>
</div>
<c:if test="${not empty message}">
    <div class="alert alert-${message.infoType.css}">${message.message}</div>
</c:if>
<div class="progress  progress-warning progress-striped hidden">
    <div class="bar" style="width: 45%;"></div>
</div>