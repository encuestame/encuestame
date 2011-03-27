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

import org.encuestame.business.service.imp.IApplicationServices;
import org.encuestame.business.service.imp.IChartService;
import org.encuestame.business.service.imp.IFrontEndService;
import org.encuestame.business.service.imp.ILocationService;
import org.encuestame.business.service.imp.IPictureService;
import org.encuestame.business.service.imp.IPollService;
import org.encuestame.business.service.imp.IProjectService;
import org.encuestame.business.service.imp.SearchServiceOperations;
import org.encuestame.business.service.imp.SecurityOperations;
import org.encuestame.business.service.imp.ISurveyService;
import org.encuestame.business.service.imp.ITweetPollService;
import org.springframework.stereotype.Service;

/**
 * Application Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 11/05/2009 11:35:01
 * @version $Id$
 */
@Service
public class ApplicationServices extends AbstractBaseService implements IApplicationServices {

    /** {@link SecurityService}. **/
    private SecurityOperations securityService;
    /** {@link SurveyService}. **/
    private ISurveyService surveyService;
    /** {@link PollService}. **/
    private IPollService pollService;
    /** {@link TweetPollService}. **/
    private ITweetPollService tweetPollService;
    /** {@link LocationService}. **/
    private ILocationService locationService;
    /** {@link ProjectService}. **/
    private IProjectService projectService;
    /** {@link ChartService}. **/
    private IChartService chartService;
    /** {@link FrontEndService}. **/
    private IFrontEndService frontEndService;
    /** {@link PictureService}. **/
    private IPictureService pictureService;
    /** {@link SearchServiceOperations}. **/
    private SearchServiceOperations searchService;

    /**
     * @return the securityService
     */
    public SecurityOperations getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(SecurityOperations securityService) {
        this.securityService = securityService;
    }

    /**
     * @return the surveyService
     */
    public ISurveyService getSurveyService() {
        return surveyService;
    }

    /**
     * @param surveyService the surveyService to set
     */
    public void setSurveyService(final ISurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * @return the pollService
     */
    public IPollService getPollService() {
        return pollService;
    }

    /**
     * @param pollService the pollService to set
     */
    public void setPollService(final IPollService pollService) {
        this.pollService = pollService;
    }

    /**
     * @return the tweetPollService
     */
    public ITweetPollService getTweetPollService() {
        return tweetPollService;
    }

    /**
     * @param tweetPollService the tweetPollService to set
     */
    public void setTweetPollService(final ITweetPollService tweetPollService) {
        this.tweetPollService = tweetPollService;
    }

    /**
     * @return the locationService
     */
    public ILocationService getLocationService() {
        return locationService;
    }

    /**
     * @param locationService the locationService to set
     */
    public void setLocationService(final ILocationService locationService) {
        this.locationService = locationService;
    }

    /**
     * @return the projectService
     */
    public IProjectService getProjectService() {
        return projectService;
    }

    /**
     * @param projectService the projectService to set
     */
    public void setProjectService(final IProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * @return the chartService
     */
    public IChartService getChartService() {
        return chartService;
    }

    /**
     * @param chartService the chartService to set
     */
    public void setChartService(final IChartService chartService) {
        this.chartService = chartService;
    }

    /**
     * @return the frontEndService
     */
    public IFrontEndService getFrontEndService() {
        return frontEndService;
    }

    /**
     * @param frontEndService the frontEndService to set
     */
    public void setFrontEndService(final IFrontEndService frontEndService) {
        this.frontEndService = frontEndService;
    }

    /**
     * @return the pictureService
     */
    public IPictureService getPictureService() {
        return pictureService;
    }

    /**
     * @param pictureService the pictureService to set
     */
    public void setPictureService(IPictureService pictureService) {
        this.pictureService = pictureService;
    }

    /**
     * @return the searchService
     */
    public SearchServiceOperations getSearchService() {
        return searchService;
    }

    /**
     * @param searchService the searchService to set
     */
    public void setSearchService(SearchServiceOperations searchService) {
        this.searchService = searchService;
    }
}