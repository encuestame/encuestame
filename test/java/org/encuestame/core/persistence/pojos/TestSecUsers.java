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
package org.encuestame.core.persistence.pojos;

import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Test;

/**
 * Test Security User Pojo.
 * @author Picado, Juan juan@encuestame.org
 * @since 01/11/2009 21:31:49
 * @version $Id$
 */
public class TestSecUsers extends AbstractBaseTest{

    /**
     * Test Security User.
     */
    @Test
    public void testSecUser(){
        final SecUsers user = new SecUsers();
      //  final Set<ProjectUser> projectUser = new HashSet<ProjectUser>();
        //user.setProjectUsers(projectUser);
        getSecUserDao().saveOrUpdate(user);
        assertNotNull(user.getUid());
    }
}
