
package org.encuestame.persistence.exception;


public class EnMeUserAccountNotFoundException extends EnMeNoResultsFoundException {

    /**
     * Constructor.
     */
    public EnMeUserAccountNotFoundException() {
        super("user not found");
    }
}
