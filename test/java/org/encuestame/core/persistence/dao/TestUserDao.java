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

import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Test;

/**
 * UserDao.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 27, 2009
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
        getUserDao().delete(user);
        assertEquals("Should be equals",0, getUserDao().findAll().size());
    }

    /**
     * Test find all users method.
     */
    @Test
    public void testFindAllUsers() {
        super.createUsers("user 1");
        super.createUsers("user 2");
        assertEquals("Should be equals",2, getUserDao().findAll().size());
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
        getUserDao().saveOrCreateUser(user);
        final SecUsers retrieveUser = getUserDao().getUserById(Long.valueOf(
              user.getUid().toString()));
        assertEquals("Password should be",newPassword,
                      retrieveUser.getPassword());
        assertEquals("Email should be",newEmail,
                retrieveUser.getEmail());
    }


}
