
package org.encuestame.business.setup.install;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.encuestame.persistence.dao.jdbc.InstallerOperations;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 19, 2011
 */
public class DatabaseInstall implements InstallDatabaseOperations{

    private static Log log = LogFactory.getLog(DatabaseInstall.class);

    private String databaseType = null;

    private final String SQLPATH = "/org/encuestame/business/sqlscripts/";

    private final String INDEX = "index.sql";
    private final String ALTER = "alter.sql";
    private final String TABLES = "tables.sql";

    @Autowired
    private InstallerOperations installerOperations;

    /*
     * (non-Javadoc)
     * @see org.encuestame.business.setup.install.InstallDatabaseOperations#install()
     */
    public void install(){
        log.debug("install.............");
        final String tables = this.buildTableScript(this.TABLES);
        try {
            final String[] scripts = this.getScripts(tables);
            log.debug("scripts size ... "+scripts.length);
            for (int i = 0; i < scripts.length; i++) {
                final String script = scripts[i];
                log.debug("script to execute ... "+script);
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
    private String[] getScripts(final String scriptFilePath) throws IOException{
        return SqlScriptParser.readScript(scriptFilePath);
    }

    /**
     *
     * @param typeScript
     * @return
     */
    private String buildTableScript(final String typeScript){
        final StringBuilder builder = new StringBuilder(this.SQLPATH);
        builder.append("postgres");
        builder.append("/install/");
        builder.append(typeScript);
        log.debug("Build sql script "+builder.toString());
        return builder.toString();
    }

    /**
     * @return the installerOperations
     */
    public InstallerOperations getInstallerOperations() {
        return installerOperations;
    }

    /**
     * @param installerOperations the installerOperations to set
     */
    public void setInstallerOperations(InstallerOperations installerOperations) {
        this.installerOperations = installerOperations;
    }

    public void upgrade(int version) {
        // TODO Auto-generated method stub

    }
}
