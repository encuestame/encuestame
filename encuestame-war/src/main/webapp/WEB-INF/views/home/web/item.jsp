<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<section class="item">
    <div class="img">
        <div dojoType="encuestame.org.core.home.votes.ItemVote"
        	 voteMessage="<spring:message code="home.item.votes" />"
        	 viewMessage="<spring:message code="home.item.views" />"
        	 votes="${item.relevance}"
        	 hits="${item.hits}"
       	></div>
    </div>
    <div class="content">
        <div class="title">
            <a href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}">${item.questionBean.questionName}</a>
        </div>
        <!-- general information -->
        <div class="bottom">
            <div class="options">
                <div class="image">
                    <a dojoType="encuestame.org.core.shared.utils.AccountPicture"
                        username=${item.ownerUsername}></a>
                </div>
                <div class="submited">
                    <spring:message code="submited.by" />
                    <strong> <a
                        href="<%=request.getContextPath()%>/profile/${item.ownerUsername}">${item.ownerUsername}</a>
                    </strong>
                    <spring:message code="added" />
                    <strong> ${item.relativeTime} | <br> <a
                        href="<%=request.getContextPath()%>/${item.itemType}/${item.id}/${item.questionBean.slugName}#comments">
                            ${item.totalComments} <spring:message code="home.item.comments" /> </a> </strong>
                </div>
            </div>
            <!-- List of Hashtags -->
            <div class="tags">
                <c:forEach items="${item.hashTags}" var="h">
                    <span dojoType="encuestame.org.core.commons.stream.HashTagInfo"
                        url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                        hashTagName="${h.hashTagName}"></span>
                </c:forEach>
            </div>
        </div>
        <div class="stats-rating">
              <nav>
                  <div class="stats-rating-section">
                        <div class="like total">2134</div>
                        <div class="image">
                            <img src="<%=request.getContextPath()%>/resources/images/icons/enme_like.png" width="20px" height="20px" />
                        </div>
                  </div>
                  <div class="stats-rating-section">
                       <div class="unlike total">213</div>
                        <div class="image">
                            <img src="<%=request.getContextPath()%>/resources/images/icons/emne_unlike.png" width="20px" height="20px" />
                       </div>
                  </div>
              </nav>
       </div>
    </div>
</section>