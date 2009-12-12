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
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUserSecondary;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Before;
import org.junit.Test;

/**
 * UserDao.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 27, 2009
 * @version $Id$
 */
public class TestUserDao extends AbstractBaseTest {

    private SecUsers userPrimary;

    /**
     * Before.
     */
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
     */
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
     */
    @Test
    public void testUpdateUser(){
        final String newPassword = "54321";
        final String newEmail = "user2@users.com";
        final SecUserSecondary user = createSecondaryUser("user 1", this.userPrimary);
        user.setPassword(newPassword);
        user.setUserEmail(newEmail);
        getSecUserDao().saveOrUpdate(user);
        final SecUserSecondary retrieveUser = getSecUserDao().getSecondaryUserById(Long.valueOf(
              user.getUid()));
        assertEquals("Password should be",newPassword,
                      retrieveUser.getPassword());
        assertEquals("Email should be",newEmail,
                retrieveUser.getUserEmail());
    }

    /**
     * Test Get User by Username.
     */
    @Test
    public void testGetUserByUsername(){
          final SecUserSecondary user = createSecondaryUser("user 3", this.userPrimary);
          final SecUserSecondary retrieveUser = getSecUserDao()
          .getUserByUsername(user.getUsername());
          assertEquals("Username should be",user.getUsername(),
          retrieveUser.getUsername());
    }

    /**
     * Test Assing Group to User.
     */
    @Test
    public void testAssingGroupToUser(){
        final SecUserSecondary user = createSecondaryUser("user 4", this.userPrimary);
        final SecGroups group = super.createGroups("group 1");
        addGroupUser(user, group);
        final List<SecGroupUser> groups = getSecUserDao()
                                .getUserGroups(user);
        assertEquals("Should be equals", 1,groups.size());
    }

    /**
     * Test Search Group Permission.
     */
    @Test
    public void testgetGroupPermission(){
        final SecUserSecondary user = createSecondaryUser("user 5", this.userPrimary);
        final SecGroups security = super.createGroups("group 1");
        addGroupUser(user, security);
        final SecPermission editor = super.createPermission("permission");
        final SecPermission admon = super.createPermission("admon");
        addPermissionToGroup(admon, security);
        addPermissionToGroup(editor, security);
        final List<SecGroupPermission> listofPermissions = getSecUserDao()
        .getGroupPermission(getSecUserDao().getUserGroups(user));
        assertEquals("Should be equals",2, listofPermissions.size());
    }
}
