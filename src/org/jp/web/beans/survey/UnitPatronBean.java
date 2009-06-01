package org.jp.web.beans.survey;

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
 * Id: UnitPatronBean.java Date: 01/06/2009 15:25:45
 * @author juanpicado
 * package: org.jp.web.beans.survey
 * @version 1.0
 */
public class UnitPatronBean extends MasterBean{
	
	private String descripcion;
	private String label;
	private Integer patronType;
	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}
	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}
	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	/**
	 * @return the patronType
	 */
	public Integer getPatronType() {
		return patronType;
	}
	/**
	 * @param patronType the patronType to set
	 */
	public void setPatronType(Integer patronType) {
		this.patronType = patronType;
	}
	
	

}
