
package org.encuestame.persistence.exception;


public class EnMePollNotFoundException extends EnMeNoResultsFoundException {

    /**
     * Constructor.
     */
    public EnMePollNotFoundException() {
        super("poll not found");
    }

    public EnMePollNotFoundException(String message) {
        super(message);
    }
}
