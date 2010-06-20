<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
<body>
    <div id="loginWrapper">
       <div class="formWrapper">
           <form class="form" name="loginForm" id="loginForm" action="<%=request.getContextPath()%>/j_spring_security_check"  method="post">
                  <div class="field">
                  <div class="label">Username</div>
                  <div class="output">
                     <input type="text" name="j_username" class="required" class="titleHintBox" id="j_username" title="Username should be unique"/></div>
                  </div>
                  <div class="field">
                  <div class="label">Password</div>
                  <div class="output">
                    <input type="password" name="j_password" class="required" id="j_password" />
                  </div>
                  </div>
                  <div class="field">
                     <div class="label"></div>
                      <div class="output">
                          <input id="remember"  type="checkbox" name="_rememberMeServices" />
                          <span>Keep me logged in</</span>
                          <span></span>
                      </div>
                   </div>
                   <div class="forgotPassword">
                        <a href="<%=request.getContextPath()%>/user/forgot">Forgot your password?</a>
                    </div>
                <div class="buttonGroup">
                    <a class="button" id="submit" href="#"><span>Sign In</span></a>
                </div>
            </form>
            <div class="options">
                <div class="createAccount">
                    <a class="button" href="<%=request.getContextPath()%>/user/signup"><span>Create Your Account</span></a>
                </div>
            </div>
      </div>
    </div>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>