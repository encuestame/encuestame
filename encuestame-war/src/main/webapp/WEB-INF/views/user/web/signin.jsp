<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div id="web-login-container">
    <form class="form" name="loginForm" id="loginForm"
        action="<%=request.getContextPath()%>/user/signin/authenticate"
        method="post">
        <div class="section-wrapper">
            <c:if test="${not empty message}">
                <div class="${message.infoType.css}">${message.message}</div>
            </c:if>
            <c:if test="${signinError}">
                <div class="error">
                    <spring:message code="signin.error" /><br />
                     <spring:message code="signin.error.description" />
                     <a href="<c:url value="/user/signup" />">
                       <spring:message code="signin.error.signup" /> <spring:message code="signin.error.free" />
                     </a>
                </div>
            </c:if>
        </div>


        <div class="section-wrapper">
            <label class="section-wrapper" for="j_username">
                 <spring:message code="signin.username" />:
             </label>
            <div class="input-design">
                <fieldset>
                    <input type="text" name="j_username" id="j_username" />
                 </fieldset>
            </div>
        </div>

        <div class="section-wrapper">
            <label class="section-wrapper" for="j_password">
                <spring:message code="signin.password" />:
             </label>
            <div class="input-design">
                <fieldset>
                    <input type="password" name="j_password" id="j_password" />
                </fieldset>
            </div>
            <div class="forgot">
                 <a href="<%=request.getContextPath()%>/user/forgot">
                    <spring:message code="signin.forgot" />
                 </a>
            </div>
        </div>
        <div class="section-wrapper loginButtonWrapper">
            <div class="login-buton">
                <input class="btn grey defaultButton" type="submit"
                       value="Iniciar sesión" name="loginButton" id="loginButton">
            </div>
            <div class="signup">
                  <a href="<%=request.getContextPath()%>/user/signup">
                    <spring:message code="signin.signup" />
                  </a>
            </div>
        </div>

        <div class="section-wrapper">
           <c:if test="${social}">
                <div class="web-social-signin">
                    <div class="title">
                        <spring:message code="signin.social.message" />
                    </div>
                    <%@ include file="/WEB-INF/jsp/includes/web/social.jsp"%>
                </div>
           </c:if>
        </div>
    </form>
</div>