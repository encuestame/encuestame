<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper recover-password-form" id="web-form-wrapper">
        <form:form modelAttribute="forgotPasswordBean" cssClass="signup-form defaultForm" action="${domain}/user/forgot">
            <div class="section-signup" title="Sign Up with Username"
                collapsed="false">
                <div class="web-form-singup-container">
                    <h3>
                        <spring:message code="forgot.username" />
                    </h3>
                    <fieldset class="textbox">
                        <div class="section">
                            <div class="validator-wrapper" id="rm" widgetid="rm">
                                <div id="_message_rm" class="sidetip">
                                    <p>
                                        <form:errors path="email" cssClass="error-message" />
                                    </p>
                                </div>
                                <div class="input-design">
                                    <fieldset>
                                        <form:input path="email" size="30" maxlength="200" />
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div class="section captcha right">
                            <div class="validator-wrapper" id="rm" widgetid="rm">
                                <div id="_message_rm" class="sidetip">
                                    <p>
                                        <form:errors path="captcha" cssClass="error-message" />
                                    </p>
                                </div>
                                <div>
                                     <c:out value="${forgotPasswordBean.captcha}" escapeXml="false" />
                                </div>
                            </div>
                        </div>

                     <div class="section right">
                         <button type="submit" class="submit btn-warning btn" name="submit">
                              <spring:message code="forgot.submit" />
                         </button>
                     </div>
                     </fieldset>
                </div>
            </div>
        </form:form>
    </div>
</div>
