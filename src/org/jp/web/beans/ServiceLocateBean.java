package org.jp.web.beans;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jp.core.persistence.dao.CatStateDaoImp;
import org.jp.web.serviceLocate.ServiceLocate;
import org.jp.web.utils.FacesUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * encuestame: system online surveys
 * Copyright (C) 2008-2009 encuestame Development Team *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of version 3 of the GNU General Public
 * License as published by the Free Software Foundation. *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details. *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA *
 * Id: ServiceLocateBean.java 1822 26/04/2009 20:25:26
 * @author dianmorales
 * @version 1.0
 * package org.jp.web.beans */

public class ServiceLocateBean implements ServiceLocate {

	private ApplicationContext appContext;
	CatStateDaoImp stateDao;
	//the user service bean nameate	
	private static final String STATE_SERVICE_BEAN_NAME = "catStateDaoImp";
	private Log logger = LogFactory.getLog(this.getClass());
	
	
	public ServiceLocateBean() {
		logger.info("init service locate bean");
		ServletContext context = FacesUtils.getServletContext();
		this.appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(context);
		this.stateDao = (CatStateDaoImp)this.lookupService(STATE_SERVICE_BEAN_NAME);
	}
	
	public CatStateDaoImp getStateDao() {
		return stateDao;
	}
	
	public Object lookupService(String serviceBeanName) {
		return appContext.getBean(serviceBeanName);
	}

}
