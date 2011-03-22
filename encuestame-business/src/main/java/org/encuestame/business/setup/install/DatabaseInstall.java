package org.encuestame.business.setup.install;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.persistence.dao.jdbc.InstallerOperations;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 19, 2011
 */
public class DatabaseInstall implements InstallDatabaseOperations {

    private static Log log = LogFactory.getLog(DatabaseInstall.class);

    private TypeDatabase databaseType = null;

    private final String SQLPATH = "/org/encuestame/business/sqlscripts/";

    private final String INDEX = "index.sql";
    private final String ALTER = "alter.sql";
    private final String TABLES = "tables.sql";

    @Autowired
    private InstallerOperations installerOperations;

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.business.setup.install.InstallDatabaseOperations#install()
     */
    public void install() {
        log.debug("install.............");
        final String tables = this.buildTableScript(this.TABLES);
        try {
            final String[] scripts = this.getScripts(tables);
            log.debug("scripts size ... " + scripts.length);
            for (int i = 0; i < scripts.length; i++) {
                final String script = scripts[i];
                log.debug("script to execute ... " + script);
                installerOperations.executeSql(script);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.fatal(e);
        }

    }

    /**
     *
     * @param scriptFilePath
     * @return
     * @throws IOException
     */
    private String[] getScripts(final String scriptFilePath) throws IOException {
        return SqlScriptParser.readScript(scriptFilePath);
    }

    /**
     * Build script path location.
     *
     * @param typeScript
     * @return
     */
    private String buildTableScript(final String typeScript) {
        final StringBuilder builder = new StringBuilder(this.SQLPATH);
        builder.append(this.databaseType.name().toLowerCase());
        builder.append("/install/");
        builder.append(typeScript);
        log.debug("Build sql script " + builder.toString());
        return builder.toString();
    }

    /**
     * @return the installerOperations
     */
    public InstallerOperations getInstallerOperations() {
        return installerOperations;
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
    public void installDatabase() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * upgradeDatabase(int)
     */
    public void upgradeDatabase(int version) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.encuestame.business.setup.install.InstallDatabaseOperations#checkDatabase
     * ()
     */
    public boolean checkDatabase() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * checkDatabaseVersion()
     */
    public int checkDatabaseVersion() {
        // TODO Auto-generated method stub
        return 0;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * checkRequiredDataExist()
     */
    public boolean checkRequiredDataExist() {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#
     * initializeDatabase()
     */
    public void initializeDatabase(final TypeDatabase installDatabase)
            throws EnmeFailOperation {
        log.debug("Check Database Conection..");
        // verify database connection.
        if (this.installerOperations.checkDatabaseConection() == 1) {
            if (this.installerOperations.checkIfDatabaseIsInstalled()) {
                //get current version of database.
                int currentVersion = this.checkDatabaseVersion();
                //if current version is smaller, we need upgrde
                if (getVersionDatabaseFromProperty() > currentVersion) {
                    //start database upgrade.
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
            throw new EnmeFailOperation(
                    "EnMe: No database conectionm check your properties");
        }
    }

    /**
     * Get database version stored on property file.
     *
     * @return
     */
    private int getVersionDatabaseFromProperty() {
        int version = 0;
        if (EncuestamePlaceHolderConfigurer
                .getIntegerProperty("encuestame.database.version") == null) {
            log.error("Encuestame Version Property is null, this is wrong !!");
        } else {
            version = EncuestamePlaceHolderConfigurer.getIntegerProperty(
                    "encuestame.database.version").intValue();
        }
        return version;
    }
}
