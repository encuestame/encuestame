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
import java.util.List;

import javax.faces.model.SelectItem;

import org.encuestame.core.exception.EnMeExpcetion;
import org.encuestame.core.persistence.dao.imp.ICatLocation;
import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitProjectBean;
import org.encuestame.web.beans.location.LocationBean;
import org.encuestame.web.beans.location.LocationTypeBean;

/**
 * Data Services.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
public interface IDataSource {

    /**
     * Load List of Project.
     * @param userId user id
     * @return {@link Collection} of {@link UnitProjectBean}
     * @throws EnMeExpcetion exception
     */
    Collection<UnitProjectBean> loadListProjects(final Long userId) throws EnMeExpcetion ;

    /**
     * Load project info.
     * @param project {@link Project}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion excepcion
     */
    UnitProjectBean loadProjectInfo(UnitProjectBean project) throws EnMeExpcetion;

    /**
     * create project.
     *
     * @param projectBean {@link UnitProjectBean}
     * @return {@link UnitProjectBean}
     * @throws EnMeExpcetion exception
     */
    UnitProjectBean createProject(UnitProjectBean projectBean) throws EnMeExpcetion;

    /**
     * create Cat LocationType.
     * @param locatType {@link LocationTypeBean}
     * @return {@link LocationTypeBean}
     * @throws EnMeExpcetion exception
     */
    UnitLocationTypeBean createCatLocationType(UnitLocationTypeBean locatType) throws EnMeExpcetion;

    /**
     * create Cat Location.
     * @param location {@link LocationBean}
     * @return {@link LocationBean}
     * @throws EnMeExpcetion exception
     */
    LocationBean createCatLocation(LocationBean location) throws EnMeExpcetion;

    /**
     * Load state by id.
     * @param stateId state id
     * @return {@link CatState}
     * @throws Exception exception
     */
    CatState getState(Long stateId) throws Exception;

    /**
     * @return the stateDao
     */
    ICatState getStateDao();

    /**
     * @return the catLocationDao
     */
    ICatLocation getCatLocationDao();

    /**
     * @return the projectDaoImp
     */
    IProject getProjectDaoImp();

    /**
     * @param locationTypeBean locationTypeBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    void updateCatLocationType(UnitLocationTypeBean locationTypeBean) throws EnMeExpcetion;

    /**
     * @param locationBean locationBean
     * @throws EnMeExpcetion EnMeExpcetion
     */
    void updateCatLocation(UnitLocationBean locationBean) throws EnMeExpcetion;

    /**
     * Load Clients on {@link SelectItem}.
     * @param projectId project id
     * @return select items of clients.
     * @throws EnMeExpcetion exception
     */
     List<SelectItem> loadSelecItemClientsByProjectId(final Long projectId) throws EnMeExpcetion;
}
