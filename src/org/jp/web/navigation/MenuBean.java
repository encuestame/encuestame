package org.jp.web.navigation;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
 * Id: MenuBean.java Date: 27/05/2009 12:49:52
 * 
 * @author juanpicado package: org.jp.web.navigation
 * @version 1.0
 */
public class MenuBean {

	private String currentSelected = "current";

	/**
	 * @return the currentSelected
	 */
	public String getCurrentSelected() {
		return currentSelected;
	}

	/**
	 * @param currentSelected
	 *            the currentSelected to set
	 */
	public void setCurrentSelected(String currentSelected) {
		this.currentSelected = currentSelected;
	}

	public void process(ActionEvent arg0) {
		FacesContext f = FacesContext.getCurrentInstance();
		// message2=message2+arg0.getComponent().getClientId(f) +
		// " Triggered anAction Listener";

	}
}
