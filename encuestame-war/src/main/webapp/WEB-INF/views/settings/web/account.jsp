<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div dojoType="encuestame.org.core.shared.utils.SettingsMenuSwitch">
	<!-- Profile Widget -->
	<div id="profile_settings"
		dojoType="encuestame.org.core.commons.profile.Profile"
		username="${account.username}"
		email="${account.email}"
		complete_name="${account.name}"
		language="${account.language}"
		data-enabled="true"
		data-label="<spring:message code="settings.config.myaccount" />"></div>
	<!-- Upload Profile Image Widget -->
	<div id="upload_image_settings" 
		dojoType="encuestame.org.core.commons.profile.UploadProfilePicture"
		data-enabled="false"
		data-label="<spring:message code="settings.config.upload.image" />"></div>
</div>