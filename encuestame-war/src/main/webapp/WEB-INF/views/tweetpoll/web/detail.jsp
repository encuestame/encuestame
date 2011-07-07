<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script type="text/javascript">
  dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail");
  dojo.require("encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail");
</script>
<div class="web-tweetpoll-detail ">
   <div class="question defaultSectionTitle">
        ${tweetpoll.questionBean.questionName}
   </div>
   <div class="web-tweetpoll-info">
             <div id="info" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollInfoDetail"/>
   </div>
    <div class="web-tweetpoll-answer-wrapper">
        <div class="web-tweetpoll-answer-chart">
            <div id="chart" dojoType="encuestame.org.core.commons.tweetPoll.detail.TweetPollChartDetail"/>
        </div>
        <div class="web-tweetpoll-answer-answer">
            <c:forEach items="${answers}" var="a">
                 <div class="answerVote">
                      ${a.answers.answer}
                 </div>
           </c:forEach>
        </div>
   </div>
   <div class="web-tweetpoll-social-links">

   </div>
   <div class="web-tweetpoll-hashtags">

   </div>
   <div class="web-tweetpoll-comments">

   </div>
</div>
