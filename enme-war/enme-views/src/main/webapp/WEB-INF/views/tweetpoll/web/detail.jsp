<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-tweetpoll-detail web-wrapper-detail">
   <div class="web-detail-header">
       <div class="title">
        <h2>${tweetpoll.questionBean.questionName}</h2>
       </div>
   </div>
   <article class="emne-box">
       <section class="web-tweetpoll-info">
<%--
    @deprecated
 <div id="info" data-dojo-type="me/web/widget/tweetpoll/detail/TweetPollInfoDetail"
                <c:if test="${tweetpoll.limitVotesDate}">
                    date="${tweetpoll.dateToLimit}"
                </c:if>
                 votes="${tweetpoll.totalVotes}"
                 question=" ${tweetpoll.questionBean.questionName}"
                 hits="${tweetpoll.hits}"
                 completed="${tweetpoll.completed}"
                 owner="${tweetpoll.ownerUsername}"
                 tweetPollid="${tweetpoll.id}"></div> --%>
       </section>
        <section class="web-tweetpoll-answer-wrapper web-wrapper-detail-wrapper">
            <div class="web-tweetpoll-answer-chart">
                <div id="chart" data-dojo-type="me/web/widget/tweetpoll/detail/TweetPollChartDetail"
                     tweetPollid="${tweetpoll.id}"
                     completed="${tweetpoll.completed}"
                     username="${tweetpoll.ownerUsername}"></div>
            </div>
            <div class="web-tweetpoll-answer-answer">
                <header>
                     <div class="answer-label">
                           <spring:message code="commons_detail_answer" />
                     </div>
                     <div class="answer-votes">
                            <spring:message code="commons_detail_total_votes" />
                     </div>
                     <div class="answer-percent">
                           <spring:message code="commons_detail_percent" />
                     </div>
                </header>
                <c:forEach items="${answers}" var="a">
                    <div class="answer"
                         data-dojo-type="me/web/widget/tweetpoll/detail/TweetPollAnswer"
                         aId="${a.answers.questionAnswerId}"
                         color="${a.answers.color}"
                         label="${a.answers.answer}"
                         owner="${tweetpoll.ownerUsername}"
                         completed="${tweetpoll.completed}"
                         url="<%=request.getContextPath()%>${a.relativeUrl}">
                     </div>
               </c:forEach>
            </div>
       </section>
       <section class="web-button-wrapper">
            <div>
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_pie.png">
            </div>
            <div>
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_bar.png">
            </div>
       </section>
   </article>
   <c:if test="${!empty hashtags}">
       <section class="emne-box">
           <header>
                <spring:message code="options.hashtag" />
           </header>
           <div class="web-tweetpoll-hashtags ">
               <c:forEach items="${hashtags}" var="h">
                       <span data-dojo-type="me/web/widget/stream/HashTagInfo"
                        url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                        hashTagName="${h.hashTagName}"></span>
               </c:forEach>
           </div>
       </section>
   </c:if>

   <article class="emne-box">
       <header>
          <spring:message code="options.links" />
       </header>
       <section>
            <div data-dojo-type="me/web/widget/social/LinksPublished"
                 more="false"
                 itemId="${tweetpoll.id}"
                 type="TWEETPOLL"
                 class="web-social-links"
                 ></div>
       </section>
   </article>

   <section class="web-tweetpoll-comments emne-box">
      <header>
            <spring:message code="options.comments" />
      </header>
      <div name="comments" data-dojo-type="me/web/widget/comments/Comments" type="tweetpoll" item_id="${tweetpoll.id}"></div>
   </section>

</article>
