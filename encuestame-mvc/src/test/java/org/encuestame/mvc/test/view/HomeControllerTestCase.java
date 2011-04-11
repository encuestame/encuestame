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
package org.encuestame.mvc.test.view;

import javax.inject.Inject;

import junit.framework.Assert;

import org.encuestame.business.security.AbstractSecurityContext;
import org.encuestame.mvc.view.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
public class HomeControllerTestCase{

        @Inject
        private ApplicationContext applicationContext;

        private MockHttpServletRequest request;
        private MockHttpServletResponse response;
        private HandlerAdapter handlerAdapter;
        private HomeController controller;

        @Before
        public void setUp() {
           request = new MockHttpServletRequest();
           response = new MockHttpServletResponse();
           Assert.assertNotNull(applicationContext);
           handlerAdapter = applicationContext.getBean(HandlerAdapter.class);
           // I could get the controller from the context here
           controller = new HomeController();
        }

        @Test
        public void testFoo() throws Exception {
           request.setRequestURI("/home");
           final ModelAndView mav = handlerAdapter.handle(request, response,
               controller);
           System.out.println(mav);
           //assertViewName(mav, null);
           //assertAndReturnModelAttributeOfType(mav, "image", Image.class);
        }

}
