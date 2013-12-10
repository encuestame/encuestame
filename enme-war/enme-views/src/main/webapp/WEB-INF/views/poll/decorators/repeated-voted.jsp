<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
  <div class="hero-unit yellow-bk">
  <h2>
      <spring:message code="poll.votes.repeated" />
  </h2>
  <p>
      <div class="link">
          <a href="<%=request.getContextPath()%>/home">
              <button class="btn btn-warning">
                <spring:message code="poll.votes.link" />
              </button>
          </a>
      </div>
  </p>
</div>