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
 * Notification Bean.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since July 19, 2010
 * @deprecated not used anymore.
 */
@Deprecated
public class NotificationBean {

    /**
     * Notification code
     ***/
    private String code;

    /**
     * Url to unsubscribe
     ***/
    private String urlUnsuscribe;

    /**
     * Email Message
     ***/
    private String message;

    /**
     * Email
     */
    private String email;

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
     * @return the urlUnsuscribe
     */
    public String getUrlUnsuscribe() {
        return urlUnsuscribe;
    }

    /**
     * @param urlUnsuscribe the urlUnsuscribe to set
     */
    public void setUrlUnsuscribe(String urlUnsuscribe) {
        this.urlUnsuscribe = urlUnsuscribe;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

}
