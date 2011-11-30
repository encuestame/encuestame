<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<style type="text/css">
@import"http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojox/widget/Portlet/Portlet.css";
@import"http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojox/layout/resources/GridContainer.css";
@import"http://ajax.googleapis.com/ajax/libs/dojo/1.6/dojox/widget/Calendar/Calendar.css";
.dndDropIndicator {
    border: 2px dashed #99BBE8;
    cursor: default;
    margin-bottom: 5px;
}
.gridContainerTable{
    border: 0px;
}
</style>
<div class="defaultMarginWrapper">
    <enme:widget type="encuestame.org.core.commons.dashboard.DashboardWrapper"></enme:widget>
</div>