<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
        <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h3>
            <spring:message code="setup.step3"></spring:message>
            <spring:message code="setup.stepDes3"></spring:message>
        </h3>
        <hr/>
        <p>
            <spring:message code="setup.step3.info"></spring:message>
        </p>
        <div class="progress  progress-warning progress-striped">
           <div class="bar" style="width: 45%;"></div>
        </div>
        <form:form method="post">
            <div class="default-rigth-aling form-actions">
                <button type="submit" name="_eventId_no"
                    class="btn btn-inverse ">
                    <spring:message code="setup.step3.button.no"></spring:message>
                </button>
                <button type="submit" name="_eventId_yes"
                    class="btn btn-warning" onclick="hideButtonsDisplayLoading(this);">
                    <spring:message code="setup.step3.button.yes"></spring:message>
                </button>
                <div class="hidden" id="loading">
                    <img src="<%=request.getContextPath()%>/resources/images/loaders/setup.gif"/>
                </div>
            </div>
        </form:form>