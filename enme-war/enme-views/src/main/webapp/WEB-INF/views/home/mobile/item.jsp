<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="item">
    <div class="img">
      <!--
        TODO: votes == relevance???
        -->
        <div data-dojo-type="me/web/widget/home/votes/ItemVote"
           voteMessage="<spring:message code="home.item.votes" />"
           viewMessage="<spring:message code="home.item.views" />"
           votes="${item.totalVotes}"
           hits="${item.hits}"
           itemType="${item.itemType}"
           itemId="${item.id}"
        ></div>
    </div>
    <div class="content">
         <%--  <div class="image">
            <a data-dojo-type="me/web/widget/pictures/AccountPicture" username="${item.ownerUsername}"></a>
         </div> --%>
        <div class="title">
            <a href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}">
                <h1>
                     ${item.questionBean.questionName}
                </h1>
            </a>
        </div>
         <!-- List of Hashtags -->
        <div class="tags">
            <c:forEach items="${item.hashTags}" var="h">
                    <span data-dojo-type="me/web/widget/stream/HashTagInfo"
                          url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                          hashTagName="${h.hashTagName}">
                    </span>
            </c:forEach>
        </div>
    </div>
     <!-- general information -->
     <div class="bottom">
         <div class="options">
             <div class="submited">
                 <spring:message code="submited.by" />
                 <strong>
                     <a href="<%=request.getContextPath()%>/profile/${item.ownerUsername}">${item.ownerUsername}</a>
                 </strong>
                 <spring:message code="added" />
                 <strong> ${item.relativeTime} |
                      <a href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}#comments">
                         ${item.totalComments} <spring:message code="home.item.comments" />
                      </a>
                 </strong>
             </div>
         </div>
     </div>
</section>