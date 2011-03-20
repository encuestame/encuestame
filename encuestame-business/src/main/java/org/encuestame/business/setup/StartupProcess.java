
package org.encuestame.business.setup;

import org.encuestame.persistence.exception.EnMeStartupException;


public interface StartupProcess {

    void startProcess() throws EnMeStartupException;

    boolean checkDatabase();

    boolean checkDatabaseVersion();

    boolean upgradeDatabase(int version);

    boolean checkStoreDirectyIfExist();

    boolean checkRequiredDataExist();

    void notifyStartupByEmail();

    void displayVersionOnStartup();

    void installDatabase();

}
