<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div dojoType="encuestame.org.core.commons.profile.ProfileMenu" 
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