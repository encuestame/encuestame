
/*
 ************************************************************************************
 * Copyright (C) 2001-2015 encuestame: system online surveys Copyright (C) 2015
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
 */
package org.encuestame.installer.izpack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.izforge.izpack.api.data.InstallData;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.izforge.izpack.api.installer.DataValidator;


/**
 * Validator
 * @author Morales, Diana  paola AT encuestame.org
 * @since  February, 27, 2015
 */
public class JDBCConnectionValidator implements DataValidator {
    private Log log = LogFactory.getLog(this.getClass());

    /** **/
    private String dbType;

    /** **/
    private String dbHostname;

    /** **/
    private String dbPort;

    /** **/
    private String dbName;

    /** **/
    private String dbUser;

    /** **/
    private String dbPassword; 

    private String urlType; 

    /**
     *  Validate Database JDBC Connection
     * @param argsData
     * @return
     */
    public Status validateData(InstallData argsData) {
		Status validationStatus = Status.ERROR;
        Connection connection = null;
        try {
            //1 - Retrieve arguments from UserInputPanel
            dbType = argsData.getVariable("db.type.selection");
            dbHostname = argsData.getVariable("db.hostname");
            dbPort = argsData.getVariable("db.port");
            dbName = argsData.getVariable("db.name");
            dbUser = argsData.getVariable("db.username");
            dbPassword = argsData.getVariable("db.password");
            urlType = argsData.getVariable("db.type.url.connection");
          
            log.debug("******************************************************************************************");
            log.debug("User=" + dbUser + " password=" + dbPassword + " port=" + dbPort
                    + " host=" + dbHostname + " database=" + dbName);
            log.debug("******************************************************************************************");
            //2- Register JDBC Driver classname
            registerJDBCDriver(dbType);
            try {
                String url = "jdbc:"+urlType+"://"+dbHostname+":"+dbPort+"/"+dbName +"?createDatabaseIfNotExist=true";
                //System.out.println("URL --------------->" +url);
                connection = DriverManager
                        .getConnection(url,dbUser, dbPassword);
                validationStatus = validationStatus.OK;

            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console");
                e.printStackTrace();
                return validationStatus.ERROR;
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
            return validationStatus.ERROR;
        }
        return validationStatus;
    }

    /**
     *
     * @throws Throwable
     */
    private void registerJDBCDriver(final String databasetype) throws Throwable {
        try {
            if(databasetype.equals("mysql")){
                Class.forName("com.mysql.jdbc.Driver");
            } else { 
                 Class.forName("org.postgresql.Driver");
            } 
        } catch (ClassNotFoundException e) {
            log.debug("Where is your MySQL JDBC Driver?");
            e.printStackTrace();
            return;
        }
    }


    @Override
    public String getErrorMessageId() {
        return "Can not connect to "+this.dbType +"  using given parameters! \n" +
                "Please verify that Database server is online and the parameters are correct.";
    }

    @Override
    public String getWarningMessageId() {
        return "Error connecting to MySQL Server. ";
    }

    @Override
    public boolean getDefaultAnswer() {
        return false;
    }
}