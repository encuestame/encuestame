/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.domain.dashboard;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Widget domain.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 27, 2011
 */
@Entity
@Table(name = "gadget")
public class Gadget {

    /** Widget id.**/
    private Long gadgetId;

    /** Widget name. **/
    private String gadgetName;

    /** Widget type**/
    private GadgetType gadgetType;

    /** Gadget color. **/
    private String gadgetColor;

    /** Column Number **/
    private Integer gadgetColumn;

    /** Gadget position **/
    private Integer gadgetPosition;

    /** {@link Dashboard} **/
    private Set<Dashboard> dashboard = new HashSet<Dashboard>();

    /**
    * @return the widgetId
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "gadgetId", unique = true, nullable = true)
    public Long getGadgetId() {
        return gadgetId;
    }

    /**
    * @param widgetId the widgetId to set
    */
    public void setGadgetId(final Long gadgetId) {
        this.gadgetId = gadgetId;
    }

    /**
    * @return the widgetName
    */
    @Column(name = "gadgetName", nullable = false)
    public String getGadgetName() {
        return gadgetName;
    }

    /**
    * @param widgetName the widgetName to set
    */
    public void setGadgetName(final String gadgetName) {
        this.gadgetName = gadgetName;
    }

    /**
    * @return the widgetType
    */
    @Column(name = "gadgetType", nullable = false)
    @Enumerated(EnumType.STRING)
    public GadgetType getGadgetType() {
        return gadgetType;
    }

    /**
    * @param widgetType the widgetType to set
    */
    public void setGadgetType(final GadgetType gadgetType) {
        this.gadgetType = gadgetType;
    }

	/**
	 * @return the gadgetColor
	 */
    @Column(name = "gadgetColor", nullable = false)
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
	 * @return the column
	 */
	@Column(name = "gadgetColumn", nullable = false)
	public Integer getGadgetColumn() {
		return gadgetColumn;
	}

	/**
	 * @param column the column to set
	 */
	public void setGadgetColumn(final Integer gadgetColumn) {
		this.gadgetColumn = gadgetColumn;
	}

	/**
	 * @return the position
	 */
	@Column(name = "gadgetPosition", nullable = false)
	public Integer getGadgetPosition() {
		return gadgetPosition;
	}

	/**
	 * @param position the position to set
	 */
	public void setGadgetPosition(final Integer gadgetPosition) {
		this.gadgetPosition = gadgetPosition;
	}

	/**
	 * @return the dashboard
	 */
    @ManyToMany()
    @JoinTable(name="dashboard_gadget",
               joinColumns={@JoinColumn(name="gadgetId")},
               inverseJoinColumns={@JoinColumn(name="dashboardId")})
	public Set<Dashboard> getDashboard() {
		return dashboard;
	}

	/**
	 * @param dashboard the dashboard to set
	 */
	public void setDashboard(final Set<Dashboard> dashboard) {
		this.dashboard = dashboard;
	}
}
