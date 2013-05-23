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
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.encuestame.utils.enums.TypeSearchResult;
import org.joda.time.DateTime;

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

	@JsonProperty(value = "filter")
	private TypeSearchResult typeSearchResult;

	/** **/
	@JsonProperty(value = "date_value")
	private DateTime dateValue;

	/** **/
	@JsonProperty(value = "miliseconds_date")
	private Long milisecondsDate;


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

	/**
	 * @return the typeSearchResult
	 */
	@JsonIgnore
	public TypeSearchResult getTypeSearchResult() {
		return typeSearchResult;
	}

	/**
	 * @param typeSearchResult the typeSearchResult to set
	 */
	public void setTypeSearchResult(TypeSearchResult typeSearchResult) {
		this.typeSearchResult = typeSearchResult;
	}

	/**
	 * @return the dateValue
	 */
	public DateTime getDateValue() {
		return dateValue;
	}

	/**
	 * @param dateValue the dateValue to set
	 */
	public void setDateValue(final DateTime dateValue) {
		this.dateValue = dateValue;
	}


	/**
	 * @return the milisecondsDate
	 */
	public Long getMilisecondsDate() {
		return milisecondsDate;
	}

	/**
	 * @param milisecondsDate the milisecondsDate to set
	 */
	public void setMilisecondsDate(final Long milisecondsDate) {
		this.milisecondsDate = milisecondsDate;
	}

	@Override
	public int compareTo(Object o) {
		HashTagDetailStats itemStat = (HashTagDetailStats) o;
		String item = this.getLabel();
		int CompareToValue = itemStat.getLabel().compareTo(item);
		return CompareToValue;

	}
}