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

import org.encuestame.mvc.page.ErrorController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Ignore;
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
public class ErrorControllerTestCase  extends AbstractMvcUnitBeans {

    @Autowired
    private ErrorController errorController;

    @Test
    public void missingPage() throws Exception {
        this.quickLogin();
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/missing");
        final ModelAndView mav = handlerAdapter.handle(request, response, errorController);
        assertViewName(mav, "404");
    }

    @Test
    public void badStatu2s() throws Exception {
        this.quickLogin();
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/400");
        final ModelAndView mav = handlerAdapter.handle(request, response, errorController);
        assertViewName(mav, "error");
    }

    @Test
    public void testDenied() throws Exception {
        this.quickLogin();
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/denied");
        final ModelAndView mav = handlerAdapter.handle(request, response, errorController);
        assertViewName(mav, "error/denied");
    }


    @Test
    public void errorController() throws Exception {
        this.quickLogin();
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/error");
        final ModelAndView mav = handlerAdapter.handle(request, response, errorController);
        assertViewName(mav, "redirect:/home");
    }

    @Test
    public void errorControllerMessage() throws Exception {
        this.quickLogin();
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/error");
        request.setAttribute("message", "errorMessage");
        final ModelAndView mav = handlerAdapter.handle(request, response, errorController);
        assertViewName(mav, "error");
    }
}
