<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="home-mobile">
    <%@ include file="search.jsp"%>
    <article class="mobile-item-wrapper">
        <nav class="category-menu">
          <%@ include file="nav.jsp"%>
        </nav>
        <article class="items">
        <c:forEach items="${items}" var="item">
            <%@ include file="item.jsp"%>
        </c:forEach>
        <c:if test="${!empty items}">
            <div data-dojo-type="me/mobile/widget/stream/FrontEnd"></div>
        </c:if>
        </article>
    </article>
    <section>
        <%@ include file="hashtag_home.jsp"%>
    </section>
</div>