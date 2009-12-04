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
package org.encuestame.core.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.core.exception.EnMeExpcetion;

import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.dao.imp.IProject;

import org.encuestame.core.persistence.pojo.CatLocation;
import org.encuestame.core.persistence.pojo.CatLocationType;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.web.beans.location.LocationBean;
import org.encuestame.web.beans.location.LocationTypeBean;
import org.encuestame.web.beans.project.UnitProjectBean;
import org.hibernate.HibernateException;
/**
 * Data Services.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since April 27, 2009
 * File name: $HeadURL$
 * Revision: $Revision$
 * Last modified: $Date$
 * Last modified by: $Author$
 */
public class DataSource implements IDataSource {


    private ICatState stateDao;
    private ICatLocation catLocationDao;
    private IProject projectDaoImp;
    protected Log log = LogFactory.getLog(this.getClass());


    /**
     * Load List of Project.
     * @return {@link Collection} of {@link UnitProjectBean}
     */
    public Collection<UnitProjectBean> loadListProjects() {
        Collection<UnitProjectBean> listProjects = new LinkedList<UnitProjectBean>();
        Collection<Project> list = getProjectDaoImp().findAll();
        log.info("list getProyectDaoImp->" + list.size());
        if (list != null && list.size() > 0) {
            for (Iterator<Project> i = list.iterator(); i.hasNext();) {
                UnitProjectBean proB = new UnitProjectBean();
                Project project = i.next();
                proB.setId(project.getProyectId());
                proB.setName(project.getDescription());
                proB.setDescription(project.getInfo());
                proB.setDateInit(project.getDateStart());
                proB.setDateFinish(project.getDateFinish());
                // falta agregar lista de grupos, usuarios y grupos de encuestas
                listProjects.add(proB);
            }
        }
        log.info("list listProjects->" + listProjects.size());
        return listProjects;
    }

    /**
     * Load project info.
     * @param project {@link Project}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion excepcion
     */
    public UnitProjectBean loadProjectInfo(UnitProjectBean project) throws EnMeExpcetion {
        log.info("loadProjectInfo DATASERVICE -->"+project);
        if (project.getId()!= null) {
            Project pro = getProjectDaoImp().getProjectbyId(project.getId());
            if (pro != null) {
                log.info("2 project found name ->"+pro.getDescription());
                project.setId(pro.getProyectId());
                project.setDateFinish(pro.getDateFinish());
                project.setDateInit(pro.getDateStart());
                project.setDescription(pro.getInfo());
                project.setName(pro.getDescription());
                project.setState(Long.valueOf(pro.getCatState().getIdState().toString()));
                log.info("Rescue->"+project.getName());
                log.info("loadProjectInfo DATASERVICE Rescue -->"+project);
                return project;
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
     * create project
     *
     * @param projectBean {@link UnitProjectBean}
     * @throws EnMeExpcetion exception
     */
    public void createProject(UnitProjectBean projectBean) throws EnMeExpcetion,
            HibernateException, Exception {
        log.info("create project");
        if (projectBean != null) {
            try {
                Project proB = new Project();
                proB.setCatState(getState(projectBean.getState()));
                proB.setDateFinish(projectBean.getDateFinish());
                proB.setDateStart(projectBean.getDateInit());
                proB.setDescription(projectBean.getName());
                proB.setInfo(projectBean.getDescription());
                log.info("create project 2");
                getProjectDaoImp().saveOrUpdate(proB);
            } catch (HibernateException e) {
                throw new HibernateException(e);
            } catch (Exception e) {
                throw new Exception(e);
            }
        } else {
            throw new EnMeExpcetion("project is null");
        }
    }



    /**
     * create Cat LocationType.
     * @param locatType {@link LocationTypeBean}
     * @throws EnMeExpcetion exception
     */
    public void createCatLocationType(LocationTypeBean locatType) throws EnMeExpcetion,HibernateException, Exception
    {
         log.info("create LocationType");
         if (locatType!=null){
             try {
             CatLocationType locType = new CatLocationType();
             locType.setDescription(locatType.getDescription());
             locType.setLevel(locatType.getLevel());
             log.info("Creating Cat Location Type");
            // getLocationTypeDao().saveOrUpdate(locType);

         } catch (HibernateException e) {
             throw new HibernateException(e);
         } catch (Exception e) {
             throw new Exception(e);
         }
     } else {
         throw new EnMeExpcetion("Cat Location Type is null");
     }
 }

    /**
     * create Cat Location.
     * @param location {@link LocationBean}
     * @throws EnMeExpcetion exception
     */

    public void createCatLocation(LocationBean location) throws EnMeExpcetion, HibernateException, Exception
    {
        log.info("create Cat Location");
        if (location!=null){
            try{
                CatLocation catLoc = new CatLocation();
                catLoc.setDescription(location.getDescription());
                catLoc.setActive(location.getActive());
                catLoc.setLat(location.getLat());
                catLoc.setLng(location.getLng());
                catLoc.setLevel(location.getLevel());
                getCatLocationDao().saveOrUpdate(catLoc);
            } catch (HibernateException e) {
                throw new HibernateException(e);
            } catch (Exception e) {
                throw new Exception(e);
            }
        } else {
            throw new EnMeExpcetion("Cat Location is null");
        }
    }



    /**
     * Load state by id.
     * @param stateId state id
     */
    public CatState getState(Long stateId) throws Exception {
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
    public void setStateDao(ICatState stateDao) {
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

    public void setCatLocationDao(ICatLocation catLocationDao) {
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
    public void setProjectDaoImp(IProject projectDaoImp) {
        this.projectDaoImp = projectDaoImp;
    }
}
