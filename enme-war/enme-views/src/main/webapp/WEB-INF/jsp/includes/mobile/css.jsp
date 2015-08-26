<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/decorators/css-common.jsp"%>
<c:if test="${development}">
    <script src="<%=request.getContextPath()%>/resources/js/debug/less.min.js"></script>
</c:if>
