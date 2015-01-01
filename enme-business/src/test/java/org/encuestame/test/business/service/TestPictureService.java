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
import org.encuestame.core.service.startup.DirectorySetupOperations;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.PictureType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;

import java.io.File;
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
       Assert.assertNotNull(picture);
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void testgetProfilePicture() throws Exception {
        final byte[] t = this.pictureService.getProfilePicture(this.secondary.getUsername(), PictureType.DEFAULT);
        //System.out.println(t);
        Assert.assertNotNull(t);
        final byte[] t1 = this.pictureService.getProfilePicture(this.secondary.getUsername(), PictureType.ICON);
        final byte[] t2 = this.pictureService.getProfilePicture(this.secondary.getUsername(), PictureType.PREVIEW);
        final byte[] t3 = this.pictureService.getProfilePicture(this.secondary.getUsername(), PictureType.PROFILE);
        final byte[] t4 = this.pictureService.getProfilePicture(this.secondary.getUsername(), PictureType.THUMBNAIL);
        Assert.assertNotNull(t1);
        Assert.assertNotNull(t2);
        Assert.assertNotNull(t3);
        Assert.assertNotNull(t4);
        this.secondary.setPictureSource(UserAccount.PictureSource.UPLOADED);
        this.getAccountDao().saveOrUpdate(this.secondary);
        new File(DirectorySetupOperations.getHomeDirectory() + "/pictures").mkdirs();
        new File(DirectorySetupOperations.getHomeDirectory() + "/pictures/"+this.secondary.getUid()).mkdirs();
        new File(DirectorySetupOperations.getHomeDirectory() + "/pictures/"+this.secondary.getUid() + "/file_64.jpg").createNewFile();
        final byte[] t5 = this.pictureService.getProfilePicture(this.secondary.getUsername(), PictureType.THUMBNAIL);
        Assert.assertNotNull(t5);
        this.secondary.setPictureSource(UserAccount.PictureSource.GRAVATAR);
        this.getAccountDao().saveOrUpdate(this.secondary);
        final byte[] t6 = this.pictureService.getProfilePicture(this.secondary.getUsername(), PictureType.THUMBNAIL);
        Assert.assertNotNull(t6);
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
