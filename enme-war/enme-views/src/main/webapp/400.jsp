<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
<!-- Stack trace -->
<jsp:scriptlet>
  exception.printStackTrace(new java.io.PrintWriter(out));
</jsp:scriptlet>