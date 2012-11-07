<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="home-mobile">
    <article class="mobile-item-wrapper">
        <nav class="category-menu category_color">
          <%@ include file="nav.jsp"%>
        </nav>
        <article class="items">
        <c:forEach items="${items}" var="item">
            <%@ include file="item.jsp"%>
        </c:forEach>
        <c:if test="${!empty items}">
            <div dadta-dojo-type="me/mobile/widget/stream/FrontEnd"></div>
        </c:if>
        </article>
    </article>
    <section>
        <%@ include file="hashtag_home.jsp"%>
    </section>
</div>