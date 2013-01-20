<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper sign-in-form" id="web-form-wrapper">
        <article>
            <section class="web-user-confirm-message">
                 <article class="web-message-center">
        <h1>
            Access Denied
        </h1>
        <h2>Your access has been denied.</h2>
        <a href="<%=request.getHeader("referer")%>">
            Go Back
        </a>
    </article>
            </section>
        </article>
    </div>
</div>