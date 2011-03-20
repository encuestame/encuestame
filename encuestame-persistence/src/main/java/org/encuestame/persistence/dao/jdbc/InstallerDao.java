package org.encuestame.persistence.dao.jdbc;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

@Repository("installerDao")
public class InstallerDao extends JdbcDaoSupport implements InstallerOperations {

    private static Log log = LogFactory.getLog(InstallerDao.class);

    @Autowired
    public InstallerDao(JdbcTemplate jdbcTemplate) {
        setJdbcTemplate(jdbcTemplate);
    }

    public void executeSql(String sql) {
        try{
            getJdbcTemplate().execute(sql);
        }catch (Exception e) {
            log.error(e);
        }
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.jdbc.InstallerOperations#checkDatabaseConection()
     */
    public void checkDatabaseConection() {
        getJdbcTemplate().execute("select 1");
        //BatchSqlUpdate d = new BatchSqlUpdate(getDataSource(), "");
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
