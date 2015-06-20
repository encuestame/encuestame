<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="nav-home-menu main-nav">
  <div class="container">
      <ul class="nav">
           <li class="nav-item">
               <a id="tp-home-nav" class="nav-link" href="<%=request.getContextPath()%>/home?view=tweetpoll">
                    <spring:message code="home.type.tweetpoll" />
               </a>
           </li>
           <li class="nav-item">
                 <a id="poll-home-nav" class="nav-link" href="<%=request.getContextPath()%>/home?view=poll">
                        <spring:message code="home.type.poll" />
                 </a>
           </li>
      </ul>
      <ul class="nav right-nav">
          <li class="nav-item">
              <a id="hot-home-nav" class="nav-link" href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "24")%>">
                  <spring:message code="home.category.hot" />
              </a>
          </span>
          <li class="nav-item">
              <a  id="week-home-nav"class="nav-link" href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "7")%>">
                  <spring:message code="home.category.weeks" />
              </a>
          </li>
           <li class="nav-item">
            <a id="month-home-nav" class="nav-link" href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "30")%>">
                  <spring:message code="home.category.month" />
              </a>
          </li>
           <li class="nav-item">
            <a id="all-home-nav" class="nav-link" href="<%=WidgetUtil.getHomeFilterPeriodParameter(request, "all")%>">
                  <spring:message code="home.category.all" />
          </a></span>
      </ul>
</div>