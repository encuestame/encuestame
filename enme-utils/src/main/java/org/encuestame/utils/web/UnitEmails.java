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

/**
 * Unit Emails Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since  July 15, 2010
 * @version $Id: $
 */
public class UnitEmails implements Serializable {

    /**
     * Serial.
     */
    private static final long serialVersionUID = -6735972465867612620L;

    /** Id Email.**/
    private Long idEmail;

    /** Email Name. **/
    private String emailName;

    /** Lists Bean. **/
    private Long listsId;

    /**
     * @return the idEmail
     */
    public Long getIdEmail() {
        return idEmail;
    }

    /**
     * @param idEmail the idEmail to set
     */
    public void setIdEmail(Long idEmail) {
        this.idEmail = idEmail;
    }

    /**
     * @return the emailName
     */
    public String getEmailName() {
        return emailName;
    }

    /**
     * @param emailName the emailName to set
     */
    public void setEmailName(String emailName) {
        this.emailName = emailName;
    }

    /**
     * @return the listsId
     */
    public Long getListsId() {
        return listsId;
    }

    /**
     * @param listsId the listsId to set
     */
    public void setListsId(Long listsId) {
        this.listsId = listsId;
    }




}
