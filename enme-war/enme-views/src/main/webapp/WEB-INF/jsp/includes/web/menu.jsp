<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
 <div class="g-nav nav-admon">
    <div class="nav-home-menu main-nav">
      <div class="container">
          <ul class="nav">
             <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/admon/members">
                   <spring:message code="menu.members" />
                </a>
             </li>
             <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/user/tweetpoll/list">
                    <spring:message code="menu.tweetpoll" />
                </a>
             </li>
              <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/user/poll">
                    <spring:message code="menu.poll" />
                </a>
              </li>
          </ul>
          <ul class="nav right-nav">
              <li class="nav-item">
                  <a class="nav-link-not" data-dojo-type="me/web/widget/notifications/Notification"></a>
              </li>
              <li class="nav-item">
                    <div data-dojo-type="me/web/widget/profile/ProfileMenu"
                             username="${account.username}"
                             completeName="${account.email}">
                        <div class="profile-menu hidden" data-url="<%=request.getContextPath()%>/settings/configuration">
                            <spring:message  code="profile.menu.configuration" />
                        </div>
                        <div class="profile-menu hidden" data-url="<%=request.getContextPath()%>/settings/social">
                            <spring:message  code="profile.menu.social" />
                        </div>
                        <div class="profile-menu hidden" data-url="<%=request.getContextPath()%>/user/help">
                            <spring:message  code="profile.menu.help" />
                        </div>
                        <div class="profile-menu hidden" data-url="<%=request.getContextPath()%>/user/logout">
                            <spring:message  code="profile.menu.logout" />
                        </div>
                    </div>
              </li>
           </ul>
        </div>
    </div>
 </div>