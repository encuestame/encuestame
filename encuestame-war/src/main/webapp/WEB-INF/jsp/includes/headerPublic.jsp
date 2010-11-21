<body class="tundra">
 <div id="publicLineHeader">
        <div class="headerOptions">
                <form method="get" action="<%=request.getContextPath()%>/search.jspx">
                        <span class="link">
                           <a href="<%=request.getContextPath()%>/signin.jspx">Sign In</a>
                        </span>
                         <span class="link">

                         </span>
                        <span class="link">
                         <span id="navbar">

                         </span>
                        </span>
                    <span class="search">
                        <input type="text" name="search" id="search" size="20" class="defaultInputClass"
                         value="SEARCH"
                         onclick = "if(this.value=='SEARCH') this.value=''"/>
                    </span>
                    <span class="link">
                    </span>
                </form>
        </div>
         <div style="float: left;">
            <a href="<%=request.getContextPath()%>/home">
                <span class="logoSmallEncuestame"></span>
            </a>
        </div>
    </div>
