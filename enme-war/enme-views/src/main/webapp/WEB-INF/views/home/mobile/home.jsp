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
              <div data-dojo-type="me/web/widget/stream/FrontEnd"
                 items="20" viewFilter="${viewFilter}" enableImage="false"></div>
        </c:if>
        </article>
    </article>
    <section class="enme-mob-section">
        <div class="mobile-home-subtitle category_color">
            Hot HashTags
        </div>
         <div data-dojo-type="me/web/widget/hashtags/Cloud" class="web-aside-section"></div>
    </section>
    <section>
       <div class="mobile-home-subtitle category_color">
             <spring:message code="home.aside.rated.comments" />
       </div>
       <div data-dojo-type="me/web/widget/rated/Comments"
            comments="5"
            class="enme-mob-section"></div>
    </section>
    <section class="enme-mob-section">
        <div class="mobile-home-subtitle category_color">
           <spring:message code="home.aside.rated.users" />
        </div>
        <div data-dojo-type="me/web/widget/rated/RatedProfile" class="web-aside-section"></div>
    </section>
</div>