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
package org.encuestame.utils.web;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Unit Dashboard.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 29, 2011
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DashboardBean implements Serializable {

    /**
     * Serial
     */
    private static final long serialVersionUID = 8091000998249747994L;

    /** Dashboard id. **/
    @JsonProperty(value = "id")
    private Long dashboardId;

    /** Dashboard name. **/
    @JsonProperty(value = "dashboard_name")
    private String dashboardName;

    /** Favorite dashboard. **/
    @JsonProperty(value = "favorite")
    private Boolean favorite;

    /** Dashboard descrtiption. **/
    @JsonProperty(value = "dashboard_description")
    private String dashboardDesc;

    /** Dashboard layout. **/
    @JsonProperty(value = "layout")
    private String layout;

    /** Sequence. **/
    @JsonProperty(value = "sequence")
    private Integer sequence;

    /** Favorite dashboard counter **/
    @JsonProperty(value = "favorite_counter")
    private Integer favoriteCounter;

    @JsonProperty(value = "selected")
    private Boolean selected;

    /**
     * @return the dashboardId
     */
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
    @JsonIgnore
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
     * @return the selected
     */
    @JsonIgnore
    public Boolean getSelected() {
        return selected;
    }

    /**
     * @param selected the selected to set
     */
    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
