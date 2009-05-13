package org.jp.web.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.service.ApplicationServices;
import org.jp.core.service.DataService;
import org.jp.core.service.SecurityService;
import org.jp.core.service.SurveyService;
import org.jp.web.beans.admon.UnitGroupBean;
import org.jp.web.beans.commons.MessageSourceFactoryBean;
import org.jp.web.serviceManager.ServiceManager;

/**
 * encuestame: system online surveys Copyright (C) 2008-2009 encuestame
 * Development Team * This program is free software; you can redistribute it
 * and/or modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation. * This program is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License along with
 * this program; if not, write to the Free Software Foundation, Inc., 59 Temple
 * Place, Suite 330, Boston, MA 02111-1307 USA * Id: ServiceLocateBean.java 1822
 * 26/04/2009 20:25:26
 * 
 * @author dianmorales
 * @version 1.0 package org.jp.web.beans
 */

public class ServiceManagerBean implements ServiceManager {

	private DataService dataService;
	private SurveyService surveyService;
	private SecurityService securityService;
	private ApplicationServices applicationService;
	private MessageSourceFactoryBean messageSource;

	private Log logger = LogFactory.getLog(this.getClass());

	public ServiceManagerBean() {
		logger.info("init service locate bean");
	}

	public DataService getDataService() {
		return dataService;
	}

	public void setDataService(DataService dataService) {
		// logger.info("dataService"+dataService);
		this.dataService = dataService;
	}

	public SurveyService getSurveyService() {
		return surveyService;
	}

	public void setSurveyService(SurveyService surveyService) {
		// logger.info("surveyService"+surveyService);
		this.surveyService = surveyService;
	}

	public MessageSourceFactoryBean getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSourceFactoryBean messageSource) {
		this.messageSource = messageSource;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}

	public ApplicationServices getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationServices applicationService) {
		// logger.info("applicationService"+applicationService);
		this.applicationService = applicationService;
	}

	public SecurityService getSecurityService() {
		return securityService;
	}

	public void setSecurityService(SecurityService securityService) {
		// logger.info("dataService"+dataService);
		this.securityService = securityService;
	}


}
