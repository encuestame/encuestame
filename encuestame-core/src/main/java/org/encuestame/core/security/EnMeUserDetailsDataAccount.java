package org.encuestame.core.security;

public interface EnMeUserDetailsDataAccount {

    /**
     * Twitter Account.
     */
    String getTwitterAccount();

    /**
     * Get User Email.
     * @return email
     */
    String getUserEmail();

    /**
     * Complete Name.
     */
    String getCompleteName();

}
