package org.encuestame.web.beans.admon;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.encuestame.web.beans.MasterBean;

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
 * Id: GroupBean.java Date: 27/04/2009
 * 
 * @author juanpicado package: org.encuestame.web.beans.admon
 * @version 1.0
 */

public class UnitUserBean extends MasterBean implements Serializable {

	private static final long serialVersionUID = -6690522000664394521L;
	private Integer id;
	private String email;
	private String name;
	private String username;
	private Boolean status;
	private String password;
	private String invite_code;
	private Date date_new;
	private String publisher;
	private Collection<UnitGroupBean> listGroups;
	private Collection<UnitPermission> listPermission;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getInvite_code() {
		return invite_code;
	}

	public void setInvite_code(String invite_code) {
		this.invite_code = invite_code;
	}

	public Date getDate_new() {
		return date_new;
	}

	public void setDate_new(Date date_new) {
		this.date_new = date_new;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public Collection<UnitGroupBean> getListGroups() {
		return listGroups;
	}

	public void setListGroups(Collection<UnitGroupBean> listGroups) {
		this.listGroups = listGroups;
	}

	public Collection<UnitPermission> getListPermission() {
		return listPermission;
	}

	public void setListPermission(Collection<UnitPermission> listPermission) {
		this.listPermission = listPermission;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
