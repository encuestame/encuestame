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
package org.encuestame.test.persistence.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Abstract Junit Configuration File.
 * @author Picado, Juan juanATencuestame.org
 * @since Oct 1, 2010 11:28:12 AM
 */
@Scope("singleton")
@TransactionConfiguration(defaultRollback = true)
@ContextConfiguration(classes = DBTestConfig.class)
@Transactional
@ActiveProfiles(profiles = "dev")
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractConfigurationBase{

    @Autowired
    public ApplicationContext applicationContext;

    public Log log = LogFactory.getLog(this.getClass());

    /**
     * Constructor.
     */
    public AbstractConfigurationBase() {
         super();
    }

    @Test
    public void test() {
        //gradle hack
    }
}