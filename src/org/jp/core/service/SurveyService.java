package org.jp.core.service;

import org.jp.core.mail.MailServiceImpl;
import org.jp.core.persistence.dao.CatStateDaoImp;
import org.jp.core.persistence.dao.SurveyDaoImp;

/**
 * encuestame:  system online surveys
 * Copyright (C) 2005-2008 encuestame Development Team
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * Id: DataSurvey.java Date: 27/04/2009 
 * @author juanpicado
 * package: org.jp.core.service
 * @version 1.0
 */
public class SurveyService extends MasterService implements ISurveyService {
	
	
	private MailServiceImpl serviceMail;
	private SurveyDaoImp surveyDaoImp;
	private SecurityService securityService;
	private DataService dataService;
	

	public MailServiceImpl getServiceMail() {
		return serviceMail;
	}

	public void setServiceMail(MailServiceImpl serviceMail) {
		this.serviceMail = serviceMail;
	}

	/**
	 * @return the surveyDaoImp
	 */
	public SurveyDaoImp getSurveyDaoImp() {
		return surveyDaoImp;
	}

	/**
	 * @param surveyDaoImp the surveyDaoImp to set
	 */
	public void setSurveyDaoImp(SurveyDaoImp surveyDaoImp) {
		this.surveyDaoImp = surveyDaoImp;
	}

	

	/**
	 * @return the securityService
	 */
	private SecurityService getSecurityService() {
		return securityService;
	}

	/**
	 * @param securityService the securityService to set
	 */
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}

	/**
	 * @return the dataService
	 */
	private DataService getDataService() {
		return dataService;
	}

	/**
	 * @param dataService the dataService to set
	 */
	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	
	
	
	
	

}
