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
import org.encuestame.core.persistence.dao.imp.ICatLocationTypeDao;
import org.encuestame.core.persistence.dao.imp.ICatState;
import org.encuestame.core.persistence.dao.imp.IClientDao;
import org.encuestame.core.persistence.dao.imp.IPoll;
import org.encuestame.core.persistence.dao.imp.IProject;
import org.encuestame.core.persistence.dao.imp.IQuestionDao;
import org.encuestame.core.persistence.dao.imp.ISecGroups;
import org.encuestame.core.persistence.dao.imp.ISecPermissionDao;
import org.encuestame.core.persistence.dao.imp.ISecUserDao;
import org.encuestame.core.persistence.dao.imp.ISurvey;
import org.encuestame.core.persistence.dao.imp.ITweetPoll;
import org.encuestame.core.persistence.pojo.CatState;
import org.encuestame.core.persistence.pojo.Project;
import org.encuestame.utils.web.LocationBean;
import org.encuestame.utils.web.LocationTypeBean;
import org.encuestame.utils.web.UnitLocationBean;
import org.encuestame.utils.web.UnitLocationTypeBean;
import org.encuestame.utils.web.UnitProjectBean;

/**
 * Data Services.
 * @author Picado, Juan juan@encuestame.org
 * @since 27/04/2009
 * @version $Id$
 */
@Deprecated
public interface IDataSource {


   /**
     * Load state by id.
     * @param stateId state id
     * @return {@link CatState}
     */
     CatState getState(final Long stateId);


    /**
     * @return the stateDao
     */
    public ICatState getStateDao();

    /**
     * @param stateDao the stateDao to set
     */
    public void setStateDao(final ICatState stateDao);

    /**
     * @return the catLocationDao
     */
    public ICatLocation getCatLocationDao();

    /**
     * @param catLocationDao the catLocationDao to set
     */

    public void setCatLocationDao(final ICatLocation catLocationDao);

    /**
     * @return the projectDaoImp
     */
    public IProject getProjectDaoImp();

    /**
     * @param projectDaoImp the projectDaoImp to set
     */
    public void setProjectDaoImp(final IProject projectDaoImp);

    /**
     * @return the catLocationTypeDao
     */
    public ICatLocationTypeDao getCatLocationTypeDao();

    /**
     * @param catLocationTypeDao the catLocationTypeDao to set
     */
    public void setCatLocationTypeDao(final ICatLocationTypeDao catLocationTypeDao);

    /**
     * @return the clientDao
     */
    public IClientDao getClientDao();

    /**
     * @param clientDao the clientDao to set
     */
    public void setClientDao(final IClientDao clientDao);

    /**
     * @return the secUserDao
     */
    public ISecUserDao getSecUserDao();

    /**
     * @param secUserDao the secUserDao to set
     */
    public void setSecUserDao(final ISecUserDao secUserDao);

    /**
     * @return the questionDao
     */
    public IQuestionDao getQuestionDao();

    /**
     * @param questionDao the questionDao to set
     */
    public void setQuestionDao(IQuestionDao questionDao);

    /**
     * @return the pollDao
     */
    public IPoll getPollDao();

    /**
     * @param pollDao the pollDao to set
     */
    public void setPollDao(IPoll pollDao);

    /**
     * @return the surveyDaoImp
     */
    public ISurvey getSurveyDaoImp();

    /**
     * @param surveyDaoImp the surveyDaoImp to set
     */
    public void setSurveyDaoImp(ISurvey surveyDaoImp);

    /**
     * @return the tweetPollDao
     */
    public ITweetPoll getTweetPollDao();

    /**
     * @param tweetPollDao the tweetPollDao to set
     */
    public void setTweetPollDao(final ITweetPoll tweetPollDao);

     /**
     * @return the groupDao
     */
    public ISecGroups getGroupDao();

    /**
     * @param groupDao the groupDao to set
     */
    public void setGroupDao(ISecGroups groupDao);

    /**
     * @return the permissionDao
     */
    public ISecPermissionDao getPermissionDao();

    /**
     * @param permissionDao the permissionDao to set
     */
    public void setPermissionDao(ISecPermissionDao permissionDao);

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
