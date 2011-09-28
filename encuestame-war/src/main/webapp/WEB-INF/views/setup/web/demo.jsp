<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_init.jsp"%>
    <div class="setup-description">
        <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h1>
            Step 3: <span>Dou you want Install Demo Data?</span>
        </h1>
        <p>This is ussefull to display demo site.</p>
        <form:form method="post">
            <input type="submit" name="_eventId_no" value="No" />
            <input type="submit" name="_eventId_yes" value="Yes" />
        </form:form>
    </div>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_finish.jsp"%>
