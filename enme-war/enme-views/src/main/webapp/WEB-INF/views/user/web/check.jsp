<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper recover-password-form" id="web-form-wrapper">
          <div class="section-signup" title="Sign Up with Username"
              collapsed="false">
              <div class="web-form-singup-container">
                  <div class="web-message-forgot">
                      <h2>
                             <spring:message code="forgot.checkyouremail" />
                             <div class="web-message-forgor-link">
                                    <span class="link">
                                      <spring:message code="commons_goto" />  <a href="<%=request.getContextPath()%>/user/signin">
                                           <spring:message code="header.signin" />
                                        </a>
                                    </span>
                             </div>
                       </h2>
                  </div>
              </div>
          </div>
    </div>
</div>