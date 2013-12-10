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
 * Unit gadget properties bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since August 03, 2011
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class GadgetPropertiesBean implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -6857682158720644832L;

    /** Gadget property id. **/
    @JsonProperty(value = "id")
    private Long propertyId;

    /** Widget property name. **/
    @JsonProperty(value = "gadget_property_name")
    private String gadgetPropName;

    /** Widget property value**/
    @JsonProperty(value = "gadget_property_value")
    private String gadgetPropValue;

    /** {@link UserAccount} **/
    private UserAccountBean userAccount;

    /** **/
    @JsonProperty(value = "gadget_id")
    private Long gadgetId;

    /**
     * @return the propertyId
     */
    @JsonIgnore
    public Long getPropertyId() {
        return propertyId;
    }

    /**
     * @param propertyId the propertyId to set
     */
    public void setPropertyId(final Long propertyId) {
        this.propertyId = propertyId;
    }

    /**
     * @return the gadgetPropName
     */
    @JsonIgnore
    public String getGadgetPropName() {
        return gadgetPropName;
    }

    /**
     * @param gadgetPropName the gadgetPropName to set
     */
    public void setGadgetPropName(final String gadgetPropName) {
        this.gadgetPropName = gadgetPropName;
    }

    /**
     * @return the gadgetPropValue
     */
    @JsonIgnore
    public String getGadgetPropValue() {
        return gadgetPropValue;
    }

    /**
     * @param gadgetPropValue the gadgetPropValue to set
     */
    public void setGadgetPropValue(final String gadgetPropValue) {
        this.gadgetPropValue = gadgetPropValue;
    }

    /**
     * @return the userAccount
     */
    @JsonIgnore
    public UserAccountBean getUserAccount() {
        return userAccount;
    }

    /**
     * @param userAccount the userAccount to set
     */
    public void setUserAccount(final UserAccountBean userAccount) {
        this.userAccount = userAccount;
    }

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
}
