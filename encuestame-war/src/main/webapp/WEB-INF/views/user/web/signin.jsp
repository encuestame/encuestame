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
     <c:if test="${social}">
      <div class="socialConnect">
          <div class="title">Do you already have an account on one of these sites?</div>
          <div class="twitter">
              <form action="<%=request.getContextPath()%>/signin/twitter" method="POST">
                  <button type="submit">
                      <img src="<c:url value="/resource/images/social/twitter/signin.png" />"
                  </button>
              </form>
          </div>
          <script src='http://connect.facebook.net/en_US/all.js'></script>
          <div class="facebook">
                  <script>
                      function signInWithFacebook() {
                          FB.getLoginStatus(function(response) {
                              if (response.session) {
                                  dojo.byId('fb_signin').submit();
                              }
                            });
                      }
                  </script>
                  <div id="fb-root"></div>
                  <form id="fb_signin" action="<c:url value="/signin/facebook" />" method="post">
                      <fb:login-button perms="email,publish_stream,offline_access" onlogin="signInWithFacebook();" v="2"
                          length="long">Sign in with Facebook</fb:login-button>
                  </form>
                   <script>
                  if(FB) {
                      FB.requireSessionThenGoTo = function(url) {
                          FB.getLoginStatus(function(response) {
                              if (response.session) {
                                  window.location = url;
                              } else {
                                  FB.login(function(response) {
                                      if (response.session) {
                                          window.location = url;
                                      }
                                  });
                              }
                          });
                      };

                      FB.logoutThenGoTo = function(url) {
                          FB.logout(function(response) {
                              window.location = url;
                          });
                      };
                  }
                  </script>
                  <script type='text/javascript'>
                      FB.init({appId: '102345790957', status: true, cookie: true, xfbml: true});
                      FB.Event.subscribe('auth.sessionChange', function(response) { if (response.session) {} else {} });
                  </script>
          </div>
         </c:if>
    </div>
