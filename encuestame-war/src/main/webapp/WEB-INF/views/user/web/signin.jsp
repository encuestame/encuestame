<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
                        <span style="margin-left: 5px;">
                            <a  href="<%=request.getContextPath()%>/user/signup"><span>You need account?</span></a>
                        </span>
                    </div>
            </form>
      </div>
    </div>
     <c:if test="${!social}">
      <div class="socialConnect">
          <div class="title">Do you already have an account on one of these sites?</div>
          <div class="twitter">
              <form action="<%=request.getContextPath()%>/signin/twitter" method="POST">
                  <button type="submit">
                      <img src="<%=request.getContextPath()%>/resources/images/social/twitter/enme_signin_connect.png" />
                  </button>
              </form>
          </div>
          <div class="twitter">
              <form action="<%=request.getContextPath()%>/signin/google" method="POST">
                  <button type="submit">
                      <img src="<%=request.getContextPath()%>/resources/images/social/google/enme_google_conn.png" />
                  </button>
              </form>
          </div>
          <div class="twitter">
              <form action="<%=request.getContextPath()%>/signin/facebook" method="POST">
                  <button type="submit">
                      <img src="<%=request.getContextPath()%>/resources/images/social/facebook/enme_connect.gif" />
                  </button>
              </form>
          </div>
          <div class="twitter">
              <form action="<%=request.getContextPath()%>/signin/identica" method="POST">
                  <button type="submit">
                      <img src="<%=request.getContextPath()%>/resources/images/social/identica/enme_identica.png" />
                  </button>
              </form>
          </div>
          <div class="twitter">
              <form action="<%=request.getContextPath()%>/signin/linkedin" method="POST">
                  <button type="submit">
                      <img src="<%=request.getContextPath()%>/resources/images/social/linkedin/enme_linkedin_connect.png" />
                  </button>
              </form>
          </div>
          <div class="twitter">
              <form action="<%=request.getContextPath()%>/signin/yahoo" method="POST">
                  <button type="submit">
                      <img src="<%=request.getContextPath()%>/resources/images/social/yahoo/enme_yahoo_connect.png" />
                  </button>
              </form>
          </div>
        </div>
      </c:if>