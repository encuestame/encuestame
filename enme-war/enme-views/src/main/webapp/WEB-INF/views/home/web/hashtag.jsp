<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<style>

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

      <div class="row web-generic-stats">
           <div data-dojo-type="me/web/widget/stats/GenericStats"
                class=""
                channel="/encuestame/hashtag/time/range/refresh/graph"
                typeGeneric="HASHTAG"
                hashtagName="${tagName.hashTagName}"
                generic="${tagName.hashTagName}"
                id="generic">
            </div>
             <div class="span2">
                <div channel="/encuestame/hashtag/time/range/refresh/graph"
                data-dojo-type="me/web/widget/menu/TimeRangeDropDownMenu"
                defaultDateRange="365"></div>
            </div>
      </div>


      <div data-dojo-type="me/web/widget/hashtags/HashTagGraph"
             channel="/encuestame/hashtag/time/range/refresh/graph"
             hashtagName="${tagName.hashTagName}"></div>
      <div style="margin-right: 3px;text-aling:right;text-align: right;">
        <div class="btn-group">
          <button class="btn active">
              by Type
          </button>
          <button class="btn">
              Middle
          </button>
          <button class="btn">Right</button>
        </div>
      </div>

    <div class="row web-ht-detail">

         <div class="span9 web-wrapper-ddetail web-dht-detail-wrapper">
             <article class="emne-box">
                  <h3>
                    <spring:message code="home.hashtag.lastpub" />
                  </h3>
                  <div class="web-pup-wrapper">
                  <c:forEach items="${tweetPolls}" var="item">
                      <%@ include file="detail_item.jsp"%>
                  </c:forEach>
                  <c:if test="${empty tweetPolls}">
                      <h3>
                         <spring:message code="home.hashtag.nolink" />
                      </h3>
                  </c:if>
                  </div>
              </article>


               <article class="emne-box">
                       <h3>
                          <spring:message code="home.hashtag.tweets" />
                       </h3>
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

          <div class="span3 aside">
            <aside class="web-ht-wrapper-top web-wrapper-detail">
                <article class="emne-box">
                     <h3>
                       <spring:message code="home.hashtag.position" />
                     </h3>
                     <section data-dojo-type="me/web/widget/stats/RatePosition"
                          tagName="${tagName.hashTagName}"
                          channel="/encuestame/hashtag/time/range/refresh/graph"
                          class="web-rated-position"
                          id="position">
                     </section>
                </article>
                <article class="emne-box">
                     <h3>
                        <spring:message code="home.hashtag.profile" />
                     </h3>
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
     </div>
</article>

