<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="nav-home-menu">
    <div class="main-options">
           <span class="option">
                <a href="<%=request.getContextPath()%>/home?view=survey">
                    <spring:message code="home.type.surveys" />
               </a>
           </span>
           <span class="option">
               <a href="<%=request.getContextPath()%>/home?view=tweetpoll">
                    <spring:message code="home.type.tweetpoll" />
               </a>
           </span>
           <span class="option">
                 <a href="<%=request.getContextPath()%>/home?view=poll">
                        <spring:message code="home.type.poll" />
                 </a>
           </span>
    </div>
    <div class="hot-tags">
        <span class="optionTags">
            <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">
                <spring:message code="home.category.hot" />
            </a>
        </span>
        <span class="optionTags">
            <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>">
                <spring:message code="home.category.weeks" />
            </a>
        </span>
         <span class="optionTags"><a
            href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>">
                <spring:message code="home.category.month" />
            </a>
        </span>
         <span class="optionTags"><a
            href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>">
                <spring:message code="home.category.all" />
        </a></span>
    </div>
</div>