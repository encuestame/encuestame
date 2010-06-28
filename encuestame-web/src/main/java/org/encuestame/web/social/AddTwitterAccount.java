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
package org.encuestame.web.social;

import java.io.Serializable;

import org.encuestame.web.beans.MasterBean;

/**
 * Add Twitter Account.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Jun 27, 2010 11:33:06 AM
 * @version $Id:$
 */
public class AddTwitterAccount extends MasterBean implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -111208303331131160L;

    /**
     * Username.
     */
    private String username;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username
     *            the username to set
     */
    public void setUsername(final String username) {
        this.username = username;
    }

}
