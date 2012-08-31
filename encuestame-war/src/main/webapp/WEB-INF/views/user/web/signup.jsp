<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<div id="web-user-actions-form-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper" id="web-form-wrapper">
            <form method="POST" id="signupForm"
                dojoType="dijit.form.Form"
                jsId="signupForm"
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
                                    dojoType="encuestame.org.core.commons.validator.RealNameValidator"
                                    placeholder="<spring:message code="signup.complete" />"
                                    enviroment="ext"></div>
                            </div>
                            <div class="section password">
                                 <div id="pssw"
                                      dojoType="encuestame.org.core.commons.validator.PasswordValidator"
                                      placeholder="<spring:message code="signup.password" />"
                                      enviroment="ext"></div>
                            </div>
                            <div class="section email">
                                <div id="em"
                                     dojoType="encuestame.org.core.commons.validator.EmailValidator"
                                     placeholder="<spring:message code="signup.email" />"
                                     enviroment="ext"></div>
                            </div>
                             <div class="section username">
                                <div id="usrva"
                                     dojoType="encuestame.org.core.commons.validator.UsernameValidator"
                                     placeholder="<spring:message code="signup.username" />"
                                     enviroment="ext"></div>
                            </div>
                            <input type="hidden" name="context" value="front">
                        </fieldset>
                        <fieldset>
                            <div class="center">
                                <div dojoType="encuestame.org.core.commons.signup.Signup"
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
                <div id="standby" dojoType="encuestame.org.core.shared.utils.StandBy" target="mainWrapper"></div>
            </div>
            </form>
    </div>
</div>
