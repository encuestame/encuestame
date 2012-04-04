<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script src="<%=request.getContextPath()%>/resources/js/req/hashtag.js"></script>
<script src="<%=request.getContextPath()%>/resources/js/req/popup.js"></script>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script type="text/javascript">
    dojo.require("encuestame.org.core.shared.publications.Publications");
    dojo.require("encuestame.org.core.commons.stream.HashTagInfo");
    dojo.require("encuestame.org.core.commons.social.LinksPublished");
    dojo.require("encuestame.org.core.shared.stats.GenericStats");
    dojo.require("encuestame.org.core.shared.stats.RatePosition");
    dojo.require("encuestame.org.core.shared.stats.TopProfiles");
    dojo.require("encuestame.org.core.commons.hashtags.HashTagGraph");
</script>