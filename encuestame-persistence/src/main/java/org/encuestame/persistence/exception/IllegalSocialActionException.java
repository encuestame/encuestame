
package org.encuestame.persistence.exception;

/**
 * Illegal social action exception.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 7, 2011
 */
public class IllegalSocialActionException  extends EnMeExpcetion{


    /**
     * Serial.
     */
    private static final long serialVersionUID = -5547611858006644546L;

    /**
     * Constructor.
     */
    public IllegalSocialActionException() {
        super("social action not defined");
    }
}
