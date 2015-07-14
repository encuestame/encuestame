<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<%@ include file="/WEB-INF/layouts/decorators/html.jsp"%>
 <head>
    <title>iframe</title>
 </head>
    <body>
        <div>
            <a data-id="${id}"
               class="${class_type}"
               data-url="${url}"
               target="_blank">
            </a>
             <script>
             !function(d,s,id){
                 var js,
                     fjs=d.getElementsByTagName(s)[0],
                     p=/^http:/.test(d.location)?'http':'https';
                     window.__enme_widget={host:p+":///${domain}/"};
                 if(!d.getElementById(id)){
                     p=/^http:/.test(d.location)?'http':'https';
                     js=d.createElement(s);
                     js.id=id;js.src=p+":///${domain}/resources/js/widget/widget.js";
                     fjs.parentNode.insertBefore(js,fjs);
                     }
                 }(document,"script","widget-enme-js");
              </script>
       </div>
    </body>
</html>