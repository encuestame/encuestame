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

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * HashTag detail stats.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since February 29, 2012
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HashTagDetailStats implements Serializable, Comparable<Object> {
	
	/** Serial **/
	private static final long serialVersionUID = -3813516985333784846L;  

	/** **/
	@JsonProperty(value = "label")
	private String label;

	/** **/
	@JsonProperty(value = "value")
	private Long value;

	/** **/
	@JsonProperty(value = "sub_label")
	private String subLabel;
	

	/**
	 * @return the label
	 */
	@JsonIgnore
	public String getLabel() {
		return label;
	}

	/**
	 * @param label
	 * the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
	}

	/**
	 * @return the value
	 */
	@JsonIgnore
	public Long getValue() {
		return value;
	}

	/**
	 * @param value
	 * the value to set
	 */
	public void setValue(final Long value) {
		this.value = value;
	}  

	/**
	 * @return the subLabel
	 */
	@JsonIgnore
	public String getSubLabel() {
		return subLabel;
	}

	/**
	 * @param subLabel the subLabel to set
	 */
	public void setSubLabel(final String subLabel) {
		this.subLabel = subLabel;
	}

	@Override
	public int compareTo(Object o) {
		HashTagDetailStats itemStat = (HashTagDetailStats) o;
		String item = this.getLabel(); 
		int CompareToValue = itemStat.getLabel().compareTo(item); 
		return CompareToValue;

	}
} 