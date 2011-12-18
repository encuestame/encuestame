<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>


<article class="web-hashtag-wrapper">

    <section class="web-hashtag-header">
        <div class="web-hashtagName">
            <h2>
                ${tagName.hashTagName}
            </h2>
        </div>
        <div class="web-hashtag-options">
             Viewing: All Time
        </div>
    </section>

    <section class="web-ht-graph-wrapper emne-box">
        <div class="web-ht-button-wrapper">
            <div class="web-ht-button-item">

            </div>
            <div class="web-ht-button-item">

            </div>
            <div class="web-ht-button-item">

            </div>
        </div>
        <div id="holder" class="web-ht-graph"></div>
    </section>

    <div class="web-ht-detail-wrapper">
       <div class="web-ht-wrapper-mainline">
           <article class="emne-box">
                <header>
                   Last 50 Publications
                </header>
                <div class="web-pup-wrapper">
                <c:forEach items="${tweetPolls}" var="item">
                    <%@ include file="detail_item.jsp"%>
                </c:forEach>
                </div>
            </article>
             <article class="emne-box">
                     <header>
                        Tweets
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
                    Stats
                 </header>
                 <div dojoType="encuestame.org.core.shared.stats.GenericStats" id="generic"></div>
             </article>
            <article class="emne-box">
                 <header>
                    Rate
                 </header>
                 <div dojoType="encuestame.org.core.shared.stats.RatePosition" id="position"></div>
            </article>
            <article class="emne-box">
                 <header>
                    Top 50 Profiles
                 </header>
                 <div dojoType="encuestame.org.core.shared.stats.TopProfiles" id="topprofiles"></div>
            </article>
        </aside>
    </div>

<!--    <div class="web-tags-tweetpolls"> -->
<!--          <div> Last 10 TweetPolls</div> -->
<%--          <c:forEach items="${tweetPolls}" var="tweets"> --%>
<%--             <div class="web-tweetpolls"> <a href="<%=request.getContextPath()%>/tweetpoll/${tweets.id}/test">  ${tweets.questionBean.questionName} </a> | --%>
<%--             <span class="web-tweetpolls-time">${tweets.relativeTime}</span> --%>
<!--             </div> -->
<%--         </c:forEach> --%>
<!--         <div> Last 10 rated</div> -->
<%--         <c:forEach items="${tweetPollrated}" var="rated"> --%>
<%--             <div class="web-tweetpolls"> <a href="<%=request.getContextPath()%>/tweetpoll/${rated.id}/test">  ${rated.questionBean.questionName} </a> --%>
<%--             <span class="web-tweetpolls-time">${rated.relativeTime}</span> |  <span class="web-tweetpolls-time">${rated.totalVotes}</span> --%>
<!--             </div> -->
<%--         </c:forEach> --%>
<!--    </div> -->


</article>

