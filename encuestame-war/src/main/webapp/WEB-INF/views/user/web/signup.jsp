<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper" id="web-form-wrapper">
            <form method="POST" id="signupForm"
                data-dojo-type="dijit/form/Form"
                jsId="signupForm"
                data-dojo-id="signupForm"
                encType="multipart/form-data"
                action="<%=request.getContextPath()%>/user/signup/create"
                class="signup-form defaultForm"
                autocomplete="off">
                    <div class="section-signup" title="Sign Up with Username" collapsed="false">
                        <div class="web-form-singup-container">
                        <h1>
                            <spring:message code="signup.title" />
                        </h1>
                        <fieldset class="textbox">
                            <div class="section name">
                                <div id="rm"
                                    data-dojo-type="me/web/widget/validator/RealNameValidator"
                                    placeholder="<spring:message code="signup.complete" />"
                                    enviroment="ext"></div>
                            </div>
                            <div class="section password">
                                 <div id="pssw"
                                      data-dojo-type="me/web/widget/validator/PasswordValidator"
                                      placeholder="<spring:message code="signup.password" />"
                                      enviroment="ext"></div>
                            </div>
                            <div class="section email">
                                <div id="em"
                                     data-dojo-type="me/web/widget/validator/EmailValidator"
                                     placeholder="<spring:message code="signup.email" />"
                                     enviroment="ext"></div>
                            </div>
                             <div class="section username">
                                <div id="usrva"
                                     data-dojo-type="me/web/widget/validator/UsernameValidator"
                                     placeholder="<spring:message code="signup.username" />"
                                     enviroment="ext"></div>
                            </div>
                            <input type="hidden" name="context" value="front">
                        </fieldset>
                        <fieldset>
                            <div class="center">
                                <div data-dojo-type="me/web/widget/signup/Signup"
                                     value="<spring:message code="signup.button" />"></div>
                            </div>
                            <div>
                                <input type="hidden" value="1">
                            </div>
                        </fieldset>
                        </div>
                    </div>
                    <div class="section-signup" title="Sign up with your favourite social network" collapsed="false">
                            <div class="web-social-signin">
                                <h3>
                                   <spring:message code="signup.social.signup" />
                                </h3>
                                <%@ include file="/WEB-INF/jsp/includes/web/social.jsp" %>
                              </div>
                    </div>
            <div class="standby">
                <div id="standby" testDojoType="encuestame.org.core.shared.utils.StandBy" target="mainWrapper"></div>
            </div>
            </form>
    </div>
</div>
