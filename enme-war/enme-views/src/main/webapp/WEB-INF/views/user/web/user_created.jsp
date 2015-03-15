<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper sign-in-form" id="web-form-wrapper">
        <article>
            <section class="web-user-confirm-message">
                <h4>
                    <spring:message code="signup.user.created" />.
                    <div>
                        <a href="<%=request.getContextPath()%>/user/signin">
                             <spring:message code="signin.button" />
                        </a>
                    </div>
               </h4>
            </section>
        </article>
    </div>
</div>