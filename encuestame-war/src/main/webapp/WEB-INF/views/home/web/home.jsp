<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script type="text/javascript">
     dojo.require("encuestame.org.core.commons.notifications.NotificationList");
</script>
<div id="web-main-public-line" class="enme-auto-center">
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
    <div class="web-left-wrapper">
         <div dojoType="encuestame.org.core.commons.notifications.NotificationList"></div>
    </div>
    <div class="web-item-wrapper">
        <div >
        <c:forEach items="${items}" var="item">
           <div class="item">
                <div class="img">
                        <div class="stats">
                            <div class="vote">
                                <div>
                                    <span class="count"><strong>${item.totalVotes}</strong></span>
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
                    <div class="bottom">
                       <div class="share">
                                <span class="title">Share on:</span>
                                <img src="<%=request.getContextPath()%>/resources/images/social/twitter/enme_twitter.png" alt="Twitter" />
                                <img src="<%=request.getContextPath()%>/resources/images/social/facebook/enme_facebook.png" alt="Facebook" />
                       </div>
                       <div class="options">
                            <div class="image">
                                    <img width="32" height="32" alt=""
                                    src="http://www.gravatar.com/avatar/6757caf55de0413ae0994293e001d465?s=32&amp;d=identicon&amp;r=PG"/>
                            </div>
                            <div>(Submited By
                                <strong>
                                    <a href="<%=request.getContextPath()%>/profile/${item.ownerUsername}">${item.ownerUsername}</a>
                                    </strong>
                                        ) added
                                    <strong>
                                ${item.relativeTime}</strong>
                            </div>
                            <div>
                                <img src="/resources/images/icons/enme_comment_reply.png"/> 25 Comments
                            </div>
                       </div>
                       <div class="tags">
                            <c:forEach items="${item.hashTags}" var="h">
                                <a href="<%=request.getContextPath()%>/tag/${h.hashTagName}/" class="tag">${h.hashTagName}</a>
                           </c:forEach>
                       </div>
                    </div>
                </div>
          </div>
        </c:forEach>
        </div>
    </div>
</div>