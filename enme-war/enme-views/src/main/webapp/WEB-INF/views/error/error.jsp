<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper sign-in-form" id="web-form-wrapper">
        <article class="error-page">
            <section class="web-user-confirm-message">
                <article class="web-message-center">
                      <c:if test="${not empty message}">
                          <h1>
                              <spring:message code="e_023" />
                          </h1>
                          <c:if test="${message.errorLevel gt 0}">
                              <div class="${message.infoType.css}">
                                  <div>
                                      ${message.message}
                                  </div>
                                   <c:if test="${message.displayErrorBugTracking}">
                                      <div class="trac">
                                        <spring:message code="error.report" />
                                        <a href="<%=EnMePlaceHolderConfigurer.getProperty("application.official.bugtrac")%>">
                                          <%=EnMePlaceHolderConfigurer.getProperty("application.official.bugtrac")%>
                                        </a>
                                    </div>
                                </c:if>
                                </div>
                            </c:if>
                            <c:if test="${not empty message.description and message.errorLevel eq 2}">
                                <div class="extra">
                                    ${message.description}
                               </div>
                          </c:if>
                      </c:if>
                      <c:if test="${empty message}">
                        <h1>
                            <spring:message code="error.empty" />
                            <a href="<%=request.getHeader("referer")%>">
                                <spring:message code="enme.return.home" />
                            </a>
                        </h1>
                      </c:if>
                </article>
            </section>
        </article>
    </div>
</div>