/**
 * 
 */
package org.encuestame.web.beans.admon;

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
 * Id: CreateStateBean.java Date: 27/04/2009
 * 
 * @author juanpicado package: org.encuestame.web.beans.admon
 * @version 1.0
 */
public class CreateStateBean {

	public Integer idestado;
	public String description;
	public String image;
	/**
	 * 
	 */
	public CreateStateBean() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * @return the idestado
	 */
	public Integer getIdestado() {
		return idestado;
	}
	/**
	 * @param idestado the idestado to set
	 */
	public void setIdestado(Integer idestado) {
		this.idestado = idestado;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

}
