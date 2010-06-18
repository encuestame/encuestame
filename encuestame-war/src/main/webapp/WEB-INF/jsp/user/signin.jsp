<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
<body>
    <div id="loginWrapper">
       <div class="formWrapper">
           <form class="form" name="loginForm" action="<%=request.getContextPath()%>/j_spring_security_check"  method="post">
                  <div class="field">
                  <div class="label">Username</div>
                  <div class="output">
                     <input type="text" name="j_username" /></div>
                  </div>
                  <div class="field">
                  <div class="label">Password</div>
                  <div class="output">
                    <input type="password" name="j_password" />
                  </div>
                  </div>
                  <div class="field">
                     <div class="label"></div>
                      <div class="output">
                          <input type="checkbox" name="_rememberMeServices" />
                          <span>Remember Me</span>
                      </div>
                   </div>
                   <div class="forgotPassword">
                        <a href="<%=request.getContextPath()%>/user/forgot">Forget Your Password?</a>
                    </div>
                <div class="buttonGroup">
                    <a class="button" href="#" onclick="document.loginForm.submit()"><span>Sign In</span></a>
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