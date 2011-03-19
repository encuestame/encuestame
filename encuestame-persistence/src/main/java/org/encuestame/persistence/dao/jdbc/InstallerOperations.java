
package org.encuestame.persistence.dao.jdbc;

/**
 * Have a installation support.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 19, 2011
 */
public interface InstallerOperations {

    /**
     * Start database installations.
     */
    public void startInstall();


    /**
     * Execute sql
     */
    public void executeInitialData();

    /**
     *
     */
    public void checkDatabaseConection();

    /**
     *
     * @return
     */
    public boolean checkIfDatabaseIsInstalled();
}
