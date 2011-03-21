package org.encuestame.business.setup;

import org.apache.log4j.Logger;
import org.encuestame.business.config.EncuestamePlaceHolderConfigurer;
import org.encuestame.business.setup.install.InstallDatabaseOperations;
import org.encuestame.core.mail.MailService;
import org.encuestame.core.util.DateUtil;
import org.encuestame.core.util.InternetUtils;
import org.encuestame.persistence.exception.EnMeStartupException;
import org.encuestame.persistence.exception.EnmeFailOperation;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Manages encuestame application startup.
 *
 * @author Picado, Juan juanATencuestame.org
 * @since Mar 15, 2011
 */
public class ApplicationStartup implements StartupProcess {

    /** Log. **/
    protected Logger log = Logger.getLogger(this.getClass());

    /** Say is app is started. **/
    private static boolean started = false;

    /**
     * Install database operations.
     */
    @Autowired
    public InstallDatabaseOperations install;

    /**
     * Mail service provider.
     */
    @Autowired
    public MailService mailService;

    /**
     * Constructor.
     */
    public ApplicationStartup() {
    }

    /**
     * Check if app started.
     *
     * @return
     */
    public static boolean isStarted() {
        return started;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.encuestame.business.setup.StartupProcess#startProcess()
     */
    public void startProcess() throws EnMeStartupException {
        // check if root directory exist
        try {
            //verify directory structure.
            if (!DirectorySetupOperations.checkIfExistRootDirectory()) {
                log.info("EnMe: Root directory is missing");
                DirectorySetupOperations.createRootFolder();
            }
            //email start up notification config.
            boolean emailNotification = EncuestamePlaceHolderConfigurer
                    .getBooleanProperty("setup.email.notification")
                    .booleanValue();
            //if email notification is enabled.
            if (emailNotification) {
                final StringBuilder startupMessage = new StringBuilder();
                startupMessage.append("startup date [");
                startupMessage.append(DateUtil.getCurrentFormatedDate());
                startupMessage.append("]");
                // NOTE: add more information
                // version.
                // host information
                // etc etc.
                mailService.sendStartUpNotification(startupMessage.toString());
            }

            //check internet connection
            checkInternetConnection();

            //check database
            //if(install)
        } catch (EnmeFailOperation e) {
            log.fatal("Error on Start Up " + e.getMessage());
            throw new EnMeStartupException(e);
        }
    }

    /**
     * Check if exist internet connection.
     * Send pings to popular websites.
     */
    private void checkInternetConnection(){
        boolean internet = false;
            log.info("Checking your internet connection");
            boolean twitter = InternetUtils.pingTwitter();
            boolean facebook = InternetUtils.pingFacebook();
            boolean google = InternetUtils.pingGoogle();
            if(twitter || facebook || google){
                log.info("## -------------------------------------------- ##");
                log.info("## ---EnMe: Your internet connection is OK !!-- ##");
                log.info("## -------------------------------------------- ##");
            } else {
                log.info("## -------------------------------------------------------------- ##");
                log.info("## ---EnMe: Check your network, internet connection is missing -- ##");
                log.info("## -------------------------------------------------------------- ##");
            }
    }

    /**
     * @param install
     *            the install to set
     */
    public void setInstall(final InstallDatabaseOperations install) {
        this.install = install;
    }

    public boolean checkDatabase() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkDatabaseVersion() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean upgradeDatabase(int version) {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkStoreDirectyIfExist() {
        // TODO Auto-generated method stub
        return false;
    }

    public boolean checkRequiredDataExist() {
        // TODO Auto-generated method stub
        return false;
    }

    public void notifyStartupByEmail() {
        // TODO Auto-generated method stub

    }

    public void displayVersionOnStartup() {
        // TODO Auto-generated method stub

    }

    public void installDatabase() {
        // TODO Auto-generated method stub

    }

    /**
     * @param mailService
     *            the mailService to set
     */
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
