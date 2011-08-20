<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<enme:require>
encuestame.org.core.commons.hashtags.Cloud
encuestame.org.core.commons.rated.Comments
encuestame.org.core.commons.rated.HashTags
encuestame.org.core.commons.rated.Users
encuestame.org.core.commons.stream.HashTagInfo
encuestame.org.core.commons.stream.FrontEnd
encuestame.org.core.commons.stream.FrontEndItem
encuestame.org.core.shared.utils.AccountPicture
</enme:require>
<div id="web-main-public-line" class="enme-auto-center">
    <div class="web-item-wrapper">
        <div class="categoryMenu">
             <div style="clear: both;">
                 <div style="float: right;">
                     <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">
                        <spring:message code="home.category.hot" />
                     </a>
                     <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>">
                        <spring:message code="home.category.weeks" />
                     </a>
                     <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>">
                        <spring:message code="home.category.month" />
                     </a>
                     <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>">
                        <spring:message code="home.category.all" />
                    </a>
                 </div>
                 <div style="float: left;">
                     <span><a href="<%=request.getContextPath()%>/home?view=survey">
                        <spring:message code="home.type.surveys" />
                        </a></span>
                     <span><a href="<%=request.getContextPath()%>/home?view=tweetpoll">
                        <spring:message code="home.type.tweetpoll" />
                        </a></span>
                     <span><a href="<%=request.getContextPath()%>/home?view=poll">
                        <spring:message code="home.type.poll" />
                     </a></span>
                 </div>
             </div>
        </div>
        <div class="items">
        <c:forEach items="${items}" var="item">
           <div class="item">
                <div class="img">
                        <div class="stats">
                            <div class="vote">
                                <div>
                                    <span class="count"><strong>${item.totalVotes}</strong></span>
                                    <div class="viewcount">
                                         <spring:message code="home.item.votes" />
                                    </div>
                                </div>
                            </div>
                             <div class="views">
                               50 <spring:message code="home.item.views" />
                            </div>
                        </div>
                </div>
                <div class="content">
                    <div class="title">
                         <a href="<%=request.getContextPath()%>/tweetpoll/${item.id}/${item.questionBean.slugName}">${item.questionBean.questionName}</a>
                    </div>
                    <div class="bottom">
                       <div class="options">
                            <div class="image">
                                 <a dojoType="encuestame.org.core.shared.utils.AccountPicture" username=${item.ownerUsername}></a>
                            </div>
                            <div class="share">
                                    <span class="title">Share on:</span>
                                    <img src="<%=request.getContextPath()%>/resources/images/social/twitter/enme_icon_twitter.png" alt="Twitter" />
                                    <img src="<%=request.getContextPath()%>/resources/images/social/facebook/enme_icon_facebook.png" alt="Facebook" />
                                </div>
                            <div class="submited">
                                <spring:message code="submited.by" />
                                    <strong>
                                        <a href="<%=request.getContextPath()%>/profile/${item.ownerUsername}">${item.ownerUsername}</a>
                                    </strong>
                                         <spring:message code="added" />
                                    <strong>
                                        ${item.relativeTime} | <br>
                                        <a href="<%=request.getContextPath()%>/tweetpoll/${item.id}/test#comments">
                                           25  <spring:message code="home.item.comments" />
                                        </a>
                                    </strong>
                            </div>
                       </div>
                       <div class="tags">
                            <c:forEach items="${item.hashTags}" var="h">
                                   <span dojoType="encuestame.org.core.commons.stream.HashTagInfo"
                                    url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                                    hashTagName="${h.hashTagName}"></span>
                           </c:forEach>
                       </div>
                    </div>
                </div>
          </div>
        </c:forEach>
        <div dojoType="encuestame.org.core.commons.stream.FrontEnd"></div>
        </div>
    </div>
    <div class="web-left-wrapper">
         <div class="section">
            <div class="sectionTitle">More Popular HashTags</div>
            <div dojoType="encuestame.org.core.commons.hashtags.Cloud"></div>
            <div class="link">
                 <a href="<%=request.getContextPath()%>/cloud">View All</a>
            </div>
         </div>
         <div class="section">
            <div class="sectionTitle">Rated Comments</div>
            <div dojoType="encuestame.org.core.commons.rated.Comments"></div>
         </div>
         <div class="section">
            <div class="sectionTitle">Rated HashTags</div>
            <div dojoType="encuestame.org.core.commons.rated.HashTags"></div>
         </div>
         <div class="section">
            <div class="sectionTitle">Rated Users</div>
            <div dojoType="encuestame.org.core.commons.rated.Users"></div>
         </div>
    </div>
</div>