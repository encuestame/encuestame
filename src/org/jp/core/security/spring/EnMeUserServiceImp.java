package org.jp.core.security.spring;

import org.emforge.entity.Role;
import org.emforge.security.dao.UserDO;
import org.jp.core.persistence.dao.UserDaoImp;
import org.jp.core.persistence.pojo.SecUsers;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2009  encuestame Development Team
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
 * Id: EnMeUserServiceImp.java Date: 07/05/2009 14:19:02
 * @author juanpicado
 * package: org.jp.core.security.spring
 * @version 1.0
 */
public class EnMeUserServiceImp implements EnMeUserService, UserDetailsService{

	protected UserDaoImp userDao;
		
	public void setUserDao(UserDaoImp userDao) {
		this.userDao = userDao;
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

	/*
	 * Utils
	 */
	 
	 /** Convert Survey User to Spring Security UserDetails
		 * 
		 * @param user
		 * @return
		 */
		protected UserDetails convertToUserDetails(SecUsers user) {
			if (user == null) {
				return null;
			}
			
			// create authorities
			GrantedAuthority[] authorities = new GrantedAuthority[user.getRoles().size()];
			int i=0;
			for (Role role : user.getRoles()) {
				authorities[i++] = new GrantedAuthorityImpl(role.getName());
			}
			
			org.springframework.security.userdetails.User userDetails = 
				new org.springframework.security.userdetails.User(
						user.getUsername(),
						user.getPassword(),
						user.isActive() == null ? false : user.isActive(),
						true,				// account not expired
						true,				// cridentials not expired
						true,				// account not locked
						authorities);
			
			return userDetails;
		}
	
}
