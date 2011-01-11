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
package org.encuestame.test.persistence.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Permission;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;

/**
 * UserDao.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 27, 2009
 * @version $Id$
 */
public class TestUserDao extends AbstractBase {

    private Account userPrimary;

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
       final UserAccount user = createSecondaryUser("user 1", this.userPrimary);
       assertNotNull(user);
    }

    /**
     * Test delete user method.
     **/
    @Test
    public void testDeleteUser() {
        final UserAccount user = createSecondaryUser("user 2", this.userPrimary);
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
        final UserAccount user = createSecondaryUser("user 1", this.userPrimary);
        user.setPassword(newPassword);
        user.setUserEmail(newEmail);
        getSecUserDao().saveOrUpdate(user);
         final UserAccount retrieveUser = getSecUserDao()
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
        final UserAccount user = createSecondaryUser("user 3", this.userPrimary);
        final UserAccount retrieveUser = getSecUserDao()
        .getUserByUsername(user.getUsername());
        assertEquals("Username should be",user.getUsername(), retrieveUser.getUsername());
    }

    /**
     * Test Assing Group to User.
     **/
/*    @SuppressWarnings("unchecked")
    @Test
    public void testAssingGroupToUser(){
         final SecUserSecondary user = createSecondaryUser("user 4", this.userPrimary);
         final SecGroup group = super.createGroups("group 1");
         user.getSecGroups().add(group);
         getSecGroup().saveOrUpdate(user);
         assertEquals("Should be equals", 1, user.getSecGroups().size());
    }*/

    /**
     * Test Add Permission to Group.
     */
    @Test
    public void testAddPermissionToGroup(){
        final Permission editor = createPermission("editor");
        final Permission admon = createPermission("publisher");
        final Permission permission = createPermission("administrator");
        final Group group = createGroups("group 1");
        group.getPermissions().add(editor);
        group.getPermissions().add(admon);
        group.getPermissions().add(permission);
        getGroup().saveOrUpdate(group);
        assertEquals("Should be equals", 3, group.getPermissions().size());
    }

    /**
     * Test getSecondaryUsersByUserId.
     */
    public void testGetSecondaryUsersByUserId(){
         createSecondaryUser("user 1", this.userPrimary);
         createSecondaryUser("user 2", this.userPrimary);
         final List<UserAccount> userList = getSecUserDao().getSecondaryUsersByUserId(this.userPrimary.getUid());
         assertEquals("Should be equals", 2, userList.size());
    }

    /**
     * Test.
     */
    @Test
    public void testSearchUsersByEmail(){
        final UserAccount secondary = createSecondaryUser("jhon", this.userPrimary);
        createSecondaryUser("paola", this.userPrimary);
        final List<UserAccount> users = getSecUserDao().searchUsersByEmail(secondary.getUserEmail());
        assertEquals("Should be equals", 1, users.size());
    }
}
