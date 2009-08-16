package org.encuestame.core.persistence.dao.imp;

import java.util.List;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2008-2009 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: BaseDao.java 1822 23/04/2009 14:07:19
 * @author juanpicado
 * @version 1.0
 * package org.encuestame.core.persistence.dao
 */

public interface IBaseDao {

	/**
	 * 	Creates or updates a record in the table
	 * 
	 * @param obj
	 */
	//public void saveOrUpdate(Object obj);

	/**
	 * Deletes a table row
	 * 
	 * @param obj
	 */
	//public void delete(Object obj);

	/**
	 * 	Find an item in an entity by id, in Table
	 * @param clazz
	 * @param id
	 */
	//public Object find(Class clazz, Integer id);
		

	/**
	 * 	Return all the elements of an entity in Table
	 * 
	 * @param clazz
	 */

	//public List<Object> findAll();

	/**
     * 
     */
	
	//public List findAllOrderFor(Class clazz, String ordenado);

	/**
	 * 	Find all the elements of a table by a parameter and value Determined
	 * 
	 * @param clazz
	 * @param param
	 * @param campo
	 */

	//public Object findAllUnParam(Class clazz, String param, String campo);
	

	//public List<Object> findAllForParamList(Class clazz, List params, List campo);
	
	/**
	 * Find all the elements of a table by a parameter and value Determined
	 * 
	 * @param clazz
	 * @param param
	 * @param row
	 */

	//public List<Object> ListfindAllOneParam(Class clazz, String param,String row);

	/**
	 * Search results in an entity with Paging
	 * 
	 * @param clazz
	 * @param start
	 * @param ends
	 * @return
	 */
	//public Object searchPaginResults(Class clazz, Integer start,Integer ends);
	

	/**
	 * exception handler default
	 * @param e
	 */
	//public void handleException(HibernateException e)throws DataAccessLayerException;
	
	/**
	 * 
	 * @return the last id of a table
	 * @throws DataAccessLayerException
	 */
	//public Integer lastRow(Class clase, String id);
			

}
