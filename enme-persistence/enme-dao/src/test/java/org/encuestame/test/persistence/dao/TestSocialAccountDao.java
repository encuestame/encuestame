/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.test.persistence.dao;

import org.encuestame.persistence.domain.security.SocialAccount;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.persistence.config.AbstractBase;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Test {@link SocialAccount}..
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since December 27, 2010
 */
@Category(DefaultTest.class)
public class TestSocialAccountDao extends AbstractBase{

    /** {@link UserAccount}. **/
    private SocialAccount socialAccount;

    /**
     * Before.
     */
   // @Before
    public void initData(){

    }

    @Test
    @Ignore
    public void testGetSocialAccountProviderId(){
        //final SocialAccountProvider socialAccount = getProviderDao().getSocialAccountProviderId(this.socialAccount.getId());
    }

}
