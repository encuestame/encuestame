<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h1>
            <spring:message code="setup.step4"></spring:message>
            <spring:message code="setup.stepDes4"></spring:message>
        </h1>
        <p>
           <spring:message code="setup.step4.info"></spring:message>
        </p>
        <form:form modelAttribute="administrator" cssClass="setup-form">
            <div class="row">
                <div class="label"><label><spring:message code="setup.step4.username"></spring:message> </label></div>
                <form:input path="username" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false"/>
                <form:errors path="username" cssClass="errors" />
            </div>
            <div class="row">
                <div class="label"><label><spring:message code="setup.step4.email"></spring:message> </label></div>
                <form:input path="email" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                <form:errors path="email" cssClass="errors" />
            </div>
            <div class="row">
                <div class="label"><label><spring:message code="setup.step4.confirm.email"></spring:message> </label></div>
                <form:input path="confirmEmail" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                <form:errors path="confirmEmail" cssClass="errors" />
            </div>
            <div class="row">
                <div class="label"><label><spring:message code="setup.step4.password"></spring:message> </label></div>
                <form:password path="password" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                <form:errors path="password" cssClass="errors" />
            </div>
            <div class="row">
                <div class="label"><label><spring:message code="setup.step4.password.confirm"></spring:message> </label></div>
                <form:password path="confirmPassword" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false"/>
                <form:errors path="confirmPassword" cssClass="errors" />
            </div>
            <div class="row">
                <div class="default-rigth-aling">
	                <button type="submit" name="_eventId_create-user"
	                    class="btn-default" onclick="hideButtonsDisplayLoading(this);">
	                    <spring:message code="setup.step4.button"></spring:message>
	                </button>
                    <div class="hidden" id="loading">
                        <img src="<%=request.getContextPath()%>/resources/images/loaders/setup.gif"/>
                    </div>
                 </div>
            </div>
        </form:form>