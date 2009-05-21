package org.jp.test;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	private String myGet;

	public String getMyGet() {
		try{
			HttpServletRequest req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
			HttpServletResponse res = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
			System.out.println("REQ->"+req);
			System.out.println("RES->"+res);
		}catch (Exception e) {
			System.out.println("ERROR->"+e);
		}
		return myGet;
	}

	public void setMyGet(String myGet) {
		this.myGet = myGet;
	}

}
