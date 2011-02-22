
package org.encuestame.test.business.social;

import org.encuestame.business.service.imp.ILinkedInService;
import org.encuestame.business.service.social.connect.ITwitterSocialProvider;
import org.encuestame.business.service.social.connect.TwitterSocialService;
import org.encuestame.test.business.service.config.AbstractServiceBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.support.ConnectionRepository;
import org.springframework.social.linkedin.connect.LinkedInServiceProvider;
import org.springframework.social.oauth1.OAuth1Template;
import org.springframework.social.oauth1.OAuthToken;
import org.springframework.util.Assert;
import org.springframework.web.context.request.WebRequest;


public class SocialServiceTestCase extends AbstractServiceBase{

    /**
     * Twitter Social Service.
     */
    @Autowired
    private ITwitterSocialProvider twitterSocialService;

    /**
     *
     */
    @Autowired
    private ILinkedInService linkedInService;


    /**
     * Test.
     */
    @Test
    public void twitterSocialServiceTest(){
        Assert.notNull(this.linkedInService);
        Assert.isTrue(true);
        ///System.out.println(this.linkedInService.getAuthorizeLinkedInUrl());

        final String consumerKey = "5hkdPhtfkRwR0uRhIftai57FA0xbpH7m2fsBFfELvVLf6KMqw1X_FdzsgPkFORuS";
        final String consumerSecret = "3Tuj7nXvACdCwffnbh-NkUXQ_Re0t1FPakogEPApw_3DBsfowdQuoggCCTd38a9o";
        final String requestTokenUrl = "https://api.linkedin.com/uas/oauth/requestToken";
        final String authorizeUrl = "https://www.linkedin.com/uas/oauth/authorize?oauth_token={requestToken}";
        final String accessTokenUrl = "https://api.linkedin.com/uas/oauth/accessToken";
        OAuth1Template tp =  new OAuth1Template(consumerKey, consumerSecret,
                requestTokenUrl,
                authorizeUrl,
                accessTokenUrl);
       OAuthToken requestToken = tp.fetchNewRequestToken("http://localhost:8080/encuestame/user/linkedIn");
       System.out.println("********************************  OAuthToken * "+requestToken);
       String url = tp.buildAuthorizeUrl(requestToken.getValue(), "http://localhost:8080/encuestame/user/linkedIn");
       System.out.println("********URL "+url);
        //request.setAttribute(OAUTH_TOKEN_ATTRIBUTE, requestToken, WebRequest.SCOPE_SESSION);
    }


    /**
     * @return the twitterSocialService
     */
    public ITwitterSocialProvider getTwitterSocialService() {
        return twitterSocialService;
    }

    /**
     * @param twitterSocialService the twitterSocialService to set
     */
    public void setTwitterSocialService(ITwitterSocialProvider twitterSocialService) {
        this.twitterSocialService = twitterSocialService;
    }


    /**
     * @param linkedInService the linkedInService to set
     */
    public void setLinkedInService(final ILinkedInService linkedInService) {
        this.linkedInService = linkedInService;
    }
}
