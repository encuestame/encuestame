/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.test.persistence.pojos;

import static org.junit.Assert.assertEquals;

import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.pojo.SecUserTwitterAccounts;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@link SecUserTwitterAccounts} Test.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 19, 2010 8:38:22 AM
 * @version $Id:$
 */
public class TestTwitterAccount extends AbstractBase {

    /**
     * {@link SecUserDaoImp}.
     */
    @Autowired
    private ISecUserDao secUserDao;

    /**
     * @return the secUserDao
     */
    public ISecUserDao getSecUserDao() {
        return secUserDao;
    }

    /**
     * Test.
     */
    //@Test
    public void testSecTwitterAccounts(){
        final String password = "12345";
        final SecUserTwitterAccounts accounts = new SecUserTwitterAccounts();
        accounts.setSecUsers(createUser());
        accounts.setTwitterAccount("encuestame");
        accounts.setTwitterPassword(password);
        getSecUserDao().saveOrUpdate(accounts);
        log.debug("Password "+password);
        log.debug("Password Retrieved "+accounts.getTwitterPassword());
        assertEquals("Should be equals", password, accounts.getTwitterPassword());
    }

    /**
     * @param secUserDao the secUserDao to set
     */
    public void setSecUserDao(ISecUserDao secUserDao) {
        this.secUserDao = secUserDao;
    }
}
