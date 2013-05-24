<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-main-public-line">
        <div class="">
            <div class="g-nav">
                <%@ include file="nav.jsp"%>
            </div>
        </div>
        <div class="container">
            <div class="row-fluid">
            <div class="span8">
                <article class="web-item-wrapper">
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
            </div>
             <div class="span4">
                <aside class="web-left-wrapper">
                    <%@ include file="aside.jsp"%>
                </aside>
            </div>
        </div>
    </div>
</div>