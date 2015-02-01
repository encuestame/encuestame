<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<article class="web-hashtag-wrapper">

      <section class="web-generic-stats">
           <div data-dojo-type="me/web/widget/stats/GenericStats"
                class="generic"
                channel="/encuestame/hashtag/time/range/refresh/graph"
                typeGeneric="HASHTAG"
                hashtagName="${tagName.hashTagName}"
                generic="${tagName.hashTagName}"
                id="generic">
            </div>
             <div class="timerange">
                <div channel="/encuestame/hashtag/time/range/refresh/graph"
                data-dojo-type="me/web/widget/menu/TimeRangeDropDownMenu"
                defaultDateRange="365"></div>
            </div>
      </section>


      <section data-dojo-type="me/web/widget/hashtags/HashTagGraph"
           channel="/encuestame/hashtag/time/range/refresh/graph"
           hashtagName="${tagName.hashTagName}">
      </section>

      <section class="web-ht-detail">
         <div class="web-wrapper-ddetail web-dht-detail-wrapper">
             <div class="emne-box">
                  <h5>
                    <spring:message code="home.hashtag.lastpub" />
                  </h5>
                  <div class="web-pup-wrapper">
                  <c:forEach items="${tweetPolls}" var="item">
                        <%@ include file="detail_item.jsp"%>
                  </c:forEach>
                  <c:if test="${empty tweetPolls}">
                      <div>
                         <spring:message code="home.hashtag.nolink" />
                      </div>
                  </c:if>
                  </div>
           </div>

          <div class="emne-box">
               <h5>
                  <spring:message code="home.hashtag.tweets" />
               </h5>
               <div data-dojo-type="me/web/widget/social/LinksPublished"
                    channel="/encuestame/hashtag/time/range/refresh/graph"
                    class="web-social-links"
                    itemId="${tagName.hashTagName}"
                    type="HASHTAG">
               </div>
          </div>

          </div>

          <aside class="aside ht-second-aside">
              <div class="web-ht-wrapper-top web-wrapper-detail">
                  <article class="emne-box">
                       <h5>
                         <spring:message code="home.hashtag.position" />
                       </h5>
                       <section data-dojo-type="me/web/widget/stats/RatePosition"
                            tagName="${tagName.hashTagName}"
                            channel="/encuestame/hashtag/time/range/refresh/graph"
                            class="web-rated-position"
                            id="position">
                       </section>
                  </article>
                  <div class="emne-box">
                       <h5>
                          <spring:message code="home.hashtag.profile" />
                       </h5>
                       <div data-dojo-type="me/web/widget/stats/TopProfiles"
                            hasthag="${tagName.hashTagName}"
                            channel="/encuestame/hashtag/time/range/refresh/graph"
                            class="web-top-profile"
                            key="HASHTAG" id="topprofiles">
                       </div>
                  </div>
              </div>
          </aside>
    </section>
</article>

