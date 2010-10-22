package org.encuestame.business.security.spring;

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
