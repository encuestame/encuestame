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
package org.encuestame.business.service.imp;
/**
 * Interface to Application Bean Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 11/05/2009 14:35:21
 * @version $Id$
 */
public interface IApplicationServices  extends IService {


    /**
     * Getter.
     * @return {@link ISecurityService}
     */
    public ISecurityService getSecurityService();

    /**
     * Setter.
     * @param securityService {@link ISecurityService}
     */
    public void setSecurityService(ISecurityService securityService);

    /**
     * @return the surveyService
     */
    public ISurveyService getSurveyService();

    /**
     * @param surveyService the surveyService to set
     */
    public void setSurveyService(ISurveyService surveyService);

    /**
     * @return the pollService
     */
    public IPollService getPollService();

    /**
     * @param pollService the pollService to set
     */
    public void setPollService(IPollService pollService);

    /**
     * @return the tweetPollService
     */
    public ITweetPollService getTweetPollService();

    /**
     * @param tweetPollService the tweetPollService to set
     */
    void setTweetPollService(ITweetPollService tweetPollService);

    /**
     * @param projectService the projectService to set
     */
    void setProjectService(final IProjectService projectService);

    /**
     * @return the projectService
     */
    IProjectService getProjectService();

    /**
     * @param locationService the locationService to set
     */
    void setLocationService(final ILocationService locationService);

    /**
     * @return the locationService
     */
    ILocationService getLocationService();
}
