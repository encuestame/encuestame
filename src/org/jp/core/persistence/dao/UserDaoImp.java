package org.jp.core.persistence.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jp.core.persistence.dao.imp.IUserDao;
import org.jp.core.persistence.pojo.SecGroupPermission;
import org.jp.core.persistence.pojo.SecGroupUser;
import org.jp.core.persistence.pojo.SecGroups;
import org.jp.core.persistence.pojo.SecPermission;
import org.jp.core.persistence.pojo.SecUserPermission;
import org.jp.core.persistence.pojo.SecUsers;
import org.jp.core.security.spring.EnMeUserServiceImp;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

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
 * 
 * Id: UserDaoImp.java Date: 07/05/2009
 * 
 * @author juanpicado package: org.jp.core.persistence.dao
 * @version 1.0
 */
public class UserDaoImp extends HibernateDaoSupport implements IUserDao {

	private static Logger log = Logger.getLogger(UserDaoImp.class);

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

	public Integer lastRow(Class clase, String id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void saveOrUpdate(Object obj) {
		// TODO Auto-generated method stub

	}

	/**
	 * Obtiene el usuario por nombre de usuarui
	 * 
	 * @param username
	 *            nombre de usuarii
	 * @return usuario o nulo si no lo encuentra
	 */
	public SecUsers getUser(String username) {
		List<SecUsers> users = getHibernateTemplate().findByNamedQuery(
				"User.loadUserByUserName", username);
		// obtiene el primer elemento
		if (users.size() > 0) {
			return (SecUsers) users.get(0);
		} else {
			return null;
		}
	}

	/**
	 * Obtiene una lista de Permisos de los diferentes grupos a los que
	 * pertenece
	 * 
	 * @param lista
	 *            de grupos
	 * @return lista de permisos
	 */
	public List<SecGroupPermission> getGroupPermission(List<SecGroupUser> groups) {
		List<SecGroupPermission> listGroupPermission = new ArrayList<SecGroupPermission>();
		Iterator<SecGroupUser> iList = groups.iterator();
		log.info("iniciando el while");
		while (iList.hasNext()) {
			SecGroupUser secGroups = (SecGroupUser) iList.next();
			log.info("secGroups " + secGroups.getSecGroups().getName());
			List<SecGroupPermission> permission = getHibernateTemplate()
					.findByNamedQuery("User.loadGroupPermission",secGroups.getSecGroups());
			log.info("permission para " + secGroups.getSecGroups().getName()
					+ "->" + permission.size());
			if (permission != null && permission.size() > 0) {
				Iterator<SecGroupPermission> ilistPermission = permission.iterator();
				while (ilistPermission.hasNext()) {
					SecGroupPermission secPermission = (SecGroupPermission) ilistPermission
							.next();
					listGroupPermission.add(secPermission);
				}
			}
		}
		log.info("lista de permisos " + listGroupPermission.size());
		return listGroupPermission;
	}

	/**
	 * Obtiene los permisos asignados a un usuario
	 * 
	 * @param user
	 *            usuario
	 * @return usuario o nulo si no lo encuentra
	 */
	public List<SecUserPermission> getUserPermission(SecUsers user) {
		List<SecUserPermission> userPermission = getHibernateTemplate()
				.findByNamedQuery("User.loadPermissionUser",
						user);
		if (userPermission == null || userPermission.size() == 0) {
			return null;
		} else {
			return userPermission;
		}
	}

	/**
	 * List of groups for one user
	 * 
	 * @param username
	 * @return list of user groups
	 */
	public List<SecGroupUser> getUserGroups(SecUsers user) {
		log.info("buscando getUserGroups " + user);
		List<SecGroupUser> userGroups = getHibernateTemplate()
				.findByNamedQuery("User.loadGroupsUser", user);
		if (userGroups == null || userGroups.size() == 0) {
			return null;
		} else {
			log.info("encontrado userGroups->" + userGroups.size());
			return userGroups;
		}
	}

}
