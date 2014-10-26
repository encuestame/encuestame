<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-tweetpoll-detail web-wrapper-detail">
    <div class="container">
        <article>
            <section class="web-tweetpoll-answer-wrapper web-wrapper-detail-wrapper">
                <div class="web-tweetpoll-answer-chart">
                    <div class="wrapper-detail-title">
                        <h4 data-relevance="${poll.relevance}">
                            ${poll.questionBean.questionName}
                        </h4>
                        <span class="badge badge-success">Votes ${poll.totalVotes}</span>
                        <span class="badge badge-warning">Hits ${poll.hits}</span>

                    </div>
                    <article class="web-detail-chart">
                        <div
                                data-dojo-type="me/web/widget/poll/detail/PollChartDetail"
                                pollId="${poll.id}"
                                percents="true"
                                username="${poll.ownerUsername}">
                        </div>
                    </article>
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
        <section class="web-tweetpoll-comments emne-box">
            <h5>
                <spring:message code="options.links" />
            </h5>
            <div data-dojo-type="me/web/widget/social/LinksPublished"
                 type="POLL"
                 more="false"
                 itemId="${poll.id}"
                 class="web-social-links">
            </div>
       </section>
        <c:if test = "${!poll.showComments == 'RESTRICT'}">
            <section class="web-tweetpoll-comments emne-box">
                <h5>
                    <spring:message code="options.comments" />
                </h5>
                <c:if test="${logged}">
                    <div name="comments" data-dojo-type="me/web/widget/comments/AddComment"
                         comment_limit="<%=EnMePlaceHolderConfigurer.getProperty("comments.max.length")%>"
                         type="poll"
                         isModerated="${isModerated}"
                         item_id="${poll.id}"
                         username="${account.username}"></div>
                </c:if>
                <c:if test="${!logged}">
                    <div class="row comment-login">
                        <div class="picture span2">
                            <img src="<%=request.getContextPath()%>/resources/images/default.png" width="60" height="60"/>
                        </div>
                        <div class="span4">
                            <div class="login">
                                <a href="<%=request.getContextPath()%>/user/signin">
                                    <h4 class="enme">
                                        <spring:message code="comments.login.post.comment" />
                                    </h4>
                                </a>
                            </div>
                        </div>
                        </a>
                    </div>
                </c:if>
                <div data-dojo-type="me/web/widget/comments/Comments" type="poll" item_id="${poll.id}"></div>
            </section>
        </c:if>
    </div>
</article>
