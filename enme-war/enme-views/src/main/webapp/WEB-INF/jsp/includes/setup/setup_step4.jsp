<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h3>
            <spring:message code="setup.step4"></spring:message>
            <spring:message code="setup.stepDes4"></spring:message>
        </h3>
        <hr/>
        <p>
           <spring:message code="setup.step4.info"></spring:message>
        </p>
        <form:form modelAttribute="administrator" cssClass="form-horizontal">
            <fieldset>
                <div class="control-group">
                    <label class="control-label">
                        <spring:message code="setup.step4.username"></spring:message>
                    </label>
                     <div class="controls">
                        <form:input path="username" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false"/>
                         <span class="help-inline">
                            <form:errors path="username" cssClass="errors" />
                        </span>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" >
                            <spring:message code="setup.step4.email"></spring:message>
                    </label>
                    <div class="controls">
                        <form:input path="email" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                        <form:errors path="email" cssClass="errors" />
                    </div>
                </div>
                <div class="control-group">
                     <label class="control-label">
                        <spring:message code="setup.step4.confirm.email"></spring:message>
                    </label>
                    <div class="controls">
                        <form:input path="confirmEmail" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                        <form:errors path="confirmEmail" cssClass="errors" />
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" >
                        <spring:message code="setup.step4.password"></spring:message>
                    </label>
                    <div class="controls">
                        <form:password path="password" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                        <form:errors path="password" cssClass="errors" />
                    </div>
                </div>
                <div class="control-group">
                     <label class="control-label">
                        <spring:message code="setup.step4.password.confirm"></spring:message>
                    </label>
                    <div class="controls">
                        <form:password path="confirmPassword" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false"/>
                        <form:errors path="confirmPassword" cssClass="errors" />
                    </div>
                </div>
                <div class="control-group">
                    <div class="progress  progress-warning progress-striped">
                       <div class="bar" style="width: 75%;"></div>
                    </div>
                </div>
                <div class="form-actions">
                    <div class="default-rigth-aling">
    	                <button type="submit" name="_eventId_create-user"
    	                    class="btn btn-warning">
    	                    <spring:message code="setup.step4.button"></spring:message>
    	                </button>
                        <div class="hidden" id="loading">
                            <img src="<%=request.getContextPath()%>/resources/images/loaders/setup.gif"/>
                        </div>
                     </div>
                </div>
            </fieldset>
        </form:form>
