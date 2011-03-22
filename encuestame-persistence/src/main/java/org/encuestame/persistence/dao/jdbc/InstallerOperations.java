
package org.encuestame.persistence.dao.jdbc;

/**
 * Have a installation support.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 19, 2011
 */
public interface InstallerOperations {

    /**
     * Start database installations.
     * @param sql
     */
    void executeSql(String sql);


    /**
     * Execute sql
     */
    void executeInitialData();

    /**
     * Check database connection.
     * @return 1 if connection is successfull.
     */
    public int checkDatabaseConection();

    /**
     * Verify several tables if database are installed.
     * @return
     */
    public boolean checkIfDatabaseIsInstalled();
}
