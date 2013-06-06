<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
  <div class="hero-unit">
  <h2>
     ${poll.questionBean.questionName}
  </h2>
  <p>
      <form action="<%=request.getContextPath()%>/poll/vote/post" method="post">
         <div class="web-poll-vote">
            <c:if test="${votePollError}">
               <div class="alert alert-error">
                  <spring:message code="poll.error"/>
               </div>
             </c:if>
              <article data-dojo-type="me/web/widget/poll/vote/PollVote" pollId="${poll.id}">
                  <c:forEach items="${answers}" var="a">
                      <section data-dojo-type="${poll.questionBean.widget}"
                               itemId="${a.answerId}"
                               name="poll"
                               label="${a.answers}">
                      </section>
                  </c:forEach>
              </article>
               <input name="itemId" type="hidden" value="${poll.id}">
               <input name="type" type="hidden" value="${poll.questionBean.pattern}">
               <input name="slugName" type="hidden" value="${poll.questionBean.slugName}">
         </div>
        <p>
           <button class="btn  btn-large btn-block btn-success" type="submit">Vote</button>
        </p>
     </form>
  </p>
</div>