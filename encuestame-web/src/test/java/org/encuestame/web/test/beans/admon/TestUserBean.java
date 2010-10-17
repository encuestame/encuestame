/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.test.beans.admon;

import org.encuestame.core.persistence.domain.security.SecUserSecondary;
import org.encuestame.utils.web.UnitUserBean;
import org.encuestame.web.beans.admon.security.UserBean;
import org.encuestame.web.test.config.AbstractBaseWeb;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * {@link UserBean} Test Cases.
 * @author Diana, Paola Morales paola@encuestame.org
 * @since March 31, 2010
 * @version $Id: $
 */

public class TestUserBean extends AbstractBaseWeb {

    /**
     * {@link UserBean}.
     * */
    private UserBean userBean;

    /**
     * Get {@link UserBean}.
     **/
    public final UserBean getUserBean() {
        return userBean;
    }

    /**
     * Set {@link UserBean}.
     * @param userBean user
     */
    public final void setUserBean(final UserBean userBean) {
        this.userBean = userBean;
    }

    /**
     * Init.
     */
    @Before
    public void init(){
        setUserBean(new UserBean());
        getUserBean().setServicemanagerBean(getServiceManager());
    }

    /**
     * Test Create User Bean.
     */
    @Test
    public void testUserBeanCreateUser(){
        final UnitUserBean unitUserBean = createUnitUserBean("jota", "jota@jota.com");
        Assert.assertNotNull(unitUserBean.getEmail());
        Assert.assertNotNull(unitUserBean.getUsername());
        getUserBean().setNewUnitUserBean(unitUserBean);
        getUserBean().createUser(getUsernameLogged());
        final SecUserSecondary secondary = getSecUserDao().getUserByUsername("jota");
        Assert.assertNotNull(secondary);
    }

}
