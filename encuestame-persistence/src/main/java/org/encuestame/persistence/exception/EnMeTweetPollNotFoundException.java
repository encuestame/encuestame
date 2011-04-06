
package org.encuestame.persistence.exception;


public class EnMeTweetPollNotFoundException extends EnMeNoResultsFoundException {

    /**
     * Constructor.
     */
    public EnMeTweetPollNotFoundException() {
        super("user not found");
    }
}
