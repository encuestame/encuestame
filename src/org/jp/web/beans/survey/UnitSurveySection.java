package org.jp.web.beans.survey;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.web.beans.MasterBean;

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
 * Id: UnitSurveySection.java Date: 01/06/2009 15:21:31
 * @author juanpicado
 * package: org.jp.web.beans.survey
 * @version 1.0
 */
public class UnitSurveySection extends MasterBean  implements Serializable{

	

	private static final long serialVersionUID = 3516611324140033097L;
	private Integer id;
	private String name;
	private Integer stateId;
	private Log log = LogFactory.getLog(this.getClass());
	
	public UnitSurveySection() {}
	
	public UnitSurveySection(Integer id, String name, Integer stateId) {
		this.id = id;
		this.name = name;
		this.stateId = stateId;
	}
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		log.info("set Secction id->"+id);
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		log.info("set Secction Name->"+name);
		this.name = name;
	}
	/**
	 * @return the stateId
	 */
	public Integer getStateId() {
		return stateId;
	}
	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Integer stateId) {
		log.info("set Secction stateId->"+stateId);
		this.stateId = stateId;
	}
	
	
}
