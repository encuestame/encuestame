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
package org.encuestame.test.business.service.config;

import org.encuestame.test.config.AbstractBaseUnitBeans;
import org.springframework.test.context.ContextConfiguration;

/**
 * Description Class.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 23, 2010 10:42:59 AM
 * @version Id:
 */
@ContextConfiguration(locations = {
        "classpath:spring-test/encuestame-email-context.xml",
        "classpath:spring-test/encuestame-security-context.xml",
        "classpath:spring-test/encuestame-service-context.xml",
        "classpath:spring-test/encuestame-integration.xml"
         })
public class AbstractServiceBase extends AbstractBaseUnitBeans{

}
