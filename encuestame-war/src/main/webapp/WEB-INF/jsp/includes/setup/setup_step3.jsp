<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
        <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h1>
            <spring:message code="setup.step3"></spring:message>
            <spring:message code="setup.stepDes3"></spring:message>
        </h1>
        <p>
            <spring:message code="setup.step3.info"></spring:message>
        </p>
        <form:form method="post">
            <div class="default-rigth-aling">
                <button type="submit" name="_eventId_no"
                    class="btn-default">
                    <spring:message code="setup.step3.button.no"></spring:message>
                </button>
                <button type="submit" name="_eventId_yes"
                    class="btn-default">
                    <spring:message code="setup.step3.button.yes"></spring:message>
                </button>
            </div>
        </form:form>