<%@ include file="/WEB-INF/jsp/includes/taglibs.jsp"%>
<script type="text/javascript">
  dojo.require("encuestame.org.core.commons.validator.AbstractValidatorWidget");
  dojo.require("encuestame.org.core.commons.validator.PasswordValidator");
  dojo.require("encuestame.org.core.commons.validator.EmailValidator");
  dojo.require("encuestame.org.core.commons.validator.PasswordValidator");
  dojo.require("encuestame.org.core.commons.validator.RealNameValidator");
  dojo.require("encuestame.org.core.commons.validator.UsernameValidator");
  dojo.require("encuestame.org.core.shared.utils.StandBy");
  encuestame.constants.passwordExcludes =  <%=WidgetUtil.getPasswordBlackList("passwords.inc")%>;
</script>
<div id="web-signup-wrapper" class="enme-auto-center">
    <div class="web-form-wrapper">
            <div class="leftMessage">
            </div>
            <form method="POST"
                action="<%=request.getContextPath()%>/signup/create" class="signup-form defaultForm" autocomplete="false">
                <fieldset class="textbox">
                    <div class="section name">
                        <div dojoType="encuestame.org.core.commons.validator.RealNameValidator" enviroment="ext"></div>
                    </div>
                    <div class="section password">
                         <div dojoType="encuestame.org.core.commons.validator.PasswordValidator" enviroment="ext"></div>
                    </div>
                    <div class="section email">
                        <div dojoType="encuestame.org.core.commons.validator.EmailValidator" enviroment="ext"></div>
                    </div>
                     <div class="section username">
                        <div dojoType="encuestame.org.core.commons.validator.UsernameValidator" enviroment="ext"></div>
                    </div>
                    <input type="hidden" name="context" value="front">
                </fieldset>
            <div class="standby">
                <div id="standby" dojoType="encuestame.org.core.shared.utils.StandBy"></div>
                </div>
                <fieldset>
                    <div>
                        <input class="submit button promotional" type="submit" value="Sign Up Now">
                    </div>
                    <div>
                        <input type="hidden" value="1">
                    </div>
                </fieldset>
            </form>

        <!-- --------------------------------------------------------------------------------------------------------------------- -->
        <form class="form" action="<%=request.getContextPath()%>/user/signup/authenticate"  method="post">
        </form>
    </div>
</div>
