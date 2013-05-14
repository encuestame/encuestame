<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-main-public-line" class="enme-auto-center">
    <article class="web-item-wrapper">
        <div class="categoryMenu">
            <%@ include file="nav.jsp"%>
        </div>
        <article class="items">
        <c:forEach items="${items}" var="item">
            <%@ include file="item.jsp"%>
        </c:forEach>
        <c:if test="${!empty items}">
            <div data-dojo-type="me/web/widget/stream/FrontEnd"
                 items="20"></div>
        </c:if>
        </article>
    </article>
    <aside class="web-left-wrapper">
        <%@ include file="aside.jsp"%>
    </aside>
</div>