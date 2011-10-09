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

import java.io.IOException;

import javax.servlet.ServletException;

import junit.framework.Assert;

import org.apache.commons.lang.RandomStringUtils;
import org.encuestame.mvc.controller.security.json.JsonUsersController;
import org.encuestame.mvc.test.config.AbstractJsonMvcUnitBeans;
import org.encuestame.utils.enums.MethodJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

/**
 * Test {@link JsonUsersController}.
 * @author Picado, Juan juanATencuestame.org
 * @since 25/07/2011
 */
public class JsonUsersControllerTestCase extends AbstractJsonMvcUnitBeans {

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testUsers()
            throws ServletException, IOException {
        initService("/api/admon/users.json", MethodJson.GET);
        setParameter("limit", "10");
        setParameter("start", "0");
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONArray users = (JSONArray) success.get("users");
        /*
         * {"error":{},"success":{"total":1,"users":[
         * {"name":"admin","id":29729,"username":"admin",
         * "tweetPoll":0,"status":true,"password":null,"inviteCode":null,"lastTimeLogged":null,
         * "followers":null,"email":"admin.811667@users.com","dateNew":1311614740318,"listGroups":null,
         * "listPermission":[],"primaryUserId":null,
         * "groupBean":{"id":null,"stateId":null,"groupName":null,"groupDescription":null},
         * "survey":0,"poll":0,"ipLastLogged":null,"groupId":null,
         * "relateTimeEnjoy":"Hace 1 segundo"
         * }]}}
         */
        Assert.assertEquals(1, users.size());
        final JSONObject user = (JSONObject) users.get(0);
        Assert.assertEquals(user.get("username"), ADMINISTRATIVE_USER);
        Assert.assertEquals(user.get("id").toString(), getSpringSecurityLoggedUserAccount().getUid().toString());
    }

    /**
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void testUserInfo()
            throws ServletException, IOException {
        initService("/api/admon/user-info.json", MethodJson.GET);
        setParameter("id", getSpringSecurityLoggedUserAccount().getUid().toString());
        final JSONObject response = callJsonService();
        final JSONObject success = getSucess(response);
        final JSONObject user = (JSONObject) success.get("user");
        Assert.assertNotNull(user);
        //System.out.println(user);
        //Assert.assertEquals(1, polls.size());
    }

   /**
    *
    * @throws ServletException
    * @throws IOException
    */
   @Test
   public void testCreateUser()
           throws ServletException, IOException {
       initService("/api/admon/create-user.json", MethodJson.POST);
       setParameter("newUsername", RandomStringUtils.randomAlphabetic(10));
       setParameter("newEmailUser", RandomStringUtils.randomAlphabetic(10)+"@demo.org");
       final JSONObject response = callJsonService();
       //System.out.println(response);
       final JSONObject success = getSucess(response);
       final String ok = (String) success.get("userAdded");
       Assert.assertNotNull(ok);
       //Assert.assertEquals(1, polls.size());
   }
}
