
package org.encuestame.business.setup.install;

import org.encuestame.persistence.exception.EnmeFailOperation;

/**
 * Install database operations.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
public interface InstallDatabaseOperations {

    /**
     * Start process to install database.
     */
    void installDatabase();

    /**
     * Start process to upgrade database.
     * @param version
     */
    void upgradeDatabase(int version);

    /**
     * Check If database exist;
     * @return true if database exist.
     */
    boolean checkDatabase();

    /**
     * Check database version.
     * @return
     */
    int checkDatabaseVersion();

    /**
     * Check if required data exist.
     * eg: permissions, default administrator, several required data.
     * @return
     */
    boolean checkRequiredDataExist();


    /**
     * Initialize operations.
     * @param installDatabase
     * @throws EnmeFailOperation
     */
    void initializeDatabase(final TypeDatabase installDatabase) throws EnmeFailOperation;

}
