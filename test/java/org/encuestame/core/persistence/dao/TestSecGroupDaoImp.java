/**
 *
 */
package org.encuestame.core.persistence.dao;

import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Test;
import org.encuestame.core.persistence.pojo.SecGroups;
/**
 * @author paola
 *
 */
public class TestSecGroupDaoImp extends AbstractBaseTest{

/**
  *Test Create Group.
 */
    @Test
    public void TestCreateGroup(){
        final SecGroups group = super.createGroups("grupo uno");
        assertNotNull(group);
    }

    /**
     * Test Delete Group.
     */
    @Test
   public void TestDeleteGroup(){
        final SecGroups group = super.createGroups("grupo dos");
        getSecGroup().Delete(group);
        assertEquals("Should be equals",0, getSecGroup().findAllGroups().size());
    }

}
