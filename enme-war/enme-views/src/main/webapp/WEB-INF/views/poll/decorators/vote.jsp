<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-vote-wrapper">
        <h3>
             ${poll.questionBean.questionName}
        </h3>
        <p>
          <form action="<%=request.getContextPath()%>/poll/vote/post" method="post">
             <div class="web-poll-vote">
                <c:if test="${votePollError}">
                   <div class="alert alert-error">
                      <spring:message code="poll.error"/>
                   </div>
                 </c:if>
                  <div data-dojo-type="me/web/widget/poll/vote/PollVote" pollId="${poll.id}">
                      <c:forEach items="${answers}" var="a">
                          <div data-dojo-type="${poll.questionBean.widget}"
                                   itemId="${a.answerId}"
                                   name="poll"
                                   multiple="${poll.multipleResponse}"
                                   label="${a.answers}">
                          </div>
                      </c:forEach>
                  </div>
                   <input name="itemId" type="hidden" value="${poll.id}">
                   <input name="type" type="hidden" value="${poll.questionBean.pattern}">
                   <input name="slugName" type="hidden" value="${poll.questionBean.slugName}">
                   <input name="type_form" type="hidden" value="NORMAL">
             </div>
            <div class="space">
               <button class="btn  btn-large btn-block btn-success" type="submit">Vote</button>
            </div>
         </form>
      </p>
</div>