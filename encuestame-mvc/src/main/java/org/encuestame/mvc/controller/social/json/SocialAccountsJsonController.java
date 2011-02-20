
package org.encuestame.mvc.controller.social.json;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.encuestame.mvc.controller.AbstractJsonController;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.RequestToken;


/**
 * Social Account Json Service.
 * @author Picado, Juan juanATencuestame.org
 * @since  Feb 19, 2011 13:20:58 AM
 */
public class SocialAccountsJsonController extends AbstractJsonController {

    /**
     * Twitter Instance.
     */
    private Twitter twitter = null;


    /**
     *
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
    public ModelMap get(
            @RequestParam(value = "consumerKey") String consumerKey,
            @RequestParam(value = "consumerSecret") String consumerSecret,
            HttpServletRequest request,
            HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
         log.debug("consumerKey "+consumerKey);
         log.debug("consumerSecret "+ (consumerSecret));
         try {
            setItemResponse("url",this.getTwitterPinUrl(consumerKey, consumerSecret));
        } catch (EnMeExpcetion e) {
            setItemResponse("url", "");
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
            // secret key and key should be different null
            if (consumerKey != null && consumerSecret != null) {
                // new twitter instance
                this.twitter = new TwitterFactory().getInstance();
                // set twitter consumer keys
                this.twitter.setOAuthConsumer(consumerKey, consumerSecret);
                RequestToken requestToken = twitter.getOAuthRequestToken();
                log.debug("Setting Request Token " + requestToken);
                url = requestToken.getAuthorizationURL();
                log.debug("Request Token {" + requestToken.getToken());
                log.debug("Request Secret Token {"
                        + requestToken.getTokenSecret());
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

}
