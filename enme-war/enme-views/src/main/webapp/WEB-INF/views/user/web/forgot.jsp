<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper recover-password-form" id="web-form-wrapper" style="margin: 0 auto;">
        <form:form modelAttribute="forgotPasswordBean" cssClass="signup-form defaultForm" action="${domain}/user/forgot">
            <div class="section-signup" title="Sign Up with Username"
                collapsed="false">
                <div class="web-form-singup-container">
                    <h3>
                        <spring:message code="forgot.username" />
                    </h3>
                    <fieldset class="textbox">
                        <div class="">
                            <div class="validator-wrapper" id="rm" widgetid="rm">
                                <div class="input-design">
                                        <form:input path="email" size="30" maxlength="200" />
                                        <br/>
                                        <br/>
                                        <button type="submit" class="submit btn-warning btn" disadbled="didsabled" name="submit" id="submit">
                                            <spring:message code="forgot.submit" />
                                        </button>
                                </div>
                            </div>
                        </div>
                        <div class="captcha right">
                            <div id="_message_rm" class="sidetipp">
                                <p>
                                    <form:errors path="email" cssClass="error-message" />
                                    <form:errors path="captcha" cssClass="error-message" />
                                </p>
                            </div>
                            <div class="validator-wrapper" id="rm" widgetid="rm">
                                 <c:out value="${forgotPasswordBean.captcha}" escapeXml="false" />
                            </div>
                        </div>
                     </fieldset>
                </div>
            </div>
        </form:form>
    </div>
</div>
