<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
	<div class="web-form-wrapper sign-in-form"
		id="web-form-wrapper">
		<form class="form defaultForm" name="loginForm" id="loginForm"
			action="<%=request.getContextPath()%>/user/signin/authenticate"
			method="post">
			<div class="section-signup" title="Sign Up with Username"
				collapsed="false">
				<div class="web-form-singup-container">
					<h1>
						<spring:message code="signin.title" />
					</h1>
					<fieldset class="textbox">
						<div>
							<c:if test="${not empty message}">
								<div class="${message.infoType.css}">${message.message}</div>
							</c:if>
							<c:if test="${signinError}">
								<div class="error">
									<spring:message code="signin.error" />
									<br />
									<spring:message code="signin.error.description" />
									<a href="<c:url value="/user/signup" />"> <spring:message
											code="signin.error.signup" /> <spring:message
											code="signin.error.free" />
									</a>
								</div>
							</c:if>
						</div>
						<div class="section name">
							<div class="validator-wrapper" id="rm" widgetid="rm">
								<div class="input-design">
									<fieldset>
										<div id="_message_rm" class="sidetip">
											<p>
												<a href="<%=request.getContextPath()%>/user/signup"> <spring:message
														code="signin.signup" />
												</a>
											</p>
										</div>
										<input type="text"
											placeholder="<spring:message code="signin.username" />"
											name="j_username" id="j_username" autocomplete="off"
											autofocus="autofocus" />
									</fieldset>
								</div>
							</div>
						</div>
						<div class="section name">
							<div class="validator-wrapper" id="rm" widgetid="rm">
								<div id="_message_rm" class="sidetip">
									<p>
										<form:errors path="captcha" cssClass="error-message" />
									</p>
								</div>
								<div class="input-design">
									<fieldset>
										<div id="_message_rm" class="sidetip">
											<p>
												<a href="<%=request.getContextPath()%>/user/forgot"> <spring:message
														code="signin.forgot" />
												</a>
											</p>
										</div>
										<input placeholder="<spring:message code="signin.password" />"
											type="password" name="j_password" id="j_username"
											autocomplete="off" autofocus="autofocus" />
									</fieldset>
								</div>
							</div>
						</div>
					</fieldset>
					<fieldset>
						<div class="center">
							<button type="submit" class="submit button" name="submit">
								<spring:message code="signin.button" />
							</button>
						</div>
					</fieldset>
					<!--
					   http://issues.encuestame.org/browse/ENCUESTAME-503 										
					<fieldset>
						<div class="section-signup"
							title="Sign up with your favourite social network"
							collapsed="false">
							<div class="web-social-signin">
								<h3>
									<spring:message code="signup.social.signup" />
								</h3>
								<%@ include file="/WEB-INF/jsp/includes/web/social.jsp"%>
							</div>
						</div>
					</fieldset>
					 -->
				</div>
			</div>
		</form>
	</div>
</div>