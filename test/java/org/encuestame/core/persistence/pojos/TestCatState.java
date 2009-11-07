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

import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.test.config.AbstractBaseTest;

/**
* Test Catalog State Pojo.
* @author Morales, Diana Paola paola@encuestame.org
* @since November 06, 2009
*/
public class TestCatState {
    /**
     * Test Catalog State.
     */
	@Test
	public void testCatState(){
		final CatState catState = new CatState();
		catState.setDescState("State Description");
		catState.setImage("Image");
		//getCatStateDaoImp().saveorUpdate(catState);
	 } 
    
	/**
     * Test Catalog Location Constructor.
     */
	@Test
	public void testCatStateConstructor(){
	final CatState catState = new CatState("State Description","Image",null,null,null);
	}
}
