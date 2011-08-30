
package org.encuestame.mvc.test.json;

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.Assert;

import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.social.SocialProvider;
import org.junit.Before;
import org.junit.Test;

/**
 * Social Account Json Service Test Cases.
 * @author Picado, Juan juanATencuestame.org
 * @since  Feb 19, 2011 13:20:58 AM
 */

public class SocialAccountsJsonControllerTestCase extends AbstractJsonMvcUnitBeans{

    /**
     *
     */
    private SocialAccount socialAccount;

      /**
       *
       */
      @Before
      public void beforeSocialTest(){
          this.socialAccount = createDefaultSettedSocialAccount(getSpringSecurityLoggedUserAccount());
          createSocialProviderAccount(getSpringSecurityLoggedUserAccount(), SocialProvider.TWITTER);
      }

      /**
       * test load social accounts.
       * @throws ServletException
       * @throws IOException
       */
      @Test
      public void testLoadSocialAccounts() throws ServletException, IOException{
          initService("/api/common/social/accounts.json", MethodJson.GET);
          setParameter("provider", "twitter");
          final org.json.simple.JSONObject response = callJsonService();
          //System.out.println(response);
          //{"error":{},"success":{"items":[],"label":"socialAccounts","identifier":"id"}}
          org.json.simple.JSONArray list = (org.json.simple.JSONArray) getSucess(response).get("items");
          Assert.assertEquals(list.size(), 2);
      }

      /**
       * test providers service.
       * @throws ServletException
       * @throws IOException
       */
      @Test
      public void testGetProviders() throws ServletException, IOException{
          initService("/api/common/social/providers.json", MethodJson.GET);
          setParameter("provider", "twitter");
          final org.json.simple.JSONObject response = callJsonService();
          //{"error":{},"success":{"items":[],"label":"socialAccounts","identifier":"id"}}
          org.json.simple.JSONArray list = (org.json.simple.JSONArray) getSucess(response).get("provider");
          Assert.assertEquals(list.size(), 5);
      }
}
