/*
 ************************************************************************************
 * Copyright (C) 2001-2009 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
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
 * @version $Id$
 */
public class ProjectDaoImp extends AbstractHibernateDaoSupport implements IProject {


    /**
     * Find all projects.
     * @return List of Project
     * @throws HibernateException hibernate expcetion
     */
    @SuppressWarnings("unchecked")
    public List<Project> findAll() throws HibernateException {
        return getHibernateTemplate().find("FROM Project");
    }

    /**
     * Retrieve project by id.
     * @param projectId project id
     * @return {@link Project}
     * @throws HibernateException hibernate expcetion
     */
    public Project getProjectbyId(Long projectId) throws HibernateException {
        return (Project) getHibernateTemplate().get(Project.class, projectId);
    }

    /**
     * @return
     * @throws HibernateException HibernateException
     */
    public List<Project> getProjectByLocationId() throws HibernateException{
       /* final String queryLocProject = "FROM Projects where"
        return getHibernateTemplate().fin
        final String queryLocation = "FROM CatLocation WHERE tidtype.id  =?";*/
        return   getHibernateTemplate().find("");
    }

}
