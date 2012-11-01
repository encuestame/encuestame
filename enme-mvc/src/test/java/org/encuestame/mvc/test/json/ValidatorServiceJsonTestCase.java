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
package org.encuestame.mvc.test.json;

import junit.framework.Assert;

import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.utils.categories.test.DefaultTest;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 18, 2011
 */
@Category(DefaultTest.class)
public class ValidatorServiceJsonTestCase extends AbstractJsonMvcUnitBeans{

    /**
     * Before.
     */
    @Before
    public void initTest() {
         Account account = createAccount();
        createUserAccount("jota", "jota@jota.com", account);
        createUserAccount("jota1", "jota1@jota.com", account);
        createUserAccount("jota2", "jota2@jota.com", account);
    }

    /**
     * Test not valid email.
     * @throws Exception
     */
    @Test
    public void validateEmailTestNotValid() throws Exception {
        initService("/api/public/validator/email.json", MethodJson.GET);
        setParameter("context", "test");
        setParameter("email", "jota@jota.com");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertEquals(sucess.get("valid").toString(), "false");
        Assert.assertEquals(sucess.get("msg").toString(), "This email is already registered");
    }

    /**
     * Test valid email.
     * @throws Exception
     */
    @Test
    public void validateEmailTestValid() throws Exception {
        initService("/api/public/validator/email.json", MethodJson.GET);
        setParameter("context", "test");
        setParameter("email", "jota555@jota.com");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertEquals(sucess.get("valid").toString(), "true");
    }


    @Test
    public void validateEmailTestEmpty() throws Exception {
        initService("/api/public/validator/email.json", MethodJson.GET);
        setParameter("context", "test");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertEquals(sucess.get("valid").toString(), "false");
    }

    @Test
    public void validateUsernameTestValid() throws Exception {
        initService("/api/public/validator/username.json", MethodJson.GET);
        setParameter("context", "test");
        setParameter("username", "jota3333");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertEquals(sucess.get("valid").toString(), "true");
        Assert.assertEquals(sucess.get("msg").toString(), "Username is perfect!");
    }

    @Test
    public void validateUsernameTestNoValid() throws Exception {
        initService("/api/public/validator/username.json", MethodJson.GET);
        setParameter("context", "test");
        setParameter("username", "jota");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertEquals(sucess.get("valid").toString(), "false");
        Assert.assertEquals(sucess.get("msg").toString(), "This username is already taken!");

    }

    @Test
    public void validateUsernameEmtpy() throws Exception {
        initService("/api/public/validator/username.json", MethodJson.GET);
        setParameter("context", "test");
        final JSONObject response = callJsonService();
        final JSONObject sucess = getSucess(response);
        Assert.assertEquals(sucess.get("valid").toString(), "false");
        Assert.assertEquals(sucess.get("msg").toString(), "A username is required!");
        //System.out.println(sucess);
    }
}
