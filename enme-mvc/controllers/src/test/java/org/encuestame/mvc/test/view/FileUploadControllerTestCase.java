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

import org.encuestame.core.config.EnMePlaceHolderConfigurer;
import org.encuestame.mvc.page.FileUploadController;
import org.encuestame.mvc.page.HomeController;
import org.encuestame.mvc.test.config.AbstractMultipartMvcUnitBeans;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * Created by jpicado on 04/12/14.
 */
@Category(DefaultTest.class)
public class FileUploadControllerTestCase extends AbstractMultipartMvcUnitBeans {


    @Autowired
    public FileUploadController fileUploadController;


    @Test
    public void handleUserProfileFileUploadEmtpyFail() throws Exception {
        this.quickLogin();
        EnMePlaceHolderConfigurer.setProperty("application.private", "true");
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                new byte[]{});
        request = new MockMultipartHttpServletRequest();
        request.addFile(mockMultipartFile);
        request.setRequestURI("/file/upload/profile");
        request.setMethod("POST");
        final ModelAndView mav = handlerAdapter.handle(request, response, fileUploadController);
        String status = (String) mav.getModelMap().get("status");
        Assert.assertNotNull(status);
        Assert.assertEquals(status, "failed");
    }

    @Test
    public void handleUserProfileFileUploadSaved() throws Exception {
        this.quickLogin();
        EnMePlaceHolderConfigurer.setProperty("application.private", "true");
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "abcdef".getBytes());
        request = new MockMultipartHttpServletRequest();
        request.addFile(mockMultipartFile);
        request.setRequestURI("/file/upload/profile");
        request.setMethod("POST");
        final ModelAndView mav = handlerAdapter.handle(request, response, fileUploadController);
        String status = (String) mav.getModelMap().get("status");
        Assert.assertNotNull(status);
        Assert.assertEquals(status, "saved");
    }


}
