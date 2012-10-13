<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="menuWrapper enme-auto-center enme-ui-gradient-header-color">
    <div class="rightSide">
        <c:if test="${logged}">
            <span class="section">
                <div dojoType="encuestame.org.core.commons.notifications.Notification"></div>
            </span>
            <span class="section"> <%@ include
                    file="/WEB-INF/jsp/includes/profile.jsp"%>
            </span>
        </c:if>
    </div>
    <div class="leftSide">
        <span class="item">
            <a
            href="<%=request.getContextPath()%>/admon/members"> <spring:message
                    code="menu.members" /> </a>
        </span>
<!--         <span class="item"> -->
<!--             <a -->
<%--             href="<%=request.getContextPath()%>/admon/location"> <spring:message --%>
<!--                     code="menu.locations" /> </a> -->
<!--         </span> -->
<!--         <span class="item"> -->
<!--             <a -->
<%--             href="<%=request.getContextPath()%>/admon/project"> <spring:message --%>
<!--                     code="menu.project" /> </a> -->
<!--         </span> -->
         <span class="item"><a
            href="<%=request.getContextPath()%>/user/tweetpoll/list"> <spring:message
                    code="menu.tweetpoll" /> </a>
        </span>
         <span class="item"><a
            href="<%=request.getContextPath()%>/user/poll"> <spring:message
                    code="menu.poll" /> </a>
        </span>
         <span class="item"> <a
            href="<%=request.getContextPath()%>/user/survey"> <spring:message
                    code="menu.survey" />
        </a>
        </span>
    </div>
    <c:if test="${logged}">
         <div dddojoType="encuestame.org.core.shared.utils.Toaster" duration="<%=EnMePlaceHolderConfigurer.getProperty("not.toaster.duration")%>"
           messageTopic="/encuestame/message/publish"
           positionDirection="<%=EnMePlaceHolderConfigurer.getProperty("not.toaster.position")%>"
           id="toasted_message">
    </div>
    </c:if>
</div>
