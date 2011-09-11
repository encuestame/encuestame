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
package org.encuestame.core.config;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

/**
 * Administrator Profile.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 10, 2011
 */
public class AdministratorProfile implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -8947758116702889573L;

    private String username;

    private String password;

    private String confirmPassword;

    private String email;

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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     *            the password to set
     */
    public void setPassword(final String password) {
        this.password = password;
    }

    /**
     * @return the confirmPassword
     */
    public String getConfirmPassword() {
        return confirmPassword;
    }

    /**
     * @param confirmPassword
     *            the confirmPassword to set
     */
    public void setConfirmPassword(final String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     *            the email to set
     */
    public void setEmail(final String email) {
        this.email = email;
    }

    /**
     *
     * @param validationContext
     */
    public void validateinstallAdmon(ValidationContext context){
        System.out.println("=============== VALIDATE =============");
        MessageContext messages = context.getMessageContext();
        if (!password.equals(confirmPassword)) {
            messages.addMessage(new MessageBuilder().error().source("checkinDate").
                defaultText("Passwors should be equals.").build());
        }
        System.out.println("=============== VALIDATE =============");
    }
}
