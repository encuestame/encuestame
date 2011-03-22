
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
    void executeSql(String sql);


    /**
     * Execute sql
     */
    void executeInitialData();

    /**
     * @return
     *
     */
    public int checkDatabaseConection();

    /**
     *
     * @return
     */
    public boolean checkIfDatabaseIsInstalled();
}
