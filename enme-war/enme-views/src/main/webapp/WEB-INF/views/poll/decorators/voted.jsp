<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-wrapper-detail web-poll-wrapper-vote">
       <div class="web-poll-vote final">
               <article class="emne-box votex-box">
                    <spring:message code="poll.votes.thanks" />
                    <div class="link">
                        <a href="<%=request.getContextPath()%>/home">
                            <spring:message code="poll.votes.link" />
                        </a>
                    </div>
               </article>
       </div>
</article>