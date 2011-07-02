<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<div class="mobile-signin">
      <div class="formWrapper">
          <div class="mobile-errorWrapper">
               <c:if test="${not empty message}">
                    <div class="${message.infoType.css}">${message.message}</div>
               </c:if>
               <c:if test="${signinError}">
                  <div class="error">
                  Your sign in information was incorrect.<br/>
                  Please try again or <a href="<c:url value="/user/signup" />">sign up</a> free.
                  </div>
              </c:if>
          </div>
          <form action="<%=request.getContextPath()%>/user/signin/authenticate"  method="post">
                 <div class="field">
                 <div class="label">Username</div>
                 <div class="output">
                    <input type="text" name="j_username" id="j_username"/></div>
                 </div>
                 <div class="field">
                 <div class="label">Password</div>
                 <div class="output">
                   <span><input type="password" name="j_password" id="j_password" /></span>
                 </div>
                 </div>
                 <div class="field">
                     <div class="output">
                         <input id="remember"  type="checkbox" name="_rememberMeServices" />
                         <span>Keep me logged in</span>
                     </div>
                </div>
                <div class="signIn">
                      <input class="btn grey" type="submit" value="Sign In" />
                </div>
           </form>
           <div class="mobile-signin-options">
                <div class="item"><a href="<%=request.getContextPath()%>/user/forgot">Forgot your username/password?</a></div>
                <div class="item"><a href="<%=request.getContextPath()%>/user/signup">Create Account</a></div>
           </div>
           <c:if test="${social}">
               <div class="mobile-signin-social-options">
                    <div class="item">
                          <form action="<%=request.getContextPath()%>/signin/google" method="POST">
                              <button type="submit">
                                  <img src="<%=request.getContextPath()%>/resources/images/social/google/enme_google_conn.png" />
                              </button>
                          </form>
                      </div>
                      <div class="item">
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
