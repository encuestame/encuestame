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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.encuestame.persistence.domain.security.UserAccount;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Widget properties.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 27, 2011
 */
@Entity
@Table(name = "gadget_properties")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GadgetProperties {

    /** Widget preference id. **/
    private Long propertyId;

    /** Widget property name. **/
    private String gadgetPropName;

    /** Widget property value**/
    private String gadgetPropValue;

    /** {@link Gadget} **/
    private Gadget gadget;

    /** {@link UserAccount} **/
    private UserAccount userAccount;

    /**
    * @return the preferenceId
    */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "propertyId", unique = true, nullable = true)
    public Long getPropertyId() {
        return propertyId;
    }

    /**
    * @param preferenceId the preferenceId to set
    */
    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    /**
    * @return the prefName
    */
    @Column(name = "gadget_prop_name", nullable = false)
    public String getGadgetPropName() {
        return gadgetPropName;
    }

    /**
    * @param prefName the prefName to set
    */
    public void setGadgetPropName(final String gadgetPropName) {
        this.gadgetPropName = gadgetPropName;
    }

    /**
    * @return the prefValue
    */
    @Column(name = "gadget_prop_value", nullable = false)
    public String getGadgetPropValue() {
        return gadgetPropValue;
    }

    /**
    * @param prefValue the prefValue to set
    */
    public void setGadgetPropValue(final String gadgetPropValue) {
        this.gadgetPropValue = gadgetPropValue;
    }

    /**
    * @return the widget
    */
    @ManyToOne()
    public Gadget getGadget() {
        return gadget;
    }

    /**
    * @param widget the widget to set
    */
    public void setGadget(Gadget gadget) {
        this.gadget = gadget;
    }

    /**
    * @return the userAccount
    */
    @ManyToOne()
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
    * @param userAccount the userAccount to set
    */
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
}
