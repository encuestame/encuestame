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
public class ProyectDaoImp extends AbstractHibernateDaoSupport implements IProyect {


    /**
     * find all projects
     *
     * @return
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<Project> findAll() throws HibernateException {
        return getHibernateTemplate().find("from Project");
    }

    /**
     * Retrieve project by id.
     * @param projectId
     * @return
     * @throws HibernateException
     */
    public Project getProject(Integer projectId) throws HibernateException {
        return (Project) getHibernateTemplate().get(Project.class, projectId);
    }

    /**
     * Delete project.
     *
     * @param project
     * @throws HibernateException
     */
    public void delete(Object project) throws HibernateException {
        getHibernateTemplate().delete(project);
    }

    /**
     *
     */
    public void saveOrUpdate(Object project) throws HibernateException {
         getHibernateTemplate().saveOrUpdate(project);
    }
}
