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

import static org.junit.Assert.*;

import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.core.test.service.config.AbstractBase;
import org.junit.Test;
/**
 * UserDao.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 29, 2009
 * @version $Id$
 */
public class TestSecGroupDaoImp extends AbstractBase{

/**
  *Test Create Group.
 */
    @Test
    public void TestCreateGroup(){
        final SecGroups group = super.createGroups("first group");
        assertNotNull(group);
    }

    /**
     * Test Delete Group.
     */
    @Test
   public void TestDeleteGroup(){
        final SecGroups group = super.createGroups("second group");
        getSecGroup().delete(group);
        assertEquals("Should be equals",0, getSecGroup().findAllGroups().size());
    }

    /**Test Find All Groups*/
    @Test
    public void TestFindGroup()
    {
        super.createGroups("group thirth");
        super.createGroups("group fourth");
        assertEquals("Should be equals",2, getSecGroup().findAllGroups().size());

    }
    /**Test Update Group**/
    @Test
    public void TestUpdateGroup(){
    final String newname="Administrator";
    final String newdescription="System Admin";
    final SecGroups group = super.createGroups("fifth group");
    group.setGroupName(newname);
    group.setGroupDescriptionInfo(newdescription);
    getSecGroup().saveOrUpdate(group);
    final SecGroups retrieveGroup =getSecGroupDaoImp().getGroupById(Long.valueOf(group.getGroupId()));
    /*sertEquals("New Name should be",newname,
            retrieveGroup.getName());
    assertEquals("New Description should be",newdescription,
            retrieveGroup.getDesInfo());*/
    }

}
