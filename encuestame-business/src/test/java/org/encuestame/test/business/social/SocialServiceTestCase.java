
package org.encuestame.test.business.social;

import org.encuestame.business.service.AbstractBaseService;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;


@Ignore
public class SocialServiceTestCase extends AbstractBaseService{



    /**
     * Test.
     */

    @Test
    @Ignore
    public void twitterSocialServiceTest(){
        Assert.isTrue(true);
        ///System.out.println(this.linkedInService.getAuthorizeLinkedInUrl());

        final String consumerKey = "5hkdPhtfkRwR0uRhIftai57FA0xbpH7m2fsBFfELvVLf6KMqw1X_FdzsgPkFORuS";
        final String consumerSecret = "3Tuj7nXvACdCwffnbh-NkUXQ_Re0t1FPakogEPApw_3DBsfowdQuoggCCTd38a9o";
        final String requestTokenUrl = "https://api.linkedin.com/uas/oauth/requestToken";
        final String authorizeUrl = "https://www.linkedin.com/uas/oauth/authorize?oauth_token={requestToken}";
        final String accessTokenUrl = "https://api.linkedin.com/uas/oauth/accessToken";
        //OAuth1Template tp =  new OAuth1Template(consumerKey, consumerSecret,
       //         requestTokenUrl,
       //         authorizeUrl,
       //         accessTokenUrl);
      // OAuthToken requestToken = tp.fetchNewRequestToken("http://localhost:8080/encuestame/user/linkedIn");
      // System.out.println("********************************  OAuthToken * "+requestToken);
      // String url = tp.buildAuthorizeUrl(requestToken.getValue(), "http://localhost:8080/encuestame/user/linkedIn");
      // System.out.println("********URL "+url);
        //request.setAttribute(OAUTH_TOKEN_ATTRIBUTE, requestToken, WebRequest.SCOPE_SESSION);
    }

}
