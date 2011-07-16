<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
 <div id="web-login-wrapper" class="enme-auto-center">
   <div class="web-form-wrapper" >
       <c:if test="${not empty message}">
            <div class="${message.infoType.css}">${message.message}</div>
       </c:if>
       <c:if test="${signinError}">
          <div class="error">
          Your sign in information was incorrect.<br/>
          Please try again or <a href="<c:url value="/user/signup" />">sign up</a> free.
          </div>
      </c:if>
      <div class="loginTitle defaultSectionTitle">Log in</div>
       <form class="form" name="loginForm" id="loginForm"
             action="<%=request.getContextPath()%>/user/signin/authenticate"
             method="post">
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
                    <input class="btn grey defaultButton" type="submit" value="Sign In" />
                </div>
        </form>
        <div class="singup">
           <span >
<<<<<<< .merge_file_tHqgiO
                New User? <a href="<%=request.getContextPath()%>/signup"><span>Sign Up</span></a> for free
=======
                New User? <a href="<%=request.getContextPath()%>/user/signup"><span>Sign Up</span></a> for free
>>>>>>> .merge_file_cH6utJ
           </span>
        </div>
         <c:if test="${social}">
              <div class="web-social-signin">
                  <div class="title">Select one of these thrid-party accouns to sign in</div>
                  <div class="section">
                      <form action="<%=request.getContextPath()%>/signin/google" method="POST">
                          <button type="submit">
                              <img src="<%=request.getContextPath()%>/resources/images/social/google/enme_google_conn.png" />
                          </button>
                      </form>
                  </div>
                  <div class="section">
                      <form action="<%=request.getContextPath()%>/signin/facebook" method="POST">
                          <button type="submit">
                              <img src="<%=request.getContextPath()%>/resources/images/social/facebook/enme_connect.gif" />
                          </button>
                      </form>
                  </div>
                </div>
          </c:if>
    </div>
</div>