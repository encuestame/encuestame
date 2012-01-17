<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-cloud">
     <article class="emne-box">
        <header class="gradient-gray">
            <spring:message code="cloud.title" />
        </header>
        <section class="cloudItems">
            <c:forEach items="${hashtags}" var="cloud">
                  <span class="item" style="font-size: ${cloud.size}px;"  dojoType="encuestame.org.core.commons.stream.HashTagInfo"
                        url="<%=request.getContextPath()%>/tag/${cloud.hashTagName}/"
                        hashTagName="${cloud.hashTagName}"></span>
            </c:forEach>
        </section>
    </article>
</article>