
package org.encuestame.mvc.test.json;

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

/**
 * Social Account Json Service Test Cases.
 * @author Picado, Juan juanATencuestame.org
 * @since  Feb 19, 2011 13:20:58 AM
 */
public class SocialAccountsJsonControllerTestCase extends AbstractJsonMvcUnitBeans{

    /** {@link Account}. **/
    private Account user;

    private SocialAccount socialTwitterAccount;

      @Before
      public void beforeSocialTest(){
          this.user = createUser();
          this.socialTwitterAccount = createDefaultSettedTwitterAccount(this.user);
      }

    /**
     * Test /api/notifications.json.
     * @throws Exception
     * Expected response
     * {"error":{},
     * "success":{"url":"http://api.twitter.com/oauth/authorize?oauth_token=gPCYxlPgWv4Ml3Ad5ylg5UwZDnK1k7iddNGEv9Ko"}}
     */
    @Test
    public void testGetAuthorizeTwitterUrl() throws Exception {
        initService("/api/social/twitter/authorize/url.json", MethodJson.GET);
        setParameter("consumerKey", this.socialTwitterAccount.getConsumerKey());
        setParameter("consumerSecret", this.socialTwitterAccount.getConsumerSecret());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        String url = (String) success.get("url");
        Assert.assertNotNull(url);
    }

}
