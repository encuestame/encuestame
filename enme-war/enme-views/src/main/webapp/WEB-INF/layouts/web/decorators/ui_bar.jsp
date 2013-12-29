<%@ include file="/WEB-INF/jsp/includes/meta.jsp"%>
<c:if test="${logged}">
      <div data-dojo-type="me/web/widget/ui/UpgradeBar" validate="${isActivated}" class="hidden">
          <div class="up-message ">
              <span>
                  <spring:message code="singup.account.not.validated" />
              </span>
              <a href="<%=request.getContextPath()%>/user/confirm/email/refresh/code">
                     <spring:message code="singup.account.send.code" />
              </a>
          </div>
      </div>
</c:if>