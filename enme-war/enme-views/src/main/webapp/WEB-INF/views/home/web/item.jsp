<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="item" id="home-item">
    <div class="content">
        <div class="tags">
            <c:forEach items="${item.hashTags}" var="h">
            <span data-dojo-type="me/web/widget/stream/HashTagInfo"
                  url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                  hashTagName="${h.hashTagName}"></span>
            </c:forEach>
        </div>
        <div class="title">
            <a id="item-question" href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}">
                <h2>
                    ${item.questionBean.questionName}
                </h2>
            </a>
        </div>
        <div class="bottom user-info">
            <div class="options">
                <div class="submited">
                    <spring:message code="submited_by" />
                     <a id="submited" href="<%=request.getContextPath()%>/profile/${item.ownerUsername}">
                        ${item.ownerUsername}
                     </a><span>,</span>
                    <spring:message code="added" /> ${item.relativeTime}
                </div>
            </div>
        </div>

        <div class="vote-item">
                <!-- vote page -->
                <div class="item-vote-block left">
                    <div data-dojo-type="me/web/widget/home/votes/ItemVote"
                         voteMessage="<spring:message code="home_item_votes"/>"
                        viewMessage="<spring:message code="home_item_views" />"
                        votes="${item.totalVotes}"
                        hits="${item.hits}"
                        voteUp="${item.voteUp}"
                        commentsTotal="${item.totalComments}"
                        itemType="${item.itemType}"
                        itemId="${item.id}">
                    </div>
                </div>
                <div class="item-vote-block left">
                    <div class="enme-rating">
                        <span class="vote_item_link_wrapper">
                            <span id="vote-comments-button" class="button_vote">
                                <spring:message code="home_item_comments" />
                                <span class="count">
                                    ${item.totalComments}
                                </span>
                            </span>
                        </span>
                    </div>
                 </div>
                 <div class="item-vote-block left">
                    <div class="enme-rating">
                        <span class="vote_item_link_wrapper">
                            <span id="vote-hits-button" class="button_vote">
                                Hits
                                <spring:message code="home_item_hits" />
                                <span class="count">
                                    ${item.hits}
                                </span>
                            </span>
                        </span>
                    </div>
                </div>
        </div>
</section>