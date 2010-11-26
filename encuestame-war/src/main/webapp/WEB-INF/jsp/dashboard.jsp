<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initPage.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/initBody.jsp" %>
<script type="text/javascript">
  dojo.require("encuestame.org.class.commons.dashboard.chart.DashboardPie");
  dojo.require("encuestame.org.class.commons.dashboard.chart.DashboardColumn2D");
  dojo.require("encuestame.org.class.commons.dashboard.chart.DashboardLine");
  dojo.require("encuestame.org.class.commons.dashboard.Dashboard");
</script>

<div>
    <div dojoType="encuestame.org.class.commons.dashboard.Dashboard"></div>
    <div id="pie" dojoType="encuestame.org.class.commons.dashboard.chart.DashboardPie"></div>
    <div id="column" dojoType="encuestame.org.class.commons.dashboard.chart.DashboardColumn2D"></div>
    <div id="line" dojoType="encuestame.org.class.commons.dashboard.chart.DashboardLine"></div>
    <%=request.getContextPath()%>
</div>

</div>
<%@ include file="/WEB-INF/jsp/includes/endBody.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/validate.jsp" %>
<%@ include file="/WEB-INF/jsp/includes/footer.jsp" %>