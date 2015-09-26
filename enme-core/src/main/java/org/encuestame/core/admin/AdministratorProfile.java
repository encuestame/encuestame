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
package org.encuestame.core.admin;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.encuestame.core.util.FieldMatch;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Administrator Profile.
 * @author Picado, Juan juanATencuestame.org
 * @since Sep 10, 2011
 */
@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"),
    @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")
})
public class AdministratorProfile implements Serializable{

    /**
     * Serial.
     */
    private static final long serialVersionUID = -8947758116702889573L;

    /**
     * Useraname.
     */
    @NotNull
    @NotEmpty
    @Length(min = 6, max = 25)
    private String username;

    /**
     * Password.
     */
    @NotNull
    @NotEmpty
    @Length(min = 6, max = 16)
    private String password;

    /**
     * Password confirmation.
     */
    @NotNull
    @NotEmpty
    @Length(min = 6, max = 16)
    private String confirmPassword;

    /**
     * Email.
     */
    @Email
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 150)
    private String email;

    /**
     * Email confirmation.
     */
    @Email
    @NotNull
    @NotEmpty
    @Length(min = 3, max = 150)
    private String confirmEmail;

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
     * @return the confirmEmail
     */
    public String getConfirmEmail() {
        return confirmEmail;
    }

    /**
     * @param confirmEmail the confirmEmail to set
     */
    public void setConfirmEmail(final String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }
}
