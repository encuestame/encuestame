<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-main-public-line" class="enme-auto-center">
    <article class="web-item-wrapper">
        <nav class="categoryMenu">
            <%@ include file="nav.jsp"%>
        </nav>
        <article class="items">
        <c:forEach items="${items}" var="item">
            <%@ include file="item.jsp"%>
        </c:forEach>
        <c:if test="${!empty items}">
            <div dojoTypeE="encuestame.org.core.commons.stream.FrontEnd"></div>
        </c:if>
        </article>
    </article>
    <aside class="web-left-wrapper">
        <%@ include file="aside.jsp"%>
    </aside>
</div>