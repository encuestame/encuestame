/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */ 

package org.encuestame.utils.web.stats;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Generic stats bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since February 07, 2012.
 */
public class GenericStatsBean implements Serializable {

	/** Serial. **/ 
	private static final long serialVersionUID = -6345752554967442263L;
	
	/** **/
	@JsonProperty(value = "like_dislike_rate")
	private Long likeDislikeRate;
	
	/** **/
	@JsonProperty(value = "hits")
	private Long hits;
	
	/** **/
	@JsonProperty(value = "created_at")
	private Date createdAt;
	
	/** **/
	@JsonProperty(value = "average")
	private Double average;
	
	/** **/
	@JsonProperty(value = "createdBy")
	private String createdBy;

	/**
	 * @return the likeDislikeRate
	 */
	@JsonIgnore
	public Long getLikeDislikeRate() {
		return likeDislikeRate;
	}

	/**
	 * @param likeDislikeRate the likeDislikeRate to set
	 */
	public void setLikeDislikeRate(Long likeDislikeRate) {
		this.likeDislikeRate = likeDislikeRate;
	}

	/**
	 * @return the hits
	 */
	@JsonIgnore
	public Long getHits() {
		return hits;
	}

	/**
	 * @param hits the hits to set
	 */
	public void setHits(Long hits) {
		this.hits = hits;
	}

	/**
	 * @return the createdAt
	 */
	@JsonIgnore
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * @return the average
	 */
	@JsonIgnore
	public Double getAverage() {
		return average;
	}

	/**
	 * @param average the average to set
	 */
	public void setAverage(Double average) {
		this.average = average;
	}

	/**
	 * @return the createdBy
	 */
	@JsonIgnore
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	} 
}
