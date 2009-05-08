package org.jp.core.persistence.dao;

import java.util.List;

import org.jp.core.persistence.dao.imp.IUserDao;
import org.jp.core.persistence.pojo.SecGroups;
import org.jp.core.persistence.pojo.SecPermission;
import org.jp.core.persistence.pojo.SecUsers;
import org.jp.core.persistence.util.DataAccessLayerException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2009 encuestame Development Team
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
 * Id: UserDaoImp.java Date: 07/05/2009 
 * @author juanpicado
 * package: org.jp.core.persistence.dao
 * @version 1.0
 */
public class UserDaoImp extends HibernateDaoSupport implements IUserDao {

	public void delete(Object obj) {
		// TODO Auto-generated method stub

	}

	public Object find(Class clazz, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer lastRow(Class clase, String id)
			throws DataAccessLayerException {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrUpdate(Object obj) {
		// TODO Auto-generated method stub

	}

	/**
	 * Obtiene el usuario por nombre de usuarui
	 * @param username nombre de usuarii
	 * @return usuario o nulo si no lo encuentra
	 */
	public SecUsers getUser(String username) {
		  List users = getHibernateTemplate().findByNamedQuery("User.loadUserByUserName", username);
	        // obtiene el primer elemento
	        if (users.size() > 0) {
	            return (SecUsers) users.get(0);
	        } else {
	            return null;
	        }
	}
	
	

	public  List<SecPermission> getGroupPermission(List<SecGroups> groups) {
		List permission = getHibernateTemplate().find("from SecGroupPermission d where d.bmGrupo = "+ user.getUid() + " and d.estado=1");
		return null;
	}

	public List<SecPermission> getUserPermission(SecUsers user) {
		
		return null;
	}
	
	

}
