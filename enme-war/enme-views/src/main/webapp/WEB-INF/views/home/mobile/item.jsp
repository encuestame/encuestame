<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="item">
    <div class="content">
        <div class="title">
            <a id="item-question" href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}">
                <h2 class="enme">
                     ${item.questionBean.questionName}
                </h2>
            </a>
        </div>
        <div class="bottom user-info">
             <div class="options">
                 <div class="submited">
                     <spring:message code="submited_by" />
                         <a id="submited" href="<%=request.getContextPath()%>/profile/${item.ownerUsername}">${item.ownerUsername}</a>
                     <spring:message code="added" />
                      ${item.relativeTime} |
                          <a id="vote-comments-button" href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}#comments">
                             ${item.totalComments} <spring:message code="home_item_comments" />
                          </a>
                 </div>
             </div>
         </div>
        <div class="vote-item">
            <div class="item-vote-block left">
                <div data-dojo-type="me/web/widget/home/votes/ItemVote"
                     voteMessage="<spring:message code="home_item_votes"/>"
                viewMessage="<spring:message code="home_item_views" />"
                votes="${item.totalVotes}"
                hits="${item.hits}"
                voteUp="${item.voteUp}"
                commentsTotal="${item.totalComments}"
                itemType="${item.itemType}"
                itemId="${item.id}"></div>
            </div>
        </div>

    </div>
</section>