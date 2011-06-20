<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="home-mobile">
    <div class="mobile-items">
    <c:forEach items="${items}" var="item">
       <div class="mobile-item">
        <div class="mobile-view">
                <div>
                    <div>
                        <div>
                            <span class="count"><strong>141</strong></span>
                            <div class="viewcount">answers</div>
                        </div>
                    </div>
                    <div class="views">
                    50 views
                    </div>
                </div>
            </div>
            <div class="mobile-content">
                <div class="mobile-tweetpoll">
                    <a href="<%=request.getContextPath()%>/tweetpoll/${item.id}/test">${item.questionBean.questionName}</a>
                </div>
                <div class="mobile-submit-bottom">
                    <div class="mobile-submit-options">
                        <div class= "mobile-submit-text">(Submited By <strong><a href="#">Jota</a></strong>) added <strong>45 minutes</strong> ago |  25 Comments</div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
  </div>
</div>
