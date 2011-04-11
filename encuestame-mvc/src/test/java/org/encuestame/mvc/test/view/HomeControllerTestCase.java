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

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.mvc.view.DashBoardController;
import org.encuestame.mvc.view.HomeController;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
public class HomeControllerTestCase extends AbstractMvcUnitBeans{


        @Before
        public void initMVc() {

        }

        @Test
        public void testHome() throws Exception {
            HomeController controller = new HomeController();
            request = new MockHttpServletRequest("GET", "/home");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "home");
        }

        @Test
        public void testDashBoardController() throws Exception {
            DashBoardController controller = new DashBoardController();
            request = new MockHttpServletRequest("GET", "/user/dashboard");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "dashboard");
        }

}
