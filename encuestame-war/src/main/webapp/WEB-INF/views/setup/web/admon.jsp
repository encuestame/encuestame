<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/setup/web/setup_init.jsp"%>
    <div class="setup-description">
        <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
        </c:if>
        <h1>
            Step 4: <span>Create Administration Account</span>
        </h1>
        <p>This user will have all permissions.</p>
        <form:form modelAttribute="administrator" cssClass="setup-form">
            <div class="row">
                <div class="label"><label>Username:</label></div>
                <form:input path="username" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false"/>
                <form:errors path="username" cssClass="errors" />
            </div>
            <div class="row">
                <div class="label"><label>Email:</label></div>
                <form:input path="email" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                <form:errors path="email" cssClass="errors" />
            </div>
            <div class="row">
                <div class="label"><label>Confirm Email:</label></div>
                <form:input path="confirmEmail" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                <form:errors path="confirmEmail" cssClass="errors" />
            </div>
            <div class="row">
                <div class="label"><label>Password:</label></div>
                <form:password path="password" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false" />
                <form:errors path="password" cssClass="errors" />
            </div>
            <div class="row">
                <div class="label"><label>Confirm Password:</label></div>
                <form:password path="confirmPassword" size="30" maxlength="40" cssErrorClass="inputError" autocomplete="false"/>
                <form:errors path="confirmPassword" cssClass="errors" />
            </div>
            <div class="row">
                <input type="submit" id="saveUser" name="_eventId_create-user"
                    value="Save User" />
            </div>
        </form:form>
    </div>
<%@ include file="/WEB-INF/jsp/includes/setup/setup_finish.jsp"%>
