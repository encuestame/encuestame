<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper sign-in-form" id="web-form-wrapper">
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
                        <div class="section">
                            <c:if test="${not empty message}">
                                <p class="box ${message.infoType.css}">${message.message}</p>
                            </c:if>
                            <c:if test="${signinError}">
                                <div class="alert alert-error">
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
                        <div class="section">
                            <div class="validator-wrapper" id="rm">
                                    <input type="text"
                                        placeholder="<spring:message code="signin.username" />"
                                        name="j_username" id="j_username" autocomplete="off"
                                        autocorrect="off" autocapitalize="off" autofocus="autofocus" />
                            </div>
                        </div>
                        <div class="section">
                            <div class="validator-wrapper" id="rm">
                                    <input placeholder="<spring:message code="signin.password" />"
                                        type="password" name="j_password" id="j_password"
                                        autocorrect="off" autocapitalize="off" autocomplete="off"
                                        autofocus="autofocus" />
                            </div>
                        </div>
                        <div class="web-section-option">
                            <a href="<%=request.getContextPath()%>/user/forgot"> <spring:message
                                    code="signin.forgot" />
                            </a>
                            <br/>
                            <br/>
                            <a href="<%=request.getContextPath()%>/user/signup"> <spring:message
                                    code="signin.signup" />
                            </a>
                        </div>
                        <div class="web-section-button right">
                            <button type="submit" class=" btn submit btn-warning" name="submit">
                                <spring:message code="signin.button" />
                            </button>
                        </div>
                    </fieldset>
                </div>
            </div>
        </form>
        <div class="section-signup"  title="<spring:message code="signup.social.signup" />" collapsed="false">
            <div class="web-social-signin">
                <h5>
                    <spring:message code="signup.social.signup" />
                </h5>
                <%@ include file="/WEB-INF/jsp/includes/web/social.jsp"%>
            </div>
        </div>
        <div class="section-real-signup">

        </div>
    </div>
</div>