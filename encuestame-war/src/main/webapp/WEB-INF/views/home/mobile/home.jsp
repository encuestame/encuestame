<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/mobile/search.jsp"%>
<div class="home-mobile">
    <div class="mobile-categoryMenu">
                <div class="menu-options">
                    <span class="option"><a href="<%=request.getContextPath()%>/home?view=survey">
                    <spring:message code="home.type.surveys" /></a></span>
                    <span class="option"><a href="<%=request.getContextPath()%>/home?view=tweetpoll">
                    <spring:message code="home.type.tweetpoll" /></a></span>
                    <span class="option"><a href="<%=request.getContextPath()%>/home?view=poll">
                    <spring:message code="home.type.poll" /></a></span>
                </div>
                <div class="mobile-MenuHotTags">
                    <span class="optionTags"><a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">
                     <spring:message code="home.category.hot" />
                     </a></span>
                    <span class="optionTags"><a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>">
                    <spring:message code="home.category.weeks" />
                    </a></span>
                    <span class="optionTags"><a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>">
                    <spring:message code="home.category.month" /></a></span>
                    <span class="optionTags"><a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>">
                     <spring:message code="home.category.all" /></a></span>
                </div>
    </div>
    <div class="mobile-items">
        <c:forEach items="${items}" var="item">
        <div class="mobile-item">
                <div class="mobile-view">
                    <div>
                        <div>
                            <div class="votes">
                                <span class="count"><strong>${item.totalVotes}</strong></span>
                                <span class="voteText"><spring:message code="home.item.votes" /></span>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="mobile-content">
                    <div class="mobile-tweetpoll">
                        <a href="<%=request.getContextPath()%>/tweetpoll/${item.id}/test">${item.questionBean.questionName}</a>
                    </div>
                    <div class="mobile-submit-bottom">
                        <div class="mobile-submit-options">
                            <div class= "mobile-submit-text">( <spring:message code="submited.by" />
                            <strong>
                               <a href="<%=request.getContextPath()%>/profile/${item.ownerUsername}">${item.ownerUsername}</a>
                             </strong>) <spring:message code="added" /> <strong>${item.relativeTime}</strong>
                              |  25 <spring:message code="home.item.comments" /></div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        <div class="mobile-items-more">
               See next 10 items...
        </div>
  </div>
  <div class="mobile-home-subtitle">
    Hot HashTags
  </div>
    <div class="mobile-HashTags">
        <c:forEach items="${hashTags}" var="tag">
            <span class="mobile-optionTag"><a href="<%=request.getContextPath()%>/tag/${tag.hashTagName}">${tag}</a> |</span>
        </c:forEach>
    <div class="mobile-hashTag-cloud"> <a href="<%=request.getContextPath()%>/cloud"> Cloud </a></div>
    </div>
</div>
