package org.encuestame.core.security.spring;

public interface EnMeUserDetailsDataAccount {

    /**
     * Twitter Account.
     */
    String getTwitterAccount();

    /**
     * Is Owner Account.
     * @return is owner
     */
    boolean isOwnerAccount();

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
