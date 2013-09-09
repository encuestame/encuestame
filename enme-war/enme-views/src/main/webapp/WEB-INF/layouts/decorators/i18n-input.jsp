<%@ include file="/WEB-INF/jsp/includes/meta.jsp"%>
<c:forEach items="${i18n}" var="entry">
	<input type="hidden" name="${entry.key}" value="${entry.value}"/>
</c:forEach>
<input type="hidden" name="not_view_all" value="<spring:message code="not_view_all" />"/>