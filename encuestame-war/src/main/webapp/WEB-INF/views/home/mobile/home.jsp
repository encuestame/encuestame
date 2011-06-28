<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/jsp/includes/mobile/search.jsp"%>
<div class="home-mobile">
    <div class="mobile-categoryMenu">
                <div class="menu-options">
                    <span class="option"><a href="<%=request.getContextPath()%>/home?view=survey">Surveys</a></span>
                    <span class="option"><a href="<%=request.getContextPath()%>/home?view=tweetpoll">TweetPoll</a></span>
                    <span class="option"><a href="<%=request.getContextPath()%>/home?view=poll">Polls</a></span>
                </div>
                <div class="mobile-MenuHotTags">
                    <span class="optionTags"><a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">Hot</a></span>
                    <span class="optionTags"><a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>">Week</a></span>
                    <span class="optionTags"><a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>">Month</a></span>
                    <span class="optionTags"><a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>">All</a></span>
                </div>
    </div>
    <div class="mobile-items">
        <c:forEach items="${items}" var="item">
        <div class="mobile-item">
                <div class="mobile-view">
                    <div>
                        <div>
                            <div class="votes">
                                <span class="count">141</span>
                                <span class="voteText">Votes</span>
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
                            <div class= "mobile-submit-text">(Submited By <strong><a href="#">${item.ownerUsername}</a></strong>) added <strong>46 minutes</strong> ago |  25 Comments</div>
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
