
package org.encuestame.persistence.exception;


/**
 * Start up exceptinon
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 20, 2011
 */
public class EnMeStartupException extends EnMeExpcetion {

    /**
     *
     */
    private static final long serialVersionUID = -4566029899637729975L;

    /**
     * Constructor.
     */
    public EnMeStartupException() {
        super();
    }

    /**
     * Exception.
     * @param cause cause
     */
    public EnMeStartupException(final Throwable cause) {
        super(cause);
    }

    /**
     *
     * @param message
     */
    public EnMeStartupException(final String message) {
        super(message);
    }
}
