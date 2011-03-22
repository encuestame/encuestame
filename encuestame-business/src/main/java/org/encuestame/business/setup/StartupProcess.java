
package org.encuestame.business.setup;

import org.encuestame.persistence.exception.EnMeStartupException;


public interface StartupProcess {

    void startProcess() throws EnMeStartupException;

    void displayVersionOnStartup();

}
