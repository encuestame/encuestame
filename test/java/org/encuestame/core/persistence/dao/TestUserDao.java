/**
 * encuestame: system online surveys Copyright (C) 2009 encuestame Development
 * Team
 *
 * This program is free software; you can redistribute it and/or modify it under
 * the terms of version 3 of the GNU General Public License as published by the
 * Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA
 */
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.pojo.SecGroupPermission;
import org.encuestame.core.persistence.pojo.SecGroupUser;
import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.persistence.pojo.SecPermission;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Test;

/**
 * UserDao.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 27, 2009
 * File name: $HeadURL:$
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public class TestUserDao extends AbstractBaseTest {

    /***
     *Test Create User.
     */
    @Test
    public void testCreateUser() {
        final SecUsers user = super.createUsers("user 1");
        assertNotNull(user);
    }

    /**
     * Test delete user method.
     */
    @Test
    public void testDeleteUser() {
        final SecUsers user = super.createUsers("user 1");
        getSecUserDao().delete(user);
        assertEquals("Should be equals",1, getSecUserDao().findAll().size());
    }

    /**
     * Test find all users method.
     */
    @Test
    public void testFindAllUsers() {
        super.createUsers("user 1");
        super.createUsers("user 2");
        assertEquals("Should be equals",3, getSecUserDao().findAll().size());
    }

    /**
     * Test Update user.
     */
    @Test
    public void testUpdateUser(){
        final String newPassword = "54321";
        final String newEmail = "user2@users.com";
        final SecUsers user = super.createUsers("user 1");
        user.setPassword(newPassword);
        user.setEmail(newEmail);
        getSecUserDao().saveOrUpdate(user);
        final SecUsers retrieveUser = getSecUserDao().getUserById(Long.valueOf(
              user.getUid()));
        assertEquals("Password should be",newPassword,
                      retrieveUser.getPassword());
        assertEquals("Email should be",newEmail,
                retrieveUser.getEmail());
    }

    /**
     * Test Get User by Username.
     */
    @Test
    public void testGetUserByUsername(){
          final SecUsers user = super.createUsers("user 3");
          final SecUsers retrieveUser = getSecUserDao()
          .getUserByUsername(user.getUsername());
          assertEquals("Username should be",user.getUsername(),
          retrieveUser.getUsername());
    }

    /**
     * Test Assing Group to User.
     */
    @Test
    public void testAssingGroupToUser(){
        final SecUsers user = super.createUsers("user 4");
        final SecGroups group = super.createGroups("group 1");
        super.addGroupUser(user, group);
        final List<SecGroupUser> groups = getSecUserDao()
                                .getUserGroups(user);
        assertEquals("Should be equals", 1,groups.size());
    }

    /**
     * Test Search Group Permission.
     */
    @Test
    public void testgetGroupPermission(){
        final SecUsers user = super.createUsers("user 1");
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
