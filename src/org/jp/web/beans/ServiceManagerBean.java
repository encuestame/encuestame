package org.jp.web.beans;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.service.DataService;
import org.jp.core.service.SecurityService;
import org.jp.core.service.SurveyService;
import org.jp.web.serviceManager.ServiceManager;
import org.jp.web.utils.FacesUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

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
 * @author dianmorales
 * @version 1.0 package org.jp.web.beans
 */

public class ServiceManagerBean implements ServiceManager {

	private ApplicationContext appContext;

	private DataService dataService;
	private SurveyService surveyService;
	private SecurityService securityService;

	// the user service bean nameate
	private static final String DATA_SERVICE_BEAN_NAME = "dataService";
	private static final String SECURITY_SERVICE_BEAN_NAME = "sercurityService";
	private static final String SURVEY_SERVICE_BEAN_NAME = "surveyService";

	private Log logger = LogFactory.getLog(this.getClass());

	public ServiceManagerBean() {
		logger.info("init service locate bean");
		ServletContext context = FacesUtils.getServletContext();
		this.appContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		this.surveyService = (SurveyService) this
				.lookupService(SURVEY_SERVICE_BEAN_NAME);
	}

	public Object lookupService(String serviceBeanName) {
		return appContext.getBean(serviceBeanName);
	}

	public DataService getDataService() {
		// TODO Auto-generated method stub
		return null;
	}

	public SecurityService getSecurityService() {
		// TODO Auto-generated method stub
		return null;
	}

	public SurveyService getSurveyService() {
		return this.surveyService;
	}
}
