package org.pao.beans;

/**
 * encuestame: system online surveys
 * Copyright (C) 2008-2009 encuestame Development Team *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation. *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA *
 * Id: UserLoginBean.java 1822 01/05/2009 14:54:45
 * @author dianmorales
 * @version 1.0
 * package org.pao.beans */

public class UserLoginBean {

	
	
	private String username;
	private String password;
	public UserLoginBean() {
		// TODO Auto-generated constructor stub 
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String ValidarUsuario() 
	{
		if(username.equals("diana") && password.equals("jota"))
		
			return "ok"; 
		else 
			return "bad login";
			
		
	}
	
	
	
}
