<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>

<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-wrapper-detail">
   <header>
       <h1>${poll.questionBean.questionName}</h1>
   </header>
   <article class="emne-box">
        <section class="web-wrapper-detail-wrapper">
            <article class="web-chart">
                <div dojoType="encuestame.org.core.commons.poll.detail.PollChartDetail"
	                 pollId="${poll.id}"
	                 username="${poll.ownerUsername}">
	            </div>
            </article>
            <article class="web-detail web-poll-results"> 
            	 <header>
            	 	<div class="header-votes">
            	 		Votes
            	 	</div>
            	 	<div class="header-percents">
            	 		Percent (%)
            	 	</div>
            	 </header>           	             	
				 <c:forEach items="${answers}" var="item">				 
				 	<section dojoType="encuestame.org.core.commons.results.answers.GenericPercentResult"
				 		itemId="${item.answerBean.answerId}"
				 		color="${item.answerBean.color}"
				 		votes="${item.result}"
				 		questionId="${item.answerBean.questionId}" 
				 		labelResponse="${item.answerBean.answers}">				 		
				 	</section>
                 </c:forEach>
                 <a class="btn" href="<%=request.getContextPath()%>/poll/vote/${poll.id}/${poll.questionBean.slugName}">Vote</a>
            </article>
       </section>
       <section class="web-button-wrapper gradient-gray">
            <div>
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_pie.png">
            </div>
            <div>
                <img src="<%=request.getContextPath()%>/resources/images/icons/enme_bar.png">
            </div>
       </section>
   </article>
   <article class="emne-box">
       <header>
          Social Links Refered
       </header>
       <section>
            <div dojoType="encuestame.org.core.commons.social.LinksPublished"
                 type="POLL" class="web-social-links">
            </div>
       </section>
    </article>
   <c:if test="${!empty hashtags}">
       <section class="emne-box">
           <header>HashTag Refered</header>
           <div class="web-tweetpoll-hashtags ">
               <c:forEach items="${hashtags}" var="h">
                       <span dojoType="encuestame.org.core.commons.stream.HashTagInfo"
                        url="<%=request.getContextPath()%>/tag/${h.hashTagName}/"
                        hashTagName="${h.hashTagName}"></span>
               </c:forEach>
           </div>
       </section>
   </c:if>
   <section class="web-tweetpoll-comments emne-box">
      <header>Comments</header>
      <div dojoType="encuestame.org.core.comments.Comments" type="poll" item_id="${poll.id}"></div>
   </section>
</article>