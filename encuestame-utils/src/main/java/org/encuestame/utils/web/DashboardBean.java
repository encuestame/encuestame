/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2010
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.utils.web;

import java.io.Serializable;

/**
 * Unit Dashboard.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
public class DashboardBean implements Serializable {

	/**
	 * Serial
	 */
	private static final long serialVersionUID = 8091000998249747994L;

	/** Dashboard id. **/
	private Long dashboardId;

	/** Dashboard name. **/
	private String dashboardName;

	/** Favorite dashboard. **/
	private Boolean favorite;

	/** Dashboard descrtiption. **/
	private String dashboardDesc;

	/** Dashboard layout. **/
	private String layout;

	/** Sequence. **/
	private Integer sequence;

	/** Favorite dashboard counter **/
	private Integer favoriteCounter;

	/** User**/
	private UserAccountBean secUser;

	/**
	 * @return the dashboardId
	 */
	public Long getDashboardId() {
		return dashboardId;
	}

	/**
	 * @param dashboardId the dashboardId to set
	 */
	public void setDashboardId(final Long dashboardId) {
		this.dashboardId = dashboardId;
	}

	/**
	 * @return the dashboardName
	 */
	public String getDashboardName() {
		return dashboardName;
	}

	/**
	 * @param dashboardName the dashboardName to set
	 */
	public void setDashboardName(final String dashboardName) {
		this.dashboardName = dashboardName;
	}

	/**
	 * @return the favorite
	 */
	public Boolean getFavorite() {
		return favorite;
	}

	/**
	 * @param favorite the favorite to set
	 */
	public void setFavorite(final Boolean favorite) {
		this.favorite = favorite;
	}

	/**
	 * @return the dashboardDesc
	 */
	public String getDashboardDesc() {
		return dashboardDesc;
	}

	/**
	 * @param dashboardDesc the dashboardDesc to set
	 */
	public void setDashboardDesc(final String dashboardDesc) {
		this.dashboardDesc = dashboardDesc;
	}

	/**
	 * @return the layout
	 */
	public String getLayout() {
		return layout;
	}

	/**
	 * @param layout the layout to set
	 */
	public void setLayout(final String layout) {
		this.layout = layout;
	}

	/**
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}

	/**
	 * @param sequence the sequence to set
	 */
	public void setSequence(final Integer sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the favoriteCounter
	 */
	public Integer getFavoriteCounter() {
		return favoriteCounter;
	}

	/**
	 * @param favoriteCounter the favoriteCounter to set
	 */
	public void setFavoriteCounter(Integer favoriteCounter) {
		this.favoriteCounter = favoriteCounter;
	}

	/**
	 * @return the secUser
	 */
	public UserAccountBean getSecUser() {
		return secUser;
	}

	/**
	 * @param secUser the secUser to set
	 */
	public void setSecUser(final UserAccountBean secUser) {
		this.secUser = secUser;
	}
}
