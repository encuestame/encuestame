<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>


<article class="web-hashtag-wrapper">

    <section class="web-hashtag-header web-detail-header">
        <div class="title">
            <h2>
                ${tagName.hashTagName}
            </h2>
        </div>
        <div class="web-hashtag-options">
        <div channel="/encuestame/hashtag/time/range/refresh/graph"
             data-dojo-type="me/web/widget/menu/TimeRangeDropDownMenu"
             defaultDateRange="365"></div>
    </div>
    </section>

    <section class="web-ht-graph-wrapper emne-box emne-box-gray">
       <div data-dojo-type="me/web/widget/hashtags/HashTagGraph"
           channel="/encuestame/hashtag/time/range/refresh/graph"
           hashtagName="${tagName.hashTagName}"
         ></div>
    </section>

    <div class="web-ht-detail-wrapper">
       <div class="web-ht-wrapper-mainline">
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
        <aside class="web-ht-wrapper-top">
            <article class="emne-box emne-box-gray">
                 <header>
                    <spring:message code="home.hashtag.stats" />
                 </header>
                 <div  data-dojo-type="me/web/widget/stats/GenericStats"
            class="web-generic-stats"
            channel="/encuestame/hashtag/time/range/refresh/graph"
            typeGeneric="HASHTAG"
            generic="${tagName.hashTagName}"
                      id="generic">
                 </div>
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
</article>

