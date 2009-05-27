package org.jp.test;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jp.core.persistence.dao.CatLocationDaoImp;

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
 * 
 * Id: MyVarGet.java Date: 20/05/2009 12:32:57
 * 
 * @author juanpicado package: org.jp.test
 * @version 1.0
 */
public class MyVarGet {
	private String myGet = "/test/page1.xhtml";
	private static Logger log = Logger.getLogger(CatLocationDaoImp.class);

	public MyVarGet() {
		log.info("myVar");
	}

	public String getMyGet() {
		return myGet;
	}

	public void setMyGet(String myGet) {
		log.info("myGet->" + myGet);
		this.myGet = myGet;
	}

	public void reasignone() {
		try {
			log.info("reasignone->" + myGet);
			setMyGet("/test/page2.xhtml");
		} catch (Exception e) {
			log.error("error reasignone->" + e);
		}
	}

	public void testing() {
		try {
			log.info("testing->" + myGet);
			setMyGet("/pages/admon/users.xhtml");
		} catch (Exception e) {
			log.error("error reasignone->" + e);
		}
	}
}
