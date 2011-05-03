package org.encuestame.core.security.details;

import org.encuestame.persistence.domain.security.UserAccount;
import org.springframework.security.core.userdetails.UserDetails;

public interface EnMeUserDetailsDataAccount extends UserDetails{

    /**
     * Get User Email.
     * @return email
     */
    String getUserEmail();

    /**
     * Complete Name.
     */
    String getCompleteName();


    /**
     * Get {@link UserAccount}.
     * @return
     */
    UserAccount getUserAccount();

    /**
     *
     * @return
     */
    boolean isSocialCredentials();

}
