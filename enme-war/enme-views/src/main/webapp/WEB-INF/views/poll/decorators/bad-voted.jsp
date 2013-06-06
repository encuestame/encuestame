<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
  <div class="hero-unit red-bk">
  <h2>
     <spring:message code="poll.votes.bad" />
  </h2>
  <p>
     <div class="link">
          <a href="<%=request.getContextPath()%>/home">
              <spring:message code="poll.votes.link" />
          </a>
      </div>
  </p>
</div>