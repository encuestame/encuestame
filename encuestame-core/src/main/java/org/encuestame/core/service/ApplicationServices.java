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

import org.encuestame.core.service.util.UrlBean;

/**
 * Application Service.
 * @author Picado, Juan juan@encuestame.org
 * @since 11/05/2009 11:35:01
 * @version $Id$
 */
@org.springframework.stereotype.Service
public class ApplicationServices extends AbstractBaseService implements IApplicationServices {

    private String name;
    private String urlImg;
    private String encoding;
    private String apiKeygoogle;
    private UrlBean url;

    private ISecurityService securityService;
    private ISurveyService surveyService;
    private IPollService pollService;
    private ITweetPollService tweetPollService;


    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter.
     * @return url image
     */
    public String getUrlImg() {
        return urlImg;
    }

    /**
     * Setter.
     * @param urlImg url image
     */
    public void setUrlImg(String urlImg) {
        this.urlImg = urlImg;
    }

    /**
     * Getter.
     * @return  encoding
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Setter.
     * @param encoding encoding
     */
    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    /**
     * @return the apiKeygoogle
     */
    public String getApiKeygoogle() {
        return apiKeygoogle;
    }

    /**
     * @param apiKeygoogle the apiKeygoogle to set
     */
    public void setApiKeygoogle(String apiKeygoogle) {
        this.apiKeygoogle = apiKeygoogle;
    }

    /**
     * @return the securityService
     */
    public ISecurityService getSecurityService() {
        return securityService;
    }

    /**
     * @param securityService the securityService to set
     */
    public void setSecurityService(ISecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @return the url
     */
    public UrlBean getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(UrlBean url) {
        this.url = url;
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
    public void setSurveyService(ISurveyService surveyService) {
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
    public void setPollService(IPollService pollService) {
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
    public void setTweetPollService(ITweetPollService tweetPollService) {
        this.tweetPollService = tweetPollService;
    }


}
