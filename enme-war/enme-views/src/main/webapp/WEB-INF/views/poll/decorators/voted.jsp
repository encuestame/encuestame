<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<article class="defaultMarginWrapper">
    <section class="web-tweetpoll-vote-options">

        <div class="web-permatlink green">
            <h2>
                <spring:message code="poll.votes.thanks" />
            </h2>
            <h3>
               ${tp_switch.tweetPoll.question.question}
            </h3>
            <div class="web-tweetpoll-info">
                <a href="<%=request.getContextPath()%>/home">
                    <button class="btn btn-success">
                        <spring:message code="poll.votes.link" />
                    </button>
                </a>
            </div>
            <!-- <div data-dojo-type="me/web/widget/geo/BasicGeoWidget" tpid="${tp_switch.tweetPoll.tweetPollId}"> </div> -->
        </div>

    </section>
</article>