
package org.encuestame.persistence.exception;

/**
 * Illegal provider exception
 * @author Picado, Juan juanATencuestame.org
 * @since Feb 27, 2011
 */
public class EnMeIllegalProviderException extends EnMeNoResultsFoundException{

    public EnMeIllegalProviderException() {
        super("illegal provider");
    }
}
