
package org.encuestame.business.setup;

import org.encuestame.util.exception.EnMeStartupException;

/**
 * Implementation for start up process.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 22, 2011
 */
public interface StartupProcess {

    /**
     *  Start up initialize process/
     * @throws EnMeStartupException if exist some error.
     */
    void startProcess() throws EnMeStartupException;

    /**
     * Display version after succesfull start up.
     */
    void displayVersionOnStartup();

}
