
package org.encuestame.mvc.test.json;

import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.SocialAccount;
import org.junit.Before;

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
          this.user = createAccount();
          this.socialTwitterAccount = createDefaultSettedTwitterAccount(this.user);
      }
}
