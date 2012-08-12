<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div dojoType="encuestame.org.core.commons.profile.ProfileMenu" 
     username="${account.username}" 
     completeName="${account.email}">
      <div class="profile-menu hidden" data-url="${contextDefaultPath}/settings/configuration">
          <spring:message  code="profile.menu.configuration" /> 
      </div>
      <div class="profile-menu hidden" data-url="${contextDefaultPath}/settings/social">
          <spring:message  code="profile.menu.social" /> 
      </div>   
      <div class="profile-menu hidden" data-url="${contextDefaultPath}/user/help">
          <spring:message  code="profile.menu.help" /> 
      </div>   
      <div class="profile-menu hidden" data-url="${contextDefaultPath}/user/logout">
          <spring:message  code="profile.menu.logout" /> 
      </div>         
</div>