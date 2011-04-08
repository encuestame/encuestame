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
package org.encuestame.persistence.dao.imp;

import java.util.ArrayList;
import java.util.List;

import org.encuestame.persistence.dao.IProjectDao;
import org.encuestame.persistence.domain.Attachment;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.security.Account;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;

/**
 * Project Dao.
 * @author Picado, Juan Carlos juan@encuestame.org
 * @since May 26, 2009
 * @version $Id$
 */
@Repository("projectDaoImp")
public class ProjectDaoImp extends AbstractHibernateDaoSupport implements IProjectDao {

    @Autowired
    public ProjectDaoImp(SessionFactory sessionFactory) {
             setSessionFactory(sessionFactory);
    }

    /**
     * Find all projects.
     * @return List of Project
     * @throws HibernateException hibernate expcetion
     */
    @SuppressWarnings("unchecked")
    public List<Project> findAll() throws HibernateException {
        return getHibernateTemplate().find("from Project");
    }

    /**
     * Find Projects by {@link Account} id.
     * @param userId user id.
     * @return list of projects.
     */
    @SuppressWarnings("unchecked")
    public List<Project> findProjectsByUserID(final Long userId) throws HibernateException{
        return getHibernateTemplate().findByNamedParam("from Project where users.id = :userId", "userId", userId);
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
     * Get Projects By Location Id.
     * @return list of projects.
     * @throws HibernateException HibernateException
     */
    @SuppressWarnings("unchecked")
    public List<Project> getProjectByLocationId() throws HibernateException{
       /* final String queryLocProject = "FROM Projects where"
        return getHibernateTemplate().fin
        final String queryLocation = "FROM CatLocation WHERE tidtype.id  =?";*/
        return   getHibernateTemplate().find("");
    }

    /**
    * Get Attachment by Id.
    * @param attachmentId
    * @return
    */
    @SuppressWarnings("unchecked")
    public Attachment getAttachmentbyId(final Long attachmentId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Attachment.class);
        criteria.add(Restrictions.eq("attachmentId", attachmentId));
        return (Attachment) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }

    /**
     * Get Attachment List by Project Id.
     * @param projectId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Attachment> getAttachmentsListbyProject(final Long projectId){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Attachment.class);
          criteria.add(Restrictions.eq("projectAttachment.proyectId", projectId));
          return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     * Get Attachment by Name.
     * @param filename
     * @return {@link Attachment} filename
     */
    @SuppressWarnings("unchecked")
    public Attachment getAttachmentbyName(final String filename){
        final DetachedCriteria criteria = DetachedCriteria.forClass(Attachment.class);
        criteria.add(Restrictions.eq("filename", filename));
        return (Attachment) DataAccessUtils.uniqueResult(getHibernateTemplate().findByCriteria(criteria));
    }
}
