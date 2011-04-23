package org.encuestame.mvc.controller.social;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;
import org.encuestame.business.service.social.api.BuzzAPITemplate;
import org.encuestame.core.social.oauth2.OAuth2RestOperations;
import org.encuestame.core.social.oauth2.OAuth2Support;
import org.encuestame.utils.oauth.AccessGrant;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;

@Controller
public class GoogleConnectSocialAccount extends AbstractSocialController{

    final String clientId = "287300901667.apps.googleusercontent.com";

    final String clientSecret = "oIJZgke5nPDSEEeNOy0lBrxv";

    OAuth2RestOperations tm;

    private Logger log = Logger.getLogger(this.getClass());

    @RequestMapping(value="/connect/google", method = RequestMethod.GET)
    public String signinTwitterGet(){
        return "connect/account";
    }

    @RequestMapping(value="/connect/google", method = RequestMethod.POST)
    public String signinTwitterGet( @RequestParam(required=false) String scope){
        this.tm = new OAuth2Support(clientId, clientSecret,
                "https://accounts.google.com/o/oauth2/auth?client_id={client_id}&redirect_uri={redirect_uri}&scope={scope}&response_type=code",
                "https://accounts.google.com/o/oauth2/token");
                return "redirect:" + tm.buildAuthorizeUrl("http://localhost:8080/encuestame/social/back/google", "https://www.googleapis.com/auth/buzz");
    }


    /**
     * Process the authorization callback from an OAuth 2 service provider.
     * Called after the member authorizes the connection, generally done by having he or she click "Allow" in their web browser at the provider's site.
     * On authorization verification, connects the member's local account to the account they hold at the service provider.
     */
    @RequestMapping(value="/social/back/google", method=RequestMethod.GET, params="code")
    public String oauth2Callback(
            @RequestParam("code") String code,
            WebRequest request) {
        log.debug("***********************************************************");
        log.debug(code);
        AccessGrant accessGrant = tm.exchangeForAccess(code, "http://localhost:8080/encuestame/social/back/google");
        log.debug(accessGrant.getAccessToken());
        log.debug(accessGrant.getRefreshToken());

        try{
        BuzzAPITemplate buzzService = new BuzzAPITemplate(accessGrant.getAccessToken(), "");
        log.debug("*****"+buzzService.updateStatus("Encuestame Test "+RandomStringUtils.randomAlphabetic(10)));
        }catch (Exception e) {
            e.printStackTrace();
            log.fatal(e);
        }
        log.debug("***********************************************************");
        return "connect/account";
    }
}
