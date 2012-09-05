<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-cloud">
     <article class="emne-box">
        <h1>
            <spring:message code="cloud.title" />
        </h1>
        <section class="cloudItems">
            <c:forEach items="${hashtags}" var="cloud">
                  <c:if test="${not empty cloud.hashTagName}">
                      <span class="item" style="font-size: ${cloud.size}px;" dojoTypeE="encuestame.org.core.commons.stream.HashTagInfo"
                            url="<%=request.getContextPath()%>/tag/${cloud.hashTagName}/"
                            hashTagName="${cloud.hashTagName}">
                      </span>
                 </c:if>
            </c:forEach>
        </section>
    </article>
</article>