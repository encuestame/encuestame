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

package org.encuestame.test.business.service;

import org.encuestame.core.service.imp.IPictureService;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.PictureType;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import java.util.Locale;

/**
 * Created by jpicado on 04/12/14.
 */
@Category(DefaultTest.class)
public class TestPictureService extends AbstractSpringSecurityContext {

    /**
     *
     */
    @Autowired
    IPictureService pictureService;


    /** {@link org.encuestame.persistence.domain.security.UserAccount}. **/
    private UserAccount secondary;


    /**
     * Mock HttpServletRequest.
     */
    MockHttpServletRequest request;

    /**
     *
     */
    @Before
    public void initData(){
        this.secondary = createUserAccount("juanpicado", createAccount());
        request = new MockHttpServletRequest();
        request.addPreferredLocale(Locale.ENGLISH);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testggetGravatarPicture() throws Exception {
           final String picture = this.pictureService.getAccountUserPicturePath(this.secondary);
           logPrint(picture);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testgetPicturePath() throws Exception {
        final byte[] t = this.pictureService.getProfilePicture(this.secondary.getUsername(), PictureType.DEFAULT);
        logPrint(t);
    }

    /**
     *
     * @return
     */
    public IPictureService getPictureService() {
        return pictureService;
    }

    /**
     *
     * @param pictureService
     */
    public void setPictureService(IPictureService pictureService) {
        this.pictureService = pictureService;
    }
}
