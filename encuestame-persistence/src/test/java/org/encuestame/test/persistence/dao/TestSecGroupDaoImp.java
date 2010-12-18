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

import org.encuestame.persistence.domain.security.SecGroup;
import org.encuestame.persistence.domain.security.SecUser;
import org.encuestame.persistence.domain.security.SecUserSecondary;
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

    /** {@link SecUserSecondary}. **/
    private SecUserSecondary secondary;

    /** {@link SecGroup} **/
    private SecGroup secGroup;

    /** {@link SecUser} **/
    private SecUser secUser;


    @Before
    public void initData(){
         this.secGroup = createGroups("adminGroup");
         this.secUser = createUser();
         this.secondary = createSecondaryUserGroup("paola", this.secUser, this.secGroup);
    }



    /**
    *Test Create Group.
    */
    @Test
    public void TestCreateGroup(){
        final SecGroup group = super.createGroups("first group");
        assertNotNull(group);
    }

    /**
     * Test Delete Group.
     */
    @Test
   public void TestDeleteGroup(){
        final SecGroup group = super.createGroups("second group");
        getSecGroup().delete(group);
        assertEquals("Should be equals",1, getSecGroup().findAllGroups().size());
    }

    /**Test Find All Groups*/
    @Test
    public void TestFindGroup()
    {
        super.createGroups("group thirth");
        super.createGroups("group fourth");
        assertEquals("Should be equals",3, getSecGroup().findAllGroups().size());

    }
    /**Test Update Group**/
    @Test
    public void TestUpdateGroup(){
    final String newname="Administrator";
    final String newdescription="System Admin";
    final SecGroup group = super.createGroups("fifth group");
    group.setGroupName(newname);
    group.setGroupDescriptionInfo(newdescription);
    getSecGroup().saveOrUpdate(group);
    final SecGroup retrieveGroup =getSecGroupDaoImp().getGroupById(Long.valueOf(group.getGroupId()));
    /*sertEquals("New Name should be",newname,
            retrieveGroup.getName());
    assertEquals("New Description should be",newdescription,
            retrieveGroup.getDesInfo());*/
    }

    public void testGetCountUserbyGroup(){
        final Long counterTest = 1L;
        final Long counter = getSecGroupDaoImp().getCountUserbyGroup(this.secGroup.getGroupId());
        assertEquals(counterTest, counter);
    }

    public void testGetUsersbyGroups(){
        final List<Object[]> usersGroups = getSecGroupDaoImp().getUsersbyGroups(this.secUser);
        assertEquals("Should be equals",1, usersGroups.size());


    }
}
