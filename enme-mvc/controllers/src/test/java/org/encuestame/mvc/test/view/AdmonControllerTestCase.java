/*
 *
 *  * Copyright 2014 encuestame
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *       http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package org.encuestame.mvc.test.view;

import org.encuestame.mvc.page.AdmonController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * Created by jpicado on 04/12/14.
 */
@Category(DefaultTest.class)
public class AdmonControllerTestCase  extends AbstractMvcUnitBeans {

    @Autowired
    private AdmonController admonController;


    @Test
    public void testadmonLocation() throws Exception {
        this.quickLogin();
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/location");
        final ModelAndView mav = handlerAdapter.handle(request, response, admonController);
        assertViewName(mav, "location");
    }

    @Test
    public void testmembers() throws Exception {
        this.quickLogin();
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/members");
        final ModelAndView mav = handlerAdapter.handle(request, response, admonController);
        assertViewName(mav, "members");
    }

    @Test
    public void testadmonProject() throws Exception {
        this.quickLogin();
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/project");
        final ModelAndView mav = handlerAdapter.handle(request, response, admonController);
        assertViewName(mav, "project");
    }

}
