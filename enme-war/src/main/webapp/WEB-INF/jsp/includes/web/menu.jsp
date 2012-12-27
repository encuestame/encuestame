<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="menuWrapper enme-auto-center">
    <div class="clearfix">
        <div class="rightSide clearfix">
            <c:if test="${logged}">
                <div class="web-notification">
                    <a data-dojo-type="me/web/widget/notifications/Notification"></a>
                </div>
                <div class=" web-profile"> <%@ include
                        file="/WEB-INF/jsp/includes/profile.jsp"%>
                </div>
            </c:if>
        </div>
        <div class="leftSide clearfix">
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
             <span class="item">
                <a href="<%=request.getContextPath()%>/user/tweetpoll/list">
                    <spring:message code="menu.tweetpoll" />
                </a>
            </span>
             <span class="item">
                <a href="<%=request.getContextPath()%>/user/poll">
                    <spring:message code="menu.poll" />
                </a>
            </span>
             <span class="item">
                <a href="<%=request.getContextPath()%>/user/survey">
                    <spring:message code="menu.survey" />
                </a>
            </span>
        </div>
    </div>
</div>
