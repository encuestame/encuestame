<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<!-- <div id="web-poll-wrapper">
    <div data-dojo-type="me/web/widget/poll/PollNavigate"></div>
</div> -->

<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div id="web-tweetpoll-wrapper" class="enme-main-section web-wrapper-detail">
    <div class="admon-table-options panel-header">
       <div class="tb-left">
            <h3>
              Poll Administration
               <!--  <spring:message code="tweetpoll.list.title" /> -->
            </h3>
            <p>
               <!-- <spring:message code="tweetpoll.list.subtitle" /> -->
               Manage your Poll
            </p>
       </div>
       <div class="tb-right">
            <a href="<%=request.getContextPath()%>/user/poll/new">
                <button id="create_tweetpoll_button" class="btn btn-warning">
                    <spring:message code="tweetpoll.new" />
                </button>
                 <span data-dojo-type="dijit/Tooltip" data-dojo-props='connectId:"create_tweetpoll_button"' style="display:none;">
                     Click if you want create a new tweetpoll
                 </span>
            </a>
      </div>
    </div>
    <div data-dojo-type="me/web/widget/poll/PollNavigate"></div>
</div>