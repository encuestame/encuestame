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

import org.encuestame.core.persistence.pojo.CatLocationType;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Test;

/**
 * Test Catatalog Location Type Dao.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since November 17,2009 09:10:55
 * File name: $HeadURL:$
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public class TestCatLocationTypeDao extends AbstractBaseTest{
    @Test
    public void testListLocationType(){
        super.createCatLocationType("First Location Type");
        // final List<CatLocationType> listCatLocationType = getCatLocationType().findAll();
        //    assertNotNull(listCatLocationType);
        //    assertEquals(listCatLocationType.size(),listCatLocationType.size());
    }

    public void testLocationTypeByID(){
        CatLocationType locType = super.createCatLocationType("Second Location Type");
        //final CatLocationType retrieveLocType = getCatLocationType().getLocationTypeById(Long.valueOf(locType.getLocationTypeId().toString()));
        //assertNotNull(retrieveLocType);

    }


}
