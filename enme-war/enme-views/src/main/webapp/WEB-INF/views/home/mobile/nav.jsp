<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="nav-home-menu container">
    <ul class="type-filter">
         <li>
             <a href="<%=request.getContextPath()%>/home?view=tweetpoll">
                  <spring:message code="home.type.tweetpoll" />
             </a>
         </li>
         <li>
             <a href="<%=request.getContextPath()%>/home?view=poll">
                    <spring:message code="home.type.poll" />
             </a>
         </li>
    </ul>
    <ul class="hot-tags">
        <li>
            <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">
                <spring:message code="home.category.hot" />
            </a>
        </span>
        <li>
            <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>">
                <spring:message code="home.category.weeks" />
            </a>
        </li>
         <li><a
            href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>">
                <spring:message code="home.category.month" />
            </a>
        </li>
         <li><a
            href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>">
                <spring:message code="home.category.all" />
        </a>
    </ul>
</div>