package org.encuestame.persistence.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Repository;

/**
 * Installer database jdbc support.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
@Repository("installerDao")
public class InstallerDao extends JdbcDaoSupport implements InstallerOperations {

    private static Log log = LogFactory.getLog(InstallerDao.class);

    /**
     * Constructor.
     * @param jdbcTemplate {@link JdbcTemplate}.
     */
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
    public int checkDatabaseConection() {
        return getJdbcTemplate().queryForInt("select 1");
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.persistence.dao.jdbc.InstallerOperations#
     * checkIfDatabaseIsInstalled()
     */
    public boolean checkIfDatabaseIsInstalled() {
        boolean success = false;
        final List<String> requiredTables = new ArrayList<String>();
        requiredTables.add("account");
        requiredTables.add("app_version");
        requiredTables.add("permission");
        requiredTables.add("useraccount");
        requiredTables.add("tweetpoll");
        requiredTables.add("poll");
        requiredTables.add("question");
        requiredTables.add("application");
        requiredTables.add("project");
        requiredTables.add("useraccount_permission");
        requiredTables.add("hash_tags");
        requiredTables.add("social_account");
        try {
            log.debug("Url connection: "+getDataSource().getConnection().getMetaData().getURL());
            ResultSet rs = getDataSource().getConnection().getMetaData()
                    .getTables(null, null, "%", null);
            log.debug("checkIfDatabaseIsInstalled "+rs);
            while (rs.next()) {
                if (requiredTables.indexOf(rs.getString("TABLE_NAME")
                        .toLowerCase()) != -1) {
                    log.debug("Table found "+rs.getString("TABLE_NAME"));
                    success = true;
                }
            }
        } catch (SQLException e) {
            log.error("Error on check database tables name");
            success = false;
        }
        log.debug("Database installed? "+success);
        return success;
    }

    public void executeInitialData() {
        // TODO Auto-generated method stub

    }
}
