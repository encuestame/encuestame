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

import junit.framework.Assert;
import org.encuestame.mvc.page.HashTagController;
import org.encuestame.mvc.page.PictureProfileFactoryController;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * Picture Controller TestCase.
 */
@Category(DefaultTest.class)
public class PictureControllerTestCase extends AbstractMvcUnitBeans{

    @Autowired
    private PictureProfileFactoryController hashTagController;

    UserAccount user;

    @Before
     public void initMVc() {
        this.user = createUserAccount("jota", createAccount());
     }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testgetPictureThumbnail() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/picture/profile/jota/thumbnail");
        final ModelAndView mav = handlerAdapter.handle(request, response, hashTagController);
    }

    @Test
    public void testgetPictureMaster() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/picture/profile/jota/default");
        final ModelAndView mav = handlerAdapter.handle(request, response, hashTagController);
    }

    @Test
    public void testgetPictureProfile() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/picture/profile/jota/profile");
        final ModelAndView mav = handlerAdapter.handle(request, response, hashTagController);
    }

    @Test
    public void testgetPicturePreview() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/picture/profile/jota/preview");
        final ModelAndView mav = handlerAdapter.handle(request, response, hashTagController);
    }

    @Test
    public void testgetPictureWeb() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/picture/profile/jota/web");
        final ModelAndView mav = handlerAdapter.handle(request, response, hashTagController);
    }

    @Test(expected = NoSuchRequestHandlingMethodException.class)
    public void testgetPictureWebNotFound() throws Exception {
        request = new MockHttpServletRequest(MethodJson.GET.toString(), "/picture/xxxxxx/jota/web");
        final ModelAndView mav = handlerAdapter.handle(request, response, hashTagController);
    }

}
