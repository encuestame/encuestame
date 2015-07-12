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

import org.encuestame.mvc.page.SettingsController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * Created by jpicado on 06/12/14.
 */
@Category(DefaultTest.class)
public class SettingsControllerTestCase  extends AbstractMvcUnitBeans {

    /**
     *
     */
    @Autowired
    private SettingsController settingsController;

    /**
     *
     */
    UserAccount user;

    /**
     *
     */
    @Before
    public void initMVc() {
        this.user = this.quickLogin();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testsettingsAccountController() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/settings/social");
        final ModelAndView mav = handlerAdapter.handle(request, response, settingsController);
        assertViewName(mav, "settings/social");
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testsocialSettingsController() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/settings/configuration");
        final ModelAndView mav = handlerAdapter.handle(request, response, settingsController);
        assertViewName(mav, "settings/account");
    }
}
