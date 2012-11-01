<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<script type="text/javascript">
  //dojo.require("encuestame.org.core.commons.validator.AbstractValidatorWidget");
  //dojo.require("encuestame.org.core.commons.validator.PasswordValidator");
  //dojo.require("encuestame.org.core.commons.validator.EmailValidator");
  //dojo.require("encuestame.org.core.commons.validator.PasswordValidator");
  //dojo.require("encuestame.org.core.commons.validator.RealNameValidator");
  //dojo.require("encuestame.org.core.commons.validator.UsernameValidator");
  //dojo.require("encuestame.org.core.shared.utils.StandBy");
  //dojo.require("encuestame.org.core.commons.signup.Signup");
  //dojo.require("encuestame.org.core.commons.panel.PanelBar");
  //dojo.require("dijit.form.Form");
  encuestame.constants.passwordExcludes =  <%=WidgetUtil.getPasswordBlackList("passwords.inc")%>;
</script>