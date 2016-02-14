/*
 ************************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.persistence.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * Installer database jdbc support.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 21, 2011
 */
//@Repository("installerDao")
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


    public void executeSql(String sql) throws DataAccessException {
           getJdbcTemplate().execute(sql);
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.persistence.dao.jdbc.InstallerOperations#checkDatabaseConection()
     */
    public int checkDatabaseConection() { 
        return getJdbcTemplate().queryForObject("SELECT 1", null, Integer.class);
        	 
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
        //requiredTables.add("app_version");
//        requiredTables.add("permission");
//        requiredTables.add("useraccount");
//        requiredTables.add("tweetpoll");
//        requiredTables.add("poll");
//        requiredTables.add("question");
//        requiredTables.add("application");
//        requiredTables.add("project");
//        requiredTables.add("useraccount_permission");
//        requiredTables.add("hash_tags");
//        requiredTables.add("social_account");
        try {
            log.debug("Url connection: "+getDataSource().getConnection().getMetaData().getURL());
            ResultSet rs = getDataSource().getConnection().getMetaData()
                    .getTables(null, null, "%", null);
            log.debug("checkIfDatabaseIsInstalled "+rs);
            while (rs.next()) {
//                System.out.println(rs.getString("TABLE_NAME"));
                if (requiredTables.indexOf(rs.getString("TABLE_NAME")
                        .toLowerCase()) != -1) {
                    log.debug("Table found "+rs.getString("TABLE_NAME"));
                    success = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
