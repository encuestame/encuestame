package org.jp.web.navigation;

import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

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
 * Id: MenuBean.java Date: 27/05/2009 12:49:52
 * 
 * @author juanpicado package: org.jp.web.navigation
 * @version 1.0
 */
public class MenuBean {

	private String currentSelected = "current";
	private String menuSelected;
	private String admonCss;
	private String indexCss;
	private String surveyCss;
	private String statsCss;
	private static Logger log = Logger.getLogger(CatLocationDaoImp.class);
	
	public MenuBean() {
		setIndexCss(getCurrentSelected());
	}
	
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

	/**
	 * 
	 */
	private void changeCurrentMenu() {
		resetMenus();
		log.info("changeCurrentMenu->"+getCurrentSelected());
		if (getMenuSelected().compareTo("index") == 0) {
			setIndexCss(getCurrentSelected());
		} else if (getMenuSelected().compareTo("admon") == 0) {
			setAdmonCss(getCurrentSelected());
		} else if (getMenuSelected().compareTo("survey") == 0) {
			setSurveyCss(getCurrentSelected());
		} else if (getMenuSelected().compareTo("stats") == 0) {
			setStatsCss(getCurrentSelected());
		} else {
			setIndexCss(getCurrentSelected());
		}
	}

	private void resetMenus() {
		setIndexCss("");
		setAdmonCss("");
		setStatsCss("");
		setSurveyCss("");
	}

	/**
	 * @return the menuSelected
	 */
	public String getMenuSelected() {
		
		return menuSelected;
	}

	/**
	 * @param menuSelected
	 *            the menuSelected to set
	 */
	public void setMenuSelected(String menuSelected) {
		log.info("menuSelected->"+menuSelected);	
		this.menuSelected = menuSelected;
		changeCurrentMenu();
	}

	/**
	 * @return the admonCss
	 */
	public String getAdmonCss() {
		return admonCss;
	}

	/**
	 * @param admonCss
	 *            the admonCss to set
	 */
	public void setAdmonCss(String admonCss) {
		this.admonCss = admonCss;
	}

	/**
	 * @return the indexCss
	 */
	public String getIndexCss() {
		return indexCss;
	}

	/**
	 * @param indexCss
	 *            the indexCss to set
	 */
	public void setIndexCss(String indexCss) {
		this.indexCss = indexCss;
	}

	/**
	 * @return the surveyCss
	 */
	public String getSurveyCss() {
		return surveyCss;
	}

	/**
	 * @param surveyCss
	 *            the surveyCss to set
	 */
	public void setSurveyCss(String surveyCss) {
		this.surveyCss = surveyCss;
	}

	/**
	 * @return the statsCss
	 */
	public String getStatsCss() {
		return statsCss;
	}

	/**
	 * @param statsCss
	 *            the statsCss to set
	 */
	public void setStatsCss(String statsCss) {
		this.statsCss = statsCss;
	}

}
