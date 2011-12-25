<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>


<article class="web-hashtag-wrapper">

    <section class="web-hashtag-header">
        <div class="web-hashtagName">
            <h2>
                ${tagName.hashTagName}
            </h2>
        </div>
        <div class="web-hashtag-options">
             <button class="emne-menu gradient-gray">
                <!-- Widget to switch Time Ranges
                     - Select All / Last Month / Last Week / Hot (Last 24) / Calendar
                     -->
                <span>View: All</span>
                <img>
             </button>
        </div>
    </section>

    <section class="web-ht-graph-wrapper emne-box">
       <div dojoType="encuestame.org.core.commons.hashtags.HashTagGraph"></div>
    </section>

    <div class="web-ht-detail-wrapper">
       <div class="web-ht-wrapper-mainline">
           <article class="emne-box">
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
             <article class="emne-box">
                     <header>
                        <spring:message code="home.hashtag.tweets" />
                     </header>
                <section>
                     <div dojoType="encuestame.org.core.commons.social.LinksPublished"
                          hasthag="${tagName.hashTagName}" class="web-social-links"
                          type="HASHTAG"></div>
                </section>
            </article>
        </div>
        <aside class="web-ht-wrapper-top">
            <article class="emne-box">
                 <header>
                    <spring:message code="home.hashtag.stats" />
                 </header>
                 <div dojoType="encuestame.org.core.shared.stats.GenericStats"
                      hasthag="${tagName.hashTagName}"
                      class="web-generic-stats"
                      key="HASHTAG" id="generic"></div>
             </article>
            <article class="emne-box">
                 <header>
                   <spring:message code="home.hashtag.position" />
                 </header>
                 <div dojoType="encuestame.org.core.shared.stats.RatePosition"
                      hasthag="${tagName.hashTagName}"
                      class="web-rated-position"
                      key="HASHTAG" id="position"></div>
            </article>
            <article class="emne-box">
                 <header>
                    <spring:message code="home.hashtag.profile" />
                 </header>
                 <div dojoType="encuestame.org.core.shared.stats.TopProfiles"
                      hasthag="${tagName.hashTagName}"
                      class="web-top-profile"
                      key="HASHTAG" id="topprofiles"></div>
            </article>
        </aside>
    </div>
</article>

