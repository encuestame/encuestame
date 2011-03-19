package org.encuestame.persistence.dao.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository("installerDao")
public class InstallerDao extends JdbcDaoSupport implements InstallerOperations {

    @Autowired
    public InstallerDao(JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }

    public void startInstall() {
        // getJdbcTemplate().
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.jdbc.InstallerOperations#checkDatabaseConection()
     */
    public void checkDatabaseConection() {
        getJdbcTemplate().execute("select 1");
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.jdbc.InstallerOperations#checkIfDatabaseIsInstalled()
     */
    public boolean checkIfDatabaseIsInstalled() {
        getJdbcTemplate().execute("select 1");
        return false;
    }

    public void executeInitialData() {
        // TODO Auto-generated method stub

    }
}
