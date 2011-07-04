 <%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
 <div class="menuWrapper enme-auto-center">
    <ul>
        <li>
            <a style="margin-left: 5px;">
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_home.png" alt="Home" />
            </a>
        </li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/members">
                <spring:message code="menu.members" />
                </a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/location">
                <spring:message code="menu.locations" />
            </a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/admon/project">
            <spring:message code="menu.project" />
            </a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/user/tweetpoll/list">
            <spring:message code="menu.tweetpoll" />
            </a></li>
        <li class="item"><a href="<%=request.getContextPath()%>/user/poll">
            <spring:message code="menu.poll" />
            </a></li>
        <li class="item">
            <a href="<%=request.getContextPath()%>/user/survey">
                <spring:message code="menu.survey" /></a></li>
    </ul>
    <div style="float: right;">
        <div dojoType="encuestame.org.core.commons.notifications.Notification"></div>
    </div>
    <br style="clear: left" />
    </div>
