<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="item">
    <div class="content">
        <div class="title">
            <a href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}">
                <h4 class="enme">
                    ${item.questionBean.questionName}
                </h4>
            </a>
        </div>
        <!-- general information -->
        <div class="bottom">
            <div class="options">
                <div class="submited">
                    <spring:message code="submited_by" />
                     <a
                        href="<%=request.getContextPath()%>/profile/${item.ownerUsername}">${item.ownerUsername}</a>
                    <spring:message code="added" />
                    ${item.relativeTime} | <a
                        href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}#comments">
                            ${item.totalComments} <spring:message code="home_item_comments" /> </a> 
                </div>
            </div>
        </div>
    </div>
    <div class="img">
        <div data-dojo-type="me/web/widget/home/votes/ItemVote"
             voteMessage="<spring:message code="home_item_votes" />"
             viewMessage="<spring:message code="home_item_views" />"
             votes="${item.totalVotes}"
             hits="${item.hits}"
             itemType="${item.itemType}"
             itemId="${item.id}"
        ></div>
    </div>
    <div class="tags">
        <c:forEach items="${item.hashTags}" var="h">
            <span data-dojo-type="me/web/widget/stream/HashTagInfo"
                url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                hashTagName="${h.hashTagName}"></span>
        </c:forEach>
    </div>
</section>