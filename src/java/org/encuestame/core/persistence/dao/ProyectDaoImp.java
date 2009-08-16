package org.encuestame.core.persistence.dao;

import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.id.IdentityGenerator.GetGeneratedKeysDelegate;
import org.encuestame.core.persistence.dao.imp.IProyect;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

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
 * Id: IProyectDao.java Date: 26/05/2009 14:46:20
 * 
 * @author juanpicado package: org.encuestame.core.persistence.dao.imp
 * @version 1.0
 */
public class ProyectDaoImp extends HibernateDaoSupport implements IProyect {

	private Log log = LogFactory.getLog(this.getClass());

	/**
	 * find all projects
	 * 
	 * @return
	 * @throws HibernateException
	 */
	public Collection<Project> findAll() throws HibernateException {
		return getHibernateTemplate().find("from Project");
	}

	/**
	 * search project by id
	 * @param id
	 * @return
	 * @throws HibernateException
	 */
	public Project getProject(Integer id) throws HibernateException {
		List<Project> project = getHibernateTemplate().findByNamedQuery(
				"Pro.loadProjByIDe", id);
		log.info("project found's ->"+project.size());
		if (project.size() > 0) {
			return (Project) project.get(0);
		} else {
			throw new HibernateException("project not found");
		}
	}

	/**
	 * create projects
	 * 
	 * @param proyect
	 * @throws HibernateException
	 */
	public void createProyect(Project proyect) throws HibernateException {
		log.info("save create project");
		getHibernateTemplate().save(proyect);
	}

	/**
	 * update proyect
	 * 
	 * @param proyect
	 * @throws HibernateExceptio
	 */
	public void updateProyect(Project proyect) throws HibernateException {
		getHibernateTemplate().update(proyect);

	}

	/**
	 * delete proyect
	 * 
	 * @param proyect
	 * @throws HibernateException
	 */
	public void deleteProyect(Project proyect) throws HibernateException {
		getHibernateTemplate().delete(proyect);

	}

}
