<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<div id="web-tweetpoll-wrapper" class="enme-main-section web-wrapper-detail">
    <div class="admon-table-options panel-header">
       <div class="tb-left">
            <h3>
               <spring:message code="settings.config.title" />
            </h3>
            <p>
               <spring:message code="settings.config.title" />
            </p>
       </div>
    </div>
    <div data-dojo-type="me/web/widget/menu/SettingsMenuSwitch">
        <div id="profile_settings"
          data-dojo-type="me/web/widget/profile/Profile"
          username="${account.username}"
          email="${account.email}"
          complete_name="${account.name}"
          language="${account.language}"
          data-enabled="true"
          data-label="<spring:message code="settings.config.myaccount" />"></div>
        <div id="upload_image_settings"
             data-dojo-type="me/web/widget/profile/UploadProfilePicture"
              data-enabled="false"
              username="${account.username}"
              pictureSource="${account.pictureSource}"
              data-label="<spring:message code="settings.config.upload.image" />"></div>
  </div>
</div>