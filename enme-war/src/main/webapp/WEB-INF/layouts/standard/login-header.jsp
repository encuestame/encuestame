<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="web-header">
	<div class="web-header-wrapper enme-auto-center">
        <%@ include file="/WEB-INF/layouts/logo.jsp"%>
		<div class="web-header-options">
			<span class="link"> <a
				href="<%=request.getContextPath()%>/home"> <spring:message
						code="header.public.line" />
			</a>
			</span>

		</div>
	</div>
</div>
