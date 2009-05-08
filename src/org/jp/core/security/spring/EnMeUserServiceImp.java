package org.jp.core.security.spring;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.jp.core.persistence.dao.UserDaoImp;
import org.jp.core.persistence.pojo.SecPermission;
import org.jp.core.persistence.pojo.SecUsers;
import org.jp.web.beans.TestBeanFaces;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.User;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

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
 * Id: EnMeUserServiceImp.java Date: 07/05/2009 14:19:02
 * 
 * @author juanpicado package: org.jp.core.security.spring
 * @version 1.0
 */
public class EnMeUserServiceImp implements EnMeUserService, UserDetailsService {

	protected UserDaoImp userDao;
	protected Boolean roleGroupAuth = true;
	protected Boolean roleUserAuth;
	private static Logger log = Logger.getLogger(EnMeUserServiceImp.class);

	public void setUserDao(UserDaoImp userDao) {
		this.userDao = userDao;
	}

	public void setRoleGroupAuth(Boolean roleGroupAuth) {
		this.roleGroupAuth = roleGroupAuth;
	}

	public void setRoleUserAuth(Boolean roleUserAuth) {
		this.roleUserAuth = roleUserAuth;
	}

	/**
	 * Carga el Usuario por nombre de usuario
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
		SecUsers user = userDao.getUser(username);
		if (user == null) {
			throw new UsernameNotFoundException("username");
		}
		return convertToUserDetails(user);
	}

	public SecUsers getSurveyUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSurveyUserPassword(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Convert Survey User to Spring Security UserDetails
	 * 
	 * @param user
	 * @return
	 */
	protected UserDetails convertToUserDetails(SecUsers user){
		List<String> listPermissions = new ArrayList<String>();
		if (user == null) {
			return null;
		}
		// verificamos si esta activado las autoridades por usuario
		if (this.roleGroupAuth == true) {
			List<SecPermission> listGroupPermissions = userDao
					.getGroupPermission(userDao.getUserGroups(user
							.getUsername()));
			log.info("listGroupPermissions "+listGroupPermissions);
			if (listGroupPermissions != null && listGroupPermissions.size() > 0) {
				Iterator<SecPermission> i = listGroupPermissions.iterator();
				while (i.hasNext()) {
					SecPermission secPermission = (SecPermission) i.next();
					if (listPermissions.indexOf(secPermission.getPermission()
							.trim()) == -1) {
						// se ignora porque el rol ya existe
					} else {
						listPermissions.add(secPermission.getPermission()
								.trim());
					}
				}				
			}
		}
		// verificamos si esta activado las autoridades por grupo
		if (this.roleUserAuth == true) {
			List<SecPermission> listUserPermissions = userDao
					.getUserPermission(user);
			log.info("listUserPermissions "+listUserPermissions);
			if (listUserPermissions != null && listUserPermissions.size() > 0) {
				Iterator<SecPermission> i = listUserPermissions.iterator();
				while (i.hasNext()) {
					SecPermission secPermission = (SecPermission) i.next();
					if (listPermissions.indexOf(secPermission.getPermission()
							.trim()) == -1) {
						// se ignora porque el rol ya existe
					} else {
						listPermissions.add(secPermission.getPermission()
								.trim());
					}
				}
			}
		}
		log.info("listPermissions "+listPermissions.size());
		// agrupamos todas las autoridades en una lista
		// crea las autoridades de spring
		GrantedAuthority[] authorities = new GrantedAuthority[listPermissions
				.size()];
		int i = 0;
		for (String permission : listPermissions) {
			authorities[i++] = new GrantedAuthorityImpl(permission.trim());
		}

		User userDetails = new User(user.getUsername(), user.getPassword(),
				user.isStatus() == null ? false : user.isStatus(), true, // account
				// not
				// expired
				true, // cridentials not expired
				true, // account not locked
				authorities);
		log.info("userDetails "+userDetails);
		return userDetails;
	}

}
