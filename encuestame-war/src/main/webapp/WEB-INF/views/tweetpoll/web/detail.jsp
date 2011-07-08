<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script type="text/javascript">
  dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail");
  dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail");
  dojo.require("encuestame.org.core.commons.stream.HashTagInfo");
  dojo.require("encuestame.org.core.comments.Comments");
  dojo.require("encuestame.org.core.commons.social.LinksPublished");
  dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer");
</script>
<div class="web-tweetpoll-detail ">
   <div class="question defaultSectionTitle">
        ${tweetpoll.questionBean.questionName}
   </div>
   <div class="web-tweetpoll-info">
        <div id="info" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail"
             date=${tweetpoll.createDate}"
             owner="${tweetpoll.ownerUsername}"
             tweetPollid="${tweetpoll.id}"></div>
   </div>
    <div class="web-tweetpoll-answer-wrapper">
        <div class="web-tweetpoll-answer-chart">
            <div id="chart" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail"
                 tweetPollid="${tweetpoll.id}"
                 username="${tweetpoll.ownerUsername}"></div>
        </div>
        <div class="web-tweetpoll-answer-answer">
            <c:forEach items="${answers}" var="a">
                 <div class="answer"
                      dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollAnswer"
                      aId="${a.answers.questionAnswerId}"
                      label="${a.answers.answer}"
                       owner="${tweetpoll.ownerUsername}"
                      url="${a.shortUrl}">
                 </div>
           </c:forEach>
        </div>
   </div>
   <div class="web-tweetpoll-social-links">
       <div dojoType="encuestame.org.core.commons.social.LinksPublished" itemId="${tweetpoll.id}" type="tp"></div>
   </div>
   <div class="web-tweetpoll-hashtags">
       <c:forEach items="${hashtags}" var="h">
               <span dojoType="encuestame.org.core.commons.stream.HashTagInfo"
                url="<%=request.getContextPath()%>/tag/${h.hashTag}/"
                hashTagName="${h.hashTag}"></span>
       </c:forEach>
   </div>
   <div class="web-tweetpoll-comments">
      <div dojoType="encuestame.org.core.comments.Comments" type="tweetpoll"></div>
   </div>
</div>
