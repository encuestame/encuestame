<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/header.jsp" %>
<body>
    <div id="mainUserWrapper">
       <form name="f" action="<%=request.getContextPath()%>/j_spring_security_check"
          method="post">
            <fieldset>
              <div class="field">
              <div class="label">User:</div>
              <div class="output">
                 <input type="text" name="j_username" /></div>
              </div>
              <div class="field">
              <div class="label">Password:</div>
              <div class="output"><input type="password" name="j_password" />
              </div>
              </div>
              <div class="field">
              <div class="label">Don't ask for my password for two weeks:</div>
              <div class="output"><input type="checkbox"
                name="_spring_security_remember_me" /></div>
              </div>
            </fieldset>
            <div class="buttonGroup"><input name="submit" type="submit" value="Login" /></div>
        </form>
      </div>
      <div>
        <a href="signup">Register</a>
        <a href="forgot">Forget Your Password?</a>
      </div>
    </div>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>