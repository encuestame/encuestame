package org.encuestame.core.security;

import org.encuestame.persistence.domain.security.UserAccount;

public interface EnMeUserDetailsDataAccount {

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

}
