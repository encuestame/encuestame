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

import org.encuestame.web.beans.admon.UserBean;
import org.encuestame.web.test.config.AbstractBaseWeb;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * {@link UserBean} Test Cases.
 * @author Diana, Paola Morales paola@encuestame.org
 * @since March 31, 2010
 * @version $Id: $
 */

public class TestUserBean extends AbstractBaseWeb {

    @Test
    public void testUserBean(){
        assertNotNull(getUserBean());
    }

}
