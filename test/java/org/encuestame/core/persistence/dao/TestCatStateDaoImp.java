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

import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.test.config.BaseManager;
import org.junit.Test;

/**
 * CatStaeDaoImp
 * @author Picado, Juan juan@encuestame.org
 * @since October 15, 2009
 */
public class TestCatStateDaoImp extends BaseManager{

    @Test
    public void testListCatState(){
        CatState state = super.createState("state1");
        System.out.println(state);
        List<CatState> listStates = getCatStateDaoImp().findAll();
        System.out.println(listStates.size());
        assertNotNull(listStates);
        assertEquals(listStates.size(),listStates.size());
    }
}
