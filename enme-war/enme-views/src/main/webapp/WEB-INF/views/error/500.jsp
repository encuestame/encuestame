<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section>
    <div style="padding: 10px;margin: 0;">
        <c:choose>
            <c:when test="${debugEnabled}">
            <h4>
                <spring:message code="error.pagenotfound.detail" />
            </h4>
            <p class="bold">
                <spring:message code="error.report" />
                <a href="<%=EnMePlaceHolderConfigurer.getProperty("application.official.bugtrac")%>">
                     <%=EnMePlaceHolderConfigurer.getProperty("application.official.bugtrac")%>
                </a>
            </p>
            </c:when>
        </c:choose>

        <c:set var="debugEnabled" scope="session" value="<%=EnMePlaceHolderConfigurer.getProperty("encuestame.error.debug")%>"/>
        <c:set var="emailWebmaster" scope="session" value="<%=EnMePlaceHolderConfigurer.getProperty("encuestame.error.webmaster.notify")%>"/>
        <c:choose>
            <c:when test="${debugEnabled}">
                <div>
                    <table width="100%" border="1">
                        <tr valign="top">
                            <td width="40%"><b>Error:</b></td>
                            <td>${pageContext.exception}</td>
                        </tr>
                        <tr valign="top">
                            <td><b>URI:</b></td>
                            <td>${pageContext.errorData.requestURI}</td>
                        </tr>
                        <tr valign="top">
                            <td><b>Status code:</b></td>
                            <td>${pageContext.errorData.statusCode}</td>
                        </tr>
                        <tr valign="top">
                            <td><b>Stack trace:</b></td>
                            <td>
                                <c:forEach var="trace"
                                           items="${pageContext.exception.stackTrace}">
                                    <p>${trace}</p>
                                </c:forEach>
                            </td>
                        </tr>
                    </table>
                </div>
            </c:when>
            <c:when test="${!debugEnabled}">
                <div>
                    <div class="page-header">
                        <h3>
                            <spring:message code="e_025" />
                        </h3>
                    </div>
                    <p class="lead">
                        <spring:message code="e_023" />
                    </p>
                    <c:if test='${not empty emailWebmaster}'>
                        <p>
                            Mail to <a href="mailto:${emailWebmaster}"> the webmaster </a> for any concern.
                        </p>
                    </c:if>

                </div>
            </c:when>

        </c:choose>

    </div>
</section>