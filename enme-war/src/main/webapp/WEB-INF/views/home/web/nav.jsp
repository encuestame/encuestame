<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div style="float: right;">
    <a href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">
        <spring:message code="home.category.hot" /> </a> <a
        href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>"> |
        <spring:message code="home.category.weeks" /> </a> <a
        href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>"> |
        <spring:message code="home.category.month" /> </a> <a
        href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>"> |
        <spring:message code="home.category.all" /> </a>
</div>
<div style="float: left;">
        <span>
            <a href="<%=request.getContextPath()%>/home">
                <spring:message code="home.category.all" /> </a> |
         </span>
<%--         <span>
            <a href="<%=request.getContextPath()%>/home?view=survey">
                <spring:message code="home.type.surveys" /> </a>
         </span> --%>
         <span> |
            <a href="<%=request.getContextPath()%>/home?view=tweetpoll">
                <spring:message code="home.type.tweetpoll" /> </a>
         </span> |
         <span>
             <a href="<%=request.getContextPath()%>/home?view=poll">
                 <spring:message code="home.type.poll" />
             </a>
         </span>
</div>