<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>


<article class="web-tweetpoll-detail web-wrapper-detail">

   <div class="container">

    <div class="row web-generic-stats">
        <div class="span12" id="generic" widgetid="generic">
          <div class="row">
            <div class="span1">
              <div class="enme-rating">
                <div class="vote">
                    <div class="count">
                      ${tweetpoll.totalVotes}
                    </div>
                    vote(s)
                </div>
                <div class="count-sub" data-dojo-attach-point="_vote">
                  ${tweetpoll.hits}
                  average</div>
              </div>
            </div>
            <div class="span9">
              <div>
                <h2>
                  ${tweetpoll.questionBean.questionName}
                </h2>
                <span class="badge badge-success" data-dojo-attach-point="_badge">
                  ${tweetpoll.relevance}
                </span>
              </div>
               <c:if test="${!empty hashtags}">
                 <c:forEach items="${hashtags}" var="h">
                         <span data-dojo-type="me/web/widget/stream/HashTagInfo"
                          url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                          hashTagName="${h.hashTagName}"></span>
                 </c:forEach>
               </c:if>
            </div>

          </div>
        </div>

      </div>

   <article class="emne-box">
        <section class="row web-tweetpoll-answer-wrapper web-wrapper-detail-wrapper">
            <div class="span6">
              <div class="web-tweetpoll-answer-chart">
                  <div id="chart" data-dojo-type="me/web/widget/tweetpoll/detail/TweetPollChartDetail"
                       tweetPollid="${tweetpoll.id}"
                       completed="${tweetpoll.completed}"
                       username="${tweetpoll.ownerUsername}"></div>
              </div>
            </div>
            <div class="span6">
              <div class="web-tweetpoll-answer-answer row">
                  <header>
                       <div class="answer-label span3">
                             <spring:message code="commons_detail_answer" />
                       </div>
                       <div class="answer-votes span1">
                              <spring:message code="commons_detail_total_votes" />
                       </div>
                       <div class="answer-percent span1">
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
            </div>
       </section>
       <section class="web-buttdon-wrapper">
            <button class="btn">
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_pie.png" width="20" height="20">
            </button>
           <button class="btn">
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_bar.png">
            </button>
       </section>
   </article>


   <article class="emne-box">
       <h3>
          <spring:message code="options.links" />
       </h3>
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
      <h3>
            <spring:message code="options.comments" />
      </h3>
      <c:if test="${logged}">
            <div name="comments" data-dojo-type="me/web/widget/comments/AddComment" type="tweetpoll" item_id="${tweetpoll.id}" username="${account.username}"></div>
      </c:if>
      <c:if test="${!logged}">
          <div>
            <a href="<%=request.getContextPath()%>/user/signin">
                <img src="<%=request.getContextPath()%>/picture/profile/demo10/profile" width="80" height="80"/>
              <b>
                  Log in to post a comment
              </b>
            </a>
          </div>
      </c:if>
      <div name="comments" data-dojo-type="me/web/widget/comments/Comments" type="tweetpoll" item_id="${tweetpoll.id}"></div>
   </section>

  </div>
</article>
