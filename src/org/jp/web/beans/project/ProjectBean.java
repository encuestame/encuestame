package org.jp.web.beans.project;

import java.util.Collection;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.web.beans.MasterBean;
import org.jp.web.beans.admon.UnitUserBean;

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
 * Id: ProjectBean.java Date: 26/05/2009 10:25:05
 * 
 * @author juanpicado package: org.jp.web.beans.project
 * @version 1.0
 */
public class ProjectBean extends MasterBean {

	public Boolean noProyects = true;
	public Boolean create = true;
	public Boolean edit;
	private Log log = LogFactory.getLog(this.getClass());
	private UnitProjectBean beanUProyect;
	private Collection<UnitProjectBean> list_unitBeans;

	public ProjectBean() {
		log.info("create proyect bean");
	}

	
	public Collection<UnitProjectBean> loadListProjects() throws Exception {
		list_unitBeans = new LinkedList<UnitProjectBean>();
		return list_unitBeans = getServicemanagerBean().getDataService().loadListProjects();
	}
	
	/**
	 * save data new proyect
	 */
	public void saveProyect() {
		try {
			log.info("save proyect");
			log.info("name->" + getBeanUProyect().getName());
			addInfoMessage("Proyecto Creado", "");
		} catch (Exception e) {
			addErrorMessage("Error Creando Proyecto", "");
		}

	}

	/**
	 * change to create form
	 */
	public void changeCreate() {
		try {
			log.info("changeCreate");
			setCreate(true);
			setEdit(false);
			log.info("Create " + getCreate());
			log.info("Edit " + getEdit());
		} catch (Exception e) {
			addErrorMessage("No se puede cambiar->" + e, e.getMessage());
		}
	}

	/**
	 * change to edit form
	 */
	public void changeEdit() {
		log.info("changeEdit");
		setCreate(false);
		setEdit(true);
		log.info("Create " + getCreate());
		log.info("Edit " + getEdit());
	}

	/**
	 * @return the noProyects
	 */
	public Boolean getNoProyects() {
		return noProyects;
	}

	/**
	 * @param noProyects
	 *            the noProyects to set
	 */
	public void setNoProyects(Boolean noProyects) {
		this.noProyects = noProyects;
	}

	/**
	 * @return the create
	 */
	public Boolean getCreate() {
		return create;
	}

	/**
	 * @param create
	 *            the create to set
	 */
	public void setCreate(Boolean create) {
		this.create = create;
	}

	/**
	 * @return the edit
	 */
	public Boolean getEdit() {
		return edit;
	}

	/**
	 * @param edit
	 *            the edit to set
	 */
	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	/**
	 * @return the beanUProyect
	 */
	public UnitProjectBean getBeanUProyect() {
		return beanUProyect;
	}

	/**
	 * @param beanUProyect
	 *            the beanUProyect to set
	 */
	public void setBeanUProyect(UnitProjectBean beanUProyect) {
		this.beanUProyect = beanUProyect;
	}

	/**
	 * @return the list_unitBeans
	 */
	public Collection<UnitProjectBean> getList_unitBeans() {
		return list_unitBeans;
	}

	/**
	 * @param list_unitBeans the list_unitBeans to set
	 */
	public void setList_unitBeans(Collection<UnitProjectBean> list_unitBeans) {
		this.list_unitBeans = list_unitBeans;
	}

}
