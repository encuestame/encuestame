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
package org.encuestame.social.api.support;

import java.io.Serializable;

/**
 * Model class containing a Facebook user's profile information.
 * @author Picado, Juan juanATencuestame.org
 * @since
 */
@SuppressWarnings("serial")
public class FacebookProfile implements Serializable {

    private final long id;
    private final String name;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String username;



    public FacebookProfile(long id, String name, String firstName,
            String lastName, String email, String username) {
        super();
        this.id = id;
        this.name = name;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
    }

    /**
     * The user's Facebook ID
     *
     * @return The user's Facebook ID
     */
    public long getId() {
        return id;
    }

    /**
     * The user's full name
     *
     * @return The user's full name
     */
    public String getName() {
        return name;
    }

    /**
     * The user's first name
     *
     * @return The user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * The user's last name
     *
     * @return The user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * The user's email address
     *
     * @return The user's email address
     */
    public String getEmail() {
        return email;
    }



    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "FacebookProfile [id=" + id + ", name=" + name + ", firstName="
                + firstName + ", lastName=" + lastName + ", email=" + email
                + ", username=" + username + "]";
    }


}
