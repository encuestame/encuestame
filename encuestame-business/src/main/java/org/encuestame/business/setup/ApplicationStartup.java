package org.encuestame.business.setup;

import org.apache.log4j.Logger;
import org.encuestame.business.setup.install.InstallDatabaseOperations;
import org.encuestame.persistence.exception.EnMeStartupException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manages encuestame application startup.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 15, 2011
 */
public class ApplicationStartup implements StartupProcess {

    protected Logger log = Logger.getLogger(this.getClass());

    private static boolean started = false;

    /**
     * Install database operations.
     */
    @Autowired
    public InstallDatabaseOperations install;

    public ApplicationStartup() {
        System.out.println("***ApplicationStartup***");
    }

    /**
     * Check if app started.
     *
     * @return
     */
    public static boolean isStarted() {
        return started;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.StartupProcess#startProcess()
     */
    public void startProcess() throws EnMeStartupException {
        // check if root directory exist
        try {
            if (!DirectorySetupOperations.checkIfExistRootDirectory()) {
                log.info("root folder is missing");
                DirectorySetupOperations.createRootFolder();
            } else {
                log.info("loading information root folder");
            }
        } catch (EnmeFailOperation e) {
            throw new EnMeStartupException(e.getMessage());
        }
    }

    /**
     * @param install
     *            the install to set
     */
    public void setInstall(final InstallDatabaseOperations install) {
        this.install = install;
    }

    public boolean checkDatabase() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkDatabaseVersion() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean upgradeDatabase(int version) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkStoreDirectyIfExist() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkRequiredDataExist() {
        // TODO Auto-generated method stub
        return false;
    }

    public void notifyStartupByEmail() {
        // TODO Auto-generated method stub

    }

    public void displayVersionOnStartup() {
        // TODO Auto-generated method stub

    }

    public void installDatabase() {
        // TODO Auto-generated method stub

    }
}
