package org.jp.web.beans.admon;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.jp.core.persistence.pojo.SecGroupUser;
import org.jp.core.persistence.pojo.SecGroups;
import org.jp.core.persistence.pojo.SecPermission;
import org.jp.web.beans.MasterBean;

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
 * @author juanpicado package: org.jp.web.beans.admon
 * @version 1.0
 */

public class UnitUserBean extends MasterBean implements Serializable {

	private static final long serialVersionUID = -6690522000664394521L;
	private String id;
	private String sex;
	private String name;
	private String email;
	private String username;
	private String birth_date;
	private String address;
	private String noc_id;
	private Boolean status;
	private String invite_code;
	private Integer id_state;
	private String date_new;
	private String publisher;
	private Collection<UnitGroupBean> listGroups;
	private Collection<UnitPermission> listPermission;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getBirth_date() {
		return birth_date;
	}

	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNoc_id() {
		return noc_id;
	}

	public void setNoc_id(String noc_id) {
		this.noc_id = noc_id;
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

	public Integer getId_state() {
		return id_state;
	}

	public void setId_state(Integer id_state) {
		this.id_state = id_state;
	}

	public String getDate_new() {
		return date_new;
	}

	public void setDate_new(String date_new) {
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

	
	

}
