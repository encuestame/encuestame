
package org.encuestame.business.setup.install;

import java.io.IOException;

import org.encuestame.persistence.exception.EnmeFailOperation;

/**
 * Install database operations.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
public interface InstallDatabaseOperations {

    /**
     * Start process to install database.
     * @throws IOException
     */
    void installDatabase() throws IOException;

    /**
     * Start process to upgrade database.
     * @param version
     */
    void upgradeDatabase(int version);


    String getScriptLog();

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
     * @throws IOException
     */
    void initializeDatabase(final TypeDatabase installDatabase) throws EnmeFailOperation, IOException;

    /**
     *
     * @throws IOException
     */
    void installDemoData() throws IOException;

    /**
     *
     * @throws IOException
     */
    void dropAll() throws IOException;

}
