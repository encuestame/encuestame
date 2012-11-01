
package org.encuestame.persistence.exception;


public class EnMeUserAccountNotFoundException extends EnMeNoResultsFoundException {

    /**
     * Constructor.
     */
    public EnMeUserAccountNotFoundException() {
        super("tweetpoll not found");
    }
}
