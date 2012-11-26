<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper sign-in-form" id="web-form-wrapper">
        <article>
            <section class="web-user-confirm-message">
                <h2>
                    <spring:message code="signup.register.confirm.message" />.
                    <div>
                        <spring:message code="commons_goto" />  <div data-dojo-type="me/web/widget/menu/DashBoardMenu"
                           contextPath="<%=request.getContextPath()%>"></div>
                    </div>
               </h2>
            </section>
        </article>
    </div>
</div>