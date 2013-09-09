<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div>
    <div class="g-nav">
        <%@ include file="nav.jsp"%>
    </div>
</div>
<div id="web-main-public-line">
        <div class="container">
            <div class="span8">
                <div class="web-item-wrapper">
                    <article class="items">
                    <c:forEach items="${items}" var="item">
                        <%@ include file="item.jsp"%>
                    </c:forEach>
                    <c:if test="${!empty items}">
                        <div data-dojo-type="me/web/widget/stream/FrontEnd"
                             items="20"></div>
                    </c:if>
                    </article>
                </div>
            </div>
            <div class="span4">
                <aside class="web-left-wrapper">
                    <%@ include file="aside.jsp"%>
                </aside>
            </div>
    </div>
</div>