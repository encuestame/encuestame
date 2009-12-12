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
 */
package org.encuestame.core.service;

import java.util.Collection;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.web.beans.location.LocationBean;
import org.encuestame.web.beans.location.LocationTypeBean;
import org.encuestame.web.beans.location.UnitLocationTypeBean;
import org.encuestame.web.beans.project.UnitProjectBean;

/**
 * Data Services.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
public interface IDataSource {

    /**
     * Load List of Project.
     * @return {@link Collection} of {@link UnitProjectBean}
     */
    public Collection<UnitProjectBean> loadListProjects();

    /**
     * Load project info.
     * @param project {@link Project}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion excepcion
     */
    public UnitProjectBean loadProjectInfo(UnitProjectBean project) throws EnMeExpcetion;

    /**
     * create project.
     *
     * @param projectBean {@link UnitProjectBean}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion exception
     */
    public UnitProjectBean createProject(UnitProjectBean projectBean) throws EnMeExpcetion;

    /**
     * create Cat LocationType.
     * @param locatType {@link LocationTypeBean}
     * @return {@link LocationTypeBean}
     * @throws EnMeExpcetion exception
     */
    public UnitLocationTypeBean createCatLocationType(UnitLocationTypeBean locatType) throws EnMeExpcetion;

    /**
     * create Cat Location.
     * @param location {@link LocationBean}
     * @return {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    public LocationBean createCatLocation(LocationBean location) throws EnMeExpcetion;

    /**
     * Load state by id.
     * @param stateId state id
     * @return {@link CatState}
     * @throws Exception exception
     */
    public CatState getState(Long stateId) throws Exception;

    /**
     * @return the stateDao
     */
    public ICatState getStateDao();

    /**
     * @return the catLocationDao
     */
    public ICatLocation getCatLocationDao();

    /**
     * @return the projectDaoImp
     */
    public IProject getProjectDaoImp();
}
