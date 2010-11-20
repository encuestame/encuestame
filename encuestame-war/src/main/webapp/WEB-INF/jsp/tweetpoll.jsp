<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initBody.jsp" %>
<script type="text/javascript">
    dojo.require("encuestame.org.class.commons.tweetPoll.TweetPoll");
</script>
<div class="defaultMarginWrapper">
    <div id="line" dojoType="encuestame.org.class.commons.tweetPoll.TweetPoll"></div>
</div>
</div>
<%@ include file="/WEB-INF/jsp/includes/endBody.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/validate.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>