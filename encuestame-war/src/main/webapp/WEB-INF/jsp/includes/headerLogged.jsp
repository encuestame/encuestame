<div class="wrapper">
 <div id="publicLineHeader">
        <div class="headerOptions">
                <form method="get" action="<%=request.getContextPath()%>/search.jspx">
                        <span class="link">
                           <a href="<%=request.getContextPath()%>/signin.jspx">Sign In</a>
                        </span>
                         <span class="link">
                            <a href="<%=request.getContextPath()%>/account/dashboard">My Dashboard</a>
                         </span>
                        <span class="link">
                         <span id="navbar">
                               <a href="#" onclick="showlayer('profile_menu')">profile</a>
                         </span>
                        </span>
                    <span class="search">
                        <input type="text" name="search" id="search" size="20" class="defaultInputClass"
                         value="SEARCH"
                         onclick = "if(this.value=='SEARCH') this.value=''"/>
                    </span>
                        <span class="link">
                               <a href="<%=request.getContextPath()%>/user/logout">Log out</a>
                        </span>
                </form>
        </div>
        <div id="profile_menu" style="display:none; right: 0px;">
               <div id="detail_profile">Profile Detail<br /><br />
                    detail
               </div>
               <div id="options_profile">Options<br /><br />
                        <ul>
                            <li>
                                <img src="" alt="test" />
                            </li>           <li>
                               <a href="/user/social.jspx">Social Accounts</a>
                            </li>
                            <li>
                               <a href="/user/lists.jspx">Email List</a>
                            </li>
                            <li>
                                <a href="/user/profile.jspx">Profile Settings</a>
                            </li>
                            <li>
                                <a href="/user/logout">Log Out</a>
                            </li>
                        </ul>
                 </div>
                 <div class="spacer"></div>
              </div>
        <div style="float: left;">
            <a href="<%=request.getContextPath()%>/home">
                <span class="logoSmallEncuestame"></span>
            </a>
        </div>
</div>
<div id="mainWrapper">
  <%@ include file="/WEB-INF/jsp/includes/menu.jsp" %>


