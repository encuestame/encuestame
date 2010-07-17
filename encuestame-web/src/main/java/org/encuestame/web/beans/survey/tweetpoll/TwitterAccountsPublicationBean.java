/*
 ************************************************************************************
 * Copyright (C) 2001-2010 encuestame: system online surveys Copyright (C) 2009
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.web.beans.survey.tweetpoll;

import java.io.Serializable;

import org.encuestame.utils.security.UnitTwitterAccountBean;

/**
 * TwitterAccounts Publication Bean.
 * @author Picado, Juan juanATencuestame.org
 * @since Jul 11, 2010 11:09:03 AM
 * @version $Id:$
 */
public class TwitterAccountsPublicationBean implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = -291204309931131495L;

    /**
     * Unit Twitter Account Bean.
     */
    private UnitTwitterAccountBean accountBean;


    private Integer id;

    /**
     * Active State.
     */
    private Boolean active = false;

    /**
     * @return the accountBean
     */
    public UnitTwitterAccountBean getAccountBean() {
        return accountBean;
    }

    /**
     * @param accountBean the accountBean to set
     */
    public void setAccountBean(UnitTwitterAccountBean accountBean) {
        this.accountBean = accountBean;
    }

    /**
     * @return the active
     */
    public Boolean getActive() {
        return active;
    }

    /**
     * @param active the active to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }
}
