package org.jp.core.security.spring;

import java.util.ArrayList;
import java.util.List;

import org.jp.core.persistence.dao.UserDaoImp;
import org.jp.core.persistence.pojo.SecPermission;
import org.jp.core.persistence.pojo.SecUsers;
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
	protected UserDetails convertToUserDetails(SecUsers user) {
		List<SecPermission> listPermissions = new ArrayList<SecPermission>();
		if (user == null) {
			return null;
		}
		//verificamos si esta activado las autoridades por usuario
		if(this.roleGroupAuth == true){
			
		}
		
		//verificamos si esta activado las autoridades por grupo
		if(this.roleUserAuth == true){
			
		}		
		//agrupamos todas las autoridades en una lista
		

		// crea las autoridades de spring
		GrantedAuthority[] authorities = new GrantedAuthority[listPermissions.size()];
		int i = 0;
		for (SecPermission permission : listPermissions) {
			authorities[i++] = new GrantedAuthorityImpl(permission.getPermission());
		}

		User userDetails = new User(
				user.getUsername(), user.getPassword(),
				user.isStatus() == null ? false : user.isStatus(), true, // account not expired						
				true, // cridentials not expired
				true, // account not locked
				authorities);
		return userDetails;
	}

}
