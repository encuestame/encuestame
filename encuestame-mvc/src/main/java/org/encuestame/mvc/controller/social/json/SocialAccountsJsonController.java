package org.encuestame.mvc.controller.social.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;


/**
 * Social Account Json Service.
 * @author Picado, Juan juanATencuestame.org
 * @since  Feb 19, 2011 13:20:58 AM
 */
@Controller
public class SocialAccountsJsonController extends AbstractJsonController {

    /**
     * Log.
     */
    private Logger log = Logger.getLogger(this.getClass());

    /**
     * Twitter Instance.
     */
    private Twitter twitter = null;

    /**
     * Request Token.
     */
    private static RequestToken requestToken = null;

    /**
     * App Consumer Key.
     */
    private @Value("${twitter.oauth.consumerKey}") String consumerKey;

    /**
     * App consumer secret.
     */
    private @Value("${twitter.oauth.consumerSecret}") String consumerSecret;


    /**
     * Get Twitter Authorize Url.
     * @param apiKey
     * @param consumerSecret
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/social/twitter/authorize/url.json", method = RequestMethod.GET)
    public ModelMap getAuthorizeTwitterUrl(
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         log.debug("consumerKey "+this.consumerKey);
         log.debug("consumerSecret "+ (this.consumerKey));
         final HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
         try {
            Assert.notNull(this.consumerKey);
            Assert.notNull(this.consumerSecret);
            jsonResponse.put("url", this.getTwitterPinUrl(this.consumerKey, this.consumerSecret));
        } catch (EnMeExpcetion e) {
            jsonResponse.put("url", "");
            setError(e.getMessage(), response);
        }
        setItemResponse(jsonResponse);
        return returnData();
    }

    /**
     * Change state of social account.
     * @param type
     * @param socialAccountId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/social/twitter/account/{type}.json", method = RequestMethod.GET)
    public ModelMap actionTwitterAccount(
            @PathVariable String type,
            @RequestParam(value = "socialAccountId", required = true) Long socialAccountId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
//         try {
//           //getSecurityService().changeStateSocialAccount(socialAccountId, getUserPrincipalUsername(), type);
//        } catch (IllegalSocialActionException e) {
//            setError(e.getMessage(), response);
//        } catch (EnMeNoResultsFoundException e) {
//            setError(e.getMessage(), response);
//        }
        return returnData();
    }

    /**
     * Confirm Twitter Pin.
     * @param pin
     * @param accountSocialId
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/social/twitter/authorize/confirm.json", method = RequestMethod.GET)
    public ModelMap confirmTwitterPin(
            @RequestParam(value = "pin", required = true) String pin,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         log.debug("pin "+pin);
         try {
            final HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
            final HashMap<String, Object> pinResponse = this.createOAuthSocialAccountWithPinNumber(
                                          pin, getUserAccount(), SocialProvider.TWITTER);
            jsonResponse.put("confirm", pinResponse.get("confirm"));
            jsonResponse.put("message", pinResponse.get("message"));
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            final HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
            jsonResponse.put("confirm", false);
            jsonResponse.put("message", e.getMessage());
            setItemResponse(jsonResponse);
            setError(e.getMessage(), response);
        }
         return returnData();
    }


    /**
     * Return Social Valid Accounts.
     * @param request
     * @param response
     * @param provider
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/social/confirmed-accounts.json", method = RequestMethod.GET)
    public ModelMap get(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "provider", required = false) String provider)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
//             final List<SocialAccountBean> accounts = getSecurityService()
//                   .getUserLoggedVerifiedTwitterAccount(getUserPrincipalUsername(), SocialProvider.getProvider(provider));
//             setItemReadStoreResponse("socialAccounts", "id", accounts);
             log.debug("Twitter Accounts Loaded");
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Return Social Valid Accounts.
     * @param request
     * @param response
     * @param provider
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/social/accounts.json", method = RequestMethod.GET)
    public ModelMap getSocialAccountByType(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "provider", required = false) String provider)
            throws JsonGenerationException, JsonMappingException, IOException {
//        try {final List<SocialAccountBean> accounts = getSecurityService()
//            .getUserLoggedVerifiedTwitterAccount(getUserPrincipalUsername(), SocialProvider.getProvider(provider));
//        setItemReadStoreResponse("socialAccounts", "id", accounts);
//             final List<SocialAccountBean> accounts = getSecurityService()
//                   .getUserLoggedSocialAccount(getUserPrincipalUsername(), SocialProvider.getProvider(provider));
//             setItemReadStoreResponse("socialAccounts", "id", accounts);
//             log.debug("Twitter Accounts Loaded");
//        } catch (Exception e) {
//            log.error(e);
//            e.printStackTrace();
//            setError(e.getMessage(), response);
//        }
        return returnData();
    }

    /**
     * Return list of enabled providers.
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/social/providers.json", method = RequestMethod.GET)
    public ModelMap getProvidersEnabled(
            HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
             final HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
             final List<SocialProvider> providers = new ArrayList<SocialProvider>();
                 providers.add(SocialProvider.TWITTER);
                 providers.add(SocialProvider.BUZZ);
                 providers.add(SocialProvider.LINKEDIN);
             jsonResponse.put("provider", providers);
             setItemResponse(jsonResponse);
             log.debug("Social providers enabled "+providers.size());
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
            setError(e.getMessage(), response);
        }
        return returnData();
    }

    /**
     * Get Twitter Pin Url.
     * @param ping
     * @param consumerKey
     * @param consumerSecret
     * @return
     * @throws EnMeExpcetion
     */
    public final String getTwitterPinUrl(final String consumerKey, final String consumerSecret)
            throws EnMeExpcetion {
        log.debug("Get getTwitterPinUrl");
        String url = null;
        try {
            // pin should be null
            // reset request and access token.
            this.twitter = null;
            this.requestToken = null;
            // secret key and key should be different null
            if (consumerKey != null && consumerSecret != null) {
                // new twitter instance
                this.twitter = new TwitterFactory().getInstance();
                // set twitter consumer keys
                this.twitter.setOAuthConsumer(consumerKey, consumerSecret);
                this.requestToken = twitter.getOAuthRequestToken();
                log.debug("Setting Request Token " + requestToken);
                url = this.requestToken.getAuthorizationURL();
                log.debug("Request Token {" + this.requestToken.getToken());
                log.debug("Request Secret Token {"
                        + this.requestToken.getTokenSecret());
                log.debug("OAuth url {" + url);
                log.debug("TwitterPin Url Generated");

            }
        } catch (Exception e) {
            log.error("Error Getting URL Twitter Authentication "
                    + e.getMessage());
            throw new EnMeExpcetion(e);
        }
        return url;
    }

    /**
     * Create Social Account with Pin Number.
     * @param pin
     * @param account
     * @param socialProvider
     * @param userId
     * @return
     */
    public HashMap<String, Object> createOAuthSocialAccountWithPinNumber(
            final String pin,
            final UserAccount account,
            final SocialProvider socialProvider){
        log.debug("confirmOAuthPin");
        boolean confirmed = false;
        AccessToken accessToken = null;
        final HashMap<String, Object> response = new HashMap<String, Object>();
        if (pin != null) {
            try{
                if(this.requestToken == null){
                    log.error("Request Token is null");
                } else{
                    log.debug("confirmOAuthPin PIN: "+pin);
                    accessToken = this.twitter.getOAuthAccessToken(this.requestToken, pin);
                    //Testing send twitter, WORKS !!
                    //this.twitter.setOAuthAccessToken(accessToken);
                    //this.twitter.updateStatus("test accessToken");
                    //set new access token and secret token.
                    if (null == accessToken) {
                        log.error("pin not valid");
                    } else {
                         log.debug("Access Token {"+accessToken);
                         log.debug("Access Token ScreenName {"+accessToken.getScreenName());
                         log.debug("Access Token UserId {"+accessToken.getUserId());
                         log.debug("New Token {"+accessToken.getToken());
                         log.debug("New Secret Token {"+accessToken.getTokenSecret());
//                         getSecurityService().addOrUpdateOAuthTokenSocialAccount((long) accessToken.getUserId(),
//                                 accessToken.getToken(),
//                                 accessToken.getTokenSecret(),
//                                 accessToken.getScreenName(),
//                                 account,
//                                 socialProvider);
//                         confirmed = true;
//                         response.put("message", "ok");
                    }
                }
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
                response.put("message", e.getMessage());
            }
        }
        response.put("confirm", confirmed);
        return response;
    }

}
