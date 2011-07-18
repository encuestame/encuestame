<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp" %>
<script type="text/javascript">
  dojo.require("encuestame.org.core.commons.validator.AbstractValidatorWidget");
  dojo.require("encuestame.org.core.commons.validator.PasswordValidator");
  dojo.require("encuestame.org.core.commons.validator.EmailValidator");
  dojo.require("encuestame.org.core.commons.validator.PasswordValidator");
  dojo.require("encuestame.org.core.commons.validator.RealNameValidator");
  dojo.require("encuestame.org.core.commons.validator.UsernameValidator");
  dojo.require("encuestame.org.core.shared.utils.StandBy");
  dojo.require("encuestame.org.core.commons.signup.Signup");
  dojo.require("encuestame.org.core.commons.panel.PanelBar");
  dojo.require("dijit.form.Form");
  encuestame.constants.passwordExcludes =  <%=WidgetUtil.getPasswordBlackList("passwords.inc")%>;
</script>
<div id="web-signup-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper" id="web-form-wrapper">
            <div class="leftMessage">
            </div>
            <form method="POST" id="signupForm"
                dojoType="dijit.form.Form"
                jsId="signupForm"
                encType="multipart/form-data"
                action="<%=request.getContextPath()%>/user/signup/create"
                class="signup-form defaultForm"
                autocomplete="off">
                <div dojoType="encuestame.org.core.commons.panel.PanelBar"></div>
                    <div class="section-signup" title="Sign Up with Username" collapsed="false">
                        <div style="text-align: center;">
                        <fieldset class="textbox">
                            <div class="section name">
                                <div id="rm" dojoType="encuestame.org.core.commons.validator.RealNameValidator" enviroment="ext"></div>
                            </div>
                            <div class="section password">
                                 <div id="pssw" dojoType="encuestame.org.core.commons.validator.PasswordValidator" enviroment="ext"></div>
                            </div>
                            <div class="section email">
                                <div id="em" dojoType="encuestame.org.core.commons.validator.EmailValidator" enviroment="ext"></div>
                            </div>
                             <div class="section username">
                                <div id="usrva" dojoType="encuestame.org.core.commons.validator.UsernameValidator" enviroment="ext"></div>
                            </div>
                            <input type="hidden" name="context" value="front">
                        </fieldset>
                        <fieldset>
                            <div class="center">
                                <div dojoType="encuestame.org.core.commons.signup.Signup"></div>
                            </div>
                            <div>
                                <input type="hidden" value="1">
                            </div>
                        </fieldset>
                        </div>
                    </div>
                    <div class="section-signup" title="Sign up with your favourite social network" collapsed="false">
                        <c:if test="<%=EnMePlaceHolderConfigurer.getBooleanProperty("application.social.signin.enabled")%>">
                            <div class="web-social-signin">
                                <h2>Select one of these thrid-party accouns to sign in</h2>
                                <%@ include file="/WEB-INF/jsp/includes/web/social.jsp" %>
                              </div>
                        </c:if>
                    </div>
            <div class="standby">
                <div id="standby" dojoType="encuestame.org.core.shared.utils.StandBy" target="web-form-wrapper"></div>
            </div>
            </form>
    </div>
</div>
