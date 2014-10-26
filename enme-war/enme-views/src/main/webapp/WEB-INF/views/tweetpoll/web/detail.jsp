<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<article class="web-tweetpoll-detail web-wrapper-detail">

   <div class="container">
   <article>
        <section class="web-tweetpoll-answer-wrapper web-wrapper-detail-wrapper">
              <div class="web-tweetpoll-answer-chart">
                  <div class="wrapper-detail-title">
                      <h4 data-relevance="${tweetpoll.relevance}">
                          ${tweetpoll.questionBean.questionName}
                      </h4>
                      <span class="badge badge-success">Votes ${tweetpoll.totalVotes}</span>
                      <span class="badge badge-warning">Hits ${tweetpoll.hits}</span>

                  </div>
                  <div id="chart" data-dojo-type="me/web/widget/tweetpoll/detail/TweetPollChartDetail"
                         tweetPollid="${tweetpoll.id}"
                         question="${tweetpoll.questionBean.questionName}"
                         completed="${tweetpoll.completed}"
                         username="${tweetpoll.ownerUsername}">
                    </div>
              </div>
              <div class="web-tweetpoll-answer-answer">
                  <div class="header-answers">
                       <div class="answer-label">
                             <spring:message code="commons_detail_answer" />
                       </div>
                       <div class="answer-votes">
                              <spring:message code="commons_detail_total_votes" />
                       </div>
                       <div class="answer-percent">
                             <spring:message code="commons_detail_percent" />
                       </div>
                  </div>
                  <div class="group-answers">
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
                  <c:if test="${!empty hashtags}">
                      <div class="hashtag-detail-group hashtag-invert">
                          <c:forEach items="${hashtags}" var="h">
                                <span data-dojo-type="me/web/widget/stream/HashTagInfo"
                                      url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                                      hashTagName="${h.hashTagName}"></span>
                          </c:forEach>
                      </div>
                  </c:if>
              </div>
       </section>
   </article>


   <article class="emne-box">
       <h5>
          <spring:message code="options.links" />
       </h5>
       <section>
              <div data-dojo-type="me/web/widget/social/LinksPublished"
                 more="false"
                 itemId="${tweetpoll.id}"
                 type="TWEETPOLL"
                 class="web-social-links">
               </div>
       </section>
   </article>

   <section class="web-tweetpoll-comments emne-box">
      <h5>
            <spring:message code="options.comments" />
      </h5>

      <c:if test="${logged}">
            <div name="comments" data-dojo-type="me/web/widget/comments/AddComment"
                 comment_limit="<%=EnMePlaceHolderConfigurer.getProperty("comments.max.length")%>"
                 type="tweetpoll"
                 isModerated="${isModerated}"
                 item_id="${tweetpoll.id}"
                 username="${account.username}"></div>
      </c:if>
      <c:if test="${!logged}">
          <div class="comment-login">
              <div class="picture">
                  <img src="<%=request.getContextPath()%>/resources/images/default.png" width="60" height="60"/>
              </div>
              <div class="form-area">
                  <a href="<%=request.getContextPath()%>/user/signin">
                    <h6>
                        <spring:message code="comments.login.post.comment" />
                    </h6>
                  </a>
              </div>
            </a>
          </div>
      </c:if>
      <div name="comments" data-dojo-type="me/web/widget/comments/Comments" type="tweetpoll" item_id="${tweetpoll.id}"></div>
   </section>

  </div>
</article>
