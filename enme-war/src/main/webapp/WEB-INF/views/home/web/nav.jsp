<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="nav-right">
    <span>
        <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">
            <spring:message code="home.category.hot" /> </a>
    </span>
    <span>
        <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>">
            <spring:message code="home.category.weeks" /> </a>
    </span>
    <span>
        <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>">
            <spring:message code="home.category.month" /> </a>
    </span>
    <span>
        <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>">
            <spring:message code="home.category.all" />
        </a>
    </span>
</div>
<div class="nav-left">
         <span>
            <a href="<%=request.getContextPath()%>/home">
                <spring:message code="home.category.all" /> </a>
         </span>
<%--         <span>
            <a href="<%=request.getContextPath()%>/home?view=survey">
                <spring:message code="home.type.surveys" /> </a>
         </span> --%>
         <span>
            <a href="<%=request.getContextPath()%>/home?view=tweetpoll">
                <spring:message code="home.type.tweetpoll" /> </a>
         </span>
         <span>
             <a href="<%=request.getContextPath()%>/home?view=poll">
                 <spring:message code="home.type.poll" />
             </a>
         </span>
</div>