<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script type="text/javascript">
    dojo.require("encuestame.org.core.commons.poll.Poll");
</script>
<enme:require>
encuestame.org.core.commons.poll.Poll
</enme:require>
<div class="defaultMarginWrapper" id="defaultMarginWrapper">
  <div id="tweetPollWrapper">
     <enme:widget type="encuestame.org.core.commons.poll.Poll"></enme:widget>
  </div>
</div>
