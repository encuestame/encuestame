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
 * @author Picado, Juan juan@encuestame.org
 * @since 26/04/2009
 * File name: $HeadURL:$
 * Revision: $Revision$
 * Last modified: $Date:$
 * Last modified by: $Author:$
 */
public class ServiceManager implements IServiceManager {

    public Log log = LogFactory.getLog(this.getClass());

    public ApplicationServices getApplicationServices() {
        // TODO Auto-generated method stub
        return null;
    }

    public DataService getDataService() {
        // TODO Auto-generated method stub
        return null;
    }

    public MessageSourceFactoryBean getMessageSource() {
        // TODO Auto-generated method stub
        return null;
    }

    public SecurityService getSecurityService() {
        // TODO Auto-generated method stub
        return null;
    }

    public SurveyService getSurveyService() {
        // TODO Auto-generated method stub
        return null;
    }

    public void setApplicationService(ApplicationServices applicationService) {
        // TODO Auto-generated method stub

    }

    public void setDataService(DataService dataService) {
        // TODO Auto-generated method stub

    }

    public void setMessageSource(MessageSourceFactoryBean messageSource) {
        // TODO Auto-generated method stub

    }

    public void setSecurityService(SecurityService securityService) {
        // TODO Auto-generated method stub

    }

    public void setSurveyService(SurveyService surveyService) {
        // TODO Auto-generated method stub

    }

}
