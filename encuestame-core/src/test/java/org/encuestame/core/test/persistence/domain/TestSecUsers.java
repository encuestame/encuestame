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
package org.encuestame.core.test.persistence.domain;

import static org.junit.Assert.*;

import org.encuestame.core.persistence.domain.security.SecUser;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Test;

/**
 * Test Security User Pojo.
 * @author Picado, Juan juan@encuestame.org
 * @since 01/11/2009 21:31:49
 * @version $Id$
 */
public class TestSecUsers extends AbstractBase{

    /**
     * Test Security User.
     */
    @Test
    public void testSecUser(){
        final SecUser user = new SecUser();
        user.setTwitterAccount("testTWitterAccount");
        user.setTwitterPassword("testTwitterPwsd");
        getSecUserDao().saveOrUpdate(user);
        assertNotNull(user.getUid());
    }
}
