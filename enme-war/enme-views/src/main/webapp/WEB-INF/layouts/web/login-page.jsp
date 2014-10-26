<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
<%--
  ~ /*
  ~  * Copyright 2014 encuestame
  ~  *
  ~  *  Licensed under the Apache License, Version 2.0 (the "License");
  ~  *  you may not use this file except in compliance with the License.
  ~  *  You may obtain a copy of the License at
  ~  *
  ~  *       http://www.apache.org/licenses/LICENSE-2.0
  ~  *
  ~  *  Unless required by applicable law or agreed to in writing, software
  ~  *  distributed under the License is distributed on an "AS IS" BASIS,
  ~  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~  *  See the License for the specific language governing permissions and
  ~  *  limitations under the License.
  ~  */
  --%>

<head>
    <title>
        <tiles:insertAttribute name="title" defaultValue="encuestame" />
    </title>
<%@ include file="/WEB-INF/jsp/includes/web/css.jsp"%>
<%@ include file="decorators/web-meta.jsp"%>

<%@ include file="/WEB-INF/jsp/includes/init-javascript.jsp"%>
<tiles:insertAttribute name="rss" ignore="true" />
</head>
<body class="enme-web-context dbootstrap login-page">
  <%@ include file="decorators/ui_bar.jsp"%>
  <div id="mainWrapper" class="page container">
    <header id="header" class="header_input_hidden">
          <%@ include file="/WEB-INF/layouts/decorators/i18n-input.jsp"%>
    </header>
    <div id="content-container">
      <tiles:insertAttribute name="menu" ignore="true" />
      <div id="enme-content" class="container">
          <%@ include file="decorators/login-header.jsp"%>
        <tiles:insertAttribute name="content" />
      </div>
    </div>
  </div>
  <!-- Insert additional javascript  -->
  <tiles:insertAttribute name="extra-js" ignore="true" />
  <c:if test="${logged}">
     <div id="modal-box"></div>
     <div id="loading"></div>
  </c:if>
  <%@ include file="/WEB-INF/jsp/includes/javascript.jsp"%>
  <tiles:insertAttribute name="dojo-layers" ignore="true" />
</body>

</html>