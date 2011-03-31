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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import junit.framework.Assert;

import org.encuestame.persistence.domain.security.Group;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.test.config.AbstractBase;
import org.junit.Before;
import org.junit.Test;
/**
 * UserDao.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 29, 2009
 * @version $Id$
 */
public class TestSecGroupDaoImp extends AbstractBase{

    /** {@link UserAccount}. **/
    private UserAccount secondary;

    /** {@link Group} **/
    private Group secGroup;

    /** {@link Account} **/
    private Account secUser;


    @Before
    public void initData(){
        this.secUser = createAccount();
        this.secGroup = createGroups("adminGroup",this.secUser);
        this.secondary = createSecondaryUserGroup("paola", this.secUser, this.secGroup);
    }



    /**
    *Test Create Group.
    */
    @Test
    public void TestCreateGroup(){
        final Group group = super.createGroups("first group");
        assertNotNull(group);
    }

    /**
     * Test Delete Group.
     */
    @Test
   public void TestDeleteGroup(){
        final Group group = super.createGroups("second group");
        getGroup().delete(group);
        assertNotNull(this.secGroup);
        assertEquals("Should be equals",1, getGroup().findAllGroups().size());
    }

    /**Test Find All Groups*/
    @Test
    public void TestFindGroup()
    {
        super.createGroups("group thirth");
        super.createGroups("group fourth");
        assertEquals("Should be equals",3, getGroup().findAllGroups().size());

    }

    /**Test Update Group**/
    @Test
    public void TestUpdateGroup(){
        final String newName = "Administrator";
        final String newDescription = "System Admin";
        final Group group = super.createGroups("fifth group");
        group.setGroupName(newName);
        group.setGroupDescriptionInfo(newDescription);
        getGroup().saveOrUpdate(group);
        final Group retrieveGroup = getGroupDaoImp().getGroupById(
                Long.valueOf(group.getGroupId()));
        /*sertEquals("New Name should be",newname,
            retrieveGroup.getName());
    assertEquals("New Description should be",newdescription,
            retrieveGroup.getDesInfo());*/
    }

    /**
     * Test Get Count User by Group.
     */
    @Test
    public void testGetCountUserbyGroup(){
        Assert.assertNotNull(this.secondary.getGroup());
        Assert.assertNotNull(this.secondary.getGroup().getGroupId());
        final Long counterTest = 1L;
        final Long counter = getGroupDaoImp().getCountUserbyGroup(this.secGroup.getGroupId());
        assertEquals(counterTest, counter);
    }

    /**
     * Test Get Counter Users by Groups.
     */
    @Test
    public void testGetUsersbyGroups(){
        Assert.assertNotNull(this.secondary.getGroup());
        Assert.assertNotNull(this.secondary.getGroup().getGroupId());
        Assert.assertNotNull(this.secUser);
        final List<Object[]> usersGroups = getGroupDaoImp().getUsersbyGroups(this.secUser);
        final String groupName = this.secGroup.getGroupName();
        final String group = (String) usersGroups.get(0)[0];
        assertEquals("Should be equals",groupName, group);
        assertEquals("Should be equals",1, usersGroups.size());
     }

    /***
     * Test Get Counter Users by Groups.
     */
    @Test
    public void testCountUsersbyGroups(){
        Assert.assertNotNull(this.secondary.getGroup());
        Assert.assertNotNull(this.secondary.getGroup().getGroupId());
        Assert.assertNotNull(this.secUser);
        final List<Object[]> usersGroups = getGroupDaoImp().countUsersbyGroups(this.secUser.getUid());
        final String groupName = this.secGroup.getGroupName();
        final String group = (String) usersGroups.get(0)[0];
        assertEquals("Should be equals",groupName, group);
        assertEquals("Should be equals",1, usersGroups.size());
     }
}

