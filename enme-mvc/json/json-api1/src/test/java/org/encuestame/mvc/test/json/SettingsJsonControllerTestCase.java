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

package org.encuestame.mvc.test.json;

import org.encuestame.mvc.test.config.AbstractJsonV1MvcUnitBeans;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Created by jpicado on 06/12/14.
 */
@Category(DefaultTest.class)
public class SettingsJsonControllerTestCase extends AbstractJsonV1MvcUnitBeans {

    private UserAccount userAccount;

    @Before
    public void initStart(){
        this.userAccount = this.quickLogin();
    }

    /**
     *
     * @return
     * @throws ServletException
     * @throws IOException
     */
    private JSONObject getCurrentProfile() throws ServletException, IOException {
        initService("/api/settings/profile/my.json", MethodJson.GET);
        final JSONObject response = callJsonService();
        logPrint(response);
        final JSONObject sucess = getSucess(response);
        JSONObject user = (JSONObject) sucess.get("account");
        return user;
    }

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testmyAccountProfile() throws ServletException, IOException {
        JSONObject user = this.getCurrentProfile();
        Assert.assertNotNull(user);
        Assert.assertEquals(user.get("username"), this.userAccount.getUsername());
        Assert.assertEquals(user.get("email"), this.userAccount.getUserEmail());
        Assert.assertEquals(user.get("complete_name"), this.userAccount.getCompleteName());
        Assert.assertNull(user.get("private")); //FIXME: wrong value I guess
        Assert.assertNull(user.get("language")); //FIXME: wrong value I guess
        Assert.assertEquals(user.get("picture_source"), this.userAccount.getPictureSource().name());
    }


    @Test
    public void testupgradeProfileEmail() throws ServletException, IOException {
        initService("/api/settings/profile/email/update.json", MethodJson.POST);
        setParameter("data", "new@demo.fake");
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
        JSONObject user = this.getCurrentProfile();
        Assert.assertEquals(user.get("email"), "new@demo.fake");
    }

    @Test
    public void testupgradeProfileEmailError() throws ServletException, IOException {
        initService("/api/settings/profile/email/update.json", MethodJson.POST);
        setParameter("data", "new@demo.fa@e");
        final JSONObject response = callJsonService();
        JSONObject error = getErrors(response);
        Assert.assertNotNull(error.get("message"));
    }

    @Test
    public void testupgradeWrongTypeError() throws ServletException, IOException {
        initService("/api/settings/profile/wrong/update.json", MethodJson.POST);
        setParameter("data", "wrong");
        final JSONObject response = callJsonService();
        JSONObject error = getErrors(response);
        Assert.assertNotNull(error.get("message"));
    }

    @Test
    public void testupgradeProfileUsernamelError() throws ServletException, IOException {
        initService("/api/settings/profile/username/update.json", MethodJson.POST);
        setParameter("data", "a");
        final JSONObject response = callJsonService();
        JSONObject error = getErrors(response);
        Assert.assertNotNull(error.get("message"));
    }

    @Test
    public void testupgradeProfileUsername() throws ServletException, IOException {
        initService("/api/settings/profile/username/update.json", MethodJson.POST);
        setParameter("data", "jotadeveloper");
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
        // new login
        this.userAccount = getAccountDao().getUserAccountById(this.userAccount.getUid());
        this.login(this.userAccount);
        // new login
        JSONObject user = this.getCurrentProfile();
        Assert.assertEquals(user.get("username"), "jotadeveloper");
    }


    @Test
    public void testupgradeProfilePictureUploaded() throws ServletException, IOException {
        initService("/api/settings/profile/picture/update.json", MethodJson.POST);
        setParameter("data", "uploaded");
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
        JSONObject user = this.getCurrentProfile();
        Assert.assertEquals(user.get("picture_source"), "UPLOADED");
    }

    @Test
    public void testupgradeProfilePictureGravatar() throws ServletException, IOException {
        initService("/api/settings/profile/picture/update.json", MethodJson.POST);
        setParameter("data", "gravatar");
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
        JSONObject user = this.getCurrentProfile();
        Assert.assertEquals(user.get("picture_source"), "GRAVATAR");
    }

    @Test
    public void testupgradeProfileWelcome() throws ServletException, IOException {
        initService("/api/settings/profile/welcome/update.json", MethodJson.POST);
        setParameter("data", "0");
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
    }

    @Test
    public void testupgradeProfileInfoData() throws ServletException, IOException {
        initService("/api/settings/profile/page_info/update.json", MethodJson.POST);
        setParameter("data", "info data");
        final JSONObject response = callJsonService();
        assertSuccessResponse(response);
    }

    // big profile update

    @Test
    public void testupgradePostProfile() throws ServletException, IOException {
        UserAccount user = this.quickLogin();
        JSONObject user_current = this.getCurrentProfile();
        initService("/api/settings/profile/update.json", MethodJson.POST);
        setParameter("email", "dsd@dsad.com");
        setParameter("username", "jota");
        setParameter("completeName", "info data");
        setParameter("language", "es");
        setParameter("bio", "new bio info");
        final JSONObject response = callJsonService();
        logPrint(response.toString());
        assertSuccessResponse(response);
        //FIXME: the last service provoce sign off of the user
        user = this.quickLogin(); // force the login
        JSONObject user2 = this.getCurrentProfile();
        logPrint(user2.toString());
    }
}
