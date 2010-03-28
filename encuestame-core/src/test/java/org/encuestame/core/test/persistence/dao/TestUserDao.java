/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.core.test.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * UserDao.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 27, 2009
 * @version $Id$
 */
public class TestUserDao extends AbstractBase {

    private SecUsers userPrimary;

    /**
     * Before.
     **/
    @Before
    public void initService(){
        this.userPrimary = createUser();
    }

    /***
     *Test Create User.
     */
    @Test
    public void testCreateUser() {
       final SecUserSecondary user = createSecondaryUser("user 1", this.userPrimary);
       assertNotNull(user);
    }

    /**
     * Test delete user method.
     **/
    @Test
    public void testDeleteUser() {
        final SecUserSecondary user = createSecondaryUser("user 2", this.userPrimary);
         getSecUserDao().delete(user);
        assertEquals("Should be equals",0, getSecUserDao().findAll().size());
    }

    /**
     * Test find all users method.
     */
    @Test
    public void testFindAllUsers() {
        createSecondaryUser("user 1", this.userPrimary);
        createSecondaryUser("user 2", this.userPrimary);
        assertEquals("Should be equals",2, getSecUserDao().findAll().size());
    }

    /**
     * Test Update user.
     **/
    @Test
    public void testUpdateUser(){
        final String newPassword = "67809";
        final String newEmail = "user2@users.com";
        final SecUserSecondary user = createSecondaryUser("user 1", this.userPrimary);
        user.setPassword(newPassword);
        user.setUserEmail(newEmail);
        getSecUserDao().saveOrUpdate(user);
         final SecUserSecondary retrieveUser = getSecUserDao()
         .getSecondaryUserById(Long.valueOf(user.getUid()));
     assertEquals("Password should be",newPassword,
                     retrieveUser.getPassword());
        assertEquals("Email should be",newEmail,
               retrieveUser.getUserEmail());
    }

    /**
     * Test Get User by Username.
     **/
    @Test
    public void testGetUserByUsername(){
        final SecUserSecondary user = createSecondaryUser("user 3", this.userPrimary);
        final SecUserSecondary retrieveUser = getSecUserDao()
        .getUserByUsername(user.getUsername());
        assertEquals("Username should be",user.getUsername(), retrieveUser.getUsername());
    }

    /**
     * Test Assing Group to User.
     **/
    @SuppressWarnings("unchecked")
    @Test
    public void testAssingGroupToUser(){
         final SecUserSecondary user = createSecondaryUser("user 4", this.userPrimary);
         final SecGroups group = super.createGroups("group 1");
         user.getSecGroups().add(group);
         getSecGroup().saveOrUpdate(user);
         assertEquals("Should be equals", 1, user.getSecGroups().size());
    }

    /**
     * Test Add Permission to Group.
     */
    @Test
    public void testAddPermissionToGroup(){
        final SecPermission editor = createPermission("editor");
        final SecPermission admon = createPermission("publisher");
        final SecPermission permission = createPermission("administrator");
        final SecGroups group = createGroups("group 1");
        group.getSecPermissions().add(editor);
        group.getSecPermissions().add(admon);
        group.getSecPermissions().add(permission);
        getSecGroup().saveOrUpdate(group);
        assertEquals("Should be equals", 3, group.getSecPermissions().size());
    }

    /**
     * Test getSecondaryUsersByUserId.
     */
    public void testGetSecondaryUsersByUserId(){
         createSecondaryUser("user 1", this.userPrimary);
         createSecondaryUser("user 2", this.userPrimary);
         final List<SecUserSecondary> userList = getSecUserDao().getSecondaryUsersByUserId(this.userPrimary.getUid());
         assertEquals("Should be equals", 2, userList.size());
    }
}
