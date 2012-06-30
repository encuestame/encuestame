<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div id="web-login-container">
    <form:form modelAttribute="forgotPasswordBean">
        <div class="section-wrapper">
            <label class="section-wrapper" for="j_username">
                 <spring:message code="forgot.username" />:
             </label>
            <div class="login-section-wrapper">
                <fieldset>
                    <form:input path="email" size="30" maxlength="25" /><br /><form:errors path="email" cssClass="errors" />
                 </fieldset>
            </div>
        </div>

        <div class="section-wrapper">
            <div class="">
                 <c:out value="${forgotPasswordBean.captcha}" escapeXml="false" />
                        <br /><form:errors path="captcha" cssClass="error" />
                 </div>
        </div>
        <div class="section-wrapper loginButtonWrapper">
            <div class="login-buton">
                <input type="submit" class="btn grey defaultButton" name="submit" value="<spring:message code="forgot.submit" />" />
            </div>
        </div>
    </form:form>
</div>