package org.jp.web.beans;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
 * Id: MasterBean.java Date: 26/04/2009 
 * @author juanpicado
 * package: org.jp.web.beans
 * @version 1.0
 */
public class MasterBean {
	
	ServiceLocateBean servicelocatebean;
	private Log logger = LogFactory.getLog(this.getClass());
	
	public MasterBean() {
		logger.info("init master bean");
	}

	public ServiceLocateBean getServicelocatebean() {
		logger.info("init getServicelocatebean");
		return servicelocatebean;
	}

	public void setServicelocatebean(ServiceLocateBean servicelocatebean) {
		logger.info("init setServicelocatebean");
		this.servicelocatebean = servicelocatebean;
		this.init();
	}
	
	protected void init() {
	}

}
