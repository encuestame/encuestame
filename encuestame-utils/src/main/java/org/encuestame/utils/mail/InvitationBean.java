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
package org.encuestame.utils.mail;

/**
 * Invitation Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 19, 2010
 * @version $Id: $
 */
public class InvitationBean {

    /****/
    private String listName;
    /****/
    private String code;
    /****/
    private String urlInvitation;

    private String email;

    /**
     * @return the listName
     */
    public String getListName() {
        return listName;
    }

    /**
     * @param listName the listName to set
     */
    public void setListName(String listName) {
        this.listName = listName;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the urlInvitation
     */
    public String getUrlInvitation() {
        return urlInvitation;
    }

    /**
     * @param urlInvitation the urlInvitation to set
     */
    public void setUrlInvitation(String urlInvitation) {
        this.urlInvitation = urlInvitation;
    }

    /**
     * @return the emailInvitation
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param emailInvitation the emailInvitation to set
     */
    public void setEmail(String email) {
        this.email = email;
    }



}
