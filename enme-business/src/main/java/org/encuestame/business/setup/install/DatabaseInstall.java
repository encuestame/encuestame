package org.encuestame.business.setup.install;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.config.startup.EnMePlaceHolderConfigurer;
import org.encuestame.persistence.dao.jdbc.InstallerOperations;
import org.encuestame.util.exception.EnmeFailOperation;
import org.encuestame.utils.enums.TypeDatabase;

import java.io.IOException;

/**
 * Database install support.
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 19, 2011
 */
public class DatabaseInstall implements InstallDatabaseOperations {

    /**
     *  Log.
     */
    private static Log log = LogFactory.getLog(DatabaseInstall.class);

    /**
     * {@link TypeDatabase}.
     */
    private TypeDatabase databaseType = null;

    /**
     * The path of all sql scripts.
     * TODO: move to PathUtils?
     */
    private final String SQLPATH = "/org/encuestame/business/sqlscripts/";

    /**
     * Index file, contains INDEX SQL sequence.
     */
    private final String INDEX = "index.sql";

    /**
     * Alter file, contains ALTER SQL sequence.
     */
    private final String ALTER = "alter.sql";

    /**
     * Drop file, contains DROP SQL sequence.
     */
    private final String DROP = "drop.sql";

    /**
     * Tables file, contains CREATE TABLE SQL sequence.
     */
    private final String TABLES = "tables.sql";

    /**
     * Index file, contains required data.
     */
    private final String INSTALL = "install.sql";

    /**
     * Demo file, contain a list o executions to create demo data.
     */
    private final String DEMO = "demo.sql";

    /**
     * Script log, useful to display all script executed.
     */
    private StringBuffer scriptLog = new StringBuffer();

    /**
     * {@link InstallerOperations}.
     */
    private InstallerOperations installerOperations;

    /**
     *
     * @param scriptFilePath
     * @return
     * @throws IOException
     */
    private String[] getScripts(final String scriptFilePath) throws IOException {
        log.debug("scriptFilePath "+scriptFilePath);
        return SqlScriptParser.readScript(scriptFilePath);
    }

    /**
     * Build script path location.
     *
     * @param typeScript
     * @return
     */
    private String buildTableScript(final String typeScript) {
        log.debug("Database Type: "+this.databaseType.name());
        final StringBuilder builder = new StringBuilder(this.SQLPATH);
        builder.append(this.databaseType.name().toLowerCase());
        builder.append("/install/");
        builder.append(typeScript);
        log.debug("Build sql script: " + builder.toString());
        return builder.toString();
    }

    /**
     * @param installerOperations
     *            the installerOperations to set
     */
    public void setInstallerOperations(InstallerOperations installerOperations) {
        this.installerOperations = installerOperations;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * installDatabase()
     */
    public void installDatabase() throws IOException {
          this.scriptLog = new StringBuffer();
          // First step: Install Tables
          log.info("Creating tables...");
          this.installScript(this.buildTableScript(this.TABLES));
          // Second step: Install Alter,
          log.info("Creating alter table...");
          this.installScript(this.buildTableScript(this.ALTER));
          // Third step: Install Required Data.
          log.info("Creating table index...");
          this.installScript(this.buildTableScript(this.INDEX));
          // Fourth step: install required data.
          this.installScript(this.buildTableScript(this.INSTALL));
    }

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#installDemoData()
     */
    public void installDemoData() throws IOException{
        // Six step : demo data if is enabled.
        this.installScript(this.buildTableScript(this.DEMO));
    }

    /**
     *
     * @param sqlLocation
     * @throws IOException
     */
    private void installScript(final String sqlLocation) throws IOException{
        final String[] scripts = this.getScripts(sqlLocation);
        log.debug("scripts size ... " + scripts.length);
        for (int i = 0; i < scripts.length; i++) {
            final String script = scripts[i];
            log.debug("script to execute ... " + script);
            scriptLog.append(script);
            installerOperations.executeSql(script);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * upgradeDatabase(int)
     */
    public void upgradeDatabase(int version) {
        //TODO: ENCUESTAME-134
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.business.setup.install.InstallDatabaseOperations#checkDatabase
     * ()
     */
    public boolean checkDatabase() {
        return this.installerOperations.checkIfDatabaseIsInstalled();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * checkDatabaseVersion()
     */
    public int checkDatabaseVersion() {
        //TODO: ENCUESTAME-135
        return 1;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * checkRequiredDataExist()
     */
    public boolean checkRequiredDataExist() {
        //FUTURE: how to check if the require data exist?
        return false;
    }

    /**
     *
     * @return
     */
    private String getTypeDatabase() {
        final String typeDatabase = EnMePlaceHolderConfigurer
                .getProperty("datasource.database");
        return typeDatabase;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * initializeDatabase()
     */
    @SuppressWarnings("unused")
    public void initializeDatabase(final TypeDatabase installDatabase)
            throws EnmeFailOperation, IOException {
        //TODO: remove parameters.
        setDatabaseType(TypeDatabase.getTypeDatabaseByString(this.getTypeDatabase()));
        log.debug("check Database conection..");
        // verify database connection.
        //if (this.installerOperations.checkDatabaseConection() == 1) {
        if (true) { //TODO: temp, comment bellow.
            if (this.checkDatabase()) {
                //get current version of database.
                log.debug("Database is installed ... checking version");
                int currentVersion = this.checkDatabaseVersion();
                //if current version is smaller, we need upgrde
                if (getVersionDatabaseFromProperty() > currentVersion) {
                    //start database upgrade.
                    log.info("EnMe : Upgrading database...");
                    this.upgradeDatabase(getVersionDatabaseFromProperty());
                   // if not, should be equals. Never less.
                } else {
                    log.info("EnMe : Database Already up-to-date");
                }
            } else {
                this.installDatabase();
            }
        } else {
            log.fatal("EnMe: No database conectionm check your properties");
            throw new EnmeFailOperation("EnMe: No database conectionm check your properties");
        }
    }

    /**
     * Get database version stored on property file.
     *
     * @return
     * @throws EnmeFailOperation
     */
    private int getVersionDatabaseFromProperty() throws EnmeFailOperation {
        final Integer ver = EnMePlaceHolderConfigurer.getConfigurationManager().getDatabaseVersion();
        if (ver == null) {
            log.fatal("database version is missing.");
            throw new EnmeFailOperation("database version is missing");
        }
        return ver;
    }

    /**
     * @param databaseType the databaseType to set
     */
    public void setDatabaseType(TypeDatabase databaseType) {
        this.databaseType = databaseType;
    }

    /**
     * @return the scriptLog
     */
    public String getScriptLog() {
        log.debug("SQL "+scriptLog.toString().length());
        return scriptLog.toString();
    }

    /**
     * @param scriptLog the scriptLog to set
     */
    public void setScriptLog(StringBuffer scriptLog) {
        this.scriptLog = scriptLog;
    }

    /**
     *
     */
    @Override
    public void dropAll() throws IOException {
        log.info("Drop all tables");
        setDatabaseType(TypeDatabase.getTypeDatabaseByString(this.getTypeDatabase()));
        this.installScript(this.buildTableScript(this.DROP));
    }
}
