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
package org.encuestame.core.service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.CatLocationTypeDao;
import org.encuestame.core.persistence.dao.ClientDao;
import org.encuestame.core.persistence.dao.ProjectDaoImp;
import org.encuestame.core.persistence.dao.SecUserDaoImp;
import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.dao.imp.ICatLocationTypeDao;
import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.dao.imp.IClientDao;
import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.CatLocationType;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.service.util.ConvertDomainBean;
import org.encuestame.web.beans.ConvertListDomainSelectBean;
import org.encuestame.web.beans.location.LocationBean;
import org.encuestame.web.beans.location.LocationTypeBean;
import org.encuestame.web.beans.location.UnitLocationBean;
import org.encuestame.web.beans.location.UnitLocationTypeBean;
import org.encuestame.web.beans.project.UnitProjectBean;
import org.hibernate.HibernateException;
/**
 * Data Services.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since April 27, 2009
 * @version $Id$
 */
public class DataSource implements IDataSource {

    /** {@link CatState}. */
    private ICatState stateDao;
    /** {@link CatLocation}. */
    private ICatLocation catLocationDao;
    /** {@link CatLocationTypeDao}. */
    private ICatLocationTypeDao catLocationTypeDao;
    /** {@link ProjectDaoImp}. */
    private IProject projectDaoImp;
    /** {@link ClientDao}. **/
    private IClientDao clientDao;
    /** {@link SecUserDaoImp}. **/
    private ISecUserDao secUserDao;
    /** Log. */
    protected Log log = LogFactory.getLog(this.getClass());


    /**
     * Load List of Project.
     * @param userId user id.
     * @return {@link Collection} of {@link UnitProjectBean}
     * @throws EnMeExpcetion exception
     */
    public Collection<UnitProjectBean> loadListProjects(final Long userId) throws EnMeExpcetion {
        try{
            final Collection<UnitProjectBean> listProjects = new LinkedList<UnitProjectBean>();
            final Collection<Project> projectList = getProjectDaoImp().findProjectsByUserID(userId);
            log.info("project by user id: "+projectList.size());
            for (Project project : projectList) {
                log.info("adding project "+project.getProjectDescription());
                log.info("groups available in this project "+project.getGroups().size());
                listProjects.add(ConvertDomainBean.convertProjectDomainToBean(project));
            }
            log.info("projects loaded: "+ listProjects.size());
            return listProjects;
        }catch (Exception e) {
            log.error(e);
            throw new EnMeExpcetion(e);
        }
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
                projectBeanRetrieved.setGroupList(ConvertListDomainSelectBean.convertListGroupDomainToSelect(projectDomain.getGroups()));
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
    public UnitProjectBean createProject(final UnitProjectBean projectBean) throws EnMeExpcetion {
        log.info("create project");
        if (projectBean != null) {
            try {
                final Project projectDomain = new Project();
                projectDomain.setStateProject(getState(projectBean.getState()));
                projectDomain.setProjectDateFinish(projectBean.getDateFinish());
                projectDomain.setProjectDateStart(projectBean.getDateInit());
                projectDomain.setProjectDescription(projectBean.getName());
                projectDomain.setProjectInfo(projectBean.getDescription());
                projectDomain.setUsers(getSecUserDao().getUserById(projectBean.getUserId()));
                getProjectDaoImp().saveOrUpdate(projectDomain);
                projectBean.setId(projectDomain.getProyectId());
                log.debug("created domain project");
            } catch (HibernateException e) {
                throw new EnMeExpcetion(e);
            } catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return projectBean;
        } else {
            throw new EnMeExpcetion("project is null");
        }
    }

    /**
     * Load Clients on {@link SelectItem}.
     * @param projectId project id
     * @return select items of clients.
     * @throws EnMeExpcetion exception
     */
    public List<SelectItem> loadSelecItemClientsByProjectId(final Long projectId) throws EnMeExpcetion {
        try{
            return ConvertListDomainSelectBean.convertListClientsDomainToSelect(
                   this.getClientDao().findAllClientByProjectId(projectId));
        }
        catch (Exception e) {
            throw new EnMeExpcetion(e);
        }
    }

    /**
     * create Cat LocationType.
     * @param locatTypeBean {@link LocationTypeBean}
     * @return locatTypeBean
     * @throws EnMeExpcetion exception
     */
    public UnitLocationTypeBean createCatLocationType(final UnitLocationTypeBean locatTypeBean) throws EnMeExpcetion
    {
         if (locatTypeBean!=null){
             try {
                 final CatLocationType locationTypeDomain = new CatLocationType();
                 locationTypeDomain.setLocationTypeDescription(locatTypeBean.getLocTypeDesc());
                 locationTypeDomain.setLocationTypeLevel(locatTypeBean.getLevel());
                 getCatLocationTypeDao().saveOrUpdate(locationTypeDomain);
                 locatTypeBean.setIdLocType((locationTypeDomain.getLocationTypeId()));
         }
         catch (HibernateException e) {
             throw new EnMeExpcetion(e);
         }
         catch (Exception e) {
             throw new EnMeExpcetion(e);
         }
         return locatTypeBean;
     }
     else {
         throw new EnMeExpcetion("Cat Location Type is null");
     }
 }

    /**
     * @param locationBean locationBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void updateCatLocation(final UnitLocationBean locationBean) throws EnMeExpcetion
    {
        log.info("Update Location");
        final CatLocation catLocation = getCatLocationDao().getLocationById(locationBean.getTid());
        if (catLocation!=null){
            catLocation.setLocationActive(locationBean.getActive());
            catLocation.setlocationDescription(locationBean.getDescriptionLocation());
            catLocation.setLocationLatitude(locationBean.getLatitude());
            catLocation.setLocationLevel(locationBean.getLevel());
            catLocation.setLocationLongitude(locationBean.getLongitude());
            final CatLocationType catLocationType = getCatLocationTypeDao().getLocationById(locationBean.getLocationTypeId());
            catLocation.setTidtype(catLocationType);
            getCatLocationDao().saveOrUpdate(catLocation);
        }
   }


    /**
     * @param locationTypeBean locationTypeBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    public void updateCatLocationType(final UnitLocationTypeBean locationTypeBean) throws EnMeExpcetion{
        log.info("update LocationType");
        final CatLocationType catLocationType = getCatLocationTypeDao().getLocationById(locationTypeBean.getIdLocType());
        if (catLocationType!=null){
            System.out.println("IDLOCATION-->");
            catLocationType.setLocationTypeDescription(locationTypeBean.getLocTypeDesc());
            catLocationType.setLocationTypeLevel(locationTypeBean.getLevel());
            getCatLocationTypeDao().saveOrUpdate(catLocationType);
        }
    }

    /**
     * create Cat Location.
     * @param location {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    public LocationBean createCatLocation(final LocationBean location) throws EnMeExpcetion
    {
        if (location!=null){
            try{
                final CatLocation catLocationDomain = new CatLocation();
                catLocationDomain.setlocationDescription(location.getDescription());
                catLocationDomain.setLocationActive(location.getActive());
                catLocationDomain.setLocationLatitude(location.getLat());
                catLocationDomain.setLocationLongitude(location.getLng());
                catLocationDomain.setLocationLevel(location.getLevel());
                catLocationDomain.setTidtype(getCatLocationTypeDao().getLocationById(location.getTidtype()));
                getCatLocationDao().saveOrUpdate(catLocationDomain);
                log.debug("create location domain");
                location.setLocateId(catLocationDomain.getLocateId());
            } catch (HibernateException e) {
                throw new EnMeExpcetion(e);
            } catch (Exception e) {
                throw new EnMeExpcetion(e);
            }
            return location;
        } else {
            throw new EnMeExpcetion("location info not found");
        }
    }

    /**
     * Load state by id.
     * @param stateId state id
     * @return {@link CatState}
     */
    public CatState getState(final Long stateId) throws Exception {
        return getStateDao().getState(stateId);
    }

    /**
     * @return the stateDao
     */
    public ICatState getStateDao() {
        return stateDao;
    }

    /**
     * @param stateDao the stateDao to set
     */
    public void setStateDao(final ICatState stateDao) {
        this.stateDao = stateDao;
    }

    /**
     * @return the catLocationDao
     */
    public ICatLocation getCatLocationDao() {
        return catLocationDao;
    }

    /**
     * @param catLocationDao the catLocationDao to set
     */

    public void setCatLocationDao(final ICatLocation catLocationDao) {
        this.catLocationDao = catLocationDao;
    }

    /**
     * @return the projectDaoImp
     */
    public IProject getProjectDaoImp() {
        return projectDaoImp;
    }

    /**
     * @param projectDaoImp the projectDaoImp to set
     */
    public void setProjectDaoImp(final IProject projectDaoImp) {
        this.projectDaoImp = projectDaoImp;
    }

    /**
     * @return the catLocationTypeDao
     */
    public ICatLocationTypeDao getCatLocationTypeDao() {
        return catLocationTypeDao;
    }

    /**
     * @param catLocationTypeDao the catLocationTypeDao to set
     */
    public void setCatLocationTypeDao(final ICatLocationTypeDao catLocationTypeDao) {
        this.catLocationTypeDao = catLocationTypeDao;
    }

    /**
     * @return the clientDao
     */
    public IClientDao getClientDao() {
        return clientDao;
    }

    /**
     * @param clientDao the clientDao to set
     */
    public void setClientDao(final IClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * @return the secUserDao
     */
    public ISecUserDao getSecUserDao() {
        return secUserDao;
    }

    /**
     * @param secUserDao the secUserDao to set
     */
    public void setSecUserDao(final ISecUserDao secUserDao) {
        this.secUserDao = secUserDao;
    }


}
