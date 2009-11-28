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

import org.encuestame.core.persistence.pojo.SecGroups;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Test;
/**
 * UserDao.
 *
 * @author Morales, Diana Paola paola@encuestame.org
 * @since October 29, 2009
 * File name: $HeadURL:$
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public class TestSecGroupDaoImp extends AbstractBaseTest{

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
    group.setName(newname);
    group.setDesInfo(newdescription);
    getSecGroup().saveOrUpdate(group);
  final SecGroups retrieveGroup =getSecGroupDaoImp().getGroupById(Long.valueOf(group.getGroupId()));
    /*sertEquals("New Name should be",newname,
            retrieveGroup.getName());
    assertEquals("New Description should be",newdescription,
            retrieveGroup.getDesInfo());*/
    }

}
