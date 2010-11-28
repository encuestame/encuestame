<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initBody.jsp" %>
<script type="text/javascript">
  dojo.require("dijit.Dialog");
  dojo.require("dijit.form.TextBox");
  dojo.require("dijit.form.TimeTextBox");
  dojo.require("dijit.form.Button");
  dojo.require("dijit.form.DateTextBox");
  dojo.require("encuestame.org.core.admon.project.Projects");
</script>
<div>
  <div dojoType="encuestame.org.core.admon.project.Projects"></div>
</div>
<%@ include file="/WEB-INF/jsp/includes/endBody.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/validate.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>