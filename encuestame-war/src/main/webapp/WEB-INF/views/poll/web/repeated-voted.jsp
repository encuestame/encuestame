<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-wrapper-detail web-poll-wrapper-vote">
       <div class="web-poll-vote final repeated">
               <article class="emne-box">
                    <spring:message code="poll.votes.repeated" />
                    <div class="link">
                        <a href="<%=request.getContextPath()%>/home">
                            <spring:message code="poll.votes.link" />
                        </a>
                    </div>
               </article>
       </div>
</article>