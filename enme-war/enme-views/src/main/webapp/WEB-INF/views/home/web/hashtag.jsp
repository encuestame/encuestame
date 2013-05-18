<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<style>

body {
  font: 10px sans-serif;
}

.axis path,
.axis line {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

.x.axis path {
  display: none;
}

.line {
  fill: none;
  stroke: steelblue;
  stroke-width: 1.2px;
}
</style>

<article class="web-hashtag-wrapper">

    <div class="container">

      <div class="row">
           <div data-dojo-type="me/web/widget/stats/GenericStats"
                class="web-generic-stats"
                channel="/encuestame/hashtag/time/range/refresh/graph"
                typeGeneric="HASHTAG"
                hashtagName="${tagName.hashTagName}"
                generic="${tagName.hashTagName}"
                id="generic">
            </div>
      </div>

      <div class="row">
          <div class="span12">
                <div channel="/encuestame/hashtag/time/range/refresh/graph"
                data-dojo-type="me/web/widget/menu/TimeRangeDropDownMenu"
                defaultDateRange="365"></div>
          </div>
      </div>

      <div data-dojo-type="me/web/widget/hashtags/HashTagGraph"
             channel="/encuestame/hashtag/time/range/refresh/graph"
             hashtagName="${tagName.hashTagName}"></div>



    <div class="web-ht-detail">
         <div class="web-wrapper-detail web-ht-detail-wrapper">
             <article class="emne-box emne-box-gray">
                  <header>
                    <spring:message code="home.hashtag.lastpub" />
                  </header>
                  <div class="web-pup-wrapper">
                  <c:forEach items="${tweetPolls}" var="item">
                      <%@ include file="detail_item.jsp"%>
                  </c:forEach>
                  <c:if test="${empty tweetPolls}">
                      <h2>
                         <spring:message code="home.hashtag.nolink" />
                      </h2>
                  </c:if>
                  </div>
              </article>
               <article class="emne-box emne-box-gray">
                       <header>
                          <spring:message code="home.hashtag.tweets" />
                       </header>
                  <section>
                       <div data-dojo-type="me/web/widget/social/LinksPublished"
                            channel="/encuestame/hashtag/time/range/refresh/graph"
                            class="web-social-links"
                            itemId="${tagName.hashTagName}"
                            type="HASHTAG">
                       </div>
                  </section>
              </article>
          </div>
          <aside class="web-ht-wrapper-top web-wrapper-detail">
              <article class="emne-box emne-box-gray">
                   <header>
                      <spring:message code="home.hashtag.stats" />
                   </header>

               </article>
              <article class="emne-box emne-box-gray">
                   <header>
                     <spring:message code="home.hashtag.position" />
                   </header>
                   <section data-dojo-type="me/web/widget/stats/RatePosition"
                        tagName="${tagName.hashTagName}"
                        channel="/encuestame/hashtag/time/range/refresh/graph"
                        class="web-rated-position"
                        id="position">
                   </section>
              </article>
              <article class="emne-box emne-box-gray">
                   <header>
                      <spring:message code="home.hashtag.profile" />
                   </header>
                   <div data-dojo-type="me/web/widget/stats/TopProfiles"
                        hasthag="${tagName.hashTagName}"
                        channel="/encuestame/hashtag/time/range/refresh/graph"
                        class="web-top-profile"
                        key="HASHTAG" id="topprofiles">
                   </div>
              </article>
          </aside>
    </div>
     </div>
</article>

