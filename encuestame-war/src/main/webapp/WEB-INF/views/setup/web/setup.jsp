<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<div class="defaultMarginWrapper">
    <form:form method="post">
        <c:if test="${status != null}">
            <c:if test="${status == 'install'}">
                <div class="setup-description">
                    <h1>
                        Step 1: <span> Welcome </span>
                    </h1>
                    <p>
                       <b>
                       <a  href="http://www.encuestame.org" target="_blank">Encuestame</a>
                       </b> is an <b>open source survey social system</b>. It includes short poll, poll and survey, manages of
                       social accounts. Encuestame is
                       <ul>
                            <li><b>Easy of Use -</b> basic set of tools that just work</li>
                            <li><b>Easy of Install -</b> here you are, just follow the instructions</li>
                            <li><b>100% Free -</b> free for all, even for comercial use</li>
                            <li><b>Web Based 2.0 - </b> a rich user interface</li>
                            <li><b>OAuth Support - </b> user your platform with third party app</li>
                            <li><b>Social Network's' more popular -</b> Facebook, Google Buzz, Twitter, IdentCa, Yahoo and more.</li>
                       </ul>
                    </p>
                    <h2>
                        Installation Steps:
                    </h2>
                    <ol>
                        <li>Welcome</li>
                        <li>Create Database</li>
                        <li>Install demo data</li>
                        <li>Create administration user</li>
                        <li>Check your installation</li>
                        <li>Configure your enviroment</li>
                        <li>Finish</li>
                    </ol>
                </div>
                <input type="submit" name="_eventId_install-submit" value="Install" />
             </c:if>
             <c:if test="${state == 'upgrade'}">
                 <div class="setup-description">
                    <p>RELEASE NOTES for: <%=EnMePlaceHolderConfigurer.getProperty("app.version")%></p>
                    <div class="release-notes">

                    </div>
                 </div>
                 <input type="submit" name="_eventId_upgrade-submit" value="Upgrade" />
             </c:if>
        </c:if>
        <c:if test="${status == null}">
           <h3 class="error">Oh oh ! Something wrong on (installation / upgrade) process.</h3>
        </c:if>
    </form:form>
</div>
