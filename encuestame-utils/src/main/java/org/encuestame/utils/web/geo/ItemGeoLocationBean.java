/*
 ************************************************************************************
 * Copyright (C) 2001-2012 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */ 
package org.encuestame.utils.web.geo;

import java.io.Serializable;

import org.encuestame.utils.enums.TypeSearchResult;

/**
 * Item geo location Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since May 31 2012
 */
public class ItemGeoLocationBean implements Serializable { 
	
	/** Serial. **/
	
	private static final long serialVersionUID = 361226054771738837L;

	/** **/
	private Long itemId;
	
	/** **/
	private TypeSearchResult itemType;
	
	/** **/
	private String question;
	
	/** **/
	private Float latitude;
	
	/** **/
	private Float longitude;
	
	/** **/
	private Double distance;
	
	/** **/
	private String socialLink;
	
	/** **/
	private String socialType;

	/**
	 * @return the itemId
	 */
	public Long getItemId() {
		return itemId;
	}

	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(final Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the itemType
	 */
	public TypeSearchResult getItemType() {
		return itemType;
	}

	/**
	 * @param itemType the itemType to set
	 */
	public void setItemType(final TypeSearchResult itemType) {
		this.itemType = itemType;
	}

	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}

	/**
	 * @param question the question to set
	 */
	public void setQuestion(final String question) {
		this.question = question;
	}

	/**
	 * @return the latitude
	 */
	public Float getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(final Float latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public Float getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(final Float longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}  
	
	/**
	 * @return the socialLink
	 */
	public String getSocialLink() {
		return socialLink;
	}

	/**
	 * @param socialLink the socialLink to set
	 */
	public void setSocialLink(final String socialLink) {
		this.socialLink = socialLink;
	}

	/**
	 * @return the socialType
	 */
	public String getSocialType() {
		return socialType;
	}

	/**
	 * @param socialType the socialType to set
	 */
	public void setSocialType(final String socialType) {
		this.socialType = socialType;
	}  
}
