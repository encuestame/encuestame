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

import org.encuestame.mvc.page.DashBoardController;
import org.encuestame.mvc.page.PictureProfileFactoryController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.EnMePermission;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * Dashboard Controller TestCase.
 */
@Category(DefaultTest.class)
public class DashBoardControllerTestCase extends AbstractMvcUnitBeans{

    /**
     *
     */
    @Autowired
    private DashBoardController dashBoardController;

    UserAccount user;

    /**
     *
     */
    @Before
     public void initMVc() {
        this.user = createUserAccount("jota", createAccount());
     }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testDashBoardController() throws Exception {
        this.quickLogin();
        logPrint(SecurityContextHolder.getContext().getAuthentication());
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/dashboard");
        final ModelAndView mav = handlerAdapter.handle(request, response, dashBoardController);
        assertViewName(mav, "dashboard");
        logPrint(mav.isEmpty());
        logPrint(mav.getModelMap().size());
        logPrint(mav.getModelMap().toString());
        Assert.assertNotNull(mav.getModelMap().get("userAccount"));
        Assert.assertNotNull(mav.getModelMap().get("i18n"));
        HashMap<String, String> i18n = (HashMap<String, String>) mav.getModelMap().get("i18n");
        Assert.assertNotNull(i18n.get("dashboard_title"));
    }



}
