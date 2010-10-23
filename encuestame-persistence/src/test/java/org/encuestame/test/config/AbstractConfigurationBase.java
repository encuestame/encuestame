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
package org.encuestame.test.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract Junit Configuration File.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 1, 2010 11:28:12 AM
 * @version $Id:$
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager" , defaultRollback = true)
@Transactional
@Scope("singleton")
@ContextConfiguration(locations = {
        "classpath:encuestame-dao-context.xml",
        "classpath:encuestame-hibernate-context.xml",
        "classpath:encuestame-param-test-context.xml"
         })
public abstract class AbstractConfigurationBase extends AbstractTransactionalJUnit4SpringContextTests{

    public Log log = LogFactory.getLog(this.getClass());
}
