<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper sign-in-form" id="web-form-wrapper">
        <article>
            <section class="web-user-confirm-message">
                 <article class="web-message-center">
            <h1>
                <spring:message code="error.access.denied.title" />
            </h1>
            <h2>
                <spring:message code="error.access.denied" />
            </h2>
            <a href="<%=request.getHeader("referer")%>">
                <spring:message code="commons_back" />
            </a>
            </section>
        </article>
    </div>
</div>