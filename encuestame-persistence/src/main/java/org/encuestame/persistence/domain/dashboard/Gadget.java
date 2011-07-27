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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private String gadgetType;

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
    public String getGadgetType() {
        return gadgetType;
    }

    /**
    * @param widgetType the widgetType to set
    */
    public void setGadgetType(final String gadgetType) {
        this.gadgetType = gadgetType;
    }
}
