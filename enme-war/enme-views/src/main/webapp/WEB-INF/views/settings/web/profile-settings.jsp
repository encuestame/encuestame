<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div data-dojo-type="me/web/widget/menu/SettingsMenuSwitch">
  <!-- Profile Widget -->
  <div id="profile_settings"
    data-dojo-type="me/web/widget/profile/Profile"
    username="${account.username}"
    email="${account.email}"
    complete_name="${account.name}"
    language="${account.language}"
    data-enabled="true"
    data-label="<spring:message code="settings.config.myaccount" />"></div>
  <!-- Upload Profile Image Widget -->

</div>