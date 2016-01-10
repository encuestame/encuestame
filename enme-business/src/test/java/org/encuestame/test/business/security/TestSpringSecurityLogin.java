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
package org.encuestame.test.business.security;

import static org.junit.Assert.assertEquals;

import org.encuestame.test.business.config.AbstractSpringSecurityContext;
import org.encuestame.utils.categories.test.DefaultTest;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Security Context Test.
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 30, 2010 9:04:44 PM
 */
@Category(DefaultTest.class)
public class TestSpringSecurityLogin extends AbstractSpringSecurityContext {

    @Test
    public void testSecurity(){
         assertEquals("Should be", getAuthentication().getPrincipal(), getSpringSecurityLoggedUserAccount().getUsername());
    }

}
