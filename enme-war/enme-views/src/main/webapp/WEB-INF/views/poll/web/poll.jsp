<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-wrapper-detail web-poll-results web-wrapper-detail">
   <div class="web-detail-header">
       <div class="title">
         <h2>
              ${poll.questionBean.questionName}
         </h2>
       </div>
   </div>
   <article class="emne-box">
        <section class="web-wrapper-detail-wrapper">
            <article class="web-detail-chart">
                <div
                   data-dojo-type="me/web/widget/poll/detail/PollChartDetail"
                   pollId="${poll.id}"
                   percents="true"
                   username="${poll.ownerUsername}">
              </div>
            </article>
            <article class="web-detail-answer-wrapper">
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
                 <div class="web-poll-options-button">
                   <a href="<%=request.getContextPath()%>/poll/vote/${poll.id}/${poll.questionBean.slugName}">
                     <button class="enme-ui-button vote">
                       <spring:message code="options.vote" />
                     </button>
                   </a>
                 </div>
            </article>
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
   <article class="emne-box">
       <header>
          <spring:message code="options.links" />
       </header>
       <section>
            <div data-dojo-type="me/web/widget/social/LinksPublished"
                 type="POLL"
                 more="false"
                 itemId="${poll.id}"
                 class="web-social-links">
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
   <section class="web-tweetpoll-comments emne-box">
      <header>
          <spring:message code="options.comments" />
      </header>
      <div data-dojo-type="me/web/widget/comments/Comments" type="poll" item_id="${poll.id}"></div>
   </section>
</article>