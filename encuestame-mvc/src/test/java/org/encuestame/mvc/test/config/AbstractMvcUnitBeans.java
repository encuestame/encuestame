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
package org.encuestame.mvc.test.config;

import org.encuestame.core.test.security.AbstractSpringSecurityContext;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Scope;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

/**
 *
 * Description Class.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 26, 2010 8:12:32 PM
 * @version Id:
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
@Scope("singleton")
@ContextConfiguration(locations = {
        "classpath:encuestame-json-context.xml",
        "classpath:encuestame-controller-context.xml",
        "classpath:encuestame-param-test-context.xml"
         })
public class AbstractMvcUnitBeans extends AbstractSpringSecurityContext {

    /**
     * Fake Request.
     */
    protected MockHttpServletRequest request;

    /**
     * Fake Response.
     */
    protected MockHttpServletResponse response;

    /**
     * Handler Adapter.
     */
    protected HandlerAdapter handlerAdapter;

    /**
     * Fake Session
     */
    protected MockHttpSession session;

    /**
     * Json View.
     */
    public MappingJacksonJsonView jacksonJsonView = new MappingJacksonJsonView();

    /**
     * Model and View.
     */
    public ModelAndView modelAndView = new ModelAndView();


    public AbstractMvcUnitBeans() {
        //http://forum.springsource.org/showthread.ph519
        jacksonJsonView.setPrefixJson(false);
        jacksonJsonView.setDisableCaching(true);
        session = new MockHttpSession();
        modelAndView.setView(jacksonJsonView);
    }



    @Before
    public void setUpJson() throws Exception{
       request = new MockHttpServletRequest();
       response = new MockHttpServletResponse();
       handlerAdapter = applicationContext.getBean(HandlerAdapter.class);
    }

}
