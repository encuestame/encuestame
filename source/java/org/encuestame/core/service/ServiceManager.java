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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.web.beans.commons.MessageSourceFactoryBean;

/**
 * Service Manager.
 *
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public class ServiceManager implements IServiceManager {

    public Log log = LogFactory.getLog(this.getClass());

    public SecurityService securityService;
    public DataService dataService;
    public ApplicationServices applicationServices;
    public MessageSourceFactoryBean messageSourceFactoryBean;
    public SurveyService surveyService;

    /**
     * @param securityService
     *            the securityService to set
     */
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    /**
     * @param dataService
     *            the dataService to set
     */
    public void setDataService(DataService dataService) {
        this.dataService = dataService;
    }

    /**
     * @param applicationServices
     *            the applicationServices to set
     */
    public void setApplicationServices(ApplicationServices applicationServices) {
        this.applicationServices = applicationServices;
    }

    /**
     * @param messageSourceFactoryBean
     *            the messageSourceFactoryBean to set
     */
    public void setMessageSource(MessageSourceFactoryBean messageSource) {
        this.messageSourceFactoryBean = messageSourceFactoryBean;
    }

    /**
     * @param surveyService
     *            the surveyService to set
     */
    public void setSurveyService(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    /**
     * @return the securityService
     */
    public SecurityService getSecurityService() {
        return securityService;
    }

    /**
     * @return the dataService
     */
    public DataService getDataService() {
        return dataService;
    }

    /**
     * @return the applicationServices
     */
    public ApplicationServices getApplicationServices() {
        return applicationServices;
    }

    /**
     * @return the messageSourceFactoryBean
     */
    public MessageSourceFactoryBean getMessageSource() {
        return messageSourceFactoryBean;
    }

    /**
     * @return the surveyService
     */
    public SurveyService getSurveyService() {
        return surveyService;
    }
}
