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
 */
package org.encuestame.core.persistence.dao;

import java.util.List;

import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.pojo.Project;
import org.hibernate.HibernateException;

/**
 * Project Dao.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since May 26, 2009
 */
public class ProjectDaoImp extends AbstractHibernateDaoSupport implements IProject {


    /**
     * Find all projects.
     * @return List of Project
     * @throws HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<Project> findAll() throws HibernateException {
        return getHibernateTemplate().find("from Project");
    }

    /**
     * Retrieve project by id.
     * @param projectId project id
     * @return {@link Project}
     * @throws HibernateException
     */
    public Project getProjectbyId(Integer projectId) throws HibernateException {
        return (Project) getHibernateTemplate().get(Project.class, projectId);
    }
}
