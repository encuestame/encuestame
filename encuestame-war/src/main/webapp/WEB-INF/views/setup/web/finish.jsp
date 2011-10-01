<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/setup/web/setup_init.jsp"%>
    <div class="setup-description">
        <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h1>
            Step 7: <span>Install Finished</span>
        </h1>
        <div class="setup-finished">
            <form:form method="post">
                <input type="submit" name="_eventId_home" value="Go to Home" />
            </form:form>
        </div>
    </div>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_finish.jsp"%>
