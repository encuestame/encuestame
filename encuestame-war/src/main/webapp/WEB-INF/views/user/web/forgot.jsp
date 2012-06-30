<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div id="web-user-actions-form-wrapper" class="enme-auto-center">
	<div class="web-form-wrapper recover-password-form" id="web-form-wrapper">
		<form:form modelAttribute="forgotPasswordBean" cssClass="signup-form defaultForm">
			<div class="section-signup" title="Sign Up with Username"
				collapsed="false">
				<div class="web-form-singup-container">
					<h1>
						  <spring:message code="forgot.username" />
					</h1>
					<fieldset class="textbox">
						<div class="section name">
							<div class="validator-wrapper" id="rm" widgetid="rm">
								<div id="_message_rm" class="sidetip">
									<p><form:errors path="email" cssClass="error-message" /></p>
								</div>
								<div class="input-design">
									<fieldset>
										<form:input path="email" size="30" maxlength="200" />
									</fieldset>
								</div>
							</div>
						</div>
						<div class="section name">
                            <div class="validator-wrapper" id="rm" widgetid="rm">
                                <div id="_message_rm" class="sidetip">
                                    <p><form:errors path="captcha" cssClass="error-message" /></p>
                                </div>
                                <div class="captcha">
                                    <fieldset>
                                        <c:out value="${forgotPasswordBean.captcha}" escapeXml="false" />
                                    </fieldset>
                                </div>
                            </div>
                        </div>

					</fieldset>
					<fieldset>
						<div class="center">
							<button type="submit" class="submit button" name="submit">
							     <spring:message code="forgot.submit" />
				            </button>
						</div>
					</fieldset>
				</div>
			</div>
		</form:form>
	</div>
</div>