package org.jp.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.persistence.dao.SecGroupDaoImp;
import org.jp.core.persistence.dao.SecPermissionDaoImp;
import org.jp.core.persistence.dao.UserDaoImp;
import org.jp.core.persistence.pojo.SecGroups;
import org.jp.web.beans.admon.UnitGroupBean;

/**
 * encuestame: system online surveys Copyright (C) 2005-2008 encuestame
 * Development Team
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
 * Id: SecurityService.java Date: 27/04/2009
 * 
 * @author juanpicado package: org.jp.core.service
 * @version 1.0
 */
public class SecurityService implements ISecurityService {

	private Log log = LogFactory.getLog(this.getClass());
	private UserDaoImp userDao;
	private SecGroupDaoImp groupDao;
	private SecPermissionDaoImp permissionDao;

	public UserDaoImp getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDaoImp userDao) {
		this.userDao = userDao;
	}

	public SecGroupDaoImp getGroupDao() {
		return groupDao;
	}

	public void setGroupDao(SecGroupDaoImp groupDao) {
		this.groupDao = groupDao;
	}

	public SecPermissionDaoImp getPermissionDao() {
		return permissionDao;
	}

	public void setPermissionDao(SecPermissionDaoImp permissionDao) {
		this.permissionDao = permissionDao;
	}

	/**
	 * Delete Group
	 * 
	 * @param group
	 */
	public void deleteGroup(UnitGroupBean group) {
		log.info("deleting group ->"+group.getGroupName());
		SecGroups g = getGroupDao().find(group.getId());
		log.info("deleting search group ->"+g.getName());
		getGroupDao().delete(g);
	}

	/**
	 * Update a Group
	 * 
	 * @param group
	 */
	public void updateGroup(UnitGroupBean uGroup) {
		SecGroups group = getGroupDao().find(uGroup.getId());
		if (group != null) {
			group.setName(uGroup.getGroupName());
			group.setDesInfo(uGroup.getGroupDescription());
			group.setIdState((new Integer(uGroup.getStateId())));
		}
		getGroupDao().update(group);
	}

	/**
	 * Create a new Group
	 * 
	 * @param group
	 */
	public void createGroup(UnitGroupBean group) {
		SecGroups newG = new SecGroups();
		newG.setDesInfo(group.getGroupDescription());
		newG.setName(group.getGroupName());
		newG.setIdState(new Integer(group.getStateId()).intValue());
		getGroupDao().newGroup(newG);
	}

}
