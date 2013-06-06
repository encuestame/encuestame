<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="container">

  <article class="web-cloud">
     <article class="emne-box">
        <h2>
            <spring:message code="cloud.title" />
        </h2>
        <section class="cloudItems">
            <c:forEach items="${hashtags}" var="cloud">
                  <c:if test="${not empty cloud.hashTagName}">
                      <span class="item" size="${cloud.size}" data-dojo-type="me/web/widget/stream/HashTagInfo"
                            url="<%=request.getContextPath()%>/tag/${cloud.hashTagName}/"
                            hashTagName="${cloud.hashTagName}">
                      </span>
                 </c:if>
            </c:forEach>
        </section>
    </article>
  </article>

</div>