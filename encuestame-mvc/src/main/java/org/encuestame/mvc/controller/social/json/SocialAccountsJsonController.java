
package org.encuestame.mvc.controller.social.json;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.security.UnitTwitterAccountBean;
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
     * @param consumerKey
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
     * Create or Update Twitter Social Account.
     * @param type
     * @param consumerKey
     * @param consumerSecret
     * @param usernameAccount
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
            @RequestParam(value = "consumerKey", required = true) String consumerKey,
            @RequestParam(value = "consumerSecret", required = true) String consumerSecret,
            @RequestParam(value = "username", required = true) String usernameAccount,
            @RequestParam(value = "socialAccountId", required = false) String socialAccountId,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         log.debug("consumerKey "+consumerKey);
         log.debug("consumerSecret "+ (consumerSecret));
         try {
           final UserAccount userAccount = getUserAccount();
           if("create".equals(type)){
               //getSecurityService().addNewTwitterAccount(userAccount, usernameAccount, consumerKey, consumerSecret);
               setSuccesResponse();
           } else if("update".equals(type)){
              log.info("update social twitter account");
           } else {
               setError("type not valid", response);
           }
        } catch (EnMeExpcetion e) {
            setItemResponse("url", "");
            setError(e.getMessage(), response);
        }
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
            @RequestParam(value = "pin") String pin,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         log.debug("pin "+pin);
         try {
            final HashMap<String, Object> jsonResponse = new HashMap<String, Object>();
            final HashMap<String, Object> r = this.createOAuthSocialAccountWithPinNumber(pin, getUserAccount());
            jsonResponse.put("confirm", r.get("confirm"));
            jsonResponse.put("screenName", r.get("screenName"));
            setItemResponse(jsonResponse);
        } catch (Exception e) {
            setItemResponse("url", "");
            setError(e.getMessage(), response);
        }
         return returnData();
    }


    /**
     * Return Social Valid Accounts.
     * @param request
     * @param response
     * @return
     * @throws JsonGenerationException
     * @throws JsonMappingException
     * @throws IOException
     */
    @PreAuthorize("hasRole('ENCUESTAME_USER')")
    @RequestMapping(value = "/api/common/twitter/valid-accounts.json", method = RequestMethod.GET)
    public ModelMap get(
            HttpServletRequest request, HttpServletResponse response)
            throws JsonGenerationException, JsonMappingException, IOException {
        try {
             final List<UnitTwitterAccountBean> accounts = getSecurityService()
                   .getUserLoggedVerifiedTwitterAccount(getUserPrincipalUsername());
             setItemReadStoreResponse("twitterAccounts", "id", accounts);
             log.debug("Twitter Accounts Loaded");
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
     *
     * @param pin
     * @param account
     * @param userId
     * @return
     */
    public HashMap<String, Object> createOAuthSocialAccountWithPinNumber(final String pin, final UserAccount account){
        log.debug("confirmOAuthPin");
        boolean confirmed = false;
        AccessToken accessToken = null;
        final HashMap<String, Object> response = new HashMap<String, Object>();
        if (pin != null || !pin.isEmpty()) {
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
                         getSecurityService().addOAuthTokenSocialAccount((long) accessToken.getUserId(),
                                 accessToken.getToken(),
                                 accessToken.getTokenSecret(),
                                 accessToken.getScreenName(),
                                 account);
                         confirmed = true;
                    }
                }
            } catch (Exception e) {
                log.error(e);
                e.printStackTrace();
            }
        }
        return response;
    }

}
