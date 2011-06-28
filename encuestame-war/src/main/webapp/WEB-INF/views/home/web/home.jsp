<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
    <div id="mainPublicLineWrapper">
    <div class="itemWrapper" style="border: 0px solid #FFF; width: 79%;">
          <div class="categoryMenu">
                <div style="clear: both;">
                <div style="float: right;">
                    <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">Hot</a>
                    <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>">Week</a>
                    <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>">Month</a>
                    <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>">All</a>
                </div>
                <div style="float: left;">
                    <span><a href="<%=request.getContextPath()%>/home?view=survey">All Surveys</a></span>
                    <span><a href="<%=request.getContextPath()%>/home?view=tweetpoll">All TweetPoll</a></span>
                    <span><a href="<%=request.getContextPath()%>/home?view=poll">All Poll</a></span>
                </div>
                </div>
            </div>
    <c:forEach items="${items}" var="item">
     <div class="item">
           <div class="item">
                <div class="img">
                        <div class="stats">
                            <div class="vote">
                                <div>
                                    <span class="count"><strong>150</strong></span>
                                    <div class="viewcount">answers</div>
                                </div>
                            </div>
                            <div class="views">
                               50 views
                            </div>
                        </div>
                </div>
                <div class="content">
                    <h3>
                         <a href="<%=request.getContextPath()%>/tweetpoll/${item.id}/test">${item.questionBean.questionName}</a>
                    </h3>
                    <div style="padding: 2px;">

                    </div>
                    <div class="bottom">
                       <div class="share">
                                <span class="title">Share on:</span><br/>
                                <img src="resources/images/icons/enme_twitter.png" alt="Twitter" />
                                <img src="resources/images/icons/enme_facebook.png" alt="Facebook" />
                       </div>
                       <div class="options">
                            <div class="image">
                                    <img width="32" height="32" alt=""
                                    src="http://www.gravatar.com/avatar/6757caf55de0413ae0994293e001d465?s=32&amp;d=identicon&amp;r=PG"/>
                            </div>
                            <div>(Submited By <strong><a href="#">Jota</a></strong>) added <strong>45 minutes</strong> ago</div>
                            <div><img src="/resources/images/icons/enme_comment_reply.png"/> 25 Comments</div>

                       </div>
                       <div class="tags">
                            <c:forEach items="${item.hashTags}" var="h">
                                <a href="<%=request.getContextPath()%>/tag/${h.hashTagName}/" class="tag">${h.hashTagName}</a>
                           </c:forEach>
                       </div>
                    </div>
                </div>
                <p class="clear"></p>
            </div>
     </div>
   </c:forEach>
    </div>
</div>