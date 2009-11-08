package org.encuestame.core.service;

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
 * Id: ApplicationServices.java Date: 11/05/2009 14:35:36
 * 
 * @author juanpicado package: org.encuestame.core.service
 * @version 1.0
 */

public class ApplicationServices extends Service implements IApplicationService {

	private String name;
	private String urlImg;
	private String encoding;
	private String apiKeygoogle;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlImg() {
		return urlImg;
	}

	public void setUrlImg(String urlImg) {
		this.urlImg = urlImg;
	}

	public String getEncoding() {
		return encoding;
	}

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

	

}
