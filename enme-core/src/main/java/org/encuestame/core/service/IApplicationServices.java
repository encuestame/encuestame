/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
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

/**
 * Interface to Application Bean Service.
 * @author Picado, Juan juanATencuestame.org
 * @since 11/05/2009 14:35:21
 * @version $Id$
 */
public interface IApplicationServices  extends ServiceOperations {


    /**
     * Getter.
     * @return {@link SecurityOperations}
     */
    SecurityOperations getSecurityService();

    /**
     * Setter.
     * @param securityService {@link SecurityOperations}
     */
    void setSecurityService(SecurityOperations securityService);

    /**
     * @return the surveyService
     */
    ISurveyService getSurveyService();

    /**
     * @param surveyService the surveyService to set
     */
    void setSurveyService(ISurveyService surveyService);

    /**
     * @return the pollService
     */
    IPollService getPollService();

    /**
     * @param pollService the pollService to set
     */
    void setPollService(IPollService pollService);

    /**
     * @return the tweetPollService
     */
    ITweetPollService getTweetPollService();

    /**
     * @param tweetPollService the tweetPollService to set
     */
    void setTweetPollService(ITweetPollService tweetPollService); 
     
    /**
     * @param locationService the locationService to set
     */
    void setLocationService(final GeoLocationSupport locationService);

    /**
     * @return the locationService
     */
    GeoLocationSupport getLocationService();

    /**
     *
     * @return
     */
    IFrontEndService getFrontEndService();

    /**
     * @param frontEndService the frontEndService to set
     */
    void setFrontEndService(final IFrontEndService frontEndService);

    /**
     *
     * @return
     */
    IPictureService getPictureService();

    /**
     *
     * @param pictureService
     */
    void setPictureService(IPictureService pictureService);

    /**
     *
     * @return
     */
    SearchServiceOperations getSearchService();

    /**
     *
     * @param searchService
     */
    void setSearchService(SearchServiceOperations searchService); 

    /**
     *
     * @return
     */
    StreamOperations getStreamOperations();

    /**
     *
     * @param streamOperations
     */
    void setStreamOperations(final StreamOperations streamOperations);

    /**
     *
     * @return
     */
    ICommentService getCommentService();

    /**
     * @param commentService the commentService to set
     */
    void setCommentService(final ICommentService commentService);
    
    /**
     * 
     * @return
     */
	IStatisticsService getStatisticService();
	
	/**
	 * 
	 * @param statisticService
	 */
	void setStatisticService(final IStatisticsService statisticService); 
}
