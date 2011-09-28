<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_init.jsp"%>
    error
    <c:if test="${not empty message}">
        <div class="${message.infoType.css}">${message.message}</div>
    </c:if>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_finish.jsp"%>