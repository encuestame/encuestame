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
package org.encuestame.business.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.service.imp.IProjectService;
import org.encuestame.core.util.ConvertDomainBean;
import org.encuestame.persistence.domain.GeoPoint;
import org.encuestame.persistence.domain.Project;
import org.encuestame.persistence.domain.Project.Priority;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.notifications.NotificationEnum;
import org.encuestame.persistence.exception.EnMeDomainNotFoundException;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.utils.web.UnitProjectBean;
import org.springframework.stereotype.Service;


/**
 * Project Service.
 * @author Morales, Diana Paola paola@encuestame.org
 * @since April 13, 2009
 * @version $Id:$
 */
@Service
public class ProjectService extends AbstractBaseService implements IProjectService {

     /** Log. */
    private Log log = LogFactory.getLog(this.getClass());

    /**
     * Load List of Project.
     * @param userId user id.
     * @return {@link Collection} of {@link UnitProjectBean}
     * @throws EnMeDomainNotFoundException
     * @throws EnMeExpcetion exception
     */
    public List<UnitProjectBean> loadListProjects(final String username) throws EnMeDomainNotFoundException {
            final List<UnitProjectBean> listProjects = new ArrayList<UnitProjectBean>();
            final Collection<Project> projectList = getProjectDaoImp().findProjectsByUserID(getUserAccount(username).getAccount().getUid());
            //log.debug("project by user id: "+projectList.size());
            for (final Project project : projectList) {
                //log.debug("adding project "+project.getProjectDescription());
                //log.debug("groups available in this project "+project.getGroups().size());
                listProjects.add(ConvertDomainBean.convertProjectDomainToBean(project));
            }
            //log.debug("projects loaded: "+ listProjects.size());
            return listProjects;
    }

    /**
     * Load project info.
     * @param projectBean {@link Project}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion excepcion
     */
    public UnitProjectBean loadProjectInfo(final UnitProjectBean projectBean) throws EnMeExpcetion {
        if (projectBean.getId()!= null) {
            final Project projectDomain = getProjectDaoImp().getProjectbyId(projectBean.getId());
            if (projectDomain != null) {
                final UnitProjectBean projectBeanRetrieved = ConvertDomainBean.convertProjectDomainToBean(projectDomain);
                //projectBeanRetrieved.setGroupList(ConvertListDomainSelectBean.convertListGroupDomainToSelect(projectDomain.getGroups()));
                final List<GeoPoint> list = new ArrayList<GeoPoint>(projectDomain.getLocations());
                log.debug("Locations on Project "+list.size());
                //projectBeanRetrieved.setUnitLocationBeans(ConvertDomainBean.convertListToUnitLocationBean(list));
                return projectBeanRetrieved;
            } else {
                log.info("id project is not found");
                throw new EnMeExpcetion("id project is not found");
            }
        } else {
            log.info("id project is null");
            throw new EnMeExpcetion("id project is null");
        }
    }

    /**
     * Create Project.
     * @param projectBean {@link UnitProjectBean}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion exception
     */
    public UnitProjectBean createProject(final UnitProjectBean projectBean, final String username) throws EnMeExpcetion {
        log.debug("new Project createProject "+projectBean);
        log.debug("new Project username "+username);
        if (projectBean != null) {
            try {
                final Project projectDomain = new Project();
                final UserAccount secondary = getAccountDao().getUserAccountById(projectBean.getLeader());
                final Account user = getUserAccount(username).getAccount();
                projectDomain.setProjectDateFinish(projectBean.getDateFinish());
                projectDomain.setProjectDateStart(projectBean.getDateInit());
                log.debug("new Project Leader "+projectBean.getName());
                projectDomain.setProjectDescription(projectBean.getDescription());
                projectDomain.setProjectName(projectBean.getName());
                projectDomain.setProjectInfo(projectBean.getProjectInfo());
                projectDomain.setHideProject(projectBean.getHide());
                projectDomain.setNotifyMembers(projectBean.getNotify());
                log.debug("Project Leader "+projectBean.getLeader());
                if(projectBean.getLeader() != null){
                    projectDomain.setLead(secondary);
                }
                projectDomain.setUsers(user);
                getProjectDaoImp().saveOrUpdate(projectDomain);
                projectBean.setId(projectDomain.getProyectId());
                log.debug("created domain project");
                createNotification(NotificationEnum.PROJECT_CREATED, secondary.getUsername() +" is the leader of this project.", user);
            }
            catch (Exception e) {
                log.error(e.getMessage());
                throw new EnMeExpcetion(e);
            }
            return projectBean;
        }
        else {
            throw new EnMeExpcetion("error on create project");
        }
    }

    /**
     * Update Project Bean.
     * @param projectBean
     * @param username
     * @return
     * @throws EnMeExpcetion
     */
    public void updateProject(final UnitProjectBean projectBean, final String username) throws EnMeExpcetion {
        log.debug("project bean "+projectBean.toString());
        final Project project = getProjectDaoImp().getProjectbyId(projectBean.getId());
        if(project == null){
            throw new EnMeExpcetion("project not found");
        }
        else{
            final UserAccount secondary = getAccountDao().getUserAccountById(projectBean.getLeader());
            final Account user = getUserAccount(username).getAccount();
            project.setProjectName(projectBean.getName());
            project.setHideProject(projectBean.getHide());
            project.setLead(secondary);
            project.setNotifyMembers(projectBean.getNotify());
            project.setProjectDateFinish(projectBean.getDateFinish());
            project.setProjectDateStart(projectBean.getDateInit());
            project.setProjectDescription(projectBean.getDescription());
            project.setProjectInfo(projectBean.getProjectInfo());
            //project.setProjectStatus(projectBean.get)
            project.setPriority(Priority.valueOf(projectBean.getPriority()));
            project.setPublished(projectBean.getPublished());
            getProjectDaoImp().saveOrUpdate(project);
            createNotification(NotificationEnum.PROJECT_UPDATED, secondary.getUsername() +" is the leader of "+projectBean.getName(), user);
        }
    }
}
