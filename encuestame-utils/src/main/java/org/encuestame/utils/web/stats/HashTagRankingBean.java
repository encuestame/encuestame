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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * HashTag ranking bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since January 12, 2012.
 */
public class HashTagRankingBean implements Serializable, Comparable<Object>{
	
	 /** Log **/
    private Log log = LogFactory.getLog(this.getClass());
	
	/** Serial. */
	private static final long serialVersionUID = -5904425568490646391L;
  
	/** **/
	private Long rankId;
	
	/** **/
	private String tagName;
	
	/** **/
	private Double average;
	
	/** **/
	private Integer position;
	
	/** **/
	private Integer lastPosition;

	/**
	 * @return the rankId
	 */
	public Long getRankId() {
		return rankId;
	}

	/**
	 * @param rankId the rankId to set
	 */
	public void setRankId(Long rankId) {
		this.rankId = rankId;
	}

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @param tagName the tagName to set
	 */
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	/**
	 * @return the average
	 */
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
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}  
	
	/**
	 * @return the lastPosition
	 */
	public Integer getLastPosition() {
		return lastPosition;
	}

	/**
	 * @param lastPosition the lastPosition to set
	 */
	public void setLastPosition(final Integer lastPosition) {
		this.lastPosition = lastPosition;
	}

	/**
	 * 
	 */
	public int compareTo(Object o) {
		HashTagRankingBean hashTagRanking = (HashTagRankingBean) o;
        log.debug("HashTag rank position Value: " + hashTagRanking.getPosition());
        log.debug("This HashTag rank position Value: " + this.getPosition());
        int CompareToValue = Float.compare(hashTagRanking.getPosition() == null ? 0
                : hashTagRanking.getPosition(),
                this.getPosition() == null ? 0 : this.getPosition()); 
        return CompareToValue;
    } 
}
