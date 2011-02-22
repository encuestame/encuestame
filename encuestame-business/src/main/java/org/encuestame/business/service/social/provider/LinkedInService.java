package org.encuestame.business.service.social.provider;

import org.encuestame.business.service.AbstractBaseService;
import org.encuestame.business.service.imp.ILinkedInService;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.LinkedInApi;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.linkedin.LinkedInTemplate;

public class LinkedInService extends AbstractBaseService implements ILinkedInService{

    /**
     * Twitter Template.
     */
    private LinkedInTemplate linkedInTemplate;

    /**
     * Consumer Key.
     */
    public @Value("${linkedIn.oauth.api.key}")
    String apiKey;

    /**
     * Consumer Secret.
     */
    public @Value("${linkedIn.oauth.api.secret}")
    String consumerSecret;

    /**
     * Authorize Url.
     */
    public @Value("${linkedIn.oauth.authorize.url}")
    String authorizeUrl;

    /**
     * Request Token Url.
     */
    public @Value("${linkedIn.oauth.request.token}")
    String requestTokenUrl;

    public LinkedInService() {
    }

    /**
     *
     * @return
     */
    public String getAuthorizeLinkedInUrl() {
        OAuthService service = new ServiceBuilder().provider(LinkedInApi.class)
                .apiKey(this.apiKey).apiSecret(this.consumerKey).build();
        Token requestToken = service.getRequestToken();
        String myToken = requestToken.getToken();
        String completeUrl = org.apache.commons.lang.StringUtils.replace(
                this.authorizeUrl, "{token}", myToken);
        return completeUrl;
    }
}
