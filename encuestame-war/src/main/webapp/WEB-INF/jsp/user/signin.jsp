<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initBody.jsp" %>
    <div id="loginWrapper">
       <div class="formWrapper">
           <form class="form" name="loginForm" id="loginForm" action="<%=request.getContextPath()%>/j_spring_security_check"  method="post">
                  <div class="field">
                  <div class="label">Username</div>
                  <div class="output">
                     <input type="text" name="j_username" id="j_username"/></div>
                  </div>
                  <div class="field">
                  <div class="label">Password</div>
                  <div class="output">
                    <span><input type="password" name="j_password" id="j_password" /></span>
                    <span><a href="<%=request.getContextPath()%>/user/forgot">Forgot?</a></span>
                  </div>
                  </div>
                  <div class="field">
                     <div class="label"></div>
                      <div class="output">
                          <input id="remember"  type="checkbox" name="_rememberMeServices" />
                          <span>Keep me logged in</span>
                      </div>
                   </div>
                    <div class="signIn">
                        <input class="btn grey" type="submit" value="Sign In" />
                    </div>


            </form>
            <div class="options">
                    <a  href="<%=request.getContextPath()%>/user/signup"><span>Create Your Account</span></a>
            </div>
      </div>
    </div>
<%@ include file="/WEB-INF/jsp/includes/endBody.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/validate.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>