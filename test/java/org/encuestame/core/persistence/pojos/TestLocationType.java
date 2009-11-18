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
package org.encuestame.core.persistence.pojos;

import org.encuestame.core.persistence.pojo.CatLocationType;
import org.encuestame.test.config.AbstractBaseTest;
import org.junit.Test;

/**
 * Test Catalog Location Type.
 * 
 * @author Morales Urbina, Diana paola@encuestame.org
 * @since 06/11/2009 12:18:49
 */

public class TestLocationType extends AbstractBaseTest {
@Test
public void testLocationType(){
	final CatLocationType catLocType = new CatLocationType();
	catLocType.setDescription("Description");
	catLocType.setLevel(1);
	catLocType.setLocationTypeId(1L);
	getCatLocationType().saveOrUpdate(catLocType); 
} 

}
