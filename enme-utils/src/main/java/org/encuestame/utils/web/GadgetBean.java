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
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Unit gadget bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 03, 2011
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GadgetBean implements Serializable {

    /** Serial. **/
    private static final long serialVersionUID = -5555664650450463269L;

    /** Widget id.**/
    @JsonProperty(value = "id")
    private Long gadgetId;

    /** Widget name. **/
    @JsonProperty(value = "gadget_name")
    private String gadgetName;

    /** Gadget color. **/
    @JsonProperty(value = "gadget_color")
    private String gadgetColor;

    /** Column Number **/
    @JsonProperty(value = "gadget_column")
    private Integer gadgetColumn;

    /** Gadget position **/
    @JsonProperty(value = "gadget_position")
    private Integer gadgetPosition;

    /** {@link DashboardBean} **/
    private Set<DashboardBean> dashboard = new HashSet<DashboardBean>();

    /** Availability gadget**/
    @JsonProperty(value = "gadget_status")
    private Boolean status;

    /**
     * @return the gadgetId
     */
    @JsonIgnore
    public Long getGadgetId() {
        return gadgetId;
    }

    /**
     * @param gadgetId the gadgetId to set
     */
    public void setGadgetId(final Long gadgetId) {
        this.gadgetId = gadgetId;
    }

    /**
     * @return the gadgetName
     */
    @JsonIgnore
    public String getGadgetName() {
        return gadgetName;
    }

    /**
     * @param gadgetName the gadgetName to set
     */
    public void setGadgetName(final String gadgetName) {
        this.gadgetName = gadgetName;
    }

    /**
     * @return the gadgetColor
     */
    @JsonIgnore
    public String getGadgetColor() {
        return gadgetColor;
    }

    /**
     * @param gadgetColor the gadgetColor to set
     */
    public void setGadgetColor(final String gadgetColor) {
        this.gadgetColor = gadgetColor;
    }

    /**
     * @return the gadgetColumn
     */
    @JsonIgnore
    public Integer getGadgetColumn() {
        return gadgetColumn;
    }

    /**
     * @param gadgetColumn the gadgetColumn to set
     */
    public void setGadgetColumn(final Integer gadgetColumn) {
        this.gadgetColumn = gadgetColumn;
    }

    /**
     * @return the gadgetPosition
     */
    @JsonIgnore
    public Integer getGadgetPosition() {
        return gadgetPosition;
    }

    /**
     * @param gadgetPosition the gadgetPosition to set
     */
    public void setGadgetPosition(final Integer gadgetPosition) {
        this.gadgetPosition = gadgetPosition;
    }

    /**
     * @return the dashboard
     */
    @JsonIgnore
    public Set<DashboardBean> getDashboard() {
        return dashboard;
    }

    /**
     * @param dashboard the dashboard to set
     */
    public void setDashboard(final Set<DashboardBean> dashboard) {
        this.dashboard = dashboard;
    }

    /**
     * @return the status
     */
    @JsonIgnore
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(final Boolean status) {
        this.status = status;
    }
}
