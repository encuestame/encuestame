package org.jp.core.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.jp.core.persistence.dao.CatLocationDaoImp;
import org.jp.core.persistence.dao.CatStateDaoImp;
import org.jp.core.persistence.dao.ProyectDaoImp;
import org.jp.core.persistence.pojo.Project;
import org.jp.core.persistence.pojo.SecUsers;
import org.jp.web.beans.admon.UnitUserBean;
import org.jp.web.beans.project.UnitProjectBean;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
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
 * Id: DataService.java Date: 27/04/2009 
 * @author juanpicado
 * package: org.jp.core.service
 * @version 1.0
 */
public class DataService extends MasterService implements IDataService {
	
	private CatStateDaoImp stateDao;
	private CatLocationDaoImp catLocationDaoImp;
	private ProyectDaoImp proyectDaoImp;
	

	public Collection<UnitProjectBean> loadListProjects(){
		
		Collection<UnitProjectBean> listProjects = new LinkedList<UnitProjectBean>();
		Collection<Project> list = getProyectDaoImp().findAll();
		
		
		if (list != null && list.size() > 0) {
			for (Iterator<Project> i = list.iterator(); i.hasNext();) {
				UnitProjectBean proB = new UnitProjectBean();
				Project project = i.next();
				proB.setId(project.getProyectId());
				proB.setName(project.getDescription());
				proB.setDescription(project.getInfo());
				proB.setDateInit(project.getDateStart());
				proB.setDateFinish(project.getDateFinish());
				
				userB.setListGroups(convertSetToUnitGroupBean(user.getUid()));
				userB.setListPermission(convertSetToUnitPermission(user
						.getUid()));
				loadListUsers.add(userB);
			}
		}
		return loadListUsers;
		
		
	}
	
	/**
	 * 
	 * @return
	 */
	public CatStateDaoImp getStateDao() {
		return stateDao;
	}

	/**
	 * 
	 * @param stateDao
	 */
	public void setStateDao(CatStateDaoImp stateDao) {
		this.stateDao = stateDao;
	}

	/**
	 * @return the catLocationDaoImp
	 */
	public CatLocationDaoImp getCatLocationDaoImp() {
		return catLocationDaoImp;
	}

	/**
	 * @param catLocationDaoImp the catLocationDaoImp to set
	 */
	public void setCatLocationDaoImp(CatLocationDaoImp catLocationDaoImp) {
		this.catLocationDaoImp = catLocationDaoImp;
	}

	/**
	 * @return the proyectDaoImp
	 */
	public ProyectDaoImp getProyectDaoImp() {
		return proyectDaoImp;
	}

	/**
	 * @param proyectDaoImp the proyectDaoImp to set
	 */
	public void setProyectDaoImp(ProyectDaoImp proyectDaoImp) {
		this.proyectDaoImp = proyectDaoImp;
	}

	
	
	
	

}
