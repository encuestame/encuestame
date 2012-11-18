<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper recover-password-form" id="web-form-wrapper">
          <div class="section-signup" title="Sign Up with Username"
              collapsed="false">
              <div class="web-form-singup-container">
                  <div class="web-message-forgot">
                      <h1>
                            <spring:message code="forgot.checkyouremail" />
                      </h1>
                      <div class="web-message-forgor-link">
                             <span class="link">
                                <a href="<%=request.getContextPath()%>/user/signin">
                                    <spring:message code="header.signin" />
                                 </a>
                             </span>
                      </div>
                  </div>
              </div>
          </div>
    </div>
</div>