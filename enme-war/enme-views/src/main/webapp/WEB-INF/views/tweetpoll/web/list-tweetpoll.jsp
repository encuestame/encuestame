<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-tweetpoll-wrapper" class="emne-box web-wrapper-detail">
    <div class="web-tweetpoll-menu panel-header">
    	<header>
	    	<h3>
	    	 	<spring:message code="tweetpoll.list.title" />
	    	</h3>
	    	<p>
	    		<spring:message code="tweetpoll.list.subtitle" />
	    	</p>
    	</header>
        <a href="<%=request.getContextPath()%>/user/tweetpoll/new">
            <button class="button">
                <spring:message code="tweetpoll.new" />
            </button>
        </a>
    </div>
    <div data-dojo-type="me/web/widget/tweetpoll/TweetPollList"></div>
</div>

