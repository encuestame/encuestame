<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="web-poll-vote web-wrapper-detail">
   <header>
       <h1>${poll.questionBean.questionName}</h1>
   </header>
   <article class="emne-box">
        <section class="web-vote">
            VOTE ${poll}
            VOTE ${poll.questionBean}
            ANSWER ${answers}
            <article dojoType="encuestame.org.core.commons.poll.vote.PollVote"
                    pollId="${poll.id}">
                <c:forEach items="${answers}" var="a">
                    <section dojoType="encuestame.org.core.commons.poll.vote.AnswerVote"
                             answerId="${a.answerId}"
                             label="${a.answers}"
                             value="">
                    </section>
                </c:forEach>
            </article>
       </section>
   </article>
</article>