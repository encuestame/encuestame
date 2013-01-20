<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper sign-in-form" id="web-form-wrapper">
        <article class="error-page">
            <section class="web-user-confirm-message">
                <article class="web-message-center">
                      <c:if test="${not empty message}">
                      <h1>Oh Oh ! Error</h1>
                           <div class="${message.infoType.css}">
                                <div>${message.message}</div>
                                <div class="trac">Report this bug on <a href="<%=EnMePlaceHolderConfigurer.getProperty("application.official.bugtrac")%>">
                                    <%=EnMePlaceHolderConfigurer.getProperty("application.official.bugtrac")%></a></div>
                                </div>
                           <div class="extra">
                                 ${message.description}
                           </div>
                      </c:if>
                      <c:if test="${empty message}">
                        <h1>
                            Sorry no errors ;) ... <a href="<%=request.getHeader("referer")%>"> Go Back</a>
                        </h1>
                      </c:if>
                </article>
            </section>
        </article>
    </div>
</div>