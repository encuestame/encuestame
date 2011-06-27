<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript">
  dojo.require("encuestame.org.mobile.notifications.NotificationList");
  dojo.require("encuestame.org.core.commons.notifications.NotificationList");
  console.log("dsadsadsa");
  var mytext = "Hello again222";
  document.write(mytext);
</script>
<div> mobile -----
    <script type="text/javascript">document.write(dojo.toJson(config));
    console.log(dojo.toJson(config));
    console.log(document.body)</script>
    <div dojoType="encuestame.org.core.commons.notifications.NotificationList"></div>
    <div dojoType="encuestame.org.mobile.notifications.NotificationList"></div>
     mobile 44 -----
</div>