<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-wrapper-detail web-poll-results web-wrapper-detail">

  <div class="container">

    <div class="row web-generic-stats">
        <div class="span12" id="generic" widgetid="generic">
          <div class="row">
            <div class="span1">
              <div class="enme-rating">
                <div class="vote">
                    <div class="count">
                       ${poll.totalVotes}
                    </div>
                    vote(s)
                </div>
                <div class="count-sub" data-dojo-attach-point="_vote">
                  ${poll.hits}
                  average</div>
              </div>
            </div>
            <div class="span9">
              <div>
                <h2>
                 ${poll.questionBean.questionName}
                </h2>
                <span class="badge badge-success" data-dojo-attach-point="_badge">
                  ${poll.relevance}
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
        <section class="web-wrapper-detail-wrapper row">

            <div class="span6">
                  <article class="web-detail-chart">
                      <div
                         data-dojo-type="me/web/widget/poll/detail/PollChartDetail"
                         pollId="${poll.id}"
                         percents="true"
                         username="${poll.ownerUsername}">
                    </div>
                  </article>
            </div>

            <div class="span6">
                <article class="web-detail-answer-wrapper">
                    <header>
                         <div class="answer-label span2">
                              <spring:message code="commons_detail_answer" />
                         </div>
                         <div class="answer-votes span1">
                              <spring:message code="commons_detail_total_votes" />
                         </div>
                         <div class="answer-percent span1">
                              <spring:message code="commons_detail_percent" />
                         </div>
                    </header>
                    <div class="answer-wrapper">
                      <c:forEach items="${answers}" var="item">
                           <section data-dojo-type="me/web/widget/results/answers/GenericPercentResult"
                             itemId="${item.answerBean.answerId}"
                             color="${item.answerBean.color}"
                             votes="${item.result}"
                             percent="${item.percent}"
                             questionId="${item.answerBean.questionId}"
                             labelResponse="${item.answerBean.answers}">
                           </section>
                       </c:forEach>
                    </div>
                     <div class="web-poll-options-button">
                       <a href="<%=request.getContextPath()%>/poll/vote/${poll.id}/${poll.questionBean.slugName}">
                         <button class=" btn btn-info btn-block">
                           <spring:message code="options.vote" />
                         </button>
                       </a>
                     </div>
                </article>
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
                 type="POLL"
                 more="false"
                 itemId="${poll.id}"
                 class="web-social-links">
            </div>
       </section>
    </article>
   <section class="web-tweetpoll-comments emne-box">
      <h3>
            <spring:message code="options.comments" />
      </h3>
      <div data-dojo-type="me/web/widget/comments/Comments" type="poll" item_id="${poll.id}"></div>
   </section>
 </div>
</article>