package org.encuestame.core.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.CatLocationDaoImp;
import org.encuestame.core.persistence.dao.CatStateDaoImp;
import org.encuestame.core.persistence.dao.ProyectDaoImp;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.core.persistence.pojo.SecUsers;
import org.encuestame.web.beans.admon.UnitUserBean;
import org.encuestame.web.beans.project.UnitProjectBean;

/**
 * encuestame: system online surveys Copyright (C) 2005-2008 encuestame
 * Development Team
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
 * Id: DataService.java Date: 27/04/2009
 *
 * @author juanpicado package: org.encuestame.core.service
 * @version 1.0
 */
public class DataService extends MasterService implements IDataService {

    private CatStateDaoImp stateDao;
    private CatLocationDaoImp catLocationDaoImp;
    private ProyectDaoImp proyectDaoImp;

    /**
     *
     * @return
     */
    public Collection<UnitProjectBean> loadListProjects() {
        Collection<UnitProjectBean> listProjects = new LinkedList<UnitProjectBean>();
        Collection<Project> list = getProyectDaoImp().findAll();
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
     * load project info
     *
     * @param id
     * @return
     * @throws EnMeExpcetion
     */
    public UnitProjectBean loadProjectInfo(UnitProjectBean project) throws EnMeExpcetion {
        log.info("loadProjectInfo DATASERVICE -->"+project);
        if (project.getId()!= null) {
            Project pro = getProyectDaoImp().getProject(project.getId());
            if (pro != null) {
                log.info("2 project found name ->"+pro.getDescription());
                project.setId(pro.getProyectId());
                project.setDateFinish(pro.getDateFinish());
                project.setDateInit(pro.getDateStart());
                project.setDescription(pro.getInfo());
                project.setName(pro.getDescription());
                project.setState(pro.getCatState().getIdState());
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
     * @param project
     * @throws EnMeExpcetion
     */
    public void createProject(UnitProjectBean project) throws EnMeExpcetion,
            HibernateException, Exception {
        log.info("create project");
        if (project != null) {
            try {
                Project proB = new Project();
                proB.setCatState(getState(project.getState()));
                proB.setDateFinish(project.getDateFinish());
                proB.setDateStart(project.getDateInit());
                proB.setDescription(project.getName());
                proB.setInfo(project.getDescription());
                log.info("create project 2");
                getProyectDaoImp().saveOrUpdate(proB);
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
     * load state by id
     *
     * @param id
     */
    private CatState getState(Integer id) throws Exception {
        return getStateDao().getState(id);
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
     * @param catLocationDaoImp
     *            the catLocationDaoImp to set
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
     * @param proyectDaoImp
     *            the proyectDaoImp to set
     */
    public void setProyectDaoImp(ProyectDaoImp proyectDaoImp) {
        this.proyectDaoImp = proyectDaoImp;
    }

}
